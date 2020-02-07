package org.example;

import net.serenitybdd.jbehave.SerenityStories;
import net.serenitybdd.jbehave.annotations.Metafilter;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

@Metafilter("+nbp")
public class AcceptanceTestSuite extends SerenityStories {
//clean verify serenity:aggregate properties:write-project-properties -Dmaven.test.failure.ignore=true -Dmockserver.skip=false -DforkCount=0 test -Dmockserver.initializationJsonPath=src/test/resources/REST/mockserver/initialize.json

    @BeforeStories
    public void beforeStories(){
        System.err.println("before stories");
    }

    @AfterStories
    public void afterStories(){
        System.err.println("after stories");
    }

}