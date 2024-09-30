package com.activitytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AverageStatsActivity extends AppCompatActivity {
    private TextView averageStatsTextView;
    private Button backButton;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_stats);

        averageStatsTextView = findViewById(R.id.averageStatsTextView);
        backButton = findViewById(R.id.button_back_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        new FetchAverageStatsTask().execute();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AverageStatsActivity.this, ProfileStatsActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    private class FetchAverageStatsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String averageStats = "";
            try {
                String host = "10.0.2.2";
                int port = 12346;
                Socket socket = new Socket(host, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                // Send the request type
                out.writeObject("AVERAGE_STATS_REQUEST");

                // Send the username
                out.writeObject(username);

                // Read the response
                Object response = in.readObject();

                if (response instanceof String) {
                    averageStats = (String) response;

                } else {
                    Log.e("AverageStatsActivity", "Unexpected response received: " + response.toString());
                }

                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                Log.e("AverageStatsActivity", "Error sending request: " + e.getMessage());
            }
            return averageStats;
        }

        @Override
        protected void onPostExecute(String averageStats) {
            if (averageStats != null && !averageStats.isEmpty()) {
                averageStatsTextView.setText(averageStats);
            } else {
                averageStatsTextView.setText("An error occurred while retrieving average stats.");
            }
        }
    }
}
