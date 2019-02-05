package uk.co.labfour.cloud2.services.user.model;

import uk.co.labfour.cloud2.entity.identity.Identity;
import uk.co.labfour.cloud2.entity.primitive.BaseObject;

import java.util.UUID;
import java.util.Vector;

public class Activity extends BaseObject {
    public final static String TYPE = "ACTIVITY";

    Identity project;
    Identity roadmapElement;
    Identity owner;
    String title;
    String descr;
    boolean completed;
    boolean milestone;

    long time_estimation = 0L;
    long time_spent = 0L;
    long time_consumptive = 0L;

    long closed_at;

    long expire_at;
    Vector<Identity> parents;
    Vector<Identity> childs;

    String type;

    public Activity() {
        super(new Identity(UUID.randomUUID(), TYPE));
        //if (null == created) created = new Date();
    }

    public Activity(UUID uuid) {
        super(new Identity(uuid, TYPE));
    }


    public Identity getOwner() {
        return owner;
    }

    public void setOwner(Identity owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isMilestone() {
        return milestone;
    }

    public void setMilestone(boolean milestone) {
        this.milestone = milestone;
    }

    public Vector<Identity> getParents() {
        return parents;
    }

    public void setParents(Vector<Identity> parents) {
        this.parents = parents;
    }

    public Vector<Identity> getChilds() {
        return childs;
    }

    public void setChilds(Vector<Identity> childs) {
        this.childs = childs;
    }

    public long getTime_estimation() {
        return time_estimation;
    }

    public void setTime_estimation(long time_estimation) {
        this.time_estimation = time_estimation;
    }

    public long getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(long time_spent) {
        this.time_spent = time_spent;
    }

    public long getTime_consumptive() {
        return time_consumptive;
    }

    public void setTime_consumptive(long time_consumptive) {
        this.time_consumptive = time_consumptive;
    }

    public String getType() {
        return type;
    }

    public Activity setType(String type) {
        this.type = type;
        return this;
    }

    public Identity getProject() {
        return project;
    }

    public Activity setProject(Identity project) {
        this.project = project;
        return this;
    }

    public Activity setTime_estimation(Long time_estimation) {
        this.time_estimation = time_estimation;
        return this;
    }

    public Activity setTime_spent(Long time_spent) {
        this.time_spent = time_spent;
        return this;
    }

    public Activity setTime_consumptive(Long time_consumptive) {
        this.time_consumptive = time_consumptive;
        return this;
    }
}
