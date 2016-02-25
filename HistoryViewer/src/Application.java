import java.util.LinkedList;

public class Application {
    Scanner in;
    private Logger logger = null;
    private History history = null;
    private String name = "not set";

    public void start(){
        in = new Scanner();

        System.out.println("Welcom!\n");

        System.out.println("Enter log file name:\n");
        String logFileName = in.nextString();

        try {
            logger = new Logger(logFileName);
            logger.log(Logger.INF, "Init logger with file: " + logFileName);
        } catch (IOExeption err) {
            System.out.println("Failed to create logger, " + err.getMessage());
        }

        System.out.println("Enter history file name:\n");
        String histFileName = in.nextString();

        try {
            history = new History(logger, histFileName);
            logger.log(Logger.INF, "Init history with file: " + histFileName);
        } catch (IOExeption err) {
            logger.log(Logger.ERR, "Failed to init history by file: " + histFileName + ", " + err.getMessage());
        }

        mainMenu();
    }

    private void mainMenu(){
        logger.log(Logger.INF, "Main menu started.");

        while(true) {
            System.out.println("-----------------\n\n");
            System.out.println("Select option:\n");
            System.out.println("   1)Add message\n");
            System.out.println("   2)Find message\n");
            System.out.println("   3)Remove message\n");
            System.out.println("   4)Print history\n");
            System.out.println("   5)Exit\n");
            int ans = in.nextInt;

            switch (ans) {
                case 1: {
                    addMessage();
                    break;
                }

                case 2: {
                    findMessage();
                    break;
                }

                case 3: {
                    removeMessage();
                    break;
                }

                case 4: {
                    printHistory();
                    break;
                }

                case 5: {
                    System.out.println("Goodbuy");
                    return;
                }

                default: {
                    logger.log(Logger.INF, "Invalid index in main menu.");
                    System.out.println("Invalid index!\n");
                    break;
                }
            }
        }
    }

    private void addMessage(){
        System.out.println("Enter yout name:\n");
        String author = in.nextString();
        System.out.println("Enter messsage:\n");
        String text = in.nextString();

        history.addNewMessage(new Message(author, text));
    }

    private void printHistory(){
        LinkedList<Message> messages = history.getAllMessages();
        for(Message msg: messages){
            System.out.println(msg.toString() + '\n');
        }
    }

    private void removeMessage(){
        System.out.println("Enter message id:\n");
        int msgID = in.nextInt();
        history.removeMessage(msgID);
    }

    private void findMessage(){
        System.out.println("-----------------\n\n");
        System.out.println("Find by:\n");
        System.out.println("   1)Author\n");
        System.out.println("   2)Keyword\n");
        System.out.println("   3)Regular expresion\n");
        System.out.println("   4)Date period\n");
        System.out.println("   5)Exit\n");
        int ans = in.nextInt;

        LinkedList<Message> result;

        boolean isValidAnswer = false;
        while(!isValidAnswer) {
            isValidAnswer = true;

            switch (ans) {
                case 1: {
                    System.out.println("Enter author: ");
                    String author = in.nextStrinh();

                    result = history.findByKey(author);
                    break;
                }

                case 2: {
                    System.out.println("Enter keyword: ");
                    String keyWord.nextString();

                    result = history.findByKeyWord(keyWord);
                    break;
                }

                case 3: {
                    System.out.println("Enter regular expression: ");
                    String regExp = in.nextString();

                    result = history.findByRexExp(regExp);
                    break;
                }

                case 4: {
                    System.out.println("Enter start date: ");
                    String startDate = in.nextString();
                    System.out.println(("Enter end date: "));
                    String endDate = in.nextString();

                    result = history.findByDatePeriod(startDate, endDate);
                    break;
                }

                case 5: {
                    return;
                }

                default: {
                    isValidAnswer = false;
                    logger.log(Logger.INF, "Invalid index in find menu.")
                    System.out.println("Invalid index!\n");
                    break;
                }
            }
        }

        "Search result:"
        for(Message msg : result){
            System.out.println("   " + msg.toString() + '/n');
        }
    }
}
