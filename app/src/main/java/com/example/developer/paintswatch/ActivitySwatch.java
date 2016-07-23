package com.example.developer.paintswatch;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ActivitySwatch extends AppCompatActivity {
    private final int max = 255;
    private final int min = 0;
    private EditText Rview;
    private EditText Gview;
    private EditText Bview;
    private EditText Aview;
    private Button Bplus;
    private Button Bminus;
    private Button Rplus;
    private Button Rminus;
    private Button Gplus;
    private Button Gminus;
    private Button Aplus;
    private Button Aminus;
    private ToggleButton hideToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swatch);
        Rview = (EditText) findViewById(R.id.RTextView);
        Gview = (EditText) findViewById(R.id.GTextView);
        Bview = (EditText) findViewById(R.id.BTextView);
        Aview = (EditText) findViewById(R.id.ATextView);
        Rview.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "255")});
        Gview.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "255")});
        Bview.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "255")});
        Aview.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "255")});

        Bplus = (Button) findViewById(R.id.BPlus);
        Bminus = (Button) findViewById(R.id.BMinus);
        Rplus = (Button) findViewById(R.id.RPlus);
        Rminus = (Button) findViewById(R.id.RMinus);
        Gplus = (Button) findViewById(R.id.GPlus);
        Gminus = (Button) findViewById(R.id.GMinus);
        Aplus = (Button) findViewById(R.id.APlus);
        Aminus = (Button) findViewById(R.id.AMinus);
        hideToggle = (ToggleButton) findViewById(R.id.toggleButton);

        setTextChangeListener(Aview);
        setTextChangeListener(Rview);
        setTextChangeListener(Gview);
        setTextChangeListener(Bview);
        setLongListener(Bplus);
        setLongListener(Bminus);
        setLongListener(Rplus);
        setLongListener(Rminus);
        setLongListener(Gplus);
        setLongListener(Gminus);
        setLongListener(Aplus);
        setLongListener(Aminus);

        if(hideToggle.getText().toString().equals("OFF"))
            setAllVisibility(View.GONE);
        else
            setAllVisibility(View.VISIBLE);

         changeColor(Rview);
    }

    private void setLongListener(View view)
    {
        view.setOnTouchListener(new RepeatListener(400, 10, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(view);
            }
        }));
    }

    private void setTextChangeListener(final EditText eText)
    {
        eText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(eText.getText().toString().isEmpty() == false)
                    changeColor(eText);
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

            findViewById(R.id.activity_swatch).
                    setBackgroundColor(
                            Color.argb(
                                    Integer.parseInt(safeInt(Aview.getText().toString())),
                                    Integer.parseInt(safeInt(Rview.getText().toString())),
                                    Integer.parseInt(safeInt(Gview.getText().toString())),
                                    Integer.parseInt(safeInt(Bview.getText().toString()))
                            ));

    }

    private String safeInt(String s)
    {
        if(s.isEmpty())
            s = "0";
        return s;
    }

    private int safeNewValue(int val)
    {
        if(val > max)
            val = max;
        else if (val < min)
            val = min;
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
        Bplus.setVisibility(vis);
        Bminus.setVisibility(vis);
        Rplus.setVisibility(vis);
        Rminus.setVisibility(vis);
        Gplus.setVisibility(vis);
        Gminus.setVisibility(vis);
        Aplus.setVisibility(vis);
        Aminus.setVisibility(vis);
    }
}
