package net.thucydides.demo.trademe.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import net.thucydides.demo.trademe.model.User;
import net.thucydides.demo.trademe.pages.DetailsPage;
import net.thucydides.demo.trademe.pages.ResultsPage;
import net.thucydides.demo.trademe.pages.SearchPage;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class WatchlistScenarioSteps {

    @Steps
    LoginSteps login;

    DetailsPage detailsPage;
    SearchPage searchPage;

    @Given("I am logged on as $user")
    public void givenIAmLoggedOnAs(User user) {
        login.as(user);
    }

    String chosenListing;

    @Given("I view an item")
    public void viewAnItem() {
        searchPage.selectFeaturedItem(1);
        chosenListing = detailsPage.getListingTitle();
    }

    @When("I save it to my watchlist")
    public void addCurrentItemToWatchlist() {
        detailsPage.saveToWatchlist();
    }

    @Then("it should appear in my watchlist")
    public void shouldSeeItemInWatchlist() {
        detailsPage.showWatchlist();
        List<String> titles = detailsPage.getWatchlistTitles();
        assertThat(titles).contains(chosenListing);
    }

}
