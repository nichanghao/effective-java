package net.sunday.effective.design.pattern.chain.logger;

/**
 * 文件日志记录器
 */
public class FileLogger extends Logger {

    public FileLogger(int level) {
        this.level = level;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("[FileLogger] level: " + level + ", message: " + message);
    }
}
