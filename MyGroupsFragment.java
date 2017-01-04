package group11.android.ntou.bosschuchu.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import group11.android.ntou.bosschuchu.data.Group;
import group11.android.ntou.bosschuchu.R;
import group11.android.ntou.bosschuchu.ui.adapter.GroupsAdapter;

public class MyGroupsFragment extends Fragment {
    private ListView listView;
    private ArrayList<Group> groups = new ArrayList<>();
    private GroupsAdapter adapter;
    private MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity)activity;

        groups = this.activity.getGroupsHandler().getUserGroups(this.activity.getUserID());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_groups_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*groups.add(new Group());
        groups.add(new Group());*/

        listView = (ListView)activity.findViewById(R.id.my_groups_list);
        adapter = new GroupsAdapter(activity, groups);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new GroupDetailDialog(activity, groups.get(i)).show();
                System.out.println(groups.get(i).getParticipate());
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.refresh_layout2);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //updateView();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void updateView(){
        activity.getGroupsHandler().setUpAllGroups();
        groups = activity.getGroupsHandler().getUserGroups(activity.getUserID());

        adapter.updateGroups(groups);
    }
}
