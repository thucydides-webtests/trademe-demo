package net.thucydides.demo.trademe.jbehave.steps;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import net.thucydides.demo.trademe.pages.SearchPage;

import static org.fest.assertions.Assertions.assertThat;

public class DailyDealsSteps {

    @Steps
    BuyerSteps buyer;

    @When("I view the daily deals")
    public void viewTheTradeMeHomePage() {
        buyer.navigatesToSearchPage();
    }

    @Then("I should see $dailyDealCount daily deals")
    public void shouldSeeNDailyDeals(int dailyDealCount) {
        buyer.should_see_daily_deal_count(dailyDealCount);
    }

    @Then("I should see no duplicated deals")
    public void shouldSeeNoDuplicates() {
        buyer.should_see_no_duplicate_daily_deals();
    }

}
