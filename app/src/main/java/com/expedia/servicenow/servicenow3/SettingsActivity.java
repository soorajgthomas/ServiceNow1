package com.expedia.servicenow.servicenow3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.expedia.servicenow.servicenow3.util.SharedPref;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox checkBox = (CheckBox) findViewById (R.id.checkBox);

        if(SharedPref.isFingerPrintSecurityEnabled(getApplicationContext())){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }



        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(getApplicationContext(), "checked", Toast.LENGTH_SHORT).show();
                    SharedPref.saveFingerPrintAuthenticationBool(getApplicationContext(),true);
                }
                else {
                    Toast.makeText(getApplicationContext(), "un-checked", Toast.LENGTH_SHORT).show();
                    SharedPref.saveFingerPrintAuthenticationBool(getApplicationContext(),false);
                }

            }
        });

    }
}
