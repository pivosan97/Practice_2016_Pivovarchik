import java.util.LinkedList;

public class History {
    private LinkedList<Message> messages;
    Logger logger;

    public History(Logger logger, String fileName){
        this.logger = logger;
        //init file reader
    }

    public void loadHistory(){
        //load from file
        logger.log("Load history from file.")
    }

    public void saveChanges(){
        //update changes
        logger.log(Logger.INF, "Save history changes.)
    }

    public void addNewMessage(Message msg){
        //add timestamp to msg
        //add id to msg
        messages.add(msg);
        logger.log(Logger.INF, "New msg added to local history.")
    }

    public LinkedList<Message> filterByAuthor(String author){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getAuthor().compareTo(author) == 0) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }

    public LinkedList<Message> filterByKeyword(String keyword){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getText.contain(keyword) == 0) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }

    public LinkedList<Message> filterByRegExp(String regExp){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(regExp.try(msg.getText) == 0) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }

    public LinkedList<Message> filterByDatePeriod(String startDate, String endDate){
        LinkedList<Message> searchResult = new LinkedList<>();

        for (Message msg: messages) {
            if(msg.getDate() >= startDate && msg.getDate() <= endDate) {
                searchResult.add(new Message(msg));
            }
        }

        return searchResult;
    }
}
