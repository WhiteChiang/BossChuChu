package group11.android.ntou.bosschuchu.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import group11.android.ntou.bosschuchu.data.Group;
import group11.android.ntou.bosschuchu.R;
import group11.android.ntou.bosschuchu.data.GroupsHandler;
import group11.android.ntou.bosschuchu.ui.adapter.GroupsAdapter;

public class AllGroupsFragment extends Fragment implements AbsListView.OnScrollListener{
    private ListView listView;
    private ArrayList<Group> groups = new ArrayList<>();
    private GroupsAdapter adapter;
    private MainActivity activity;
    private FloatingActionButton floatingButton;
    private static Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity)activity;
        context = activity;
        groups = this.activity.getGroupsHandler().getGroups();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_groups_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        floatingButton = (FloatingActionButton) activity.findViewById(R.id.float_action_add);
        floatingButton.setAlpha(0.8f);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewGroupDialog(activity).show();
            }
        });

        listView = (ListView)activity.findViewById(R.id.all_groups_list);
        adapter = new GroupsAdapter(activity, groups);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new GroupDetailDialog(activity, groups.get(i)).show();
                System.out.println(groups.get(i).getParticipate());
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateView();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
            //userScrolled = true;
            floatingButton.setAlpha(0.25f);
        }
        else{
            floatingButton.setAlpha(0.8f);
        }
    }

    public void onScroll(AbsListView view, int firstVisible, final int visibleCount, int totalCount) {

    }

    public void updateView(){
        activity.getGroupsHandler().setUpAllGroups();
        groups = activity.getGroupsHandler().getGroups();

        adapter.updateGroups(groups);
    }

    static public void message(){
        new MessageDialog(context).show();
    }
}
