package net.onest.moment.entity;

import android.graphics.Bitmap;

/*qzz：消息界面实体类*/
public class Message {

    private int headId;
    private Bitmap head;
    private String checkedMessage;
    private String title;

    public Message(int headId,String title,String checkedMessage) {
        this.headId = headId;
//        this.head = head;
        this.checkedMessage = checkedMessage;
        this.title = title;
    }

    public Message() {

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeadId() {
        return headId;
    }
    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public String getCheckedMessage() {
        return checkedMessage;
    }
    public void setCheckedMessage(String checkedMessage) {
        this.checkedMessage = checkedMessage;
    }

    public Bitmap getHead() {
        return head;
    }
    public void setHead(Bitmap head) {
        this.head = head;
    }

}
