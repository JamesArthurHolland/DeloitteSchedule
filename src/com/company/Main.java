package com.company;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Activity> activities = ActivityParser.parseActivities("src/com/company/activities.txt");

        Schedule schedule = new Schedule(2, activities);
        GameTree tree = new GameTree(schedule);
        tree.idaStar();
    }
}
