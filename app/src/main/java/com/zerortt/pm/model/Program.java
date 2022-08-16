package com.zerortt.pm.model;

public class Program implements Identity {
    public static final String KEY = "id";
    public String id;
    public String name;

    public Program() {
        this.id = IdentityData.createRandomId();
    }

    public Program(String id) {
       this.id = id;
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
