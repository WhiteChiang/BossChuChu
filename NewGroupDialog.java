package group11.android.ntou.bosschuchu.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import group11.android.ntou.bosschuchu.R;


public class NewGroupDialog implements DialogInterface.OnCancelListener, View.OnClickListener{

    private Context mContext;
    private MainActivity mainActivity;
    private Dialog mDialog;
    private Button submitButton;
    private Button dateButton;
    private Button timeButton;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RelativeLayout relativeLayout;
    private Calendar calendar;
    private Spinner spinner;
    private TextView dateText;
    private TextView timeText;
    private SeekBar numberSeekBar;
    private TextView seekBarText;
    private EditText remarkEditText;
    private EditText placeEditText;

    NewGroupDialog(Context context){
        this.mContext = context;
        mainActivity = (MainActivity)context;
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
    }

    public NewGroupDialog show(){
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_new_group);

        // 點邊取消
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);


        mDialog.setOnCancelListener(this);
        mDialog.show();

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

        // 表單面板
        relativeLayout = (RelativeLayout)mDialog.findViewById(R.id.dialog_form);
        submitButton = (Button)mDialog.findViewById(R.id.submit);
        spinner = (Spinner) mDialog.findViewById(R.id.spinner_type);
        numberSeekBar = (SeekBar) mDialog.findViewById(R.id.seekBar);
        seekBarText = (TextView) mDialog.findViewById(R.id.seekBar_text);
        placeEditText = (EditText) mDialog.findViewById(R.id.edit_place);

        submitButton.setBackgroundResource(R.drawable.sign_out_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("submit");
                Date date = new Date();
                date.setYear(datePicker.getYear());
                date.setMonth(datePicker.getMonth());
                date.setDate(datePicker.getDayOfMonth());
                date.setHours(timePicker.getHour());
                date.setMinutes(timePicker.getMinute());

                mainActivity.getGroupsHandler().newGroup(mainActivity.getUserID(), spinner.getSelectedItem().toString(), date, placeEditText.getText().toString(), numberSeekBar.getProgress()+2, remarkEditText.getText().toString());
                onCancel(mDialog);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.type,
                R.layout.spinner_item);
        spinner.setAdapter(adapter);

        numberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarText.setText(String.valueOf(i+2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        remarkEditText = (EditText) mDialog.findViewById(R.id.remark_editText);
        remarkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(remarkEditText.getLineCount()>6){
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 選擇日期
        dateButton = (Button)mDialog.findViewById(R.id.button_pick_date);
        dateText = (TextView)mDialog.findViewById(R.id.text_date);
        datePicker = (DatePicker)mDialog.findViewById(R.id.datePicker);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.GONE);
                dateButton.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                dateText.setText(datePicker.getYear() + " / " + String.valueOf(datePicker.getMonth()+1) + " / " + datePicker.getDayOfMonth());
            }
        });

        dateText.setText(calendar.get(Calendar.YEAR) + " / " + String.valueOf(calendar.get(Calendar.MONTH)+1) + " / " + calendar.get(Calendar.DAY_OF_MONTH));
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.VISIBLE);
                dateButton.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
            }
        });


        // 選擇時間
        timeButton = (Button) mDialog.findViewById(R.id.button_pick_time);
        timePicker = (TimePicker) mDialog.findViewById(R.id.timePicker);
        timeText = (TextView)mDialog.findViewById(R.id.text_time);

        timeText.setText("17 : 30");
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setVisibility(View.VISIBLE);
                timeButton.setVisibility(View.VISIBLE);

                relativeLayout.setVisibility(View.GONE);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setVisibility(View.GONE);
                timeButton.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                timeText.setText(timePicker.getCurrentHour() + " : " + timePicker.getCurrentMinute());
            }
        });



    }
}