package org.example.steps.serenity;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.thucydides.core.annotations.Step;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class RESTSteps {

    String address = "http://api.nbp.pl/api/exchangerates/tables/A/?format=json";
    String schema = "REST/schemas/nbp.json";
    String rate = null;

    @Step
    public void checkCurrencyRateFor(String thb) {
        given().get(address).then().assertThat().body(matchesJsonSchemaInClasspath(schema));

        when().get(address).then().body("[0].rates.find {it.code='"+thb+"'}.mid", lessThan(0.13));

        rate = RestAssured.given().get(address).then().statusCode(200).extract().jsonPath().get("[0].rates.find {it.code='"+thb+"'}.mid").toString();

    }

}