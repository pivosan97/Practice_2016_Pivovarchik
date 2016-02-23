import java.util.LinkedList;

public class Application {
    Logger logger;
    private History history;

    public Application(){
        logger = new Logger();
        history = new History(logger, "Hist");
    }


}
