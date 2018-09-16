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
        String lastWord = inputLine.substring(inputLine.lastIndexOf(" ")+1);
        if(lastWord.equals("sprint")) {
            // TODO
        }
        else {
            // TODO
        }

        return null;
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
            //error happened
        }
        return null;
    }
}
