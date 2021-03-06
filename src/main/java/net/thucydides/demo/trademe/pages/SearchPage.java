package net.thucydides.demo.trademe.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import net.thucydides.demo.trademe.model.Deal;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@DefaultUrl("http://www.trademe.co.nz")
public class SearchPage extends TrademePage {

    @FindBy(id = "searchString")
    WebElement searchField;

    @FindBy(css = "button[value='Search']")
    WebElement searchButton;

    @FindBy(id = "main-box-categories")
    WebElement categoriesList;

    @FindBy(linkText = "Register")
    WebElement registerLink;

    @FindBys({@FindBy(className = "site-header-links"), @FindBy(linkText = "Community")})
    WebElement communityMenuEntry;

    SidebarFeatureComponent treatMeDeals;
    SidebarFeatureComponent dailyDeals;

    public SearchPage(WebDriver driver) {
        super(driver);

        treatMeDeals = new SidebarFeatureComponent(driver,"treatMe_dailyDealsDiv");
        dailyDeals = new SidebarFeatureComponent(driver,"dailyDeals_dailyDealsDiv");
    }

    public ResultsPage searchFor(String searchTerms) {
        searchField.sendKeys(searchTerms);
        searchButton.click();
        return switchToPage(ResultsPage.class);
    }

    public SearchPage
    selectsCategory(String category) {
        categoriesList.findElement(By.linkText(category)).click();
        return this;
    }

    public void register() {
        registerLink.click();
    }

    public List<Deal> getDailyDeals() {
        return dailyDeals.getDeals();
    }

    public List<Deal> getTreatMeDeals() {
        getDriver().findElement(By.name("clientList")).click();
        return treatMeDeals.getDeals();
    }

    public Boolean dailyDealsDoesNotHaveDuplicates() {
        return null;
    }

    public void selectFeaturedItem(int itemNumber) {
        findAll(".listing-content").get(itemNumber - 1).then().click();
    }
}
