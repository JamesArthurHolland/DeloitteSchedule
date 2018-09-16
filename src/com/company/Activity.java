package com.company;

public class Activity
{
    final private String name;
    final private int durationMinutes;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return this.name == activity.name
                && this.durationMinutes == activity.durationMinutes;
    }

    public String getName() {
        return name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
