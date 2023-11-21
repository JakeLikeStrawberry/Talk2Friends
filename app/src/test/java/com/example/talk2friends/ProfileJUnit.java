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

class TestFirebaseClientProfile implements ICustomFirebaseClient_Profile {
    public ArrayList<String> codes = new ArrayList<String>();
    public void getCodes(OnReceiveCodes onReceiveCodes) {
        onReceiveCodes.onReceiveCodes(new ArrayList<String>());
    }

    public void saveCode(String code) {
        codes.add(code);
    }
}

public class ProfileJUnit {



}

