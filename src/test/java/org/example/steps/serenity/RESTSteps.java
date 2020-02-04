package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RESTSteps {

    String address = "http://api.nbp.pl/api/exchangerates/tables/A/?format=json";

    @Step
    public void checkThaiCurrency(String thb) {
        when().get(address).then().body("[0].rates[0].code", equalTo(thb));
    }

}
