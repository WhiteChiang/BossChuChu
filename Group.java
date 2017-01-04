package group11.android.ntou.bosschuchu.data;

import java.util.Date;

public class Group {
    private String groupID;
    private String promoterID;
    private String promoterName;
    private String type;
    private Date time;
    private String place;
    private String remark;
    private int maxNumber;
    private int nowNumber;
    private boolean participate;
    private int order;

    private final int ADD = 0;
    private final int DELETE = 1;

    public Group() {

    }

    public Group(String groupID, String promoterID, String promoterName, String type, Date time,String place,  String remark, int maxNumber, int nowNumber, boolean participate, int order) {
        this.groupID = groupID;
        this.promoterID = promoterID;
        this.promoterName = promoterName;
        this.type = type;
        this.time = time;
        this.place = place;
        this.remark = remark;
        this.maxNumber = maxNumber;
        this.nowNumber = nowNumber;
        this.participate = participate;
        this.order = order;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getPromoterID() {
        return promoterID;
    }

    public String getType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getRemark() {
        return remark;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getNowNumber() {
        return nowNumber;
    }

    public String getPromoterName() {
        return promoterName;
    }

    public boolean getParticipate() {
        return participate;
    }

    public int getOrder() {
        return order;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void participate(int action){
        switch(action){
            case ADD :
                nowNumber++;
                break;
            case DELETE :
                nowNumber--;
                break;
            default:
                break;
        }

    }
}
