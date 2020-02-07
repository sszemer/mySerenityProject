package org.example.steps.serenity;

import config.SystemConfigProvider;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.specification.ResponseSpecification;
import net.thucydides.core.annotations.Step;
import org.example.pojos.Nbp;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.config.FailureConfig.failureConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class RESTSteps {

    public RESTSteps() {
//        jesli mock jest włączony to host inaczej mock + address
        switch (SystemConfigProvider.getProperty("mockserver.skip")) {
            case "true":
                address = host + api;
                break;
            case "false":
                address = mock + api;
                break;
            default:
                address = host + api;
                break;
        }
    }

    String api = "/api/exchangerates/tables/A/?format=json";
    String address;
    String host = "http://api.nbp.pl";
    String mock = "http://"+SystemConfigProvider.getProperty("mockserver.host")+":"+SystemConfigProvider.getProperty("mockserver.port")+"";
    String schema = "REST/schemas/nbp.json";
    BigDecimal rate;
    String currency;
    ResponseValidationFailureListener emailOnFailure = (reqSpec, respSpec, resp) -> System.err.println("Important test failed! Status code was: " + resp.statusCode());

    @Step
    public void rateIs(BigDecimal rate) {
    }

    @Step
    public BigDecimal getCurrencyRateFor(String currency) {
        this.currency = currency;
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectResponseTime(lessThan(1L), TimeUnit.SECONDS);
        ResponseSpecification responseSpec = builder.build();

        given()
                .config(RestAssured.config().failureConfig(failureConfig().with().failureListeners(emailOnFailure)))
                .get(address)
                .then()
                .spec(responseSpec)
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath(schema));
        rate = given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .and()
                .get(address)
                .then()
                .spec(responseSpec)
                .log().ifValidationFails()
                .extract()
                .jsonPath().get("[0].rates.find {it.code='" + currency + "'}.mid");
//        deserializacja
        Nbp[] response;
        response = get(address).body().as(Nbp[].class);
        Objects.requireNonNull(response[0].getRates().stream().filter(rate1 -> rate1.getCode().equals(currency)).findAny().orElse(null)).getMid();
//        deserializaca generyczna
        List<Map<String, Object>> genericResponse = get(address).as(new TypeRef<List<Map<String, Object>>>() {
        });
        Assert.assertThat(genericResponse.get(0).get("table"), equalTo("A"));
        return rate;
    }

    @Step
    public void rateIsLowerThan(BigDecimal rateThreshold) {
        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .when()
                .get(address)
                .then()
                .log().ifError()
                .body("[0].rates.find {it.code='" + currency + "'}.mid", lessThan(rateThreshold))
                .time(lessThan(1L), TimeUnit.SECONDS); // to nie jest najwiarygodniejsze
        Assert.assertThat(rate, lessThan(rateThreshold));
    }

    @Step
    public void goOnVacation() {
        System.err.println("YAY!");
    }
}