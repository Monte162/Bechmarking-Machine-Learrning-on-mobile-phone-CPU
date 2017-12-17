package com.example.group17.mlbenchmarking;

/**
 * Created by naitikshah on 11/18/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class SupportVectorMachine extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svm);

        final Button SVMTrain = (Button) findViewById(R.id.SVMTrain);
        SVMTrain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WRITE training CODE

            }
        });

        final Button SVMDownload = (Button) findViewById(R.id.SVMDownload);
        SVMDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WRITE Download Model Code

            }
        });

        final Button SVMTest = (Button) findViewById(R.id.SVMTest);
        SVMTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WRITE Testing code

            }
        });


    }
}