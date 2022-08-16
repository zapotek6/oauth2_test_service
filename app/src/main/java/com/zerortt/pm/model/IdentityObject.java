package com.zerortt.pm.model;

public abstract class IdentityObject implements Identity {
    public static String kind;
    public IdentityObject(String kind) {
        IdentityObject.kind = kind;
    }
}
