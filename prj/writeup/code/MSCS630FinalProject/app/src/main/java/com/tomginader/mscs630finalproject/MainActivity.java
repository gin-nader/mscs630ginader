/**
 *  Code for how to send SMS message is taken from here: https://mobiforge.com/design-development/sms-messaging-android
 * */

package com.tomginader.mscs630finalproject;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
    Button btnToInbox;
    public static int key = 123456789;
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



        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String phoneNo = txtPhoneNo.getText().toString();
                String message = txtMessage.getText().toString();
                int hmac = hashFunction(message, key);
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

    public static int hashFunction(String message, int key){
        int intMessage = 0;
        for(int i = 0; i < message.length(); i++){
            intMessage += message.charAt(i);
        }
        int preHmac = intMessage + key;
        int hmac = preHmac * 2 + 49 * 3 / 7;

        return hmac;
    }

    private void sendSMS(String phoneNumber, String message, int hmac)
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
}