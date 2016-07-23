package com.example.developer.paintswatch;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ActivitySwatch extends AppCompatActivity {
    private TextView Rview;
    private TextView Gview;
    private TextView Bview;
    private TextView Aview;
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
        View thisView = findViewById(R.id.activity_swatch);
        //thisView.setBackgroundColor(Color.argb( 255,255,255, 255));
        Rview = (TextView) findViewById(R.id.RTextView);
        Gview = (TextView) findViewById(R.id.GTextView);
        Bview = (TextView) findViewById(R.id.BTextView);
        Aview = (TextView) findViewById(R.id.ATextView);
        Bplus = (Button) findViewById(R.id.BPlus);
        Bminus = (Button) findViewById(R.id.BMinus);
        Rplus = (Button) findViewById(R.id.RPlus);
        Rminus = (Button) findViewById(R.id.RMinus);
        Gplus = (Button) findViewById(R.id.GPlus);
        Gminus = (Button) findViewById(R.id.GMinus);
        Aplus = (Button) findViewById(R.id.APlus);
        Aminus = (Button) findViewById(R.id.AMinus);
        hideToggle = (ToggleButton) findViewById(R.id.toggleButton);

        setLongListener(Bplus);
        setLongListener(Bminus);
        setLongListener(Rplus);
        setLongListener(Rminus);
        setLongListener(Gplus);
        setLongListener(Gminus);

        if(hideToggle.getText().equals("off"))
            setAllVisibility(View.GONE);

         changeColor(Rview);
    }

    private void setLongListener(View view)
    {
        view.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(view);
            }
        }));
    }

    public void changeColor(View view)
    {
        int newValue;
        switch (view.getId()) {
            case R.id.BPlus:
                newValue = (Integer.parseInt(Bview.getText().toString()) + 1);
                Bview.setText(newValue + "");
                break;
            case R.id.BMinus:
                newValue = (Integer.parseInt(Bview.getText().toString()) - 1);
                Bview.setText(newValue + "");
                break;
            case R.id.RPlus:
                newValue = (Integer.parseInt(Rview.getText().toString()) + 1);
                Rview.setText(newValue + "");
                break;
            case R.id.RMinus:
                newValue = (Integer.parseInt(Rview.getText().toString()) - 1);
                Rview.setText(newValue + "");
                break;
            case R.id.GPlus:
                newValue = (Integer.parseInt(Gview.getText().toString()) + 1);
                Gview.setText(newValue + "");
                break;
            case R.id.GMinus:
                newValue = (Integer.parseInt(Gview.getText().toString()) - 1);
                Gview.setText(newValue + "");
                break;
            case R.id.APlus:
                newValue = (Integer.parseInt(Aview.getText().toString()) + 1);
                Aview.setText(newValue + "");
                break;
            case R.id.AMinus:
                newValue = (Integer.parseInt(Aview.getText().toString()) - 1);
                Aview.setText(newValue + "");
                break;
        }

            findViewById(R.id.activity_swatch).
                    setBackgroundColor(
                            Color.argb(
                                    Integer.parseInt(Aview.getText().toString()),
                                    Integer.parseInt(Rview.getText().toString()),
                                    Integer.parseInt(Gview.getText().toString()),
                                    Integer.parseInt(Bview.getText().toString())
                            ));

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
