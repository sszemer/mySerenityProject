package org.example.steps.serenity;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.mapper.ObjectMapper;
import net.thucydides.core.annotations.Step;
import org.example.pojos.Nbp;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class RESTSteps {

    String address = "http://api.nbp.pl/api/exchangerates/tables/A/?format=json";
    String schema = "REST/schemas/nbp.json";
    BigDecimal rate;
    Nbp[] response;

    @Step
    public void getCurrencyRateFor(String currency) {
        given().get(address).then().assertThat().body(matchesJsonSchemaInClasspath(schema));
        rate = given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .and().get(address)
                .then().statusCode(200).extract().jsonPath().get("[0].rates.find {it.code='" + currency + "'}.mid");
//        deserializacja
        response = get(address).body().as(Nbp[].class);
        System.err.println(response[0].getTable());
//        deserializaca generyczna
        List<Map<String,Object>> genericResponse = get(address).as(new TypeRef<List<Map<String, Object>>>() {});
        Assert.assertThat(genericResponse.get(0).get("table"), equalTo("A"));
    }

    @Step
    public void rateIsLowerThan(BigDecimal rateThreshold) {
        given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .when().get(address).then().body("[0].rates.find {it.code='" + rateThreshold + "'}.mid", lessThan(rateThreshold));
        Assert.assertThat(rate, lessThan(rateThreshold));
    }

    @Step
    public void goOnVacation() {
        System.err.println("YAY!");
    }
}