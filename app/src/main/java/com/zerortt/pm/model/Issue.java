package com.zerortt.pm.model;

import java.util.Vector;

public class Issue implements Identity {
    public static final String KEY = "id";
    public String id;
    public IssueType type = IssueType.TASK;
    public IssueStatus status = IssueStatus.READY;
    public String name;
    public String assigneeId;
    public String reporterId;
    public String dueDate = "";
    public String closeDate = "";
    //public Set<IssueLink> issueDependencies;

    public Vector<IdentityData> projects;
    public Vector<IdentityData> programs;

    public Issue() {
        this.id = IdentityData.createRandomId();
        init();
    }

    public Issue(String id) {
        this.id = id;
        init();
    }
    private void init() {
        programs = new Vector<>();
        projects = new Vector<>();
    }

    @Override
    public String key() {
        return KEY;
    }

    @Override
    public String value() {
        return id;
    }

    @Override
    public IdentityData identityData() {
        return new IdentityData(kind(), key(), value());
    }
}
