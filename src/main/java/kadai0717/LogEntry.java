package kadai0717;

import java.time.LocalDateTime;

/**
 * ログエントリ
 * ログの1行を表すデータ構造
 */
class LogEntry {
    private final LocalDateTime timestamp;
    private final String level;
    private final String module;
    private final String message;
    private final Integer responseTime; // nullable

    public LogEntry(LocalDateTime timestamp, String level, String module,
                    String message, Integer responseTime) {
        this.timestamp = timestamp;
        this.level = level;
        this.module = module;
        this.message = message;
        this.responseTime = responseTime;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLevel() { return level; }
    public String getModule() { return module; }
    public String getMessage() { return message; }
    public Integer getResponseTime() { return responseTime; } // nullを返す可能性あり

    // デバッグ用のtoStringメソッド
    @Override
    public String toString() {
        return "LogEntry{" +
                "timestamp=" + timestamp +
                ", level='" + level + '\'' +
                ", module='" + module + '\'' +
                ", message='" + message + '\'' +
                ", responseTime=" + responseTime +
                '}';
    }
}