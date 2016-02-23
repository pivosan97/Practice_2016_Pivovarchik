import java.util.LinkedList;

public class Application {
    private Logger logger;
    private History history;
    private String name = "not set";

    public Application(){
        logger = new Logger();
        history = new History(logger, "Hist");
    }

    public void start(){
        System.out.println("Welcom!");

    }
}
