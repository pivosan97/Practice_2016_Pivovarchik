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

    public void loadFromFile(String fileName) throws IOException{
        serializer.initReader(fileName);
        LinkedList<Message> buf = new LinkedList<>();

        Message msg = null;
        while((msg = serializer.read()) != null){
            buf.add(msg);
        }

        serializer.closeReader();

        messages = buf;

        logger.log(Logger.INF, "Load history from file.");
    }

    public void saveChanges(String histFileName) throws IOException {
        serializer.initWriter(histFileName);

        for(Message msg : messages){
            serializer.write(msg);
        }

        serializer.closeWriter();

        logger.log(Logger.INF, "Save history changes.");
    }

    public void addNewMessage(final Message msg){
        Message copy = new Message(msg);

        Timestamp curTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        copy.setTimestamp(curTime);

        copy.setId(nextID);
        nextID = nextID.add(BigInteger.ONE);

        messages.add(copy);
        logger.log(Logger.INF, "New msg added to local history.");
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
            if(msg.getText().contentEquals(keyword)) {
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
                messages.remove(msg);
                break;
            }
        }
    }
}
