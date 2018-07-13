package com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "forms")
public class Form {

    @PrimaryKey
    @ColumnInfo (name = "form_id")
    private int formid;

    @ColumnInfo (name = "date_time")
    private String dateTime;

    @ColumnInfo (name = "inspector")
    private String inspector;

    @ColumnInfo (name = "client_name")
    private String clientName;

    @ColumnInfo (name = "project")
    private String project;

    @ColumnInfo (name = "job_location")
    private String jobLocation;

    @ColumnInfo (name = "walked")
    private boolean walked;

    @ColumnInfo (name = "live_system")
    private boolean liveSystem;

    @ColumnInfo (name = "trained")
    private boolean trained;

    @ColumnInfo (name = "msds")
    private boolean msds;

    @ColumnInfo (name = "air_monitoring")
    private boolean airMonitoring;

    @ColumnInfo (name = "work_permit")
    private boolean workPermits;

    @ColumnInfo (name = "evatuation_routes")
    private boolean evacuationRoutes;

    @ColumnInfo (name = "ppe")
    private boolean ppe;

    @ColumnInfo (name = "image")
    private String image;

    @ColumnInfo (name = "scan_format")
    private String scanFormat;

    @ColumnInfo (name = "scan_content")
    private String scanContent;

    @ColumnInfo (name = "customer_name")
    private String customerName;

    @ColumnInfo (name = "customer_sign")
    private String customerSignature;


    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public boolean isWalked() {
        return walked;
    }

    public void setWalked(boolean walked) {
        this.walked = walked;
    }

    public boolean isLiveSystem() {
        return liveSystem;
    }

    public void setLiveSystem(boolean liveSystem) {
        this.liveSystem = liveSystem;
    }

    public boolean isTrained() {
        return trained;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public boolean isMsds() {
        return msds;
    }

    public void setMsds(boolean msds) {
        this.msds = msds;
    }

    public boolean isAirMonitoring() {
        return airMonitoring;
    }

    public void setAirMonitoring(boolean airMonitoring) {
        this.airMonitoring = airMonitoring;
    }

    public boolean isWorkPermits() {
        return workPermits;
    }

    public void setWorkPermits(boolean workPermits) {
        this.workPermits = workPermits;
    }

    public boolean isEvacuationRoutes() {
        return evacuationRoutes;
    }

    public void setEvacuationRoutes(boolean evacuationRoutes) {
        this.evacuationRoutes = evacuationRoutes;
    }

    public boolean isPpe() {
        return ppe;
    }

    public void setPpe(boolean ppe) {
        this.ppe = ppe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getScanFormat() {
        return scanFormat;
    }

    public void setScanFormat(String scanFormat) {
        this.scanFormat = scanFormat;
    }

    public String getScanContent() {
        return scanContent;
    }

    public void setScanContent(String scanContent) {
        this.scanContent = scanContent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(String customerSignature) {
        this.customerSignature = customerSignature;
    }
}
