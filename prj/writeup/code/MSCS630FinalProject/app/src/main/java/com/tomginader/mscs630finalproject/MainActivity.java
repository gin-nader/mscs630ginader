/**
 *  Code for how to send SMS message is taken from here: https://mobiforge.com/design-development/sms-messaging-android
 * */

package com.tomginader.mscs630finalproject;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// bouncy castle SHA3
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jcajce.provider.digest.*;



public class MainActivity extends AppCompatActivity {
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
    Button btnToInbox;

    public static Editable key;
    private boolean isInFront;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnToInbox = (Button) findViewById(R.id.btnToInbox);

        if(key == null) {
            inputKey();
        }


        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                String hmac = hashFunction(message, key.toString());
                if (phoneNo.length()>0 && message.length()>0)
                    sendSMS(phoneNo, message, hmac);
                else
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
            }
        });

        btnToInbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toInbox(v);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isInFront = true;
    }

    public static String hashFunction(String message, String k){
        String hmac = "";
        SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
        String pHmac = message + k;
        md.update(pHmac.getBytes());
        byte[] hash = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        hmac = sb.toString();
        return hmac;
    }

    private void sendSMS(String phoneNumber, String message, String hmac)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message + "\nHMAC:" + hmac, pi, null);
    }

    public void toInbox(View view) {
        Intent intent = new Intent(this, SmsReceiverActivity.class);
        startActivity(intent);
    }

    private void inputKey(){
        final EditText input = new EditText(MainActivity.this);
        String message = "Please enter a key: ";

        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        key = input.getText();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }
}