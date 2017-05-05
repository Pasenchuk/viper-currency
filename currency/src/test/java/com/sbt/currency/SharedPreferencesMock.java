package com.sbt.currency;

import android.content.SharedPreferences;

import org.mockito.Mock;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Pasenchuk Victor on 06/05/2017
 */

public class SharedPreferencesMock extends KeyValueMock<SharedPreferences.Editor> {
    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;


    public SharedPreferencesMock() {
        super();

        keyValueGetterMock(when(sharedPreferences.getBoolean(anyString(), anyBoolean())));
        keyValueGetterMock(when(sharedPreferences.getFloat(anyString(), anyFloat())));
        keyValueGetterMock(when(sharedPreferences.getInt(anyString(), anyInt())));
        keyValueGetterMock(when(sharedPreferences.getLong(anyString(), anyLong())));
        keyValueGetterMock(when(sharedPreferences.getString(anyString(), anyString())));

        when(sharedPreferences.edit()).thenReturn(editor);

        keyValueSetterMock(when(editor.putBoolean(anyString(), anyBoolean())), editor);
        keyValueSetterMock(when(editor.putFloat(anyString(), anyFloat())), editor);
        keyValueSetterMock(when(editor.putInt(anyString(), anyInt())), editor);
        keyValueSetterMock(when(editor.putLong(anyString(), anyLong())), editor);
        keyValueSetterMock(when(editor.putString(anyString(), anyString())), editor);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
