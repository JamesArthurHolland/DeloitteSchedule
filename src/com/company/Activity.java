package com.company;

public class Activity
{
    final private String name;
    final private int durationMinutes;
    private int startTimeMinutesFromMidnight;

    public Activity(String name, int durationMinutes) {
        this.name = name;
        this.durationMinutes = durationMinutes;
    }

    public Activity(Activity activity) {
        this.name = activity.getName();
        this.durationMinutes = activity.getDurationMinutes();
    }

    public Activity clone() throws CloneNotSupportedException
    {
        Activity clone = (Activity)super.clone();
        return clone;
    }

    public String getName() {
        return name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getStartTimeMinutesFromMidnight() {
        return startTimeMinutesFromMidnight;
    }

    public void setStartTimeMinutesFromMidnight(int startTimeMinutesFromMidnight) {
        this.startTimeMinutesFromMidnight = startTimeMinutesFromMidnight;
    }
}