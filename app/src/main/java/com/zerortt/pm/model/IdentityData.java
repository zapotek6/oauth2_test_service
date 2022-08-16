package com.zerortt.pm.model;

import java.util.UUID;

public class IdentityData {
    private String kind;
    private String key;
    private String value;


	public IdentityData(IdentityData value) {
		this.key = value.getKey();
		this.value = value.getValue();
		this.kind = value.getKind();
	}

	public IdentityData(String kind, String key, UUID uuid) {
		this.key = key;
        this.value = uuid.toString();
		this.kind = kind;
	}

	public IdentityData(String kind, String key, String value) {
		this.key = key;
        this.value = value;
		this.kind = kind;
	}

	public IdentityData(String kind, String key) {
		this.key = key;
        this.value = UUID.randomUUID().toString();
		this.kind = kind;
	}

	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}

	public String getKind() {
		return kind;
	}

	@Override
	public String toString() {
		return "Identity: " + value + "of kind: " + kind;
	}

	public boolean compaterTo(IdentityData identity) {
		if ((0 == value.compareTo(identity.value)) && (0 == kind.compareTo(identity.kind))) {
			return true;
		} else {
			return false;
		}
	}

    public static UUID convertUuidfromString(String uuid) {
        return UUID.fromString(uuid);
    }

    public static String createRandomId() { return UUID.randomUUID().toString(); }
}
