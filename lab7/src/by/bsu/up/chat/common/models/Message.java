package by.bsu.up.chat.common.models;

import java.io.Serializable;

public class Message implements Serializable {

    public static final String NEW = "new";
    public static final String EDDITED = "edited";
    public static final String REMOVED = "removed";

    private String id;
    private String author;
    private long timestamp;
    private String text;
    private String status;

    public Message(){

    }

    public Message(String str) throws Exception{
        Integer idStart = str.indexOf("id='", 0);
        Integer idEnd = str.indexOf('\'', idStart + 4);
        id = str.substring(idStart + 4, idEnd);

        Integer authorStart = str.indexOf("author='", idEnd);
        Integer authorEnd = str.indexOf('\'', authorStart + 8);
        author = str.substring(authorStart + 8, authorEnd);

        Integer timeStart = str.indexOf("timestamp=", authorEnd);
        Integer timeEnd = str.indexOf(',', timeStart + 10);

        try {
            timestamp = Long.parseLong(str.substring(timeStart + 10, timeEnd));
        } catch (NumberFormatException err){
            throw new Exception("Failed to parse msg from string");
        }

        Integer textStart = str.indexOf("text='", timeEnd);
        Integer textEnd = str.indexOf('\'', textStart + 6);
        text = str.substring(textStart + 6, textEnd);

        Integer statusStart = str.indexOf("status='", textEnd);
        Integer statusEnd = str.indexOf('\'', statusStart + 8);
        status = str.substring(statusStart + 8, statusEnd);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", timestamp=" + timestamp +
                ", text='" + text + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
