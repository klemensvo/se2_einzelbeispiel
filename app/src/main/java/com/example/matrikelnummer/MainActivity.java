package com.example.matrikelnummer;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Debug Info";
    EditText matrikelnummerInput;
    TextView showResultFromServer;
    TextView showResultFromCalculation;
    Button sendToServerButton;
    Button calculateButton;

    String matrikelnummer;
    String resultFromServer = "Server did not reply"; // default, if no reply
    String resultFromCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrikelnummerInput = (EditText) findViewById(R.id.matrikelnummerInput);
        showResultFromServer = (TextView) findViewById(R.id.resultFromServer);
        showResultFromCalculation = (TextView) findViewById(R.id.resultFromCalculation);

        sendToServerButton = (Button) findViewById(R.id.sendToServerButton);
        sendToServerButton.setOnClickListener(view -> { // lambda, entspricht new View.onClickListener() { ... }
            matrikelnummer = matrikelnummerInput.getText().toString();
            Log.d(TAG, "Before startTransmission");
            startTransmission();
            Log.d(TAG, "After startTransmission");
            showResultFromServer.setText(resultFromServer);
            // showResponseFromServer.setText(matrikelnummer);
        });

        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(view -> {
            matrikelnummer = matrikelnummerInput.getText().toString();
            resultFromCalculation = findAlternatingDigitSum(matrikelnummer);
            showResultFromCalculation.setText(resultFromCalculation);
        });
    }

    private void startTransmission() {

        TCPClient tcpClient = new TCPClient();
        Thread thread = new Thread(tcpClient);
        tcpClient.setMatrikelnummer(matrikelnummer);
        thread.start();

        Log.d(TAG, "Before try");
        try {
            Log.d(TAG, "Before thread.join()");
            thread.join(); //todo set milliseconds?
            Log.d(TAG, "After thread.join()");
            // responseFromServer = tcpClient.getResult();
        } catch (InterruptedException ie) {
            System.out.println("Der Thread funktioniert nicht. Exception-Trace:\n");
            ie.printStackTrace();
        }
        Log.d(TAG, "After catch");

        resultFromServer = tcpClient.getResult();
    }

    private String findAlternatingDigitSum(String matrikelnummer) {
        int sum = 0;

        return "1100"; // todo implement function
    }

}
