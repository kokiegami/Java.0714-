package kadai0717;

import java.io.IOException;
import java.io.PrintWriter; // この行があることを確認
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ログ解析器
 */
class LogAnalyzer {
    private final Path logFile;
    private List<LogEntry> entries;
    private Map<String, Long> levelCounts;
    private Map<String, Long> moduleCounts;
    private Map<String, List<LogEntry>> errorsByModule;

    public LogAnalyzer(Path logFile) throws IOException {
        this.logFile = logFile;
        this.entries = new ArrayList<>();
        parseLogFile();
    }

    /**
     * ログファイルの解析
     */
    private void parseLogFile() throws IOException {
        // この正規表現になっていることを確認 (?:\\.\\d{1,9})?
        Pattern logPattern = Pattern.compile(
                "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d{1,9})?) \\[(\\w+)\\] \\[(\\w+)\\] (.+?)(?:\\s*\\(response_time=(\\d+)ms\\))?$"
        );

        try (Stream<String> lines = Files.lines(logFile, StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                Matcher matcher = logPattern.matcher(line);
                if (matcher.find()) {
                    LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1));
                    String level = matcher.group(2);
                    String module = matcher.group(3);
                    String message = matcher.group(4);
                    Integer responseTime = matcher.group(5) != null ?
                            Integer.parseInt(matcher.group(5)) : null;

                    entries.add(new LogEntry(timestamp, level, module, message, responseTime));
                }
            });
        }

        levelCounts = entries.stream()
                .collect(Collectors.groupingBy(LogEntry::getLevel, Collectors.counting()));

        moduleCounts = entries.stream()
                .collect(Collectors.groupingBy(LogEntry::getModule, Collectors.counting()));

        errorsByModule = entries.stream()
                .filter(e -> "ERROR".equals(e.getLevel()))
                .collect(Collectors.groupingBy(LogEntry::getModule));
    }

    /**
     * 基本統計の表示
     */
    public void displayBasicStatistics() {
        System.out.println("=== 基本統計 ===");
        System.out.println("総ログエントリ数: " + entries.size());

        if (entries.isEmpty()) {
            System.out.println("ログエントリがありません。");
            return;
        }

        System.out.println("\nログレベル別:");
        levelCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  %s: %d件 (%.1f%%)%n",
                        e.getKey(), e.getValue(),
                        100.0 * e.getValue() / entries.size()));

        System.out.println("\nモジュール別:");
        moduleCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.printf("  %s: %d件%n", e.getKey(), e.getValue()));

        // 時間範囲
        Optional<LocalDateTime> minTime = entries.stream()
                .map(LogEntry::getTimestamp)
                .min(LocalDateTime::compareTo);
        Optional<LocalDateTime> maxTime = entries.stream()
                .map(LogEntry::getTimestamp)
                .max(LocalDateTime::compareTo);

        if (minTime.isPresent() && maxTime.isPresent()) {
            System.out.println("\n期間: " + minTime.get() + " ～ " + maxTime.get());
            Duration duration = Duration.between(minTime.get(), maxTime.get());
            System.out.println("期間: " + duration.toDays() + "日間");
        }
    }

    /**
     * エラー分析
     */
    public void analyzeErrors() {
        System.out.println("\n=== エラー分析 ===");

        long errorCount = levelCounts.getOrDefault("ERROR", 0L);
        System.out.println("総エラー数: " + errorCount);

        if (errorCount > 0) {
            System.out.println("\nモジュール別エラー:");
            errorsByModule.forEach((module, errors) -> {
                System.out.printf("  %s: %d件%n", module, errors.size());

                // 最頻出エラーメッセージ
                Map<String, Long> messageCounts = errors.stream()
                        .collect(Collectors.groupingBy(LogEntry::getMessage,
                                Collectors.counting()));

                messageCounts.entrySet().stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .limit(3)
                        .forEach(e -> System.out.printf("    - %s (%d回)%n",
                                e.getKey(), e.getValue()));
            });

            // エラー発生時間帯の分析
            System.out.println("\n時間帯別エラー分布:");
            Map<Integer, Long> errorsByHour = entries.stream()
                    .filter(e -> "ERROR".equals(e.getLevel()))
                    .collect(Collectors.groupingBy(
                            e -> e.getTimestamp().getHour(),
                            Collectors.counting()
                    ));

            for (int hour = 0; hour < 24; hour++) {
                long count = errorsByHour.getOrDefault(hour, 0L);
                System.out.printf("  %02d時: %s (%d)%n", hour,
                        "#".repeat(Math.min((int)(count / 5), 20)), count);
            }
        }
    }

    /**
     * パフォーマンス分析
     */
    public void analyzePerformance() {
        System.out.println("\n=== パフォーマンス分析 ===");

        List<LogEntry> entriesWithTime = entries.stream()
                .filter(e -> e.getResponseTime() != null)
                .collect(Collectors.toList());

        if (!entriesWithTime.isEmpty()) {
            // 基本統計
            DoubleSummaryStatistics stats = entriesWithTime.stream()
                    .mapToDouble(LogEntry::getResponseTime)
                    .summaryStatistics();

            System.out.printf("応答時間統計:%n");
            System.out.printf("  平均: %.2fms%n", stats.getAverage());
            System.out.printf("  最小: %.0fms%n", stats.getMin());
            System.out.printf("  最大: %.0fms%n", stats.getMax());

            // パーセンタイル計算
            List<Integer> sortedTimes = entriesWithTime.stream()
                    .map(LogEntry::getResponseTime)
                    .sorted()
                    .collect(Collectors.toList());

            // サイズチェックを追加してIndexOutOfBoundsExceptionを避ける
            int size = sortedTimes.size();
            if (size > 0) {
                int p50 = sortedTimes.get(size / 2);
                int p90 = sortedTimes.get((int)(size * 0.9));
                int p99 = sortedTimes.get((int)(size * 0.99));

                System.out.printf("  50パーセンタイル: %dms%n", p50);
                System.out.printf("  90パーセンタイル: %dms%n", p90);
                System.out.printf("  99パーセンタイル: %dms%n", p99);
            }

            // 遅いリクエストの特定
            System.out.println("\n遅いリクエスト（上位5件）:");
            entriesWithTime.stream()
                    .sorted(Comparator.comparing(LogEntry::getResponseTime).reversed())
                    .limit(5)
                    .forEach(e -> System.out.printf("  %s [%s] %s - %dms%n",
                            e.getTimestamp(), e.getModule(), e.getMessage(),
                            e.getResponseTime()));
        }
    }

    /**
     * 時系列分析
     */
    public void analyzeTimeSeries() {
        System.out.println("\n=== 時系列分析 ===");

        // 日別のログ数
        Map<LocalDate, Long> dailyCounts = entries.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTimestamp().toLocalDate(),
                        TreeMap::new, // 日付順にソートするためにTreeMapを使用
                        Collectors.counting()
                ));

        System.out.println("日別ログ数:");
        dailyCounts.forEach((date, count) ->
                System.out.printf("  %s: %d件%n", date, count));

        // 最もアクティブな日
        Map.Entry<LocalDate, Long> busiestDay = dailyCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (busiestDay != null) {
            System.out.printf("\n最もアクティブな日: %s (%d件)%n",
                    busiestDay.getKey(), busiestDay.getValue());
        }
    }

    /**
     * レポート生成
     */
    public void generateReport(Path reportFile) throws IOException {
        System.out.println("\n=== レポート生成 ===");

        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(reportFile, StandardCharsets.UTF_8))) {

            writer.println("ログ解析レポート");
            writer.println("=".repeat(50));
            writer.println("生成日時: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            writer.println("解析対象: " + logFile.getFileName());
            writer.println();

            // サマリー
            writer.println("## サマリー");
            writer.println("総エントリ数: " + entries.size());
            // entriesが空の場合のゼロ除算を避ける
            if (!entries.isEmpty()) {
                writer.println("エラー率: " + String.format("%.2f%%",
                        100.0 * levelCounts.getOrDefault("ERROR", 0L) / entries.size()));
            } else {
                writer.println("エラー率: N/A (ログエントリなし)");
            }

            // 詳細統計
            writer.println("\n## ログレベル別統計");
            levelCounts.forEach((level, count) ->
                    writer.printf("%s: %d件%n", level, count));

            writer.println("\n## モジュール別統計");
            moduleCounts.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(e -> writer.printf("%s: %d件%n", e.getKey(), e.getValue()));

            // 推奨事項
            writer.println("\n## 推奨事項");
            if (!entries.isEmpty() && levelCounts.getOrDefault("ERROR", 0L) > entries.size() * 0.05) {
                writer.println("- エラー率が高いです。エラーの原因を調査してください。");
            } else if (entries.isEmpty()){
                writer.println("- ログエントリがありません。");
            }

            // レスポンスタイムがあるエントリのみを対象
            List<LogEntry> entriesWithResponseTime = entries.stream()
                    .filter(e -> e.getResponseTime() != null)
                    .collect(Collectors.toList());

            if (!entriesWithResponseTime.isEmpty()) {
                DoubleSummaryStatistics responseStats = entriesWithResponseTime.stream()
                        .mapToDouble(LogEntry::getResponseTime)
                        .summaryStatistics();

                if (responseStats.getAverage() > 500) {
                    writer.println("- 平均応答時間が遅いです。パフォーマンス改善を検討してください。");
                }
            } else {
                writer.println("- 応答時間のデータがありません。");
            }

            System.out.println("レポートを生成しました: " + reportFile);
        }
    }
}