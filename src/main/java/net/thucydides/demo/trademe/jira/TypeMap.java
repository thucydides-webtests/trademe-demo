package net.thucydides.demo.trademe.jira;

import com.google.common.collect.Maps;
import net.thucydides.plugins.jira.service.JIRAConnection;
import thucydides.plugins.jira.soap.RemoteIssueType;

import java.util.Map;

public class TypeMap {
    private final JIRAConnection jiraConnection;
    private Map<String, String> typeMap = null;

    public TypeMap(JIRAConnection jiraConnection) {
        this.jiraConnection = jiraConnection;
    }

    public String getTypeNameFor(String issueType) {
        return getMap().get(issueType);
    }

    private Map<String, String> getMap() {
        if (typeMap == null) {
            loadTypeMap();
        }
        return typeMap;
    }

    private void loadTypeMap() {
        typeMap = Maps.newHashMap();
        try {
            RemoteIssueType[] issueTypes = jiraConnection.getJiraSoapService().getIssueTypes(jiraConnection.getAuthenticationToken());
            for (RemoteIssueType issueType : issueTypes) {
                typeMap.put(issueType.getId(), issueType.getName().toLowerCase());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read the requirements from JIRA", e);
        }
    }
}