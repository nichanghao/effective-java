package net.sunday.effective.java.core.design.chain.logger;

/**
 * 控制台日志记录器
 */

public class ConsoleLogger extends Logger {

    public ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("[ConsoleLogger] level: " + level + ", message: " + message);
    }
}
