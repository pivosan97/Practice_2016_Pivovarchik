import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Application {
    private Scanner in;
    private Logger logger;
    private History history;

    public Application() {
        logger = new Logger();
        history = new History(logger);
    }

    public void start() {
        in = new Scanner(System.in);

        System.out.println("Welcome!");
        System.out.println("Enter log file name (\"NO\" - for console log):");
        String logFileName;
        try {
            logFileName = in.next();
        } catch (InputMismatchException err) {
            System.out.println("Invalid input, please enter single word, " + err.getMessage());
            return;
        } finally {
            in.nextLine();
        }

        if (logFileName.compareTo("NO") != 0) {
            try {
                logger.redirectToFile(logFileName);
                logger.log(Logger.INF, "Redirect log to file: " + logFileName);
            } catch (IOException err) {
                System.out.println("Failed to redirect log to file: " + logFileName + ", " + err.getMessage());
            }
        }

        mainMenu();
    }

    private void mainMenu() {
        logger.log(Logger.INF, "Main menu started.");

        while (true) {
            System.out.println("-----------------\n");
            System.out.println("Select option:");
            System.out.println("   1)Add message");
            System.out.println("   2)Find message");
            System.out.println("   3)Remove message");
            System.out.println("   4)Print history");
            System.out.println("   5)Load history from file");
            System.out.println("   6)Save history to file");
            System.out.println("   7)Exit");

            int ans = 0;

            try {
                ans = in.nextInt();
            } catch (InputMismatchException err) {
                System.out.println("Invalid input, please enter number, " + err.getMessage());
            } finally {
                in.nextLine();
            }

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
                    loadHistoryFromFile();
                    break;
                }

                case 6: {
                    saveHistoryToFile();
                    break;
                }

                case 7: {
                    logger.log(Logger.INF, "Close logger.");
                    logger.close();
                    System.out.println("Goodbuy");
                    return;
                }

                default: {
                    logger.log(Logger.INF, "Invalid index in main menu.");
                    System.out.println("Invalid index!");
                    break;
                }
            }
        }
    }

    private void addMessage() {
        System.out.println("Enter yout name:");
        String author = in.nextLine();
        System.out.println("Enter messsage:");
        String text = in.nextLine();

        Message msg = new Message();
        msg.setAuthor(author);
        msg.setText(text);
        history.addNewMessage(msg);
    }

    private void printHistory() {
        LinkedList<Message> messages = history.getMessages();
        for (Message msg : messages) {
            System.out.println(msg.toString());
        }
    }

    private void removeMessage() {
        System.out.println("Enter message id:");
        String ans = in.nextLine();
        try {
            history.removeMessage(new BigInteger(ans));
        } catch (NumberFormatException err) {
            System.out.println("Invalid msgID, please enter number, " + err.getMessage());
        }
    }

    private void findMessage() {
        System.out.println("-----------------");
        System.out.println("Find by:");
        System.out.println("   1)Author");
        System.out.println("   2)Keyword");
        System.out.println("   3)Regular expression");
        System.out.println("   4)Date period");
        System.out.println("   5)Exit");

        int ans = 5;
        try {
            ans = in.nextInt();
        } catch (InputMismatchException err) {
            System.out.println("Invalid input, please enter number, " + err.getMessage());
            return;
        } finally {
            in.nextLine();
        }

        LinkedList<Message> result = new LinkedList<>();

        boolean isValidAnswer = false;
        while (!isValidAnswer) {
            isValidAnswer = true;

            switch (ans) {
                case 1: {
                    System.out.println("Enter author: ");
                    String author = in.nextLine();

                    result = history.findByAuthor(author);
                    break;
                }

                case 2: {
                    System.out.println("Enter keyword: ");
                    String keyWord = in.nextLine();

                    result = history.findByKeyword(keyWord);
                    break;
                }

                case 3: {
                    System.out.println("Enter regular expression: ");
                    String regExp = in.nextLine();

                    result = history.findByRegExp(regExp);
                    break;
                }

                case 4: {
                    System.out.println("Enter start date: ");
                    String startDate = in.nextLine();
                    System.out.println(("Enter end date: "));
                    String endDate = in.nextLine();

                    try {
                        result = history.findByDatePeriod(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
                    } catch (IllegalArgumentException err) {
                        System.out.println("Invalid date format, " + err.getMessage());

                    }

                    break;
                }

                case 5: {
                    return;
                }

                default: {
                    isValidAnswer = false;
                    logger.log(Logger.INF, "Invalid index in find menu.");
                    System.out.println("Invalid index!\n");
                    break;
                }
            }
        }

        System.out.println("Search result:");
        for (Message msg : result) {
            System.out.println(msg.toString() + '\n');
        }
    }

    private void loadHistoryFromFile(){
        System.out.println("Enter history file name:");
        String histFileName;
        try {
            histFileName = in.next();
        } catch (InputMismatchException err) {
            System.out.println("Invalid input, please enter single word, " + err.getMessage());
            return;
        } finally {
            in.nextLine();
        }

        try {
            history.loadFromFile(histFileName);
            logger.log(Logger.INF, "Load history from file: " + histFileName);
        } catch (IOException err) {
            System.out.println("Failed to load history from file: " + histFileName + ", " + err.getMessage());
            logger.log(Logger.ERR, "Failed to load history from file: " + histFileName + ", " + err.getMessage());
        }
    }

    private void saveHistoryToFile(){
        System.out.println("Enter history file name:");
        String fileName;

        try {
            fileName = in.next();
        } catch (InputMismatchException err) {
            System.out.println("Invalid input, please enter single word, " + err.getMessage());
            return;
        } finally {
            in.nextLine();
        }

        try {
            history.saveChanges(fileName);
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
