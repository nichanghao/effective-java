package net.sunday.effective.java.core.design.chain;

import net.sunday.effective.java.core.design.chain.logger.ConsoleLogger;
import net.sunday.effective.java.core.design.chain.logger.FileLogger;
import net.sunday.effective.java.core.design.chain.logger.Logger;

/**
 * 日志链的运行器
 */

public class LoggerChainRunner {

    public static void main(String[] args) {
        Logger loggerChain = initChainOfLoggers();

        System.out.println("Log INFO Level:");
        loggerChain.logMessage(Logger.INFO, "This is an informational message.");

        System.out.println("\nLog DEBUG Level:");
        loggerChain.logMessage(Logger.DEBUG, "This is a debug level message.");
    }

    private static Logger initChainOfLoggers() {
        Logger fileLogger = new FileLogger(Logger.DEBUG);
        Logger consoleLogger = new ConsoleLogger(Logger.INFO);

        // 设置责任链的顺序：fileLogger -> consoleLogger
        fileLogger.setNextLogger(consoleLogger);

        return fileLogger;
    }
}
