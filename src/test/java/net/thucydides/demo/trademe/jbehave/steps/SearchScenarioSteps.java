package net.thucydides.demo.trademe.jbehave.steps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import net.thucydides.demo.trademe.pages.ResultsPage;
import net.thucydides.demo.trademe.pages.SearchPage;

import static org.fest.assertions.Assertions.assertThat;

public class SearchScenarioSteps {

    SearchPage searchPage;
    ResultsPage resultsPage;

    @Given("I want to purchase a $keyword")
    @Alias("I want to find an item in a particular category")
    public void givenIAmOnTheTrademeHomePage() {
        searchPage.open();
    }


    @When("I search for listings relating to '$keywords'")
    public void searchForListingsRelatingTo(String keywords) {
        resultsPage = searchPage.searchFor(keywords);
    }

    @When("I search for listings relating to <keyword> in the <category> category")
    public void searchByCategory(String keyword, String category) {
        resultsPage = searchPage.selectsCategory(category).searchFor(keyword);
    }


    @Then("I should only see listings with '$keyword' in the title")
    public void shouldOnlySeeListingsWithATitleContaining(String keyword) {
        resultsPage.shouldDisplayTitlesWith(keyword);
    }

    @Then("I should see a listing with '<expected_title>' in the title")
    public void shouldSeeListingsWithATitleContaining(@Named("expected_title")String expectedTitle) {
        assertThat(resultsPage.getAdTitles()).contains(expectedTitle);
    }
}
