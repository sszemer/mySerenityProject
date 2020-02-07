package org.example;

import config.MockServerConfigProvider;
import net.serenitybdd.jbehave.SerenityStories;
import net.serenitybdd.jbehave.annotations.*;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

@Metafilter("+nbp")
public class AcceptanceTestSuite extends SerenityStories {
//clean verify serenity:aggregate properties:write-project-properties -Dmaven.test.failure.ignore=true -DforkCount=0 test -Dmockserver.skip=true
    @BeforeStories
    public void beforeStories(){
        System.err.println("before stories");
        MockServerConfigProvider.emulateNbp();
    }

    @AfterStories
    public void afterStories(){
        System.err.println("after stories");
    }

}