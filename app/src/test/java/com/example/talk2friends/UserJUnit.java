package com.example.talk2friends;


import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class UserJUnit {

    @Test
    public void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertEquals("", user.getEmail());
        assertEquals("", user.getPassword());
    }

    @Test
    public void testParameterizedConstructor() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, password);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testFullConstructor() {
        // Create sample data
        String email = "test@example.com";
        String password = "password123";
        String name = "John Doe";
        String age = "25";
        String affiliation = "Example University";
        String type = "Student";
        ArrayList<String> friends = new ArrayList<>();
        ArrayList<String> interests = new ArrayList<>();
        // default
        interests.add("yes");
        interests.add("yes");
        interests.add("yes");
        interests.add("yes");
        interests.add("yes");
        ArrayList<Meeting> meetings = new ArrayList<>();
        String key = "userKey";

        // Create user using the full constructor
        User user = new User(email, password, name, age, affiliation, type, friends, interests, meetings, key);

        // Validate the user object
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(type, user.getType());
        assertEquals(friends, user.getFriends());
        assertEquals(interests, user.getInterests());
        assertEquals(meetings, user.getMeetings());
        assertEquals(key, user.getKey());
    }

    @Test
    public void testAddFriend() {
        User user = new User("test@example.com", "password123");
        String friendEmail = "friend@example.com";

        assertEquals(0, user.getFriends().size());

        user.addFriend(friendEmail);

        assertEquals(1, user.getFriends().size());
        assertTrue(user.getFriends().contains(friendEmail));
    }

    @Test
    public void testRemoveFriend() {
        User user = new User("test@example.com", "password123");
        String friendEmail = "friend@example.com";

        user.addFriend(friendEmail);

        assertEquals(1, user.getFriends().size());

        user.removeFriend(friendEmail);

        assertEquals(0, user.getFriends().size());
        assertFalse(user.getFriends().contains(friendEmail));
    }

    @Test
    public void testAddMeeting() {
        User user = new User("test@example.com", "password123");
        Meeting meeting = new Meeting("Meeting 1", "Discussion", "2023-11-20 15:00", "Room 101", "creator@example.com");
        assertEquals(0, user.getMeetings().size());

        user.addMeeting(meeting);

        assertEquals(1, user.getMeetings().size());
        assertTrue(user.getMeetings().contains(meeting));
    }

    @Test
    public void testRemoveMeeting() {
        User user = new User("test@example.com", "password123");
        Meeting meeting = new Meeting("Meeting 1", "Discussion", "2023-11-20 15:00", "Room 101", "creator@example.com");
        user.addMeeting(meeting);

        assertEquals(1, user.getMeetings().size());

        user.removeMeeting(meeting.getName());

        assertEquals(0, user.getMeetings().size());
        assertFalse(user.getMeetings().contains(meeting));
    }
}
