package com.example.group17.mlbenchmarking;

/**
 * Created by naitikshah on 11/18/17.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;


public class RandomForest extends FirstActivity {

    private static TextView RFtextView;
    final String uploadFilePath = (Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/Download/");
    String upLoadServerUri = "http://10.0.2.2:80/MLBenchmarking/file2.php";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.randomforest);


        final Button SetParameters = (Button) findViewById(R.id.SetParameters);



        SetParameters.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                String filename1 = "trainsetcommand";
                String filecontent1 = "java -cp \"wekaSTRIPPED.jar\" weka.classifiers.trees.RandomForest -I 10 -K 0 -S 1 -t \"train.arff\" -d youtube.model";

                FileOperations fop1 = new FileOperations();
                fop1.write(filename1, filecontent1);
                if(fop1.write(filename1, filecontent1)){
                    Toast.makeText(getApplicationContext(), filename1+".txt created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();

                }
                String filename3 = "trainsetcommand.txt";
                new UploadService.UploadFile().execute(uploadFilePath,filename3);




            }
        });

        final Button RFtrain = (Button) findViewById(R.id.RFTrain);

        final Button RFTrain = (Button) findViewById(R.id.RFTrain);
        RFTrain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new PHPFileExecutor.updateData().execute(upLoadServerUri);

            }
        });




        final Button RFDownload = (Button) findViewById(R.id.RFDownload);
        RFDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new PHPFileExecutor.updateData().execute(upLoadServerUri);


                new DownloadService.DownloadFile().execute();


            }
        });



        Button RFTest = (Button)findViewById(R.id.RFTest);
        RFTest.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try {

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(getAssets().open("youtube1.arff")));
                    Instances inst = new Instances(reader);
                    int percent =  Integer.valueOf(FirstActivity.textView03.getText().toString());
                    int trainSize = (int) Math.round(inst.numInstances() * percent / 100);
                    int testSize = inst.numInstances() - trainSize;
                    Instances train = new Instances(inst, 0, trainSize);
                    Instances test = new Instances(inst, trainSize, testSize);
                    int number1 = train.numInstances();
                    int number = test.numInstances();


//                    File train_location= new File(Environment.getExternalStorageDirectory().getPath() + "/download/train.arff");
//                    BufferedWriter writer1 = new BufferedWriter(new FileWriter(new File(String.valueOf(train_location))));
//                    writer1.write(train.toString());
//
//                    File test_location= new File(Environment.getExternalStorageDirectory().getPath() + "/download/test.arff");
//                    BufferedWriter writer2 = new BufferedWriter(new FileWriter(new File(String.valueOf(test_location))));
//                    writer1.write(train.toString());



                    TextView txtview = (TextView)findViewById(R.id.RFAnswers);
                    txtview.setText(Integer.toString(number1) + " " + Integer.toString(number));
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/download/youtube.model"));

                    Classifier cls = (Classifier) ois.readObject();
                    ois.close();

                    reader.close();
                    test.setClassIndex(test.numAttributes() - 1);
                    Evaluation eval = new Evaluation(test);
                    eval.evaluateModel(cls, test);
                    System.out.println(eval.toSummaryString("\nResults\n======\n", false));

                    txtview.setText(eval.toSummaryString("Results:\n", false));
                } catch (Exception e) { // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }

        });

    }


}


