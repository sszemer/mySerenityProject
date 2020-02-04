package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class RESTSteps {

    String address = "http://api.nbp.pl/api/exchangerates/tables/A/?format=json";
    String schema = "REST/schemas/nbp.json";

    @Step
    public void checkThaiCurrency(String thb) {
        given().get(address).then().assertThat().body(matchesJsonSchemaInClasspath(schema));
        when().get(address).then().body("[0].rates[0].code", equalTo(thb));
    }

}