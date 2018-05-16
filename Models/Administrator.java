package Models;

public class Administrator {
    private int serialNum;
    private String name = null;
    private String loginIP = null;
    private String lastLoginTime = null;

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getSerialNum() {
        return this.serialNum;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }
}
