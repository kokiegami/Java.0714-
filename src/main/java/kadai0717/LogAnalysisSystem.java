package kadai0717;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

/**
 * 実践課題：ログ解析システム
 * 大規模なログファイルを効率的に解析し、統計情報を生成
 */
public class LogAnalysisSystem {
    // ★★★ ここを修正 ★★★
    // プロジェクトのルートディレクトリを基点とする絶対パスを指定
    private static final Path PROJECT_ROOT = Paths.get("C:\\Users\\ek020\\IdeaProjects\\JavaTraining");
    private static final Path APPLICATION_LOG_PATH = PROJECT_ROOT.resolve("application.log");
    private static final Path ANALYSIS_REPORT_PATH = PROJECT_ROOT.resolve("analysis_report.txt");
    private static final Path REALTIME_LOG_PATH = PROJECT_ROOT.resolve("realtime.log");
    // ★★★ 修正ここまで ★★★

    public static void main(String[] args) {
        System.out.println("=== ログ解析システム ===\n");

        try {
            // サンプルログファイルの生成
            generateSampleLogFile(APPLICATION_LOG_PATH, 10000); // 絶対パスを渡す

            try { // ログ解析中のエラーを詳細に出力するためのtry-catchブロック
                // ログ解析システムの初期化
                LogAnalyzer analyzer = new LogAnalyzer(APPLICATION_LOG_PATH); // 絶対パスを渡す

                // 基本統計の表示
                analyzer.displayBasicStatistics();

                // エラー分析
                analyzer.analyzeErrors();

                // パフォーマンス分析
                analyzer.analyzePerformance();

                // 時系列分析
                analyzer.analyzeTimeSeries();

                // レポート生成
                analyzer.generateReport(ANALYSIS_REPORT_PATH); // 絶対パスを渡す
            } catch (Exception parseException) {
                System.err.println("\n--- ログ解析中にエラーが発生しました ---");
                System.err.println("エラーメッセージ: " + parseException.getMessage());
                parseException.printStackTrace(); // 詳細なスタックトレースを出力
                System.err.println("------------------------------------");
            }

            // リアルタイム監視のシミュレーション
            demonstrateRealTimeMonitoring(); // REALTIME_LOG_PATHはメソッド内で使用

        } catch (IOException e) {
            System.err.println("初期化またはリアルタイム監視でファイル操作エラー: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("システム全体で不明なエラー: " + e.getMessage());
            e.printStackTrace();
        } finally { // クリーンアップ処理はfinallyブロックに入れると確実
            // クリーンアップ
            try {
                // ファイルが存在しない場合でもエラーにならないように deleteIfExists を使用
                Files.deleteIfExists(APPLICATION_LOG_PATH);
                Files.deleteIfExists(ANALYSIS_REPORT_PATH);
                Files.deleteIfExists(REALTIME_LOG_PATH); // リアルタイム監視で使用したファイルも削除
            } catch (IOException e) {
                System.err.println("ファイル削除中にエラーが発生しました: " + e.getMessage());
            }
        }
    }

    /**
     * サンプルログファイルの生成
     */
    private static void generateSampleLogFile(Path logFile, int entries) throws IOException { // Path型に変更
        Random random = new Random();

        String[] levels = {"DEBUG", "INFO", "WARN", "ERROR"};
        String[] modules = {"Auth", "Database", "API", "Cache", "Queue"};
        String[] messages = {
                "User login successful",
                "Database connection established",
                "API request processed",
                "Cache hit for key",
                "Queue message processed",
                "Authentication failed",
                "Database query timeout",
                "API rate limit exceeded",
                "Cache miss for key",
                "Queue processing failed"
        };

        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(logFile, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE))) {

            LocalDateTime baseTime = LocalDateTime.now().minusDays(7);

            for (int i = 0; i < entries; i++) {
                LocalDateTime timestamp = baseTime.plusMinutes(random.nextInt(10080));
                String level = levels[random.nextInt(levels.length)];
                String module = modules[random.nextInt(modules.length)];
                String message = messages[random.nextInt(messages.length)];
                int responseTime = random.nextInt(1000) + 50;

                writer.printf("%s [%s] [%s] %s (response_time=%dms)%n",
                        timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        level, module, message, responseTime);

                if (level.equals("ERROR") && random.nextBoolean()) {
                    writer.println("  Stack trace:");
                    writer.println("    at com.example.Module.method(Module.java:123)");
                    writer.println("    at com.example.Service.process(Service.java:45)");
                }
            }

            System.out.println("サンプルログファイルを生成しました: " +
                    logFile.toAbsolutePath() + " (" + entries + "エントリ)");

        } catch (IOException e) {
            System.err.println("ログファイル生成エラー: " + e.getMessage());
            throw e;
        }
    }

    /**
     * リアルタイム監視のデモンストレーション
     */
    private static void demonstrateRealTimeMonitoring() {
        // ★★★ ここも修正 ★★★
        Path monitorFile = REALTIME_LOG_PATH; // 定義済みの絶対パスを使用
        // ★★★ 修正ここまで ★★★

        System.out.println("\n=== リアルタイム監視シミュレーション ===");

        try {
            Thread monitor = new Thread(() -> {
                try (LogMonitor logMonitor = new LogMonitor(monitorFile)) {
                    logMonitor.startMonitoring();
                } catch (Exception e) {
                    if (!(e instanceof InterruptedException || e.getCause() instanceof InterruptedException)) {
                        System.err.println("監視エラー: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            monitor.start();

            Thread generator = new Thread(() -> {
                try (PrintWriter writer = new PrintWriter(
                        Files.newBufferedWriter(monitorFile, StandardCharsets.UTF_8,
                                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE))) { // APPENDは削除済み

                    for (int i = 0; i < 5; i++) {
                        String log = String.format("%s [INFO] リアルタイムログ #%d",
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), i + 1);
                        writer.println(log);
                        writer.flush();
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    System.err.println("生成エラー: " + e.getMessage());
                }
            });

            generator.start();
            generator.join();

            Thread.sleep(2000);
            monitor.interrupt();
            monitor.join(5000);

        } catch (Exception e) {
            System.err.println("リアルタイム監視デモンストレーション中にエラーが発生しました: " + e.getMessage());
            e.printStackTrace();
        }
    }
}