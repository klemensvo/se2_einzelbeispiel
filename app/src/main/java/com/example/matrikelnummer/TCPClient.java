package com.example.matrikelnummer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient implements Runnable {
    String matrikelnummer;
    String result;

    @Override
    public void run() {
        String DOMAIN_NAME = "se2-isys.aau.at";
        int PORT = 53212;

        Socket clientSocket;
        DataOutputStream outToServer;
        BufferedReader inFromServer;

        try {
            clientSocket = new Socket(DOMAIN_NAME, PORT);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(matrikelnummer + "\n"); //every Input must be a line and end with \n
            result = inFromServer.readLine();
            System.out.println(result);

            outToServer.flush();
            clientSocket.close();

        } catch (IOException IOEx) {
            System.out.println("Es konnte keine Verbindung zum Server aufgebaut werden, Exception-Trace:\n");
            IOEx.printStackTrace();
        }
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public String getResult() {
        return result;
    }
}
