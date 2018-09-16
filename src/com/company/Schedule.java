package com.company;

import java.util.*;

public class Schedule
{
    // TODO mVariable naming
    private int numberOfTeams;
    private int teamToMove = 1;
    private int numberOfActivities;
    private HashMap<Integer, ArrayList<Activity>> remainingActivitiesForTeam;
    private HashMap<Integer, Itinerary> teamItineraries;
    private TreeMap<String, TreeSet<Integer>> activityInUseTimes = new TreeMap<>();; // TODO no clashes

    public static final int FEASIBLE_SCHEDULE = 0;

    public static final int START_OF_DAY = 540;
    public static final int LUNCH_TIME = 715;

    public Schedule(int numberOfTeams, ArrayList<Activity> activities)
    {
        this.numberOfTeams = numberOfTeams;
        this.teamToMove = teamToMove;
        this.numberOfActivities = activities.size();
        remainingActivitiesForTeam = new HashMap<>();
        activityInUseTimes = new TreeMap<>();
        teamItineraries = new HashMap<>();
        for(int i = 1; i <= numberOfTeams; i ++) {
            remainingActivitiesForTeam.put(new Integer(i), activities);
        }
        for(int i = 1; i <= numberOfTeams; i ++) {
            teamItineraries.put(new Integer(i), new Itinerary());
        }
        for(Activity activity : activities) {
            activityInUseTimes.put(activity.getName(), new TreeSet<>());
        }
    }

    public Schedule(Schedule scheduleToClone)
    {
        numberOfTeams = scheduleToClone.numberOfTeams;
        teamToMove = scheduleToClone.teamToMove;
        this.numberOfActivities = scheduleToClone.numberOfActivities;
        Iterator it = scheduleToClone.getRemainingActivitiesForTeam().entrySet().iterator();
        remainingActivitiesForTeam = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int teamNumber = (int) pair.getKey();
            ArrayList<Activity> activities = (ArrayList<Activity>) pair.getValue();
            remainingActivitiesForTeam.put(new Integer(teamNumber), new ArrayList<>());
            for(Activity activity : activities) {
                remainingActivitiesForTeam.get(new Integer(teamNumber)).add(new Activity(activity));
            }
        }

        teamItineraries = new HashMap<>();
        it = scheduleToClone.teamItineraries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int teamNumber = (int) pair.getKey();
            Itinerary itinerary = (Itinerary) pair.getValue();
            teamItineraries.put(new Integer(teamNumber), new Itinerary(itinerary));
        }

        activityInUseTimes = new TreeMap<>();
        it = scheduleToClone.activityInUseTimes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String activityName = (String) pair.getKey();
            TreeSet<Integer> times = (TreeSet<Integer>) pair.getValue();
            activityInUseTimes.put(activityName, new TreeSet<>());
            for(Integer i : times) {
                activityInUseTimes.get(activityName).add(new Integer(i));
            }
        }
    }

    public int evaluate()
    {
        boolean scheduleFull = true;
        Iterator it = getTeamItineraries().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Itinerary itinerary = (Itinerary) pair.getValue();
            if(itinerary.currentTimeSlotMinutesFromMidnight > LUNCH_TIME && itinerary.timeOfActivity("Lunch") != LUNCH_TIME) {
                return GameTree.INFINITY;
            }

            if(itinerary.activities.size() != numberOfActivities) {
                scheduleFull = false;
            }
        }
        if(scheduleFull == true) {
            return FEASIBLE_SCHEDULE;
        }
        return 1;
    }

    public void printSchedule()
    {
        Iterator it = teamItineraries.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int teamNumber = (int) pair.getKey();
            Itinerary itinerary = (Itinerary) pair.getValue();
            System.out.println("Itinerary for team" + teamNumber);
            itinerary.printItinerary();
            System.out.println();
        }
    }

    private int nextTeamNumber()
    {
        return (teamToMove % numberOfTeams) + 1;
    }

    public boolean isActivityFree(Activity activity) // TODO test
    {
        TreeSet<Integer> times = activityInUseTimes.get(activity.getName());
        Iterator it = times.iterator();
        while (it.hasNext()) {
            int startTime = (int) it.next();
            int finishTime = startTime + activity.getDurationMinutes();
            int requestedTime = getCurrentInsertionTime();
            int requestedFinishTime = getCurrentInsertionTime() + activity.getDurationMinutes();
            if((requestedTime >= startTime && requestedTime < finishTime) ||
                requestedFinishTime > startTime && requestedTime < finishTime) {
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
        int currentTime = teamItineraries.get(teamToMove).currentTimeSlotMinutesFromMidnight;
        if(activity.getName().equals("Lunch") == false && activity.getName().equals("Staff Motivation Presentation") == false) {
            activityInUseTimes.get(activity.getName()).add(currentTime);
        }
        teamItineraries.get(teamToMove).addActivity(new Activity(activity));

        ArrayList<Activity> remainingActivitiesForCurrentTeam = getRemainingActivitiesForCurrentTeam();
        Iterator it = remainingActivitiesForCurrentTeam.iterator();
        while (it.hasNext()) {
            Activity currentActivity = (Activity) it.next();
            if(currentActivity.getName().equals(activity.getName())) {
                it.remove();
            }
        }
        teamToMove = nextTeamNumber();
    }

    public ArrayList<Activity> getRemainingActivitiesForCurrentTeam() {
        return remainingActivitiesForTeam.get(teamToMove);
    }

    public HashMap<Integer, Itinerary> getTeamItineraries() {
        return teamItineraries;
    }

    public HashMap<Integer, ArrayList<Activity>> getRemainingActivitiesForTeam() {
        return remainingActivitiesForTeam;
    }

    public class Itinerary
    {
        private TreeMap<Integer, Activity> activities;
        private int currentTimeSlotMinutesFromMidnight = START_OF_DAY; // Starts at 9am

        public Itinerary() {
            this.activities = new TreeMap<>();
        }

        public Itinerary(Itinerary other)
        {
            this.activities = new TreeMap<>();
            Iterator it = other.activities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                int time = (int) pair.getKey();
                Activity activity = (Activity) pair.getValue();
                activities.put(time, new Activity(activity));
            }
            currentTimeSlotMinutesFromMidnight = other.currentTimeSlotMinutesFromMidnight;
        }

        public int timeOfActivity(String activityName){
            Iterator it = activities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                int time = (int) pair.getKey();
                Activity activity = (Activity) pair.getValue();
                if(activity.getName().equals(activityName)) {
                    return time;
                }
            }
            return -1;
        }

        public void printItinerary()
        {
            Iterator it = activities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                int time = (int) pair.getKey();
                Activity activity = (Activity) pair.getValue();
                String hourOfActivity = "" + time / 60;
                String minutesOfActivity = "" + time % 60;
                System.out.println(activity.getName() + " " + hourOfActivity + ":" + minutesOfActivity + "----"  + time);
                activities.put(time, activity);
            }
        }

        public void addActivity(Activity activity)
        {
            activities.put(currentTimeSlotMinutesFromMidnight, new Activity(activity));
            currentTimeSlotMinutesFromMidnight += activity.getDurationMinutes();
        }

        public int getCurrentInsertionTime()
        {
            return currentTimeSlotMinutesFromMidnight;
        }
    }
}
