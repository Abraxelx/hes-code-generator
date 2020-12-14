package com.abraxel.hes_kodu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HesSMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceived";
    static int counter;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent Received: " +intent.getAction());
        if(intent.getAction() == SMS_RECEIVED){
            setCounter(getCounter() + 1);
            if (getCounter() > 2){
                setCounter(0);
            }
        }

    }


    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        HesSMSReceiver.counter = counter;
    }
}