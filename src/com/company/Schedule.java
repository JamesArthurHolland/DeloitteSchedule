package com.company;

import java.util.*;

public class Schedule
{
    // TODO mVariable naming
    private int teamToMove = 1;
    private HashMap<Integer, ArrayList<Activity>> remainingActivitiesForTeam;
    private HashMap<Integer, Itinerary> teamItineraries;
    private TreeMap<String, TreeSet<Integer>> activityInUseTimes; // TODO no clashes

    public static final int FEASIBLE_SCHEDULE = 0;

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

    public int evaluate()
    {
        return 1;
    }

    public boolean isActivityFree(Activity activity) // TODO test
    {
        TreeSet<Integer> times = activityInUseTimes.get(activity.getName());
        Iterator it = times.iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int startTime = (int) pair.getValue();
            int finishTime = startTime + activity.getDurationMinutes();
            int requestedTime = getCurrentInsertionTime();
            if(requestedTime > startTime && requestedTime < finishTime) {
                return false;
            }
        }
        return true;
    }

    public int getCurrentInsertionTime()
    {
        return teamItineraries.get(teamToMove).getCurrentInsertionTime();
    }

    public void addActivity(Activity activity)
    {
        teamItineraries.get(teamToMove).addActivity(activity);
        Iterator it = getRemainingActivitiesForCurrentTeam().iterator();
        while (it.hasNext()) {
            Activity currentActivity = (Activity) it;
            if(currentActivity.getName().equals(activity.getName())) {
                it.remove();
            }
        }
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
        private int currentTimeSlotMinutesFromMidnight = 0;

        public Itinerary(int startTimeMinutesFromMidnight) {
            this.activities = new ArrayList<>();
            this.currentTimeSlotMinutesFromMidnight = startTimeMinutesFromMidnight;
        }

        public void addActivity(Activity activity)
        {
            activities.add(activity);
            currentTimeSlotMinutesFromMidnight += activity.getDurationMinutes();
        }

        public int getCurrentInsertionTime()
        {
            return currentTimeSlotMinutesFromMidnight;
        }
    }
}
