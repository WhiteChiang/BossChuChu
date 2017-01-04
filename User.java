package group11.android.ntou.bosschuchu.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class User {
    private String userID;
    private String name;
    private String department;
    private int grade;
    private String uClass;
    private String hobby;
    private String about;

    public User(String userID, String name){
        this.userID = userID;
        this.name = name;
    }

    public User(String userID, String name, String department, int grade, String uClass, String hobby, String about) {
        this.userID = userID;
        this. name = name;
        this.department = department;
        this.grade = grade;
        this.uClass = uClass;
        this.hobby = hobby;
        this.about = about;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getGrade() {
        return grade;
    }

    public String getuClass() {
        return uClass;
    }

    public String getHobby() {
        return hobby;
    }

    public String getAbout() {
        return about;
    }

    public void checkIsNewAccount(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 建立連線
                    URL url = new URL("http://140.121.196.20:7780/BOSSCHU/NewAccountServlet?userID="+ userID +"&userName=" + name);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(6000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    reader.readLine();
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

}
