package org.example.steps.serenity;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import net.thucydides.core.annotations.Step;
import org.example.pojos.Nbp;
import org.junit.Assert;

import java.math.BigDecimal;

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

        response = get(address).body().as(Nbp[].class);
        System.err.println(response[0].getTable());
    }

    @Step
    public void rateIsLowerThan(BigDecimal rateThreshold) {
//        when().get(address).then().body("[0].rates.find {it.code='" + rateThreshold + "'}.mid", lessThan(0.13));
        Assert.assertThat(rate, lessThan(rateThreshold));
    }

    @Step
    public void goOnVacation() {
        System.err.println("YAY!");
    }
}