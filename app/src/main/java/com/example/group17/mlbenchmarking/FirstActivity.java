package com.example.group17.mlbenchmarking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirstActivity extends AppCompatActivity {
    SeekBar seekBar1;
    TextView textView01;
    TextView textView02;
    static TextView textView03;

    TextView messageText;
    Button uploadButton;

    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = null;

    /**********  File Path *************/
    final String uploadFilePath = (Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/Download/");
    final String uploadFileName = "youtube1.arff";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        seekvalue();
        uploadButton = (Button) findViewById(R.id.UploadDatabase);
        messageText = (TextView) findViewById(R.id.textView04);



        final Button UploadDatabase = (Button) findViewById(R.id.UploadDatabase);

//        messageText.setText("Uploading file path :- '/mnt/sdcard/" + uploadFileName + "'");


        /************* Php script path ****************/
        upLoadServerUri = "http://10.0.2.2:80/MLBenchmarking/index.php";
        Log.d("upload",upLoadServerUri);


        UploadDatabase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                UploadService cls2 = new UploadService();
//                cls2.uploadFile(uploadFilePath + "" + uploadFileName,uploadFilePath,uploadFileName);

                new UploadService.UploadFile().execute(uploadFilePath,uploadFileName);

                TextView theFact = (TextView) findViewById(R.id.textView03);
                String shareFact = theFact.getText().toString();

                String filename = "dividesetcommand";
                String filecontent = "java -cp \"wekaSTRIPPED.jar\" weka.filters.unsupervised.instance.RemovePercentage -P " + shareFact +" -i \"youtube1.arff\" -o train.arff";

                FileOperations fop = new FileOperations();
                fop.write(filename, filecontent);
                if(fop.write(filename, filecontent)){
                    Toast.makeText(getApplicationContext(), filename+".txt created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();

                }

                new UploadService.UploadFile().execute(uploadFilePath,filename+".txt");

//                dialog = ProgressDialog.show(FirstActivity.this, "", "Uploading file...", true);

//                new Thread(new Runnable() {
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                messageText.setText("uploading started.....");
//                            }
//                        });
//
//                    }
//                }).start();

            }
        });


        Button SecondPageButton = (Button)findViewById(R.id.button);
        SecondPageButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try {

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(getAssets().open("youtube1.arff")));
                    Instances inst = new Instances(reader);
                    int percent =  Integer.valueOf(textView03.getText().toString());
                    int trainSize = (int) Math.round(inst.numInstances() * percent / 100);
                    int testSize = inst.numInstances() - trainSize;
                    Instances train = new Instances(inst, 0, trainSize);
                    Instances test = new Instances(inst, trainSize, testSize);
                    int number1 = train.numInstances();
                    int number = test.numInstances();

                    TextView txtview = (TextView)findViewById(R.id.textView04);
                    txtview.setText("Training Instances:"+ Integer.toString(number1) + "Testing Instances " + Integer.toString(number));
                    Classifier cls = (Classifier) SerializationHelper.read(getAssets().open("youtube1.model"));
                    reader.close();
                    test.setClassIndex(test.numAttributes() - 1);
                    Evaluation eval = new Evaluation(test);
                    eval.evaluateModel(cls, test);
                    System.out.println(eval.toSummaryString("\nResults\n======\n", false));
                } catch (Exception e) { // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        });



        

        final Button SVMButton = (Button) findViewById(R.id.SVMButton);
        SVMButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(FirstActivity.this, SupportVectorMachine.class);

                // currentContext.startActivity(activityChangeIntent);

                FirstActivity.this.startActivity(activityChangeIntent);
            }
        });

        final Button MLPButton = (Button) findViewById(R.id.MLPButton);
        MLPButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(FirstActivity.this, MLP.class);

                // currentContext.startActivity(activityChangeIntent);

                FirstActivity.this.startActivity(activityChangeIntent);
            }
        });

        final Button RandomForestButton = (Button) findViewById(R.id.RandomForestButton);
        RandomForestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(FirstActivity.this, RandomForest.class);

                // currentContext.startActivity(activityChangeIntent);

                FirstActivity.this.startActivity(activityChangeIntent);
            }
        });

    }

    public void seekvalue() {
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        textView03 = (TextView) findViewById(R.id.textView03);
        textView03.setText("" + seekBar1.getProgress());


        seekBar1.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textView03.setText("" + progress);
//                        Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
//                        Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView03.setText("" + progress_value);

//                        Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

}