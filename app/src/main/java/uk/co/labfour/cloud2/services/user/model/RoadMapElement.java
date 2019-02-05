package uk.co.labfour.cloud2.services.user.model;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

import java.util.UUID;

public class RoadMapElement extends BaseObject {
    public final static String TYPE = "ROADMAPELEMENT";

    Identity project;
    Long prio;
    boolean milestone;
    boolean canBeOpened = false;
    boolean opened = false;
    boolean closed = false;

    public RoadMapElement() {
        super(new Identity(UUID.randomUUID(), TYPE));
    }

}
