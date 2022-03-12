package com.example.matrikelnummer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

// import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText enterMatrikelnummer;
    EditText showResponseFromServer;
    // Button sendToServerButton; // todo use later
    Button go;

    String matrikelnummer;
    String responseFromServer;
    // String resultFromCalculation; // todo use later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterMatrikelnummer = findViewById(R.id.matrikelnummer);
        go = findViewById(R.id.go);
        showResponseFromServer = findViewById(R.id.responseFromServer);

        go = (Button) findViewById(R.id.go);
        go.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrikelnummer = enterMatrikelnummer.getText().toString();
                transmission();
                showResponseFromServer.setText(responseFromServer);

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
        } catch (InterruptedException ie) {
            System.out.println("Der Thread funktioniert nicht. Exception-Trace:\n");
            ie.printStackTrace();
        }

        responseFromServer = tcpClient.getResult();
    }


}
