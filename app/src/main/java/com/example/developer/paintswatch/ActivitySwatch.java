package com.example.developer.paintswatch;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivitySwatch extends AppCompatActivity {
    private final int max = 255;
    private final int min = 0;
    private final int presetMax = 25;
    private final int presetMin = 0;
    private EditText Rview;
    private EditText Gview;
    private EditText Bview;
    private EditText Aview;
    private EditText PresetView;
    private Button Bplus;
    private Button Bminus;
    private Button Rplus;
    private Button Rminus;
    private Button Gplus;
    private Button Gminus;
    private Button Aplus;
    private Button Aminus;
    private Button presetPlus;
    private Button presetMinus;
    private ToggleButton hideToggle;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String ALPHA = "A";
    private static final String RED = "R";
    private static final String GREEN = "G";
    private static final String BLUE = "B";
    private static final String CurrentPreset = "CurrentPreset";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swatch);

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        Rview = (EditText) findViewById(R.id.RTextView);
        Gview = (EditText) findViewById(R.id.GTextView);
        Bview = (EditText) findViewById(R.id.BTextView);
        Aview = (EditText) findViewById(R.id.ATextView);
        PresetView = (EditText) findViewById(R.id.presetView);

        Bplus = (Button) findViewById(R.id.BPlus);
        Bminus = (Button) findViewById(R.id.BMinus);
        Rplus = (Button) findViewById(R.id.RPlus);
        Rminus = (Button) findViewById(R.id.RMinus);
        Gplus = (Button) findViewById(R.id.GPlus);
        Gminus = (Button) findViewById(R.id.GMinus);
        Aplus = (Button) findViewById(R.id.APlus);
        Aminus = (Button) findViewById(R.id.AMinus);
        presetPlus = (Button) findViewById((R.id.presetPlus));
        presetMinus = (Button) findViewById((R.id.presetMinus));
        hideToggle = (ToggleButton) findViewById(R.id.toggleButton);
        //loadAllValues();

        setTextChangeListener(Aview);
        setTextChangeListener(Rview);
        setTextChangeListener(Gview);
        setTextChangeListener(Bview);
        setTextChangeListenerPreset(PresetView);
        setLongListener(Bplus);
        setLongListener(Bminus);
        setLongListener(Rplus);
        setLongListener(Rminus);
        setLongListener(Gplus);
        setLongListener(Gminus);
        setLongListener(Aplus);
        setLongListener(Aminus);
        setLongListener(presetPlus);
        setLongListener(presetMinus);

        checkToggle();
        initialLoadAllValues();
        changePresetColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkToggle();
        initialLoadAllValues();
        changePresetColor();
    }

    @Override
    protected void onDestroy() {
        saveAllValues();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        saveAllValues();
        super.onStop();
    }

    @Override
    protected void onPause() {
        saveAllValues();
        super.onPause();
    }

    private void saveAllValues()
    {
        String presetNum = PresetView.getText().toString();
        preferenceEditor.putString(CurrentPreset, presetNum);
        preferenceEditor.putString(presetNum,presetNum);
        preferenceEditor.putString(ALPHA + presetNum,Aview.getText().toString());
        preferenceEditor.putString(RED + presetNum,Rview.getText().toString());
        preferenceEditor.putString(GREEN + presetNum,Gview.getText().toString());
        preferenceEditor.putString(BLUE + presetNum,Bview.getText().toString());
        preferenceEditor.commit();
    }

    private void loadAllValues()
    {
        String presetNum = PresetView.getText().toString();
        Aview.setText(preferenceSettings.getString(ALPHA + presetNum, max + ""));
        Rview.setText(preferenceSettings.getString(RED + presetNum, max + ""));
        Gview.setText(preferenceSettings.getString(GREEN + presetNum, max + ""));
        Bview.setText(preferenceSettings.getString(BLUE + presetNum, max + ""));
    }
    private void initialLoadAllValues()
    {
        String presetNum = preferenceSettings.getString(CurrentPreset, presetMin + "");
        PresetView.setText(presetNum);
        Aview.setText(preferenceSettings.getString(ALPHA + presetNum, max + ""));
        Rview.setText(preferenceSettings.getString(RED + presetNum, max + ""));
        Gview.setText(preferenceSettings.getString(GREEN + presetNum, max + ""));
        Bview.setText(preferenceSettings.getString(BLUE + presetNum, max + ""));
    }


    private void setLongListener(Button view)
    {
        view.setOnTouchListener(new RepeatListener(400, 10, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.presetPlus || view.getId() == R.id.presetMinus)
                    changePresetColor(view);
                else
                    changeColor(view);
            }
        }));
    }

    private void checkToggle()
    {
        if(hideToggle.getText().toString().equals("OFF"))
            setAllVisibility(View.GONE);
        else
            setAllVisibility(View.VISIBLE);
    }

    private void setTextChangeListenerPreset(final EditText eText)
    {
        eText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(checkPresetString(eText.getText().toString())) {
                    loadAllValues();
                    changeColor();
                }
                else
                {
                    fixColorPresetValue(eText);
                    loadAllValues();
                    changeColor();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //saveAllValues();
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //Field1.setText("");
            }
        });
    }

    private void setTextChangeListener(final EditText eText)
    {
        eText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(checkString(eText.getText().toString()))
                    changeColor();
                else
                {
                    fixColorValue(eText);
                    changeColor();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //Field1.setText("");
            }
        });
    }

    private void fixColorValue(EditText eText)
    {
        eText.setText(safeString(eText.getText().toString()));
    }

    private void fixColorPresetValue(EditText eText)
    {
        eText.setText(safePresetString(eText.getText().toString()));
    }

    private void changeColor()
    {
        findViewById(R.id.activity_swatch).
                setBackgroundColor(
                        Color.argb(
                                Integer.parseInt(safeString(Aview.getText().toString())),
                                Integer.parseInt(safeString(Rview.getText().toString())),
                                Integer.parseInt(safeString(Gview.getText().toString())),
                                Integer.parseInt(safeString(Bview.getText().toString()))
                        ));

    }

    public void changeColor(View view)
    {
        int newValue;
        switch (view.getId()) {
            case R.id.BPlus:
                newValue = (Integer.parseInt(Bview.getText().toString()) + 1);
                Bview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.BMinus:
                newValue = (Integer.parseInt(Bview.getText().toString()) - 1);
                Bview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.RPlus:
                newValue = (Integer.parseInt(Rview.getText().toString()) + 1);
                Rview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.RMinus:
                newValue = (Integer.parseInt(Rview.getText().toString()) - 1);
                Rview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.GPlus:
                newValue = (Integer.parseInt(Gview.getText().toString()) + 1);
                Gview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.GMinus:
                newValue = (Integer.parseInt(Gview.getText().toString()) - 1);
                Gview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.APlus:
                newValue = (Integer.parseInt(Aview.getText().toString()) + 1);
                Aview.setText(safeNewValue(newValue) + "");
                break;
            case R.id.AMinus:
                newValue = (Integer.parseInt(Aview.getText().toString()) - 1);
                Aview.setText(safeNewValue(newValue) + "");
                break;
        }

        changeColor();

    }

    private void changePresetColor()
    {
        loadAllValues();
        changeColor();
    }

    public void changePresetColor(View view)
    {
        int newValue;
        saveAllValues();

        switch (view.getId()) {
            case R.id.presetPlus:
                newValue = (Integer.parseInt(PresetView.getText().toString()) + 1);
                PresetView.setText(safePresetNewValue(newValue) + "");
                break;
            case R.id.presetMinus:
                newValue = (Integer.parseInt(PresetView.getText().toString()) - 1);
                PresetView.setText(safePresetNewValue(newValue) + "");
                break;
        }

        loadAllValues();
        changeColor();
    }

    private boolean checkPresetString(String s)
    {
        try {
            if (s.isEmpty())
                return false;
            return checkPresetInt(Integer.valueOf(s));
        }catch (Exception e){
            return false;
        }
    }

    private boolean checkPresetInt(int val)
    {
        if(val > presetMax || val < presetMin)
            return false;
        return true;
    }

    private boolean checkString(String s)
    {
        try {
            if (s.isEmpty())
                return false;
            return checkInt(Integer.valueOf(s));
        }catch (Exception e){
            return false;
        }
    }



    private boolean checkInt(int val)
    {
        if(val > max || val < min)
            return false;
        return true;
    }

    private String safeString(String s)
    {
        if(s.isEmpty())
            return (min + "");
        return Integer.toString(safeNewValue(Integer.valueOf(s)));
    }

    private int safeNewValue(int val)
    {
        if(val > max)
            val = max;
        else if (val < min)
            val = min;
        return val;
    }

    private String safePresetString(String s)
    {
        if(s.isEmpty())
            return (presetMin + "");
        return Integer.toString(safePresetNewValue(Integer.valueOf(s)));
    }

    private int safePresetNewValue(int val)
    {
        if(val > presetMax)
            val = presetMax;
        else if (val < presetMin)
            val = presetMin;
        return val;
    }

    public void hideButtons(View view)
    {
        if(Rview.getVisibility() == View.VISIBLE)
            setAllVisibility(View.GONE);
        else
            setAllVisibility(View.VISIBLE);
    }

    private void setAllVisibility(int vis)
    {
        Rview.setVisibility(vis);
        Gview.setVisibility(vis);
        Bview.setVisibility(vis);
        Aview.setVisibility(vis);
        PresetView.setVisibility(vis);
        Bplus.setVisibility(vis);
        Bminus.setVisibility(vis);
        Rplus.setVisibility(vis);
        Rminus.setVisibility(vis);
        Gplus.setVisibility(vis);
        Gminus.setVisibility(vis);
        Aplus.setVisibility(vis);
        Aminus.setVisibility(vis);
        presetPlus.setVisibility(vis);
        presetMinus.setVisibility(vis);
    }
}

