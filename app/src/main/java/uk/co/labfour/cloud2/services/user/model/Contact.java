package uk.co.labfour.cloud2.services.user.model;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

import java.util.UUID;

public class Contact extends BaseObject {
    public final static String TYPE = "CONTACT";

    String firstname;
    String lastname;
    String email;
    String phone;
    String role;
    String nickname;
    String company;

    public Contact() {
        super(new Identity(UUID.randomUUID(), TYPE));
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Contact setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public Contact setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Contact setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Contact setRole(String role) {
        this.role = role;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Contact setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
