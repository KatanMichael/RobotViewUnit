package com.max.michael.robotviewunit.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectTask extends AsyncTask<String, Void, Void>
{

    public static String TAG = "ConnectTask";
    private Socket socket;
    private ObjectOutputStream out;
    private boolean connected;
    private String payload;


    private Context context;

    public ConnectTask(Context context)
    {
        this.context = context;
    }

    public ConnectTask setPayload(String payload)
    {
        this.payload = payload;
        return this;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        if (connected) {
        }
        super.onPostExecute(result);
    }

    private String host;
    private int port;

    @Override
    protected Void doInBackground(String... params) {
        try {
            String host = params[0];
            int port = 12345;
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(payload);
        } catch (UnknownHostException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


}