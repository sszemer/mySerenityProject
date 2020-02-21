package org.example;

import net.serenitybdd.jbehave.SerenityStories;
import net.serenitybdd.jbehave.annotations.Metafilter;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.reports.OutcomeFormat;
import net.thucydides.core.reports.TestOutcomeLoader;
import net.thucydides.core.reports.TestOutcomes;
import net.thucydides.core.steps.StepEventBus;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import java.io.File;
import java.io.IOException;

//@Metafilter("+nbp")
public class AcceptanceTestSuite extends SerenityStories {
//clean verify serenity:aggregate properties:write-project-properties -Dmaven.test.failure.ignore=true -Dmockserver.skip=false -DforkCount=0 test -Dmockserver.initializationJsonPath=src/test/resources/REST/mockserver/initialize.json

    @BeforeStories
    public void beforeStories(){
        System.err.println("BEFORE STORIES");
    }

    @AfterStories
    public void afterStories(){
        System.err.println("AFTER STORIES");
    }

    @AfterScenario
    public void afterScenario(){
        System.err.println("AFTER SCENARIO");
        TestOutcome lastOutcome = getLastOutcome();
        System.err.println("--------------------------------------");
        System.err.println(lastOutcome.getName());
        System.err.println(lastOutcome.getResult());
        System.err.println(lastOutcome.getErrorMessage());
        System.err.println(lastOutcome.getTagValue("id"));
        System.err.println("--------------------------------------");

    }

    private TestOutcome getLastOutcome() {
        int lastIndex = StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes().size() - 1;
        return StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes().get(lastIndex);
    }

}