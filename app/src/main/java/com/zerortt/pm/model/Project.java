package com.zerortt.pm.model;

import java.util.Vector;

public class Project implements Identity {
    public static final String KEY = "id";
    public String id;
    public String name;
    public Vector<IdentityData> programs;

    public Project() {
        this.id = IdentityData.createRandomId();
        init();
    }

    public Project(String id) {
        this.id = id;
        init();
    }

    private void init() {
        programs = new Vector<>();
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
