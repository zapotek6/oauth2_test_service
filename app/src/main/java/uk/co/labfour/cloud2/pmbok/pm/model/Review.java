package uk.co.labfour.cloud2.pmbok.pm.model;

public class Review {
    public String msg;

    public Review() { }

    public Review(String msg) {
        this.msg = msg;
    }
    public void append(String msg) {
        this.msg += msg + "\n";
    }
}
