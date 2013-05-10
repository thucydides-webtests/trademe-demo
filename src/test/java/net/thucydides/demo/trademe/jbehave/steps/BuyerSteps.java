package net.thucydides.demo.trademe.jbehave.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.demo.trademe.pages.SearchPage;

import static org.fest.assertions.Assertions.assertThat;

public class BuyerSteps extends ScenarioSteps {

    SearchPage searchPage;

    public BuyerSteps(Pages pages) {
        super(pages);
    }


    @Step
    public void navigatesToSearchPage() {
        searchPage.open();
    }

    @Step("should see {0} daily deals")
    public void should_see_daily_deal_count(int dailyDealCount) {
        assertThat(searchPage.getDailyDeals()).hasSize(dailyDealCount);
    }

    @Step
    public void should_see_no_duplicate_daily_deals() {
        assertThat(searchPage.getDailyDeals()).doesNotHaveDuplicates();
    }
}
