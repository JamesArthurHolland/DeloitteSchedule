package com.company;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ActivityParser
{
    public static Activity parseActivityFromLine(String inputLine)
    {
        String timeString = inputLine.substring(inputLine.lastIndexOf(" ") + 1);
        String activityName = inputLine.substring(0, inputLine.lastIndexOf(" "));
        if(timeString.equals("sprint")) {
            // TODO
            return new Activity(activityName, 15);
        }
        else {
            String timeInMinutesString = timeString.substring(0, timeString.indexOf("m"));
            int timeInMinutes = Integer.parseInt(timeInMinutesString);
            return new Activity(activityName, timeInMinutes);
            // TODO
        }
    }

    public static ArrayList<Activity> parseActivities(String fileName)
    {
        Path path = Paths.get(fileName);
        ArrayList<Activity> activities = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEachOrdered(line -> {
                activities.add(parseActivityFromLine(line));
            });
        } catch (IOException e) {
            System.out.println(e.toString());
            //error happened
        }
        return activities;
    }
}
