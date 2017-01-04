package group11.android.ntou.bosschuchu.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import group11.android.ntou.bosschuchu.ProfilePictureView;
import group11.android.ntou.bosschuchu.R;
import group11.android.ntou.bosschuchu.data.Group;


public class MessageDialog implements DialogInterface.OnCancelListener, View.OnClickListener{

    private Context mContext;
    private MainActivity mainActivity;
    private Dialog mDialog;

    public MessageDialog(Context context){
        this.mContext = context;
    }

    public MessageDialog show(){
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_message);

        // 點邊取消
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);


        mDialog.setOnCancelListener(this);
        mDialog.show();

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setUp();

        return this;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
    }

    public void setUp(){
    }


}