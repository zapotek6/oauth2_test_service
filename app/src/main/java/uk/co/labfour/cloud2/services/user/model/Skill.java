package uk.co.labfour.cloud2.services.user.model;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

import java.util.UUID;

public class Skill extends BaseObject {
    public final static String TYPE = "SKILL";


    public Skill() {
        super(new Identity(UUID.randomUUID(), TYPE));
    }
}
