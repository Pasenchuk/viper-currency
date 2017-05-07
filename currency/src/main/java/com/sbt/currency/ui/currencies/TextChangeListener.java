package com.sbt.currency.ui.currencies;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Pasenchuk Victor on 07/05/2017
 */

public class TextChangeListener implements TextWatcher {

    private final TextListener listener;

    public TextChangeListener(TextListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (listener != null)
            listener.onTextChanged(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    interface TextListener {
        void onTextChanged(String s);
    }

}
