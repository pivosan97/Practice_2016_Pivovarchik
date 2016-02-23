public class Logger {
    public static final int ERR = 1;
    public static final int WRN = 2;
    public static final int DBG = 3;
    public static final int FILE = 10;
    public static final int CONSOLE = 11;

    private PrintWriter fileWriter;

    public Logger(final String logFile){
        fileWriter = new PrintWriter(new FileWriter(logFile));
    }

    public void log(int dest, int priority, final String text){
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
            default:
            {
                logMessage = "ERROR: Invalid log priority";
            }
        }

        if(dest == CONSOLE){
            System.out.println(logMessage);
        } else {
            fileWriter.write(logMessage + '\n');
        }
    }

    public void close(){
        fileWriter.close();
    }
}
