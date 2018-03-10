package uk.co.labfour.cloud2.services.user.model;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

public class Activity extends BaseObject {
	public final static String TYPE = "ACTIVITY";

	String title = "";
	String description = "";
    boolean completed;
    Date created = new Date();


    public Activity(UUID uuid) {
        super(new Identity(uuid, TYPE));
    }

}
