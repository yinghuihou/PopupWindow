package com.demo.popupwindowdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.demo.popupwindowdemo.R;

import androidx.appcompat.widget.AppCompatRadioButton;

public class ToggleRadioButton extends AppCompatRadioButton {

    public ToggleRadioButton(Context context) {
        this(context, null);
    }

    public ToggleRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.radioButtonStyle);
    }

    public ToggleRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
        if (!isChecked()) {
            ((RadioGroup) getParent()).clearCheck();
        }
    }
}
