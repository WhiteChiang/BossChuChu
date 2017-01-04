package group11.android.ntou.bosschuchu.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import group11.android.ntou.bosschuchu.R;

public class ThreePartLoginActivity extends Activity {
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //初始化FacebookSdk，記得要放第一行，不然setContentView會出錯
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_three_part_login);

        if(isLogged()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userID", Profile.getCurrentProfile().getId());
            intent.putExtra("userName", Profile.getCurrentProfile().getName());
            startActivity(intent);
        }

        imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.chuchu);

        //宣告callback Manager

        callbackManager = CallbackManager.Factory.create();

        //找到login button

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        //幫loginButton增加callback function

        //這邊為了方便 直接寫成inner class
        System.out.println("3333333333333333333");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            //登入成功
            @Override
            public void onSuccess(LoginResult loginResult) {

                //System.out.println("TEST!!!!!!!!!!!!!!!!!!!!");

                //accessToken之後或許還會用到 先存起來

                accessToken = loginResult.getAccessToken();

                Log.d("FB","access token got.");

                //send request and call graph api

                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {

                            //當RESPONSE回來的時候

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                //讀出姓名 ID FB個人頁面連結

                                Log.d("FB","complete");
                                Log.d("FB",object.optString("name"));
                                Log.d("FB",object.optString("link"));
                                Log.d("FB",object.optString("id"));

                            }
                        });

                //包入你想要得到的資料 送出request

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();

            }

            //登入取消

            @Override
            public void onCancel() {
                // App code
                Log.d("FB","CANCEL");
            }

            //登入失敗

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("FB",exception.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(isLogged()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userID", Profile.getCurrentProfile().getId());
            intent.putExtra("userName", Profile.getCurrentProfile().getName());
            startActivity(intent);
        }
    }

    public static boolean isLogged() {
        Profile profile = Profile.getCurrentProfile().getCurrentProfile();
        if (profile != null) {
            return true;
        } else {
            return false;
        }
    }

    /*public static Bitmap getFacebookProfilePicture(String userID){

        Bitmap bitmap = null;
        try {
            URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        }catch(IOException ex){
            return bitmap;
    }

    Bitmap bitmap = getFacebookProfilePicture(userId);*/
}
