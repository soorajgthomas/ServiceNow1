package com.expedia.servicenow.servicenow1.util;

/**
 * Created by SOORAJ on 13-11-2016.
 */

public class Incident {

    String number;
    int incidentState;
    String shortDescription;
    String sysUpdatedBy;
    String sysId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getIncidentState() {
        return incidentState;
    }

    public void setIncidentState(int incidentState) {
        this.incidentState = incidentState;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSysUpdatedBy() {
        return sysUpdatedBy;
    }

    public void setSysUpdatedBy(String sysUpdatedBy) {
        this.sysUpdatedBy = sysUpdatedBy;
    }


    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
