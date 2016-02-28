import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    public static final int ERR = 1;
    public static final int WRN = 2;
    public static final int DBG = 3;
    public static final int INF = 4;

    private PrintWriter out;

    public Logger() {
        out = new PrintWriter(System.out);
    }

    public void redirectToFile(String fileName) throws IOException{
        out = new PrintWriter(new FileWriter(fileName));
    }

    public void log(int priority, String text){
        String logMessage;
        switch (priority)
        {
            case DBG:
            {
                logMessage = "DEBUG: " + text;
                break;
            }

            case WRN:
            {
                logMessage = "WARNING: " + text;
                break;
            }

            case ERR:
            {
                logMessage = "ERROR: " + text;
                break;
            }

            case INF:
            {
                logMessage = "INFO: " + text;
                break;
            }

            default:
            {
                logMessage = "ERROR: Invalid log priority";
            }
        }


        out.print(logMessage + '\n');
    }

    public void close(){
        out.close();
    }
}
