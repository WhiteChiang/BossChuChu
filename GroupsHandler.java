package group11.android.ntou.bosschuchu.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GroupsHandler {
    private String userID;
    private ArrayList<Group> groups = new ArrayList<>();
    private int queueNumberTemp;

    public GroupsHandler(String userID){
        this.userID = userID;
    }

    public void setUpAllGroups()
    {
        groups.clear();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 建立連線
                    URL url = new URL("http://140.121.196.20:7780/BOSSCHU/GroupsServlet?userID="+userID);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(6000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    // 讀取資料
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String jsonData = reader.readLine();
                    JSONArray response = new JSONArray(jsonData);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);

                        Date date = formatter.parse(jsonObject.getString("time"));
                        Group group = new Group(
                                jsonObject.getString("group_ID"),
                                jsonObject.getString("promoter_ID"),
                                jsonObject.getString("promoter_name"),
                                jsonObject.getString("type"),
                                date,
                                jsonObject.getString("place"),
                                jsonObject.getString("remark"),
                                jsonObject.getInt("maxNumber"),
                                jsonObject.getInt("count"),
                                jsonObject.getBoolean("participate"),
                                jsonObject.getInt("order")
                        );

                        groups.add(group);
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            thread.start();
            thread.join(15000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    public void getGroupsByUserID(final String userID)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 建立連線
                    URL url = new URL("http://140.121.196.20:7780/BOSSCHU/GroupsServlet?userID='"+ userID +"'");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(6000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    // 讀取資料
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String jsonData = reader.readLine();
                    JSONArray response = new JSONArray(jsonData);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Group group = new Group(
                                jsonObject.getString("group_ID"),
                                jsonObject.getString("promoter_ID"),
                                jsonObject.getString("promoter_name"),
                                jsonObject.getString("type"),
                                jsonObject.getString("time"),
                                jsonObject.getString("place"),
                                jsonObject.getString("remark"),
                                jsonObject.getInt("maxNumber"),
                                jsonObject.getInt("nowNumber"),
                                jsonObject.getBoolean("participate")
                        );

                        groups.add(group);
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            thread.start();
            thread.join(15000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public void newGroup(final String promoterID, final String type, final Date time, final String place, final int maxNumber, final String remark)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(time);

                    System.out.println("!!!!!!!!!!!!!" + time.getDate());
                    // 建立連線
                    URL url = new URL("http://140.121.196.20:7780/BOSSCHU/NewGroupServlet?promoterID="+promoterID+
                            "&type="+type+
                            "&year="+time.getYear()+
                            "&month="+(time.getMonth()+1)+
                            "&date="+time.getDate()+
                            "&hour="+time.getHours()+
                            "&minute="+time.getMinutes()+
                            "&place="+place+
                            "&maxNumber="+maxNumber+
                            "&remark="+remark);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(6000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String groupID = reader.readLine();

                    System.out.println(groupID);
                    participate("add", groupID, promoterID);

                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            thread.start();
            thread.join(15000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int participate(final String action, final String groupID, final String userID){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 建立連線
                    URL url = new URL("http://140.121.196.20:7780/BOSSCHU/ParticipationServlet?action="+action+"&groupID="+groupID+"&userID="+userID);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(6000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String s = reader.readLine();
                    queueNumberTemp = Integer.parseInt(s);
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            thread.start();
            thread.join(15000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        return queueNumberTemp;
    }

    public ArrayList<Group> getGroups(){
        return groups;
    }

    public ArrayList<Group> getUserGroups(String userID){
        ArrayList<Group> userGroups = new ArrayList<>();

        for(int i=0; i<groups.size(); i++){
            if(groups.get(i).getParticipate())
                userGroups.add(groups.get(i));
        }

        return userGroups;
    }
}
