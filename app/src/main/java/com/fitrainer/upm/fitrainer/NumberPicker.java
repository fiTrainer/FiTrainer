package com.fitrainer.upm.fitrainer;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by abel on 11/12/16.
 */

public class NumberPicker {
    private int value=0;

    private Button btnIncr;
    private Button btnDecr;
    private EditText editText;

    public NumberPicker(Button btnIncr, Button btnDecr, EditText editText) {
        this.btnIncr=btnIncr;
        this.btnDecr=btnDecr;
        this.editText=editText;
        this.btnIncr.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                value++;
                NumberPicker.this.editText.setText(String.valueOf(value));
            }
        });

        this.btnDecr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value--;
                NumberPicker.this.editText.setText(String.valueOf(value));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                value=Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }



    public int getValue() {
        return value;
    }
}