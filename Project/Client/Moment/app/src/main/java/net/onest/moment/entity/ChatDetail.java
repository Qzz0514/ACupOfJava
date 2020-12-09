package net.onest.moment.entity;

/*qzz：消息详情实体类*/
public class ChatDetail {
    public static int RECEIVED = 0;
    public static int SEND = 1;

    private String content;
    private int type;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }


    public ChatDetail(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
