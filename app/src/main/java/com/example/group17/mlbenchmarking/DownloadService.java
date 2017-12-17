package com.example.group17.mlbenchmarking;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by naitikshah on 11/25/17.
 */

public class DownloadService extends FirstActivity{





    public static class DownloadFile extends AsyncTask<Void, Void, Boolean> {

        private String downloadFileName= "youtube.model";
        String path = (Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download/"+downloadFileName);



        @Override
        protected Boolean doInBackground(Void... params) {

            Log.d(path,"path");

            String urlOfServer = "http://10.0.2.2:80/MLBenchmarking/";
            InputStream inputStream = null;
            OutputStream outputStream = null;

            HttpURLConnection con = null;

            //Code to set trust Manager, used from website
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            } };

            try {
                SSLContext sc = SSLContext.getInstance("TLS");

                sc.init(null, trustAllCerts, new java.security.SecureRandom());

                //HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(urlOfServer+ downloadFileName);
                con = (HttpURLConnection) url.openConnection();
                con.connect();

                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return false;
                }

                inputStream = con.getInputStream();
                outputStream = new FileOutputStream(path);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                }
                if (total > 0) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
            finally
            {
                try {
                    if (outputStream != null)
                        outputStream.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ex) {
                    return false;
                }
                if (con != null)
                    con.disconnect();
            }
            return false;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
