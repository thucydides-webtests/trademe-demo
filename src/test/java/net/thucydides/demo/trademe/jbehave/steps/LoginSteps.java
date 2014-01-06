package net.thucydides.demo.trademe.jbehave.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.demo.trademe.model.User;
import net.thucydides.demo.trademe.pages.SearchPage;
import net.thucydides.demo.trademe.pages.TrademePage;

public class LoginSteps extends ScenarioSteps {
    public LoginSteps(Pages pages) {
        super(pages);
    }

    TrademePage trademePage;

    @Step("Login as {0}")
    public void as(User user) {
        trademePage.open();
        chooseToLogin();
        loginWithEmailAndPassword(user.email, user.password);
    }

    @Step
    public void loginWithEmailAndPassword(String email, String password) {
        trademePage.inLoginDialog().setEmail(email);
        trademePage.inLoginDialog().setPassword(password);
        trademePage.inLoginDialog().login();
    }

    @Step
    public void chooseToLogin() {
        trademePage.login();
    }

    public void shouldSeeWelcomeMessage(String expectedMessage) {
        trademePage.shouldContainText(expectedMessage);
    }
}
