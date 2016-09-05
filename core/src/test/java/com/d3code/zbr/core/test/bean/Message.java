package com.d3code.zbr.core.test.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jileilei on 16/9/5.
 */
public class Message implements Serializable {

    private String content;
    private Date date;

    public Message(){}

    public Message(String content, Date date){
        this.content = content;
        this.date = date;
    }

    public static Message create(String content, Date date){
        Message message = new Message(content, date);
        return message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
