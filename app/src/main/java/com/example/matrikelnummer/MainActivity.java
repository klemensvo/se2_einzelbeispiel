package com.example.matrikelnummer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
    String responseFromServer;
    String resultFromCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        matrikelnummerInput = (EditText) findViewById(R.id.matrikelnummerInput);
        showResponseFromServer = (TextView) findViewById(R.id.responseFromServer);
        showResponseFromServer = (TextView) findViewById(R.id.resultFromCalculation);

        sendToServerButton = (Button) findViewById(R.id.sendToServerButton);

        sendToServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrikelnummer = matrikelnummerInput.getText().toString();
                transmission();
                showResponseFromServer.setText(responseFromServer);

            }
        });

        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrikelnummer = matrikelnummerInput.getText().toString();
                resultFromCalculation = findAlternatingDigitSum(matrikelnummer);
                showResultFromCalculation.setText(resultFromCalculation);
            }
        });
    }

    private void transmission() {
        TCPClient tcpClient = new TCPClient();
        Thread thread = new Thread(tcpClient);
        tcpClient.setMatrikelnummer(matrikelnummer);
        thread.start();

        try {
            thread.join(); //todo set milliseconds?
            responseFromServer = tcpClient.getResult();
        } catch (InterruptedException ie) {
            System.out.println("Der Thread funktioniert nicht. Exception-Trace:\n");
            ie.printStackTrace();
        }

        // responseFromServer = tcpClient.getResult();
    }

    private String findAlternatingDigitSum(String matrikelnummer) {
        return "1100"; // todo implement function
    }

}
