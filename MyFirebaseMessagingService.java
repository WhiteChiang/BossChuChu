package group11.android.ntou.bosschuchu;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import group11.android.ntou.bosschuchu.ui.AllGroupsFragment;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM", "onMessageReceived:"+remoteMessage.getData());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                AllGroupsFragment.message();
            }
        });


    }
}