package com.example.mihai.msajobapp;

public class JobInfo {
    private String jobTitle;
    private String shortJobDesc;

    public JobInfo(){

    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getShortJobDesc() {
        return shortJobDesc;
    }

    public void setShortJobDesc(String shortJobDesc) {
        this.shortJobDesc = shortJobDesc;
    }
}
