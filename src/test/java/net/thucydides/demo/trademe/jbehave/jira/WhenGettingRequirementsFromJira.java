package net.thucydides.demo.trademe.jbehave.jira;

import net.thucydides.core.requirements.model.Requirement;
import net.thucydides.demo.trademe.jira.JIRARequirementsProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * A description goes here.
 * User: john
 * Date: 4/03/13
 * Time: 8:25 PM
 */
public class WhenGettingRequirementsFromJira {

    @Before
    public void setunEnvironment() {
        System.setProperty("jira.url", "http://wakaleo.atlassian.com");
        System.setProperty("jira.username", "builder");
        System.setProperty("jira.password", "br1cks&mortar");
        System.setProperty("jira.project","TRAD");
    }

    @Test
    @Ignore
    public void shouldReadEpicsAsTopLevelRequirements() {
        JIRARequirementsProvider requirementsProvider = new JIRARequirementsProvider();

        List<Requirement> requirements = requirementsProvider.getRequirements();
        assertThat(requirements).isNotEmpty();

    }


}
