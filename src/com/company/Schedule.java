package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Schedule
{
    // TODO mVariable naming
    private int teamToMove = 1;
    private HashMap<Integer, ArrayList<Activity>> remainingActivitiesForTeam;
    private HashMap<Integer, Itinerary> teamItineraries;

    public Schedule(int numberOfTeams, ArrayList<Activity> activities)
    {
        this.teamToMove = teamToMove;
        remainingActivitiesForTeam = new HashMap<>();
        teamItineraries = new HashMap<>();
        for(int i = 1; i <= numberOfTeams; i ++) {
            remainingActivitiesForTeam.put(new Integer(i), activities);
        }
    }

    public Schedule(Schedule scheduleToClone)
    {
        Iterator it = scheduleToClone.getRemainingActivitiesForTeam().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int teamNumber = (int) pair.getKey();
            ArrayList<Activity> activities = (ArrayList<Activity>) pair.getValue();
            ArrayList<Activity> clonedActivities = new ArrayList<>();
            for(Activity activity : activities) {
                clonedActivities.add(activity);
            }

            remainingActivitiesForTeam.put(new Integer(teamNumber), activities);
        }
    }

    public void addActivity(Activity activity)
    {
        teamItineraries.get(teamToMove)
    }

    public ArrayList<Activity> getRemainingActivitiesForCurrentTeam() {
        return remainingActivitiesForTeam.get(teamToMove);
    }

    public int getTeamToMove() {
        return teamToMove;
    }

    public HashMap<Integer, ArrayList<Activity>> getRemainingActivitiesForTeam() {
        return remainingActivitiesForTeam;
    }

    public class Itinerary
    {
        private ArrayList<Activity> activities;
        private int currentTimeSlotMinutesFromMidnight;

        public Itinerary(int startTimeMinutesFromMidnight) {
            this.activities = new ArrayList<>();
            this.currentTimeSlotMinutesFromMidnight = startTimeMinutesFromMidnight;
        }

        public void addActivity(Activity activity)
        {
            activity.setStartTimeMinutesFromMidnight(currentTimeSlotMinutesFromMidnight);
            activities.add(activity);
            currentTimeSlotMinutesFromMidnight += activity.getDurationMinutes();
        }
    }
}
