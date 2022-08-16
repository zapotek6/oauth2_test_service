package com.zerortt.pm.model;

public interface Identity {
    default String kind() {
        return this.getClass().getCanonicalName();
    }
    String key();
    String value();
    IdentityData identityData();
}
