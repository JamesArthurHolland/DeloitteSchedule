package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Activity> activities = ActivityParser.parseActivities("activities.txt");

        Schedule schedule = new Schedule(2, activities);
        GameTree tree = new GameTree(schedule);
        tree.idaStar();
    }
}
