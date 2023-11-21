package com.example.talk2friends;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileJUnit {




    // 10 Whitebox tests

    @Test
    public void testAgeFieldInput() {
        // Create a mock TextView
        TextView ageTextView = Mockito.mock(TextView.class);

        // Assuming the age is initially set to "21"
        when(ageTextView.getText()).thenReturn("21");

        // Input a non-number (e.g., "test") and check if it's still the original number
        when(ageTextView.getText()).thenReturn("test");
        assertEquals("21", ageTextView.getText());

        // Input a valid number (e.g., "30") and check if it's updated
        when(ageTextView.getText()).thenReturn("30");
        assertEquals("30", ageTextView.getText());
    }

    @Test
    public void testInputLimit() {

        // Create a mock TextView for the name field
        TextView nameTextView = Mockito.mock(TextView.class);

        // Set a name with more than 30 characters
        when(nameTextView.getText()).thenReturn("ThisNameIsTooLongToExceedTheLimit");

        // Ensure that the name is truncated to 30 characters
        assertEquals("ThisNameIsTooLongToExceedTheLimi", nameTextView.getText().toString());
    }

    @Test
    public void testFriendsSuggested() {

    }

    @Test
    public void testSaveInfo() {

        DatabaseHandler db = new DatabaseHandler();

        db.updateValue("users", "email", "testUser@usc.edu", "interests/sports", "testInterest");
        db.updateValue("users", "email", "testUser@usc.edu", "interests/music", "testInterest");
        db.updateValue("users", "email", "testUser@usc.edu", "interests/reading", "testInterest");
        db.updateValue("users", "email", "testUser@usc.edu", "interests/exercise", "testInterest");
        db.updateValue("users", "email", "testUser@usc.edu", "interests/movies", "testInterest");

    }





}

