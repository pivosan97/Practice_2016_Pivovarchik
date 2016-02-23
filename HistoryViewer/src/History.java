import java.util.LinkedList;

public class History {
    private LinkedList<Message> messages;
    Logger logger;

    public History(Logger logger, String fileName){
        this.logger = logger;
        //load history from file
    }

    public void addNewMessage(Message msg){
        messages.add(msg);
    }

    public LinkedList<Message> filterByAuthor(String author){
        LinkedList<Message> filteredHist = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getAuthor().compareTo(author) == 0) {
                filteredHist.add(new Message(msg));
            }
        }

        return filteredHist;
    }

    public void saveChanges(){
        //update changes
    }

}
