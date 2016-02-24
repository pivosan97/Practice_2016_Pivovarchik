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

        System.out.println("Enter history file name:\n");
        String histFileName = in.nextString();

        try {
            logger = new Logger(logFileName);
        } catch (IOExeption err){
            System.out.println("Failed to create logger, " + err.getMessage());
        }

        try {
            history = new History(logger, histFileName);
        } catch (IOExeption err) {
            System.out.println("Failed to open history, " + err.getMessage());
        }

        mainMenu();
    }

    private void mainMenu(){
        while(true) {
            System.out.println("-----------------\n\n");
            System.out.println("Select option:\n");
            System.out.println("   1)Add message\n");
            System.out.println("   2)find message\n");
            System.out.println("   3)Remove message\n");
            System.out.println("   4)Print history\n");
            System.out.println("   5)Exit\n");
            int ans = in.nextInt;

            switch (ans) {
                case 1: {
                    addMessage();
                }

                case 2: {
                    findMessage();
                }

                case 3: {
                    removeMessage();
                }

                case 4: {
                    printHistory();
                }

                case 5: {
                    System.out.println("Goodbuy");
                    return;
                }

                default: {
                    System.out.println("Invalid index!\n");
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
            System.out.println("Author: " + msg.author + '\n');
            System.out.println("    Text: " + msg.text + '\n');
            System.out.println("        ID: " + msg.id + '\n');
        }
    }

    private void removeMessage(){
        System.out.println("Enter message id:\n");
        int msgID = in.nextInt();
        history.removeMessage(msgID);
    }

    private void findMessage(){

    }
}
