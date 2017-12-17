package com.example.group17.mlbenchmarking;

/**
 * Created by naitikshah on 11/18/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MLP extends Activity {

    private static TextView MLPtextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mlp);

        final Button MLPtrain = (Button) findViewById(R.id.MLPTrain);
        MLPtrain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WRITE training CODE

            }
        });

        final Button MLPDownload = (Button) findViewById(R.id.MLPDownload);
        MLPDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WRITE Download Model Code

            }
        });

        final Button MLPTest = (Button) findViewById(R.id.MLPTest);
        MLPTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //WRITE Testing code

            }
        });




    }
}