package com.ds.owl.dsdormitory;

/**
 * Created by Seonggyeong on 2017-02-24.
 */

public class OutListview {

    private String apply_day;
    private String out_term;
    private String out_destination;
    private String out_reason;

    public void setDay(String day) {
        this.apply_day = day;
    }

    public void setTerm(String term) {
        this.out_term = term;
    }

    public void setDestination(String destination) {
        this.out_destination = destination;
    }

    public void setReason(String reason) {
        this.out_reason = reason;
    }

    public String getDay() {
        return this.apply_day;
    }

    public String getTerm() {
        return this.out_term;
    }

    public String getDestination() {
        return this.out_destination;
    }

    public String getReason() {
        return this.out_reason;
    }
}
