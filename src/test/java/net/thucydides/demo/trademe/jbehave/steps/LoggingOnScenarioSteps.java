package net.thucydides.demo.trademe.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import net.thucydides.demo.trademe.model.User;
import net.thucydides.demo.trademe.pages.SearchPage;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class LoggingOnScenarioSteps {


    User currentUser;

    @Steps
    LoginSteps login;

    @Given("$user has a valid user account")
    public void userHasAValidAccount(User user) {
        this.currentUser = user;
    }


    @When("he logs on to My Trademe")
    public void userLogsIn() {
        login.as(currentUser);
    }

    @Then("he should see a welcome message with his name")
    public void userShouldSeeWelcomeMessage() {
        login.shouldSeeWelcomeMessage("Hi " + currentUser.firstName + "!");
    }
}
