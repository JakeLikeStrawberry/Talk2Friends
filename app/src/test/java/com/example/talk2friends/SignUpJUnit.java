package com.example.talk2friends;

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

class TestFirebaseClient implements ICustomFirebaseClient {
    public ArrayList<String> codes = new ArrayList<String>();
    public void getCodes(OnReceiveCodes onReceiveCodes) {
        onReceiveCodes.onReceiveCodes(new ArrayList<String>());
    }

    public void saveCode(String code) {
        codes.add(code);
    }
}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(JUnit4.class)
//@PrepareForTest({ FirebaseDatabase.class})
public class SignUpJUnit {

    public static void main(String[] args) {
        // run all login tests
        System.out.println("Running Login tests...");

        SignUpJUnit signUpJUnit = new SignUpJUnit();

        ValidationCode();
        CheckSignUpErrors();
    }

    public static void ValidationCode() {
        SignUpJUnit signUpJUnit = new SignUpJUnit();
        signUpJUnit.ValidationCode_length();
        signUpJUnit.ValidationCode_alphanumeric();
        signUpJUnit.ValidationCode_uppercase();
        signUpJUnit.ValidationCode_string();
        signUpJUnit.ValidationCode_saved();
    }


    @Test
    public void ValidationCode_length() {

        // test if generateValidationCode() function...
        // test if getUniqueValidationCode() function...

        TestFirebaseClient client = new TestFirebaseClient();
        SignUpUtils.getUniqueValidationCode(new Callback() {
            @Override
            public void onCallback(String value) {
                // TODO: 1. returns a 4 digit code
                assertEquals(4, value.length());
            }
        }, client);

    }

    @Test
    public void ValidationCode_alphanumeric() {

        // test if generateValidationCode() function...
        // test if getUniqueValidationCode() function...

        TestFirebaseClient client = new TestFirebaseClient();
        SignUpUtils.getUniqueValidationCode(new Callback() {
            @Override
            public void onCallback(String value) {
                // TODO: 2. returns alphanumeric
                assertEquals(true, value.matches("[A-Z0-9]+"));
            }
        }, client);

    }

    @Test
    public void ValidationCode_uppercase() {

        // test if generateValidationCode() function...
        // test if getUniqueValidationCode() function...

        TestFirebaseClient client = new TestFirebaseClient();
        SignUpUtils.getUniqueValidationCode(new Callback() {
            @Override
            public void onCallback(String value) {
                // TODO: 3. is all uppercase
                assertEquals(true, value.equals(value.toUpperCase()));
            }
        }, client);

    }

    @Test
    public void ValidationCode_string() {

        // test if generateValidationCode() function...
        // test if getUniqueValidationCode() function...

        TestFirebaseClient client = new TestFirebaseClient();
        SignUpUtils.getUniqueValidationCode(new Callback() {
            @Override
            public void onCallback(String value) {
                assertEquals(true, value instanceof String);
            }
        }, client);

    }

    @Test
    public void ValidationCode_saved() {

        // test if generateValidationCode() function...
        // test if getUniqueValidationCode() function...

        TestFirebaseClient client = new TestFirebaseClient();
        client.codes.add("1234");
        SignUpUtils.getUniqueValidationCode(new Callback() {
            @Override
            public void onCallback(String value) {
                // TODO: 5. is saved to Client and does not overlap with existing codes (unique)
                assertEquals(true, client.codes.contains(value));
                assertEquals(true, !value.equals("1234"));
            }
        }, client);

    }


    public static void CheckSignUpErrors() {
        SignUpJUnit signUpJUnit = new SignUpJUnit();
        signUpJUnit.CheckSignUpErrors_emptyPassword();
        signUpJUnit.CheckSignUpErrors_emptyEmail();
        signUpJUnit.CheckSignUpErrors_passwordLength();
        signUpJUnit.CheckSignUpErrors_emailLength();
        signUpJUnit.CheckSignUpErrors_USCEmail();
        signUpJUnit.CheckSignUpErrors_emailUsername();
    }

    @Test
    public void CheckSignUpErrors_emptyPassword() {
        // test if checkSignUpErrors() function...
        // TODO: 1. returns false for empty password
        TextView view = Mockito.mock(TextView.class);
        assertEquals(false, SignUpUtils.checkSignUpErrors("mdjun@usc.edu", "", view));
    }

    @Test
    public void CheckSignUpErrors_emptyEmail() {
        // test if checkSignUpErrors() function...
        // TODO: 2. returns false for empty email
        TextView view = Mockito.mock(TextView.class);
        assertEquals(false, SignUpUtils.checkSignUpErrors("", "123", view));
    }

    @Test
    public void CheckSignUpErrors_passwordLength() {
        // test if checkSignUpErrors() function...
        // TODO: 3. limits password length to 30 characters (input 31 chars)
        // limits password > 6 chars
        TextView view = Mockito.mock(TextView.class);

        // input 31 chars
        String password = "";
        for (int i = 0; i < 31; i++) {
            password += "a";
        }
        assertEquals(false, SignUpUtils.checkSignUpErrors("mdjun@usc.edu", password, view));

        // input 30 chars
        String password2 = "";
        for (int i = 0; i < 30; i++) {
            password2 += "a";
        }
        assertEquals(true, SignUpUtils.checkSignUpErrors("mdjun@usc.edu", password2, view));

        // input 6 chars
        String password3 = "";
        for (int i = 0; i < 6; i++) {
            password3 += "a";
        }
        assertEquals(true, SignUpUtils.checkSignUpErrors("mdjun@usc.edu", password3, view));

        // input 5 chars
        String password4 = "";
        for (int i = 0; i < 5; i++) {
            password4 += "a";
        }
        assertEquals(false, SignUpUtils.checkSignUpErrors("mdjun@usc.edu", password4, view));
    }

    @Test
    public void CheckSignUpErrors_emailLength() {
        // test if checkSignUpErrors() function...
        // TODO: 4. limits email length to 64 + 8 characters, including @usc.edu
        TextView view = Mockito.mock(TextView.class);

        // input 65 + 8 chars
        String email2 = "";
        for (int i = 0; i < 65; i++) {
            email2 += "a";
        }
        email2 += "@usc.edu";
        assertEquals(false, SignUpUtils.checkSignUpErrors(email2, "123456", view));

        // input 64 + 8 chars
        String email = "";
        for (int i = 0; i < 64; i++) {
            email += "a";
        }
        email += "@usc.edu";
        assertEquals(true, SignUpUtils.checkSignUpErrors(email, "123456", view));
    }

    @Test
    public void CheckSignUpErrors_USCEmail() {
        // test if checkSignUpErrors() function...
        // limits email to @usc
        TextView view = Mockito.mock(TextView.class);

        assertEquals(true, SignUpUtils.checkSignUpErrors("asdf@usc.edu", "123456", view));

        assertEquals(false, SignUpUtils.checkSignUpErrors("asdf@gmail.com", "123456", view));
    }

    @Test
    public void CheckSignUpErrors_emailUsername() {
        // test if checkSignUpErrors() function...
        // has username before @
        TextView view = Mockito.mock(TextView.class);

        assertEquals(true, SignUpUtils.checkSignUpErrors("asdf@usc.edu", "123456", view));

        assertEquals(false, SignUpUtils.checkSignUpErrors("@usc.edu", "123456", view));
    }
}