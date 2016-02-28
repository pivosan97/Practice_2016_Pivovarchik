import java.math.BigInteger;
import java.sql.Timestamp;

public class Message {
    private BigInteger id;
    private String author;
    private String text;
    private Timestamp timestamp;

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getID() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Message(){}

    public Message(Message msg){
        id = msg.id;
        author = msg.author;
        text = msg.text;
        timestamp = msg.timestamp;
    }

    @Override
    public String toString() {
        return id + " : " + timestamp + "\n" + author + "\n" + "   " + text;
    }
}
