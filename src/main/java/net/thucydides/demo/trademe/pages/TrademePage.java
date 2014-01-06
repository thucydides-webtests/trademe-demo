package net.thucydides.demo.trademe.pages;

import ch.lambdaj.function.convert.Converter;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import net.thucydides.demo.trademe.model.Deal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

import static ch.lambdaj.Lambda.convert;

@DefaultUrl("http://www.trademe.co.nz")
public class TrademePage extends PageObject {

    @FindBy(id = "LoginLink")
    WebElementFacade login;

    @FindBy(id = "viewWatchlistDropDownLink")
    WebElementFacade watchlistDropdownLink;

    public TrademePage(WebDriver driver) {
        super(driver);
    }

    public void login() {
        login.click();
        waitFor("#LoginModalDiv");
    }

    public void showWatchlist() {
        $("#watchlist-toggle > a").click();
        watchlistDropdownLink.waitUntilVisible();

    }

    public LoginBuilder inLoginDialog() {
        return new LoginBuilder(getDriver());
    }

    public List<String> getWatchlistTitles() {
        List<WebElementFacade> watchlistTitles = findAll("#watchlist-toggle-extension-line #GenearlCardTitle");
        return convert(watchlistTitles, toText());
    }

    private Converter<WebElementFacade,String> toText() {
        return new Converter<WebElementFacade, String>() {
            @Override
            public String convert(WebElementFacade from) {
                return from.getText();
            }
        };
    }

    public class LoginBuilder extends PageObject {

        public LoginBuilder(WebDriver driver) {
            super(driver);
        }

        public void setEmail(String email) {
            $("#modalEmail").type(email);
        }

        public void setPassword(String password) {
            $("#modalPassword").type(password);
        }

        public void login() {
            $("#LoginModalDiv button[type='submit']").click();
        }
    }
}
