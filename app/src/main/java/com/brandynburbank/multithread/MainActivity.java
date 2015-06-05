package com.brandynburbank.multithread;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity {

    private final String FILENAME = "theFile.txt";

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    private Context context = getApplicationContext();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void writeFileHandler(View v) {
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgressStatus = 0;

        final File file = new File(context.getFilesDir(), FILENAME);

        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 10) {
                    mProgressStatus++;

                    try {
                        BufferedWriter buf = new BufferedWriter(new FileWriter(file));
                        buf.write("Wheat");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep((long) 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus * 10);
                        }
                    });
                }
            }
        }).start();

        file.Close();

    }

    public void readFileHandler(View v) {
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgressStatus = 0;

        final File file = new File(context.getFilesDir(), FILENAME);

        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 10) {
                    mProgressStatus++;

                    try {
                        BufferedReader buf = new BufferedReader(new FileReader(file));
                        System.out.println(buf.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep((long) 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus * 10);
                        }
                    });
                }
            }
        }).start();

        file.close();

    }
}
