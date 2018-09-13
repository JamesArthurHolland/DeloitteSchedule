package com.company.tests;

import com.company.Activity;
import com.company.ActivityParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityParserTest {
    @Test
    void parseTimedActivity() {
        Activity controlActivity = new Activity("Clay pigeon shooting", 60);

        String activityString = "Clay pigeon shooting 60min";
        Activity testActivity = ActivityParser.parseActivityFromLine(activityString);

        assertEquals(controlActivity.equals(testActivity), true);
    }
}