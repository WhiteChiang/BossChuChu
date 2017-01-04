package group11.android.ntou.bosschuchu.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import group11.android.ntou.bosschuchu.ProfilePictureView;
import group11.android.ntou.bosschuchu.R;
import group11.android.ntou.bosschuchu.data.Group;


public class GroupDetailDialog implements DialogInterface.OnCancelListener, View.OnClickListener{

    private Context mContext;
    private MainActivity mainActivity;
    private Dialog mDialog;
    private Group group;
    private ImageView typeIcon;
    private TextView typeText;
    private TextView dateText;
    private TextView timeText;
    private TextView placeText;
    private TextView userNameText;
    private TextView remarkText;
    private TextView numberText;
    private Button button;

    private final int ADD = 0;
    private final int DELETE = 1;

    GroupDetailDialog(Context context, Group group){
        this.mContext = context;
        mainActivity = (MainActivity)context;
        this.group = group;
    }

    public GroupDetailDialog show(){
        System.out.println(group.getOrder());
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_group_detail);

        // 點邊取消
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);


        mDialog.setOnCancelListener(this);
        mDialog.show();

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setUp();

        typeIcon = (ImageView) mDialog.findViewById(R.id.detail_type_icon);
        typeIcon.setImageResource(transferResource(group.getType()));

        dateText = (TextView) mDialog.findViewById(R.id.detail_date_text);
        dateText.setText((group.getTime().getYear()-11) + " / " + (group.getTime().getMonth()+1) + " / " + group.getTime().getDate());

        timeText = (TextView) mDialog.findViewById(R.id.detail_time_text);
        timeText.setText(group.getTime().getHours() + " : " + (group.getTime().getMinutes()<10? "0"+group.getTime().getMinutes() : group.getTime().getMinutes()));

        typeText = (TextView) mDialog.findViewById(R.id.typeText_detail);
        typeText.setText(group.getType());

        placeText = (TextView) mDialog.findViewById(R.id.placeText_detail);
        placeText.setText(group.getPlace());

        remarkText = (TextView) mDialog.findViewById(R.id.remarkText_detail);
        remarkText.setText(group.getRemark());

        numberText = (TextView) mDialog.findViewById(R.id.numberText_detail);
        numberText.setText(group.getNowNumber()+" / "+group.getMaxNumber());

        ProfilePictureView profilePictureView = (ProfilePictureView)mDialog.findViewById(R.id.FBImage_detail);
        profilePictureView.setProfileId(group.getPromoterID());

        userNameText = (TextView) mDialog.findViewById(R.id.name_detail);
        userNameText.setText(group.getPromoterName());

        button = (Button) mDialog.findViewById(R.id.button_participate);

        button.setText((group.getParticipate()&&group.getOrder()==0) ? "已加入" : ( group.getNowNumber()==group.getMaxNumber() ? "額滿"+(group.getOrder()>0? " (排"+ group.getOrder() +")": "") : "加入" ));
        button.setBackgroundResource((group.getParticipate()&&group.getOrder()==0) ? R.drawable.bg_button_participated : ( group.getNowNumber()==group.getMaxNumber() ? R.drawable.bg_button_participated_full : R.drawable.bg_button_un_participated));
        setButtonListener();

        return this;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mainActivity.getAllGroupsFragment().updateView();
        mDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
    }

    public void setUp(){
    }

    public void setButtonListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group.getParticipate()) {
                    if(group.getOrder()==0) {
                        group.setParticipate(false);
                        group.participate(DELETE);
                        numberText.setText(group.getNowNumber() + " / " + group.getMaxNumber());

                        mainActivity.getGroupsHandler().participate("delete", group.getGroupID(), mainActivity.getUserID());
                        Toast.makeText(mainActivity, "已從揪團中退出", Toast.LENGTH_SHORT).show();
                        setButtonListener();
                    }
                    else if(group.getOrder()>0){
                        group.setParticipate(false);
                        mainActivity.getGroupsHandler().participate("delete", group.getGroupID(), mainActivity.getUserID());
                        group.setOrder(-1);
                        Toast.makeText(mainActivity, "已從揪團中取消排隊", Toast.LENGTH_SHORT).show();
                        setButtonListener();
                    }
                }
                else {
                    if (group.getNowNumber() == group.getMaxNumber()) {
                        group.setParticipate(true);
                        int queueNumber = mainActivity.getGroupsHandler().participate("queue", group.getGroupID(), mainActivity.getUserID());
                        group.setOrder(queueNumber);
                        Toast.makeText(mainActivity, "已成功排隊此揪團", Toast.LENGTH_SHORT).show();
                        setButtonListener();
                    }
                    else {
                        group.setParticipate(true);
                        group.setOrder(0);
                        group.participate(ADD);
                        numberText.setText(group.getNowNumber()+" / "+group.getMaxNumber());

                        mainActivity.getGroupsHandler().participate("add", group.getGroupID(), mainActivity.getUserID());
                        Toast.makeText(mainActivity, "已成功加入此揪團", Toast.LENGTH_SHORT).show();
                        setButtonListener();
                    }
                }
                button.setText((group.getParticipate()&&group.getOrder()==0) ? "已加入" : ( group.getNowNumber()==group.getMaxNumber() ? "額滿"+(group.getOrder()>0? " (排"+ group.getOrder() +")": "") : "加入" ));
                button.setBackgroundResource((group.getParticipate()&&group.getOrder()==0) ? R.drawable.bg_button_participated : ( group.getNowNumber()==group.getMaxNumber() ? R.drawable.bg_button_participated_full : R.drawable.bg_button_un_participated));
            }
        });
    }

    public int transferResource(String type){
        switch(type){
            case "排球":
                return R.drawable.volleyball;
            case "網球":
                return R.drawable.tennis;
            case "重訓":
                return R.drawable.training;
            case "籃球":
                return R.drawable.basketball;
            default:
                return 9487;
        }
    }
}