package group11.android.ntou.bosschuchu.ui;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import group11.android.ntou.bosschuchu.R;
import group11.android.ntou.bosschuchu.ui.adapter.AccountListAdapter;

public class AccountFragment extends Fragment {
    private MainActivity activity;
    private String userID;
    private String userName;
    private AccountListAdapter adapter;
    private ListView listView;
    private Button button;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity)activity;
        userID = this.activity.getUserID();
        userName = this.activity.getUserName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)activity.findViewById(R.id.account_list);
        adapter = new AccountListAdapter(activity, userID, userName);
        listView.setAdapter(adapter);
    }
}
