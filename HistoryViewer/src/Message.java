public class Message {
    private Integer id;
    private String author;
    private String text;
    private String date;

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public Message(Message msg){
        id = msg.id;
        author = msg.author;
        text = msg.text;
        date = msg.date;
    }

    public Message(String author, String text){
        this.author = author;
        this.text = text;
        id = -1;
        date = "not set";
    }

    public Message(String author, String text, String date, Integer id) {
        this.author = author;
        this.text = text;
        this.date = date;
        this.id = id;
    }
}
