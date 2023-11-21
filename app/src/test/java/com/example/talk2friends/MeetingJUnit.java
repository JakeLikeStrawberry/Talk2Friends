package com.example.talk2friends;


import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class MeetingJUnit {

    @Test
    public void testDefaultConstructor() {
        Meeting meeting = new Meeting();
        assertNotNull(meeting);
        assertEquals("", meeting.getName());
        assertEquals("", meeting.getTopic());
    }

    @Test
    public void testParameterizedConstructor() {
        String name = "Meeting 1";
        String topic = "Discussion";
        String time = "2023-11-20 15:00";
        String location = "Room 101";
        String creatorEmail = "creator@example.com";

        Meeting meeting = new Meeting(name, topic, time, location, creatorEmail);

        assertNotNull(meeting);
        assertEquals(name, meeting.getName());
        assertEquals(topic, meeting.getTopic());
        assertEquals(time, meeting.getTime());
        assertEquals(location, meeting.getLocation());
        assertEquals(1, meeting.getParticipants().size());
        assertTrue(meeting.getParticipants().contains(creatorEmail));
    }

    @Test
    public void testFullConstructorWithParticipants() {
        String name = "Meeting 1";
        String topic = "Discussion";
        String time = "2023-11-20 15:00";
        String location = "Room 101";
        ArrayList<String> participants = new ArrayList<>();
        participants.add("participant1@example.com");
        participants.add("participant2@example.com");
        String key = "meetingKey";

        Meeting meeting = new Meeting(name, topic, time, location, participants, key);

        assertNotNull(meeting);
        assertEquals(name, meeting.getName());
        assertEquals(topic, meeting.getTopic());
        assertEquals(time, meeting.getTime());
        assertEquals(location, meeting.getLocation());
        assertEquals(participants, meeting.getParticipants());
        assertEquals(key, meeting.getKey());
    }

    @Test
    public void testAddParticipant() {
        Meeting meeting = new Meeting("Meeting 1", "Discussion", "2023-11-20 15:00", "Room 101", "creator@example.com");

        assertEquals(1, meeting.getParticipants().size());

        meeting.addParticipant("participant@example.com");

        assertEquals(2, meeting.getParticipants().size());
        assertTrue(meeting.getParticipants().contains("participant@example.com"));
    }

    @Test
    public void testRemoveParticipant() {
        Meeting meeting = new Meeting("Meeting 1", "Discussion", "2023-11-20 15:00", "Room 101", "creator@example.com");
        meeting.addParticipant("participant@example.com");

        assertEquals(2, meeting.getParticipants().size());

        meeting.removeParticipant("participant@example.com");

        assertEquals(1, meeting.getParticipants().size());
        assertFalse(meeting.getParticipants().contains("participant@example.com"));
    }
}

