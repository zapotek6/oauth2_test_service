package uk.co.labfour.cloud2.services.user.model;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

import java.util.UUID;
import java.util.Vector;

public class Project  extends BaseObject {
    public final static String TYPE = "PROJECT";

    Long prio;
    Identity leader;
    Identity sponsor;
    Vector<Identity> stakeholders = new Vector<>();

    public Project() {
        super(new Identity(UUID.randomUUID(), TYPE));
    }

    public long getPrio() {
        return prio;
    }

    public Project setPrio(long prio) {
        this.prio = prio;
        return this;
    }

    public Identity getLeader() {
        return leader;
    }

    public Project setLeader(Identity leader) {
        this.leader = leader;
        return this;
    }

    public Identity getSponsor() {
        return sponsor;
    }

    public Project setSponsor(Identity sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public Vector<Identity> getStakeholders() {
        return stakeholders;
    }

    public Project setStakeholders(Vector<Identity> stakeholders) {
        this.stakeholders = stakeholders;
        return this;
    }
}
