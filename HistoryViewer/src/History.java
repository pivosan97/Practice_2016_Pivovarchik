import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;

public class History {
    private JSONSerializer serializer;
    private Logger logger;
    private LinkedList<Message> messages;
    private BigInteger nextID = BigInteger.ONE;

    public History(Logger logger){
        this.logger = logger;
        messages = new LinkedList<>();
        serializer = new JSONSerializer();
    }

    public void loadFromFile(String fileName) throws JsonParsingException, IOException{
        serializer.initReader(fileName);

        messages = serializer.read();

        serializer.closeReader();
    }

    public void saveChanges(String histFileName) throws IOException {
        serializer.initWriter(histFileName);

        serializer.write(messages);

        serializer.closeWriter();
    }

    public void addNewMessage(final Message msg){
        Message copy = new Message(msg);

        Timestamp curTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        copy.setTimestamp(curTime);

        copy.setId(nextID);
        nextID = nextID.add(BigInteger.ONE);

        messages.add(copy);
    }

    public LinkedList<Message> findByAuthor(String author){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getAuthor().compareTo(author) == 0) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }

    public Message findByID(BigInteger msgID){
        for (Message msg: messages) {
            if(msg.getID().compareTo(msgID) == 0) {
                return new Message(msg);
            }
        }

        return null;
    }

    public LinkedList<Message> findByKeyword(final String keyword){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getText().contains(keyword)) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }

    public LinkedList<Message> findByRegExp(String regExp){
        LinkedList<Message> searchResult = new LinkedList<>();

        /*for (Message msg: messages) {
            if(regExp.try(msg.getText()) == 0) {
                searchResult.add(new Message(msg));
            }
        }*/

        return searchResult;
    }

    public LinkedList<Message> findByDatePeriod(Timestamp startDate, Timestamp endDate){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getTimestamp().after(startDate) && msg.getTimestamp().before(endDate)) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }

    public final LinkedList<Message> getMessages() {
        LinkedList<Message> copy = new LinkedList<>();

        for(Message msg : messages){
            copy.add(new Message(msg));
        }

        return copy;
    }

    public void removeMessage(BigInteger msgID){
        for(Message msg : messages){
            if(msg.getID().compareTo(msgID) == 0){
                logger.log(Logger.INF, "Remove msg from history, msgID: " + msgID);
                messages.remove(msg);
                break;
            }
        }
    }
}
