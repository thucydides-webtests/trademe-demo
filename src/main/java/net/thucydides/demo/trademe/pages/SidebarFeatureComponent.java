package net.thucydides.demo.trademe.pages;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import net.thucydides.demo.trademe.model.Deal;

import java.util.List;

public class SidebarFeatureComponent {

    private final WebDriver driver;
    private final String id;

    public SidebarFeatureComponent(WebDriver driver, String id) {
        this.driver = driver;
        this.id = id;
    }

    public List<Deal> getDeals() {
        List<WebElement> dealElements = driver.findElement(By.id(id))
                                              .findElements(By.name("daily-deals-card"));

        List<Deal> deals = Lists.newArrayList();
        for(WebElement dealElement : dealElements) {
            deals.add(convertToDeal(dealElement));
        }
        return deals;
    }

    private Deal convertToDeal(WebElement from) {
        return new Deal(readField(from, "title"),
                        readField(from, "price"),
                        readField(from, "details"));
    }

    private String readField(WebElement element, String fieldname) {
        return element.findElement(By.className(fieldname)).getText();
    }
}


