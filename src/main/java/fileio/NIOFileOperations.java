package fileio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * NIO.2（New I/O 2）によるモダンなファイル操作
 * Java 7以降で利用可能
 */
public class NIOFileOperations {
    public static void main(String[] args) {
        System.out.println("=== NIO.2によるファイル操作 ===\n");

        // Pathの基本操作
        demonstratePathOperations();

        // ファイルの読み書き（簡潔な方法）
        demonstrateSimpleFileOperations();

        // ディレクトリの走査
        demonstrateDirectoryTraversal();

        // ファイル属性の操作
        demonstrateFileAttributes();

        // ファイルの監視
        demonstrateFileWatching();

        // 実践的な例
        demonstratePracticalExamples();
    }

    /**
     * Pathの基本操作
     */
    private static void demonstratePathOperations() {
        System.out.println("=== Path の基本操作 ===");

        // Pathの作成
        Path path1 = Paths.get("folder", "subfolder", "file.txt");
        Path path2 = Paths.get("/home/user/documents/report.pdf");
        Path path3 = Paths.get(".");  // 現在のディレクトリ

        System.out.println("Path1: " + path1);
        System.out.println("Path2: " + path2);
        System.out.println("Path3（絶対パス）: " + path3.toAbsolutePath());

        // Pathの情報取得
        Path currentPath = Paths.get("src/main/java/Example.java");
        System.out.println("\nPathの情報:");
        System.out.println("ファイル名: " + currentPath.getFileName());
        System.out.println("親ディレクトリ: " + currentPath.getParent());
        System.out.println("ルート: " + currentPath.getRoot());
        System.out.println("名前の数: " + currentPath.getNameCount());

        // パス要素へのアクセス
        System.out.println("\nパス要素:");
        for (int i = 0; i < currentPath.getNameCount(); i++) {
            System.out.println("  [" + i + "] " + currentPath.getName(i));
        }

        // パスの結合と正規化
        Path base = Paths.get("/home/user");
        Path relative = Paths.get("../documents/./file.txt");
        Path resolved = base.resolve(relative);
        Path normalized = resolved.normalize();

        System.out.println("\nパスの操作:");
        System.out.println("結合前: " + base + " + " + relative);
        System.out.println("結合後: " + resolved);
        System.out.println("正規化: " + normalized);

        // 相対パスの計算
        Path path4 = Paths.get("/home/user/documents");
        Path path5 = Paths.get("/home/user/downloads/file.txt");
        Path relativePath = path4.relativize(path5);
        System.out.println("\n相対パス: " + path4 + " → " + path5);
        System.out.println("結果: " + relativePath);

        System.out.println();
    }

    /**
     * 簡潔なファイル操作
     */
    private static void demonstrateSimpleFileOperations() {
        System.out.println("=== 簡潔なファイル操作 ===");

        Path textFile = Paths.get("nio_sample.txt");

        // ファイルへの書き込み（1行で完結）
        System.out.println("--- ファイル書き込み ---");
        try {
            List<String> lines = Arrays.asList(
                    "NIO.2による簡単なファイル操作",
                    "1行でファイルの読み書きが可能",
                    "日本語も問題なく扱えます",
                    "最終行"
            );

            Files.write(textFile, lines, StandardCharsets.UTF_8);
            System.out.println("ファイル書き込み完了: " + textFile);

            // 追記モード
            Files.write(textFile,
                    Arrays.asList("追記された行"),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("書き込みエラー: " + e.getMessage());
        }

        // ファイルの読み込み（全行を一度に）
        System.out.println("\n--- ファイル読み込み（全行） ---");
        try {
            List<String> allLines = Files.readAllLines(textFile, StandardCharsets.UTF_8);
            allLines.forEach(line -> System.out.println("  " + line));

        } catch (IOException e) {
            System.err.println("読み込みエラー: " + e.getMessage());
        }

        // ファイルの読み込み（Stream API）
        System.out.println("\n--- Stream APIでの読み込み ---");
        try (Stream<String> lines = Files.lines(textFile, StandardCharsets.UTF_8)) {
            lines.filter(line -> line.contains("NIO"))
                    .forEach(line -> System.out.println("  NIOを含む行: " + line));

        } catch (IOException e) {
            System.err.println("Stream読み込みエラー: " + e.getMessage());
        }

        // ファイルのコピー
        System.out.println("\n--- ファイルコピー ---");
        Path copyFile = Paths.get("nio_sample_copy.txt");
        try {
            Files.copy(textFile, copyFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("コピー完了: " + textFile + " → " + copyFile);

        } catch (IOException e) {
            System.err.println("コピーエラー: " + e.getMessage());
        }

        // ファイルの移動（リネーム）
        System.out.println("\n--- ファイル移動 ---");
        Path movedFile = Paths.get("moved_file.txt");
        try {
            Files.move(copyFile, movedFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("移動完了: " + copyFile + " → " + movedFile);

        } catch (IOException e) {
            System.err.println("移動エラー: " + e.getMessage());
        }

        // ファイルの削除
        System.out.println("\n--- ファイル削除 ---");
        try {
            Files.deleteIfExists(movedFile);
            Files.deleteIfExists(textFile);
            System.out.println("ファイル削除完了");

        } catch (IOException e) {
            System.err.println("削除エラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * ディレクトリの走査
     */
    private static void demonstrateDirectoryTraversal() {
        System.out.println("=== ディレクトリの走査 ===");

        // テスト用ディレクトリ構造の作成
        createTestDirectoryStructure();

        Path rootDir = Paths.get("test_root");

        // ディレクトリ内のファイル一覧（1階層のみ）
        System.out.println("--- DirectoryStream ---");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootDir)) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName() +
                        (Files.isDirectory(entry) ? "/" : ""));
            }
        } catch (IOException e) {
            System.err.println("ディレクトリ読み込みエラー: " + e.getMessage());
        }

        // フィルタ付きディレクトリストリーム
        System.out.println("\n--- フィルタ付き（.txtファイルのみ） ---");
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(rootDir, "*.txt")) {
            stream.forEach(path -> System.out.println("  " + path.getFileName()));
        } catch (IOException e) {
            System.err.println("フィルタエラー: " + e.getMessage());
        }

        // 再帰的なファイル走査
        System.out.println("\n--- Files.walk（再帰的） ---");
        try (Stream<Path> paths = Files.walk(rootDir)) {
            paths.forEach(path -> {
                int depth = path.getNameCount() - rootDir.getNameCount();
                String indent = "  ".repeat(depth);
                System.out.println(indent + path.getFileName() +
                        (Files.isDirectory(path) ? "/" : ""));
            });
        } catch (IOException e) {
            System.err.println("walk エラー: " + e.getMessage());
        }

        // ファイル検索
        System.out.println("\n--- ファイル検索 ---");
        try (Stream<Path> paths = Files.walk(rootDir)) {
            List<Path> javaFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());

            System.out.println(".javaファイル:");
            javaFiles.forEach(path -> System.out.println("  " +
                    rootDir.relativize(path)));

        } catch (IOException e) {
            System.err.println("検索エラー: " + e.getMessage());
        }

        // ディレクトリサイズの計算
        System.out.println("\n--- ディレクトリサイズ ---");
        try {
            long size = calculateDirectorySize(rootDir);
            System.out.println("ディレクトリサイズ: " + size + " バイト");
        } catch (IOException e) {
            System.err.println("サイズ計算エラー: " + e.getMessage());
        }

        // クリーンアップ
        try {
            deleteDirectoryRecursively(rootDir);
        } catch (IOException e) {
            // 無視
        }

        System.out.println();
    }

    /**
     * ファイル属性の操作
     */
    private static void demonstrateFileAttributes() {
        System.out.println("=== ファイル属性 ===");

        Path file = Paths.get("attribute_test.txt");

        try {
            // ファイルの作成
            Files.write(file, Arrays.asList("テストファイル"), StandardCharsets.UTF_8);

            // 基本属性の取得
            BasicFileAttributes attrs = Files.readAttributes(file,
                    BasicFileAttributes.class);

            System.out.println("基本属性:");
            System.out.println("  作成時刻: " + attrs.creationTime());
            System.out.println("  最終更新: " + attrs.lastModifiedTime());
            System.out.println("  最終アクセス: " + attrs.lastAccessTime());
            System.out.println("  サイズ: " + attrs.size() + " バイト");
            System.out.println("  通常ファイル: " + attrs.isRegularFile());
            System.out.println("  ディレクトリ: " + attrs.isDirectory());

            // ファイル所有者（POSIX環境）
            try {
                UserPrincipal owner = Files.getOwner(file);
                System.out.println("\n所有者: " + owner.getName());
            } catch (UnsupportedOperationException e) {
                System.out.println("\n所有者情報: このOSではサポートされていません");
            }

            // ファイル権限の設定（POSIX環境）
            try {
                Set<PosixFilePermission> permissions = EnumSet.of(
                        PosixFilePermission.OWNER_READ,
                        PosixFilePermission.OWNER_WRITE,
                        PosixFilePermission.GROUP_READ,
                        PosixFilePermission.OTHERS_READ
                );
                Files.setPosixFilePermissions(file, permissions);
                System.out.println("権限設定: rw-r--r--");
            } catch (UnsupportedOperationException e) {
                System.out.println("POSIX権限: このOSではサポートされていません");
            }

            // 最終更新時刻の変更
            FileTime newTime = FileTime.from(
                    LocalDateTime.now().minusDays(7)
                            .atZone(ZoneId.systemDefault()).toInstant()
            );
            Files.setLastModifiedTime(file, newTime);
            System.out.println("\n最終更新時刻を7日前に変更");

            // ファイルの削除
            Files.deleteIfExists(file);

        } catch (IOException e) {
            System.err.println("属性操作エラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * ファイルシステムの監視
     */
    private static void demonstrateFileWatching() {
        System.out.println("=== ファイルシステムの監視 ===");

        Path watchDir = Paths.get("watch_directory");

        try {
            // 監視用ディレクトリの作成
            Files.createDirectories(watchDir);
            System.out.println("監視ディレクトリ: " + watchDir.toAbsolutePath());

            // WatchServiceの作成
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // ディレクトリを監視対象に登録
            watchDir.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            System.out.println("監視を開始します（5秒間）...");
            System.out.println("別のターミナルでファイルを作成/変更/削除してください");

            // 別スレッドでファイル操作をシミュレート
            Thread fileOperator = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Files.write(watchDir.resolve("test1.txt"),
                            Arrays.asList("新規ファイル"));

                    Thread.sleep(1000);
                    Files.write(watchDir.resolve("test1.txt"),
                            Arrays.asList("更新されたファイル"));

                    Thread.sleep(1000);
                    Files.delete(watchDir.resolve("test1.txt"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            fileOperator.start();

            // イベントの監視（5秒間）
            long endTime = System.currentTimeMillis() + 5000;
            while (System.currentTimeMillis() < endTime) {
                WatchKey key = watchService.poll();
                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        Path fileName = (Path) event.context();

                        System.out.println("イベント: " + kind + " - " + fileName);
                    }
                    key.reset();
                }
                Thread.sleep(100);
            }

            watchService.close();

            // クリーンアップ
            deleteDirectoryRecursively(watchDir);

        } catch (IOException | InterruptedException e) {
            System.err.println("監視エラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * 実践的な例
     */
    private static void demonstratePracticalExamples() {
        System.out.println("=== 実践的な例 ===");

        // 1. 設定ファイルの読み込み
        System.out.println("--- 設定ファイルの読み込み ---");
        Path configFile = Paths.get("config.properties");
        try {
            // 設定ファイルの作成
            List<String> configLines = Arrays.asList(
                    "# アプリケーション設定",
                    "app.name=MyApplication",
                    "app.version=1.0.0",
                    "database.url=jdbc:mysql://localhost:3306/mydb",
                    "database.user=root",
                    "debug.mode=true"
            );
            Files.write(configFile, configLines, StandardCharsets.UTF_8);

            // Properties形式で読み込み
            Properties props = new Properties();
            try (var reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
                props.load(reader);
            }

            System.out.println("設定値:");
            props.forEach((key, value) ->
                    System.out.println("  " + key + " = " + value));

            Files.delete(configFile);

        } catch (IOException e) {
            System.err.println("設定ファイルエラー: " + e.getMessage());
        }

        // 2. CSVファイルの処理
        System.out.println("\n--- CSVファイルの処理 ---");
        Path csvFile = Paths.get("data.csv");
        try {
            // CSVデータの作成
            List<String> csvData = Arrays.asList(
                    "ID,Name,Age,Department",
                    "001,山田太郎,30,営業部",
                    "002,鈴木花子,25,開発部",
                    "003,佐藤次郎,35,人事部"
            );
            Files.write(csvFile, csvData, StandardCharsets.UTF_8);

            // Stream APIでCSVを処理
            System.out.println("CSVデータ（開発部のみ）:");
            try (Stream<String> lines = Files.lines(csvFile, StandardCharsets.UTF_8)) {
                lines.skip(1)  // ヘッダーをスキップ
                        .map(line -> line.split(","))
                        .filter(fields -> fields[3].equals("開発部"))
                        .forEach(fields ->
                                System.out.printf("  %s: %s (%s歳)\n",
                                        fields[0], fields[1], fields[2]));
            }

            Files.delete(csvFile);

        } catch (IOException e) {
            System.err.println("CSVエラー: " + e.getMessage());
        }

        // 3. ログファイルのローテーション
        System.out.println("\n--- ログファイルのローテーション ---");
        simulateLogRotation();

        // 4. ファイルの暗号化（簡易版）
        System.out.println("\n--- ファイルの簡易暗号化 ---");
        demonstrateSimpleEncryption();

        System.out.println();
    }

    // ===== ヘルパーメソッド =====

    /**
     * テスト用ディレクトリ構造の作成
     */
    private static void createTestDirectoryStructure() {
        try {
            Path root = Paths.get("test_root");
            Files.createDirectories(root.resolve("src/main/java"));
            Files.createDirectories(root.resolve("src/test/java"));
            Files.createDirectories(root.resolve("docs"));

            Files.write(root.resolve("README.txt"),
                    Arrays.asList("Test Project"));
            Files.write(root.resolve("src/main/java/Main.java"),
                    Arrays.asList("public class Main {}"));
            Files.write(root.resolve("src/test/java/Test.java"),
                    Arrays.asList("public class Test {}"));
            Files.write(root.resolve("docs/manual.txt"),
                    Arrays.asList("User Manual"));

        } catch (IOException e) {
            // 無視
        }
    }

    /**
     * ディレクトリの再帰的削除
     */
    private static void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            // 無視
                        }
                    });
        }
    }

    /**
     * ディレクトリサイズの計算
     */
    private static long calculateDirectorySize(Path path) throws IOException {
        try (Stream<Path> paths = Files.walk(path)) {
            return paths.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0;
                        }
                    })
                    .sum();
        }
    }

    /**
     * ログファイルのローテーションシミュレーション
     */
    private static void simulateLogRotation() {
        Path logFile = Paths.get("app.log");
        Path rotatedLog = Paths.get("app.log.1");

        try {
            // 現在のログファイルを作成
            List<String> logEntries = Arrays.asList(
                    "2024-01-01 10:00:00 INFO Application started",
                    "2024-01-01 10:00:01 DEBUG Loading configuration",
                    "2024-01-01 10:00:02 INFO Server listening on port 8080"
            );
            Files.write(logFile, logEntries, StandardCharsets.UTF_8);

            // ログのローテーション
            if (Files.exists(logFile)) {
                // 既存のローテーションファイルがあれば削除
                Files.deleteIfExists(rotatedLog);

                // 現在のログをローテーション
                Files.move(logFile, rotatedLog);
                System.out.println("ログローテーション完了: " +
                        logFile + " → " + rotatedLog);

                // 新しいログファイルを作成
                Files.write(logFile,
                        Arrays.asList("2024-01-02 09:00:00 INFO New log file created"),
                        StandardCharsets.UTF_8);
            }

            // クリーンアップ
            Files.deleteIfExists(logFile);
            Files.deleteIfExists(rotatedLog);

        } catch (IOException e) {
            System.err.println("ログローテーションエラー: " + e.getMessage());
        }
    }

    /**
     * 簡易暗号化のデモンストレーション
     */
    private static void demonstrateSimpleEncryption() {
        Path originalFile = Paths.get("secret.txt");
        Path encryptedFile = Paths.get("secret.enc");
        Path decryptedFile = Paths.get("decrypted.txt");

        try {
            // 元のファイルを作成
            String secretMessage = "これは秘密のメッセージです。";
            Files.write(originalFile, Arrays.asList(secretMessage),
                    StandardCharsets.UTF_8);

            // 簡易XOR暗号化
            byte[] key = "MySecretKey".getBytes(StandardCharsets.UTF_8);
            byte[] data = Files.readAllBytes(originalFile);
            byte[] encrypted = xorEncrypt(data, key);
            Files.write(encryptedFile, encrypted);
            System.out.println("暗号化完了: " + originalFile + " → " + encryptedFile);

            // 復号化
            byte[] encryptedData = Files.readAllBytes(encryptedFile);
            byte[] decrypted = xorEncrypt(encryptedData, key);  // XORは可逆
            Files.write(decryptedFile, decrypted);

            // 検証
            String decryptedMessage = Files.readString(decryptedFile,
                    StandardCharsets.UTF_8);
            System.out.println("復号化完了: " + decryptedMessage);

            // クリーンアップ
            Files.deleteIfExists(originalFile);
            Files.deleteIfExists(encryptedFile);
            Files.deleteIfExists(decryptedFile);

        } catch (IOException e) {
            System.err.println("暗号化エラー: " + e.getMessage());
        }
    }

    /**
     * 簡易XOR暗号化
     */
    private static byte[] xorEncrypt(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
}
