package net.onest.moment.entity;

import android.graphics.Bitmap;

import java.util.Date;

/*zx：发布实体类*/
public class Release {

    private int headId;
    private Bitmap head; //发布页的用户头像
    private String username; //发布页的用户名
    private String usercontent; //发布页的用户内容
    private String time; //发布页的用户动态时间
    private Bitmap userimg; //发布页的用户动态图片
    private String userimgurl; //发布页用户动态图片的文件名（xxx.jpg）
    private String headurl;//发布页用户头像的文件名

    public String getUserimgurl() {
        return userimgurl;
    }
    public void setUserimgurl(String userimgurl) {
        this.userimgurl = userimgurl;
    }

    public String getHeadurl() {
        return headurl;
    }
    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public int getHeadId() {
        return headId;
    }
    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public Bitmap getHead() {
        return head;
    }
    public void setHead(Bitmap head) {
        this.head = head;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercontent() {
        return usercontent;
    }
    public void setUsercontent(String usercontent) {
        this.usercontent = usercontent;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public Bitmap getUserimg() {
        return userimg;
    }
    public void setUserimg(Bitmap userimg) {
        this.userimg = userimg;
    }

    public Release(int headId,String username, String usercontent, String time, Bitmap userimg) {
        this.headId = headId;
        this.username = username;
        this.usercontent = usercontent;
        this.time = time;
        this.userimg = userimg;
    }

    public Release() {

    }

    @Override
    public String toString() {
        return "Release{" +
                "head=" + head +
                ", username='" + username + '\'' +
                ", usercontent='" + usercontent + '\'' +
                ", time='" + time + '\'' +
                ", userimg=" + userimg +
                '}';
    }
}

