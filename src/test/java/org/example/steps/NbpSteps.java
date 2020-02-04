package org.example.steps;

import net.thucydides.core.annotations.Steps;
import org.example.steps.serenity.RESTSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.math.BigDecimal;

public class NbpSteps {

    @Steps
    RESTSteps restSteps;

    @Given("We know the current rate for $currencyCode")
    public void getCurrencyRateFor(String currencyCode){
        restSteps.getCurrencyRateFor(currencyCode);
    }

    @When("The rate is less than $lessThan")
    public void isRateLessThan(BigDecimal lessThan){
        restSteps.rateIsLowerThan(lessThan);
    }

    @Then("We can go on vacation")
    public void goOnVacation(){
        restSteps.goOnVacation();
    }

}
