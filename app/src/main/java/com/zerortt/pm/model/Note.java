package com.zerortt.pm.model;

import java.util.Vector;

public class Note implements Identity {
    public static final String KEY = "id";
    public String id;
    public String name;
    public Vector<IdentityData> projects;
    public Vector<IdentityData> programs;
    public Vector<IdentityData> issues;

    public Note() {
        this.id = IdentityData.createRandomId();
        init();
    }

    public Note(String id) {
        this.id = id;
        init();
    }

    private void init() {
        programs = new Vector<>();
        projects = new Vector<>();
        issues = new Vector<>();
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
