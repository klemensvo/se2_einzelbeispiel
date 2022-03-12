package com.example.matrikelnummer;

import androidx.appcompat.app.AppCompatActivity;

// import android.view.View; // durch Lambda-Funktion ersetzt
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText matrikelnummerInput;
    TextView showResponseFromServer;
    TextView showResultFromCalculation;
    Button sendToServerButton;
    Button calculateButton;

    String matrikelnummer;
    String responseFromServer = "Server did not reply"; // default, if no reply
    String resultFromCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrikelnummerInput = (EditText) findViewById(R.id.matrikelnummerInput);
        showResponseFromServer = (TextView) findViewById(R.id.responseFromServer);
        showResultFromCalculation = (TextView) findViewById(R.id.resultFromCalculation);

        sendToServerButton = (Button) findViewById(R.id.sendToServerButton);
        sendToServerButton.setOnClickListener(view -> { // lambda, entspricht new View.onClickListener() { ... }
            matrikelnummer = matrikelnummerInput.getText().toString();
            startTransmission();
            showResponseFromServer.setText(responseFromServer);
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

        try {
            thread.join(); //todo set milliseconds?
            // responseFromServer = tcpClient.getResult();
        } catch (InterruptedException ie) {
            System.out.println("Der Thread funktioniert nicht. Exception-Trace:\n");
            ie.printStackTrace();
        }

        responseFromServer = tcpClient.getResult();
    }

    private String findAlternatingDigitSum(String matrikelnummer) {
        return "1100"; // todo implement function
    }

}
