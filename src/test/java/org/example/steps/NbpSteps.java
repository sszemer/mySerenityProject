package org.example.steps;

import net.thucydides.core.annotations.Steps;
import org.example.steps.serenity.EndUserSteps;
import org.example.steps.serenity.RESTSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class NbpSteps {

    @Steps
    RESTSteps restSteps;

    @Given("We know the current rate for $currencyCode")
    public void getCurrencyRateFor(String currencyCode){
        restSteps.checkCurrencyRateFor(currencyCode);
    }

    @When("The rate is less than $lessThan")
    public void isRateLessThan(String lessThan){

    }

    @Then("We can go on vacation")
    public void goOnVacation(String lessThan){

    }

}
