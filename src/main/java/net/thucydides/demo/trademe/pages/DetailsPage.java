package net.thucydides.demo.trademe.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("http://www.trademe.co.nz")
public class DetailsPage extends TrademePage {

    @FindBy(css = ".SaveToWatchlistButton")
    WebElementFacade saveToWatchlist;

    @FindBy(id="ListingTitle_title")
    WebElementFacade listingTitle;

    public DetailsPage(WebDriver driver) {
        super(driver);
    }

    public void saveToWatchlist() {
        saveToWatchlist.click();
    }

    public String getListingTitle() {
        return listingTitle.getText();
    }
}
