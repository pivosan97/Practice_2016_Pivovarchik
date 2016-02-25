public class Logger {
    public static final int ERR = 1;
    public static final int WRN = 2;
    public static final int DBG = 3;
    public static final int INF = 4;

    private PrintWriter fileWriter;

    public Logger(final String logFile) throws IOExeption {
        fileWriter = new PrintWriter(new FileWriter(logFile));
    }

    public void log(int priority, final String text){
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


        fileWriter.print(logMessage + '\n');
    }

    public void close(){
        fileWriter.close();
    }
}
