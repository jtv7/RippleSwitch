package com.jtv7.rippleswitch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jtv7.rippleswitchlib.RippleSwitch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RippleSwitch rippleSwitch = findViewById(R.id.rippleSwitch);
        rippleSwitch.setOnCheckedChangeListener(new RippleSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckChanged(boolean checked) {

                if (checked) {
                    Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
