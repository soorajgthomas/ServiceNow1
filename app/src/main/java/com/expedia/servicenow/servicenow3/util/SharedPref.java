package com.expedia.servicenow.servicenow3.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SOORAJ on 13-11-2016.
 */

public class SharedPref {

    public static void saveAuthenticationDetails(Context context, String username,
                                                    String accessToken, String refreshToken){
        SharedPreferences pref = context.getSharedPreferences(Constants.SHARED_PREF_XML, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.USER_NAME, username);
        editor.putString(Constants.ACCES_TOKEN, accessToken);
        editor.putString(Constants.REFRESH_TOKEN, refreshToken);
        editor.commit();
    }

    public static String getData(Context context, String param){
        SharedPreferences pref = context.getSharedPreferences(Constants.SHARED_PREF_XML, MODE_PRIVATE);
        return (pref.getString(param, ""));
    }

    public static void removeData(Context context){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREF_XML, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.USER_NAME);
        editor.remove(Constants.ACCES_TOKEN);
        editor.remove(Constants.REFRESH_TOKEN);
        editor.commit();
    }

}
