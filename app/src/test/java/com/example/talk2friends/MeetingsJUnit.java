package com.example.talk2friends;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.kotlin.doReturn;
//import org.mockito.kotlin.mock;

//import org.mockito.Mockito;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


class TestFirebaseClient_Meetings implements ICustomFirebaseClient_Meetings {
    public ArrayList<Meeting> meetings = new ArrayList<Meeting>();

    public void getMeetings(OnReceiveMeetings onReceiveMeetings) {
        onReceiveMeetings.onReceiveMeetings(new ArrayList<Meeting>());
    }
    public void saveMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}

class TestFirebaseClient_Update implements ICustomFirebaseClient_Update {
    public ArrayList<Object> savedValues = new ArrayList<Object>();
    @Override
    public <T> void saveValue(String firstLayer, String thirdLayer, String matchValue, String targetField, T newValue) {
        savedValues.add(newValue);
    }
}

public class MeetingsJUnit {

    @Test
    public void testGetMeetings() {
        TestFirebaseClient_Meetings client = new TestFirebaseClient_Meetings();
        client.meetings.add(new Meeting());
        DatabaseHandler.getMeetings(new MeetingsCallback() {
            @Override
            public void onCallback(ArrayList<Meeting> meetings) {
                assertEquals(1, client.meetings.size());
            }
        }, client);
    }

    @Test
    public void testSaveMeeting() {
        TestFirebaseClient_Meetings client = new TestFirebaseClient_Meetings();
        client.saveMeeting(new Meeting());
        assertEquals(1, client.meetings.size());
    }

    @Test
    public void testSavePersonal() {
        TestFirebaseClient_Update client = new TestFirebaseClient_Update();
        client.saveValue("users", "test", "test", "test", "personalInfo");
        assertEquals(1, client.savedValues.size());
    }

}
