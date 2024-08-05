
package com.tau.steps;

import io.cucumber.core.cli.Main;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)
@CucumberOptions(
	    glue = "com.tau.steps",
	    features = "src/test/resources",
	    plugin = { "pretty", "html:target/site/cucumber-pretty", "json:target/cucumber.json" }
	)

public class TestRunner {
	 public static void main(String[] args) {
	        Main.main(args);  // Use io.cucumber.core.cli.Main instead of the deprecated cucumber.api.cli.Main
	    }

}
