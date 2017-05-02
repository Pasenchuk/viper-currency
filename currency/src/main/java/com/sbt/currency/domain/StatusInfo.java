package com.sbt.currency.domain;

/**
 * Created by Pasenchuk Victor on 02/05/2017
 */

public class StatusInfo {

    private String date;

    private boolean displayRefreshIndicator;

    private boolean displayDaysDigit;

    private boolean displayRefreshButton;

    private int days;

    private int messageId;

    public StatusInfo(String date, boolean displayRefreshIndicator, boolean displayDaysDigit, boolean displayRefreshButton, int days, int messageId) {
        this.date = date;
        this.displayRefreshIndicator = displayRefreshIndicator;
        this.displayDaysDigit = displayDaysDigit;
        this.displayRefreshButton = displayRefreshButton;
        this.days = days;
        this.messageId = messageId;
    }

    public String getDate() {
        return date;
    }

    public boolean isDisplayRefreshIndicator() {
        return displayRefreshIndicator;
    }

    public boolean isDisplayDaysDigit() {
        return displayDaysDigit;
    }

    public boolean isDisplayRefreshButton() {
        return displayRefreshButton;
    }

    public int getDays() {
        return days;
    }

    public int getMessageId() {
        return messageId;
    }

}
