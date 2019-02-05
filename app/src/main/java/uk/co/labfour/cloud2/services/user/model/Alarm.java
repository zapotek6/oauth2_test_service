package uk.co.labfour.cloud2.services.user.model;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

import java.util.UUID;

public class Alarm extends BaseObject {
    public final static String TYPE = "ALARM";

    Identity owner;
    long alarm_time = 0;

    public Alarm() {
        super(new Identity(UUID.randomUUID(), TYPE));
    }

}
