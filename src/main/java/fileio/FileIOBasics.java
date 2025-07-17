package fileio;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

/**
 * ファイルI/Oの基礎
 * テキストファイルとバイナリファイルの読み書き
 */
public class FileIOBasics {
    public static void main(String[] args) {
        System.out.println("=== ファイルI/Oの基礎 ===\n");

        // テキストファイルの操作
        demonstrateTextFileOperations();

        // バイナリファイルの操作
        demonstrateBinaryFileOperations();

        // ファイル情報の取得
        demonstrateFileInfo();

        // ディレクトリ操作
        demonstrateDirectoryOperations();

        // 大きなファイルの効率的な処理
        demonstrateEfficientFileProcessing();
    }

    /**
     * テキストファイルの読み書き
     */
    private static void demonstrateTextFileOperations() {
        System.out.println("=== テキストファイルの操作 ===");

        String filename = "sample.txt";

        // テキストファイルへの書き込み
        System.out.println("--- ファイルへの書き込み ---");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {

            writer.write("Javaファイル入出力のサンプル\n");
            writer.write("2行目のテキスト\n");
            writer.write("日本語も正しく書き込めます。\n");

            // 複数行を一度に書き込む
            List<String> lines = Arrays.asList(
                    "リスト1行目",
                    "リスト2行目",
                    "リスト3行目"
            );
            for (String line : lines) {
                writer.write(line);
                writer.newLine();  // OSに依存しない改行
            }

            System.out.println("ファイル書き込み完了: " + filename);

        } catch (IOException e) {
            System.err.println("書き込みエラー: " + e.getMessage());
        }

        // テキストファイルの読み込み
        System.out.println("\n--- ファイルからの読み込み ---");
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }

        } catch (IOException e) {
            System.err.println("読み込みエラー: " + e.getMessage());
        }

        // ファイルの追記
        System.out.println("\n--- ファイルへの追記 ---");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8, true))) {  // appendモード

            writer.newLine();
            writer.write("追記されたテキスト\n");
            writer.write("タイムスタンプ: " + new Date() + "\n");

            System.out.println("追記完了");

        } catch (IOException e) {
            System.err.println("追記エラー: " + e.getMessage());
        }

        // PrintWriterを使った簡単な書き込み
        System.out.println("\n--- PrintWriterの使用 ---");
        try (PrintWriter pw = new PrintWriter(
                new FileWriter("output.txt", StandardCharsets.UTF_8))) {

            pw.println("PrintWriterは便利です");
            pw.printf("フォーマット出力: %d + %d = %d%n", 10, 20, 30);
            pw.println("自動フラッシュも可能");

            // オブジェクトの文字列表現を書き込む
            pw.println(Arrays.asList("A", "B", "C"));

            System.out.println("PrintWriter書き込み完了");

        } catch (IOException e) {
            System.err.println("PrintWriterエラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * バイナリファイルの操作
     */
    private static void demonstrateBinaryFileOperations() {
        System.out.println("=== バイナリファイルの操作 ===");

        String binaryFile = "data.bin";

        // バイナリデータの書き込み
        System.out.println("--- バイナリ書き込み ---");
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(binaryFile))) {

            // 各種データ型の書き込み
            dos.writeBoolean(true);
            dos.writeByte(127);
            dos.writeShort(32767);
            dos.writeInt(123456789);
            dos.writeLong(9876543210L);
            dos.writeFloat(3.14f);
            dos.writeDouble(2.71828);
            dos.writeUTF("UTF文字列");

            // バイト配列の書き込み
            byte[] bytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F};  // "Hello"
            dos.write(bytes);

            System.out.println("バイナリデータ書き込み完了");

        } catch (IOException e) {
            System.err.println("バイナリ書き込みエラー: " + e.getMessage());
        }

        // バイナリデータの読み込み
        System.out.println("\n--- バイナリ読み込み ---");
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream(binaryFile))) {

            // 書き込んだ順序で読み込む
            boolean boolValue = dis.readBoolean();
            byte byteValue = dis.readByte();
            short shortValue = dis.readShort();
            int intValue = dis.readInt();
            long longValue = dis.readLong();
            float floatValue = dis.readFloat();
            double doubleValue = dis.readDouble();
            String utfString = dis.readUTF();

            System.out.println("読み込んだデータ:");
            System.out.println("  boolean: " + boolValue);
            System.out.println("  byte: " + byteValue);
            System.out.println("  short: " + shortValue);
            System.out.println("  int: " + intValue);
            System.out.println("  long: " + longValue);
            System.out.println("  float: " + floatValue);
            System.out.println("  double: " + doubleValue);
            System.out.println("  UTF文字列: " + utfString);

            // 残りのバイトを読む
            byte[] remainingBytes = new byte[5];
            dis.readFully(remainingBytes);
            System.out.println("  バイト配列: " + new String(remainingBytes));

        } catch (IOException e) {
            System.err.println("バイナリ読み込みエラー: " + e.getMessage());
        }

        // 画像ファイルのコピー（バイナリファイルの例）
        System.out.println("\n--- バイナリファイルのコピー ---");
        copyBinaryFile("input.dat", "output.dat");

        System.out.println();
    }

    /**
     * ファイル情報の取得
     */
    private static void demonstrateFileInfo() {
        System.out.println("=== ファイル情報の取得 ===");

        File file = new File("sample.txt");

        if (file.exists()) {
            System.out.println("ファイル名: " + file.getName());
            System.out.println("絶対パス: " + file.getAbsolutePath());
            System.out.println("親ディレクトリ: " + file.getParent());
            System.out.println("サイズ: " + file.length() + " バイト");
            System.out.println("読み取り可能: " + file.canRead());
            System.out.println("書き込み可能: " + file.canWrite());
            System.out.println("実行可能: " + file.canExecute());
            System.out.println("隠しファイル: " + file.isHidden());
            System.out.println("最終更新日時: " + new Date(file.lastModified()));

            // ファイルサイズを人間が読みやすい形式で表示
            System.out.println("サイズ（読みやすい形式）: " +
                    humanReadableByteCount(file.length()));
        } else {
            System.out.println("ファイルが存在しません: " + file.getName());
        }

        System.out.println();
    }

    /**
     * ディレクトリ操作
     */
    private static void demonstrateDirectoryOperations() {
        System.out.println("=== ディレクトリ操作 ===");

        // ディレクトリの作成
        File dir = new File("test_directory");
        if (!dir.exists()) {
            boolean created = dir.mkdir();
            System.out.println("ディレクトリ作成: " + (created ? "成功" : "失敗"));
        }

        // 複数階層のディレクトリ作成
        File deepDir = new File("parent/child/grandchild");
        boolean createdDirs = deepDir.mkdirs();
        System.out.println("複数階層作成: " + (createdDirs ? "成功" : "既存"));

        // ディレクトリ内のファイル一覧
        File currentDir = new File(".");
        System.out.println("\n現在のディレクトリのファイル一覧:");

        File[] files = currentDir.listFiles();
        if (files != null) {
            for (File f : files) {
                String type = f.isDirectory() ? "[DIR] " : "[FILE]";
                System.out.printf("%s %-30s %10s%n",
                        type, f.getName(),
                        f.isFile() ? humanReadableByteCount(f.length()) : "");
            }
        }

        // ファイルフィルタの使用
        System.out.println("\n.txtファイルのみ:");
        File[] textFiles = currentDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        if (textFiles != null) {
            for (File f : textFiles) {
                System.out.println("  " + f.getName());
            }
        }

        // ラムダ式を使ったフィルタ
        File[] javaFiles = currentDir.listFiles(
                (dir1, name) -> name.endsWith(".java")
        );
        System.out.println("\n.javaファイルの数: " +
                (javaFiles != null ? javaFiles.length : 0));

        System.out.println();
    }

    /**
     * 大きなファイルの効率的な処理
     */
    private static void demonstrateEfficientFileProcessing() {
        System.out.println("=== 効率的なファイル処理 ===");

        String largeFile = "large_file.txt";

        // 大きなファイルの作成（デモ用）
        createLargeFile(largeFile, 1000);

        // バッファサイズを指定した読み込み
        System.out.println("--- バッファを使った読み込み ---");
        int bufferSize = 8192;  // 8KB

        try (BufferedReader reader = new BufferedReader(
                new FileReader(largeFile, StandardCharsets.UTF_8), bufferSize)) {

            String line;
            int lineCount = 0;
            int charCount = 0;

            long startTime = System.currentTimeMillis();

            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
            }

            long endTime = System.currentTimeMillis();

            System.out.println("行数: " + lineCount);
            System.out.println("文字数: " + charCount);
            System.out.println("処理時間: " + (endTime - startTime) + "ms");

        } catch (IOException e) {
            System.err.println("読み込みエラー: " + e.getMessage());
        }

        // RandomAccessFileの使用
        System.out.println("\n--- RandomAccessFile ---");
        try (RandomAccessFile raf = new RandomAccessFile(largeFile, "r")) {

            // ファイルの末尾から読む
            long fileLength = raf.length();
            long pos = fileLength - 100;  // 末尾から100バイト前

            if (pos > 0) {
                raf.seek(pos);
                String lastPart = raf.readLine();
                System.out.println("末尾付近のデータ: " + lastPart);
            }

            // ランダムな位置へのアクセス
            raf.seek(fileLength / 2);  // ファイルの中央
            String middlePart = raf.readLine();
            System.out.println("中央付近のデータ: " + middlePart);

        } catch (IOException e) {
            System.err.println("RandomAccessFileエラー: " + e.getMessage());
        }

        // クリーンアップ
        new File(largeFile).delete();

        System.out.println();
    }

    // ===== ヘルパーメソッド =====

    /**
     * バイナリファイルのコピー
     */
    private static void copyBinaryFile(String source, String destination) {
        // サンプルファイルを作成
        createSampleBinaryFile(source);

        try (
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(destination)
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("ファイルコピー完了: " + source + " → " + destination);

        } catch (IOException e) {
            System.err.println("コピーエラー: " + e.getMessage());
        }
    }

    /**
     * サンプルバイナリファイルの作成
     */
    private static void createSampleBinaryFile(String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            // ダミーのバイナリデータ
            byte[] data = new byte[256];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) i;
            }
            fos.write(data);
        } catch (IOException e) {
            // 無視
        }
    }

    /**
     * 大きなファイルの作成（テスト用）
     */
    private static void createLargeFile(String filename, int lines) {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {

            for (int i = 0; i < lines; i++) {
                writer.println("Line " + (i + 1) + ": " +
                        "This is a sample text for testing file I/O operations. " +
                        "各行には同じテキストが含まれています。");
            }

        } catch (IOException e) {
            System.err.println("ファイル作成エラー: " + e.getMessage());
        }
    }

    /**
     * バイト数を人間が読みやすい形式に変換
     */
    private static String humanReadableByteCount(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), unit);
    }
}
