package net.sunday.effective.java.core.design.chain.logger;


import lombok.Setter;

public abstract class Logger {

    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    /**
     * 日志级别
     */
    protected int level;


    @Setter
    protected Logger nextLogger;

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            writeMessage(message);
        }

        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract void writeMessage(String message);


}
