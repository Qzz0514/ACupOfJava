package net.onest.moment.entity;

import android.graphics.Bitmap;

import java.util.Date;

/*zx：发布实体类*/
public class Release {

    private int userId;
    private Bitmap head; //发布页的用户头像
    private String userName; //发布页的用户名
    private String message; //发布页的用户内容
    private String shareTime; //发布页的用户动态时间
    private Bitmap shareImg; //发布页的用户动态图片
    private String imgs; //发布页用户动态图片的文件名（xxx.jpg）
    private String userImg;//发布页用户头像的文件名

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Bitmap getHead() {
        return head;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public Bitmap getShareImg() {
        return shareImg;
    }

    public void setShareImg(Bitmap shareImg) {
        this.shareImg = shareImg;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Override
    public String toString() {
        return "Release{" +
                "userId=" + userId +
                ", head=" + head +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", shareTime='" + shareTime + '\'' +
                ", shareImg=" + shareImg +
                ", imgs='" + imgs + '\'' +
                ", userImg='" + userImg + '\'' +
                '}';
    }
}

