package group11.android.ntou.bosschuchu;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "Token:"+token);
    }
}