package net.thucydides.demo.trademe.jira;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestTag;
import net.thucydides.core.requirements.RequirementsTagProvider;
import net.thucydides.core.requirements.model.Requirement;
import net.thucydides.plugins.jira.client.SOAPSession;
import net.thucydides.plugins.jira.service.JIRAConfiguration;
import net.thucydides.plugins.jira.service.JIRAConnection;
import net.thucydides.plugins.jira.service.SystemPropertiesJIRAConfiguration;
import org.apache.commons.lang3.StringUtils;
import thucydides.plugins.jira.soap.RemoteIssue;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Integrate Thucydides reports with requirements, epics and stories in the Barclays JIRA server.
 */
public class JIRARequirementsProvider implements RequirementsTagProvider {

    private List<Requirement> requirements = null;
    private final JIRAConnection jiraConnection;
    private final TypeMap typeMap;

    public JIRARequirementsProvider() {
        this(new SystemPropertiesJIRAConfiguration());
    }

    public JIRARequirementsProvider(JIRAConfiguration jiraConfiguration) {
        this.jiraConnection = new JIRAConnection(jiraConfiguration);
        this.typeMap = new TypeMap(jiraConnection);
    }

    private String getProjectKey() {
        return System.getProperty("jira.project");
    }

    private String getPassword() {
        return System.getProperty("jira.password");
    }

    private String getUsername() {
        return System.getProperty("jira.username");
    }

    @Override
    public List<Requirement> getRequirements() {

        if (requirements == null) {
            requirements = Lists.newArrayList();
            try {

                SOAPSession session = SOAPSession.openConnectionTo(new URL("https://wakaleo.atlassian.net/rpc/soap/jirasoapservice-v2"))
                                                 .usingCredentials(getUsername(), getPassword());

                String token = session.getAuthenticationToken();

                //String token = jiraConnection.getAuthenticationToken();
                RemoteIssue[] issues = session.getJiraSoapService().getIssuesFromJqlSearch(token, "issuetype = epic and project=" + getProjectKey(), 100);
                for (RemoteIssue issue : issues) {
                    Requirement requirement = requirementFrom(issue);
                    List<Requirement> stories = findStoriesFor(requirement);
                    requirements.add(requirement.withChildren(stories));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to read the requirements from JIRA", e);
            }
        }
        return requirements;
    }

    private List<Requirement> findStoriesFor(Requirement parent) throws MalformedURLException {
        List<Requirement> requirements = Lists.newArrayList();

        try {
            String token = jiraConnection.getAuthenticationToken();

            RemoteIssue[] issues = jiraConnection.getJiraSoapService()
                    .getIssuesFromJqlSearch(token,
                            "issue in linkedIssues('" + parent.getCardNumber() + "','Links to') and type = Story", 100);

            for (RemoteIssue issue : issues) {
                Requirement story = requirementFrom(issue);
                requirements.add(story);
            }
        } catch (RemoteException e) {
            throw new IllegalArgumentException("Failed to read the requirements from JIRA", e);
        }
        return requirements;
    }

    private Requirement requirementFrom(RemoteIssue issue) {

        String issueTypeName = typeMap.getTypeNameFor(issue.getType());

        return Requirement.named(issue.getSummary())
                .withOptionalCardNumber(issue.getKey())
                .withType(issueTypeName)
                .withNarrativeText(issue.getDescription());
    }

    @Override
    public Optional<Requirement> getParentRequirementOf(TestOutcome testOutcome) {
        List<String> issueKeys = testOutcome.getIssueKeys();
        try {
            String token = jiraConnection.getAuthenticationToken();
            if (!issueKeys.isEmpty()) {
                RemoteIssue[] storyIssues = jiraConnection.getJiraSoapService().getIssuesFromJqlSearch(token,"key=" + issueKeys.get(0), 1);
                RemoteIssue storyIssue = storyIssues[0];
                return Optional.of(requirementFrom(storyIssue));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read parent requirement", e);
        }
        return Optional.absent();
    }

    @Override
    public Optional<Requirement> getRequirementFor(TestTag testTag) {
        for (Requirement requirement : getRequirements()) {
            if (requirement.getType().equals(testTag.getType()) && requirement.getName().equals(testTag.getName())) {
                return Optional.of(requirement);
            }
        }
        return Optional.absent();
    }

    @Override
    public Set<TestTag> getTagsFor(TestOutcome testOutcome) {
        List<String> issues  = testOutcome.getIssueKeys();
        Set<TestTag> tags = Sets.newHashSet();
        for(String issue : issues) {
            tags.addAll(tagsFromIssue(issue));
        }
        return ImmutableSet.copyOf(tags);
    }

    private Collection<? extends TestTag> tagsFromIssue(String issueKey) {

        System.out.println("Reading tags from issue " + issueKey);
        List<TestTag> tags = Lists.newArrayList();
        try {
            String token = jiraConnection.getAuthenticationToken();
            String decodedIssueKey = decoded(issueKey);
            RemoteIssue[] behaviourIssues = jiraConnection.getJiraSoapService().getIssuesFromJqlSearch(token, "key=" + decodedIssueKey, 1);
            RemoteIssue behaviourIssue = (behaviourIssues.length > 0) ? behaviourIssues[0] : null;

            RemoteIssue[] parentIssues = jiraConnection.getJiraSoapService().getIssuesFromJqlSearch(token,"issue in subtask('key=" + decodedIssueKey + "')", 1);
            RemoteIssue parentIssue = (parentIssues.length > 0) ? parentIssues[0] : null;

            if (behaviourIssue != null) {
                TestTag behaviorTag = TestTag.withName(behaviourIssue.getSummary()).andType("story");
                tags.add(behaviorTag);
            }

            if (parentIssue != null) {
                TestTag featureTag = TestTag.withName(parentIssue.getSummary()).andType("feature");
                tags.add(featureTag);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read parent requirement", e);
        }
        return tags;
    }

    private String decoded(String issueKey) {
        if (issueKey.startsWith("#")) {
            issueKey = issueKey.substring(1);
        }
        if (StringUtils.isNumeric(issueKey)) {
            issueKey = getProjectKey() + "-" + issueKey;
        }
        return issueKey;
    }
}
