package net.thucydides.demo.trademe.jbehave;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
public class Fixtures {

    @BeforeScenario
    public void doStuffBeforeTheScenario() {
        System.out.println("Before scenario");
    }

    @BeforeStory
    public void doStuffBeforeTheStory() {
        System.out.println("Before story");
    }
}
