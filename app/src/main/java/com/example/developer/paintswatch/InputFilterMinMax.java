package com.example.developer.paintswatch;


import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ?
                c >= a && c <= b :
                c >= b && c <= a;
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
}
