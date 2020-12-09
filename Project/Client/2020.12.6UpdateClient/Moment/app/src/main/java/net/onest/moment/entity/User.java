package net.onest.moment.entity;

import android.graphics.Bitmap;

/*用户实体类*/
public class User {

    private int userId;
    private String userEmail;
    private String userName;
    private String userPwd;
    private Bitmap userHead;

    public User() {

    }

    public User(int userId, String userEmail,String userName, String userPwd) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Bitmap getUserHead() {
        return userHead;
    }
    public void setUserHead(Bitmap userHead) {
        this.userHead = userHead;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }
}
