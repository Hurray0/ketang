/**
 * @Author hurray
 * @Part class
 * @Note 会晤类
 * @Encoding UTF-8 
 * @Date 2014-11-22 05:11:52
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 * 
 */

package bundle;

/**
 * @Author hurray
 * @Class Meeting
 */
public class Meeting {
    private String user1;
    private String user2;
    private long startTime;
    private long endTime;
    private String label;
    private int id;
    private int status = 1;//0为已删除

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}