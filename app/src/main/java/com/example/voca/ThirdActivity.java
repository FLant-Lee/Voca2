package com.example.voca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends Activity {

    Button backbtn2;
    Button d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocalist);

        Button backbtn = (Button) findViewById(R.id.backbtn2);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
