package Summary;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day4Summary {
    public static void main(String[] args) {
        System.out.println("=== Day 4 まとめ ===\n");

        System.out.println("1. 学習した内容:");
        System.out.println("   - 例外処理の基礎とカスタム例外");
        System.out.println("   - try-with-resourcesによるリソース管理");
        System.out.println("   - 従来のFile I/O（InputStream/OutputStream）");
        System.out.println("   - NIO.2による効率的なファイル操作");
        System.out.println("   - シリアライゼーションとデータ永続化");

        System.out.println("\n2. 例外処理のベストプラクティス:");
        displayExceptionBestPractices();

        System.out.println("\n3. ファイルI/Oの使い分け:");
        displayFileIOComparison();

        System.out.println("\n4. 重要なコード例:");
        showImportantCodeExamples();

        System.out.println("\n5. PythonとJavaの比較:");
        comparePythonJava();

        System.out.println("\n6. 明日（Day 5）の予定:");
        System.out.println("   - Java 8の新機能（ラムダ式、Stream API詳細）");
        System.out.println("   - Optional型による null 安全性");
        System.out.println("   - 日付と時刻API（java.time）");
        System.out.println("   - 関数型プログラミングの基礎");
        System.out.println("   - 並列ストリームとパフォーマンス");

        System.out.println("\n7. 実践的なアドバイス:");
        System.out.println("   - 例外は適切な抽象レベルで扱う");
        System.out.println("   - リソースは必ず解放する（try-with-resources推奨）");
        System.out.println("   - 大きなファイルはストリーム処理を使用");
        System.out.println("   - NIO.2を優先的に使用（より簡潔で強力）");
        System.out.println("   - エラーログは詳細に、ただし機密情報は含めない");
    }

    private static void displayExceptionBestPractices() {
        System.out.println("┌─────────────────────┬────────────────────────────────────┐");
        System.out.println("│ 悪い例              │ 良い例                             │");
        System.out.println("├─────────────────────┼────────────────────────────────────┤");
        System.out.println("│ catch (Exception e) │ catch (IOException e)              │");
        System.out.println("│ { }                 │ { log.error(\"Failed\", e); }       │");
        System.out.println("├─────────────────────┼────────────────────────────────────┤");
        System.out.println("│ throw new           │ throw new IllegalArgumentException │");
        System.out.println("│ Exception(\"Error\") │ (\"Age must be positive: \" + age)  │");
        System.out.println("├─────────────────────┼────────────────────────────────────┤");
        System.out.println("│ e.printStackTrace() │ logger.error(\"Error occurred\", e) │");
        System.out.println("└─────────────────────┴────────────────────────────────────┘");
    }

    private static void displayFileIOComparison() {
        System.out.println("┌───────────────┬─────────────────────┬─────────────────────┐");
        System.out.println("│ 方式          │ 使用場面            │ 例                  │");
        System.out.println("├───────────────┼─────────────────────┼─────────────────────┤");
        System.out.println("│ Files.write() │ 小さなファイル      │ 設定ファイル        │");
        System.out.println("│ Files.lines() │ 大きなテキスト      │ ログファイル解析    │");
        System.out.println("│ InputStream   │ バイナリファイル    │ 画像、動画          │");
        System.out.println("│ RandomAccess  │ ランダムアクセス    │ データベースファイル│");
        System.out.println("│ WatchService  │ ファイル監視        │ 設定の自動リロード  │");
        System.out.println("└───────────────┴─────────────────────┴─────────────────────┘");
    }

    private static void showImportantCodeExamples() {
        System.out.println("// try-with-resources");
        System.out.println("try (BufferedReader reader = Files.newBufferedReader(path)) {");
        System.out.println("    return reader.lines()");
        System.out.println("                 .filter(line -> line.contains(keyword))");
        System.out.println("                 .collect(Collectors.toList());");
        System.out.println("}");

        System.out.println("\n// NIO.2 ファイル操作");
        System.out.println("Files.write(path, lines, StandardCharsets.UTF_8);");
        System.out.println("List<String> content = Files.readAllLines(path);");
        System.out.println("Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);");

        System.out.println("\n// カスタム例外");
        System.out.println("public class BusinessException extends Exception {");
        System.out.println("    private final ErrorCode errorCode;");
        System.out.println("    public BusinessException(String message, ErrorCode code) {");
        System.out.println("        super(message);");
        System.out.println("        this.errorCode = code;");
        System.out.println("    }");
        System.out.println("}");

        System.out.println("\n// ディレクトリの走査");
        System.out.println("try (Stream<Path> paths = Files.walk(dir)) {");
        System.out.println("    paths.filter(Files::isRegularFile)");
        System.out.println("         .filter(p -> p.toString().endsWith(\".java\"))");
        System.out.println("         .forEach(this::processFile);");
        System.out.println("}");
    }

    private static void comparePythonJava() {
        Map<String, List<String>> comparison = new LinkedHashMap<>();

        comparison.put("例外処理", Arrays.asList(
                "Python: try/except/finally",
                "Java: try/catch/finally + checked exceptions"
        ));

        comparison.put("ファイル読み込み", Arrays.asList(
                "Python: with open('file.txt') as f:",
                "Java: try (var reader = Files.newBufferedReader(path))"
        ));

        comparison.put("パス操作", Arrays.asList(
                "Python: os.path.join() / pathlib",
                "Java: Paths.get() / Path interface"
        ));

        comparison.put("ディレクトリ走査", Arrays.asList(
                "Python: os.walk() / pathlib.glob()",
                "Java: Files.walk() / DirectoryStream"
        ));

        comparison.put("JSON処理", Arrays.asList(
                "Python: json.dumps() / json.loads() (標準)",
                "Java: Jackson / Gson (外部ライブラリ)"
        ));

        System.out.println("┌─────────────────┬──────────────────────────────────────────┐");
        System.out.println("│ 機能            │ Python vs Java                           │");
        System.out.println("├─────────────────┼──────────────────────────────────────────┤");

        comparison.forEach((feature, details) -> {
            System.out.printf("│ %-15s │ %-40s │\n", feature, details.get(0));
            System.out.printf("│ %-15s │ %-40s │\n", "", details.get(1));
            if (comparison.keySet().toArray()[comparison.size()-1] != feature) {
                System.out.println("├─────────────────┼──────────────────────────────────────────┤");
            }
        });

        System.out.println("└─────────────────┴──────────────────────────────────────────┘");
    }
}
