package com.vilyever.howtocustomlayoutmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

/**
 * copy from https://github.com/devunwired/recyclerview-playground/blob/master/app/src/main/java/com/example/android/recyclerplayground/NumberPickerDialog.java
 */
public class NumberPickerDialog extends AlertDialog implements DialogInterface.OnClickListener {

    public interface OnNumberSelectedListener {
        void onNumberSelected(int value);
    }

    private NumberPicker mPicker;
    private OnNumberSelectedListener mNumberSelectedListener;

    public NumberPickerDialog(Context context) {
        super(context);
        mPicker = new NumberPicker(context);
        mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    protected NumberPickerDialog(Context context, int theme) {
        super(context, theme);
    }

    protected NumberPickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setButton(BUTTON_NEGATIVE, getContext().getString(android.R.string.cancel), this);
        setButton(BUTTON_POSITIVE, getContext().getString(android.R.string.ok), this);
        setView(mPicker);

        //Install contents
        super.onCreate(savedInstanceState);
    }

    public void setOnNumberSelectedListener(OnNumberSelectedListener listener) {
        mNumberSelectedListener = listener;
    }

    public void setPickerRange(int minValue, int maxValue) {
        mPicker.setMinValue(minValue);
        mPicker.setMaxValue(maxValue);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_POSITIVE && mNumberSelectedListener != null) {
            mNumberSelectedListener.onNumberSelected(mPicker.getValue());
        }
    }
}
