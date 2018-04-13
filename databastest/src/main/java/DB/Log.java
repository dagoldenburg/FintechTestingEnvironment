package DB;

/**
 * Logs what happens in the database
 */
public class Log{

    /**
     * Decides if log events will be printed in the console or not.
     */
    private static boolean printLog = true;

    public static boolean isPrintLog() {
        return printLog;
    }

    public static void setPrintLog(boolean printLog) {
        Log.printLog = printLog;
    }

    /**
     * Information log.
     * @param c Class who calls the log
     * @param message Message that gets logged
     */
    public static void i(Object c, String message){
        if(printLog)
            System.out.println("i: "+((Class)c.getClass()).getName()+", "+message);
    }
    /**
     * Error log.
     * @param c Class who calls the log
     * @param message Message that gets logged
     */
    public static void e(Object c, String message){
        if(printLog)
            System.out.println("e: "+((Class)c.getClass()).getName()+", "+message);
    }
}
