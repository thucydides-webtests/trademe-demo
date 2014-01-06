package net.thucydides.demo.trademe.pages;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;


public class ResultsPage extends TrademePage {

    @FindBy(css = ".listingTitle")
    List<WebElement> titleElements;

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAdTitles() {
        List<String> titles = new ArrayList<String>();
        for(WebElement titleElement : titleElements) {
            titles.add(titleElement.getText());
        }
        return titles;
    }

    public void shouldDisplayTitlesWith(String... searchTerms) {
        List<String> adTitles = getAdTitles();
        assertThat(adTitles).isNotEmpty();
        for(String title : adTitles) {
            for(String expectedSearchTerm : searchTerms) {
                assertThat(title).containsIgnoringCase(expectedSearchTerm);
            }
        }
    }
}
