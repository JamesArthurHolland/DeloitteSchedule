package com.company;

import java.util.ArrayList;

public class Schedule
{
    private ArrayList<Activity> team1_remaining_activities;
    private ArrayList<Activity> team2_remaining_activities;

    public Schedule(ArrayList<Activity> team1_remaining_activities, ArrayList<Activity> team2_remaining_activities)
    {
        this.team1_remaining_activities = team1_remaining_activities;
        this.team2_remaining_activities = team2_remaining_activities;
    }

    public Schedule(Schedule scheduleToClone)
    {
        for(Activity activity : scheduleToClone.team1_remaining_activities) {
            this.team1_remaining_activities.add(new Activity(activity));
        }
        for(Activity activity : scheduleToClone.team2_remaining_activities) {
            this.team1_remaining_activities.add(new Activity(activity));
        }
    }
}
