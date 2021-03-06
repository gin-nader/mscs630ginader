/**
 * Code for receiving SMS messages is derived from here:
 * http://javapapers.com/android/android-receive-sms-tutorial/
 */

package com.tomginader.mscs630finalproject;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.tomginader.mscs630finalproject.MainActivity.key;

public class SmsReceiverActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static SmsReceiverActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    static ArrayAdapter arrayAdapter;
    private static boolean isInFront;

    public static SmsReceiverActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receiver);
        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                                    smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);

        refreshSmsInbox();
    }

    @Override
    public void onResume() {
        super.onResume();
        isInFront = true;
    }

    public static boolean currentActivity(){
        return  isInFront;
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),
                                null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    public static void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String senderHmac = smsMessage.substring(smsMessage.lastIndexOf(":")+1);
            System.out.println(senderHmac);
            String receiverHmac = MainActivity.hashFunction(smsMessage.substring(0,
                                                  smsMessage.indexOf(":") - 4),key.toString());
            if(senderHmac.equals(receiverHmac)) {
                Toast.makeText(this, "This message is verified and has not been altered.",
                                                                    Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "This message is not verified and may have been altered!!",
                                                                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
