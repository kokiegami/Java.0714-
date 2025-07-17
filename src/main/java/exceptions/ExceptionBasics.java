package exceptions;

import java.io.*;
import java.util.*;

/**
 * 例外処理の基礎
 * Pythonとの比較を含む
 */
public class ExceptionBasics {
    public static void main(String[] args) {
        System.out.println("=== 例外処理の基礎 ===\n");

        // 基本的なtry-catch
        demonstrateBasicTryCatch();

        // 複数の例外処理
        demonstrateMultipleExceptions();

        // finally節
        demonstrateFinallyBlock();

        // 例外の再スロー
        demonstrateRethrow();

        // チェック例外と非チェック例外
        demonstrateCheckedVsUnchecked();

        // Pythonとの比較
        comparePythonJavaExceptions();
    }

    /**
     * 基本的なtry-catch
     */
    private static void demonstrateBasicTryCatch() {
        System.out.println("=== 基本的なtry-catch ===");

        // 算術例外
        try {
            int result = 10 / 0;
            System.out.println("結果: " + result);
        } catch (ArithmeticException e) {
            System.out.println("エラー: ゼロ除算が発生しました");
            System.out.println("例外メッセージ: " + e.getMessage());
        }

        // 配列インデックス例外
        System.out.println("\n--- 配列インデックス例外 ---");
        int[] numbers = {1, 2, 3};
        try {
            System.out.println("4番目の要素: " + numbers[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("エラー: 配列の範囲外にアクセスしました");
            System.out.println("スタックトレース:");
            e.printStackTrace(System.out);
        }

        // NullPointerException
        System.out.println("\n--- NullPointerException ---");
        String str = null;
        try {
            int length = str.length();
            System.out.println("文字列の長さ: " + length);
        } catch (NullPointerException e) {
            System.out.println("エラー: nullオブジェクトにアクセスしました");
        }

        System.out.println();
    }

    /**
     * 複数の例外処理
     */
    private static void demonstrateMultipleExceptions() {
        System.out.println("=== 複数の例外処理 ===");

        // 個別のcatch節
        System.out.println("--- 個別のcatch節 ---");
        try {
            // ユーザー入力をシミュレート
            String input = "abc";  // 数値でない入力
            int number = Integer.parseInt(input);
            int result = 100 / number;
            System.out.println("結果: " + result);
        } catch (NumberFormatException e) {
            System.out.println("エラー: 数値の形式が正しくありません");
        } catch (ArithmeticException e) {
            System.out.println("エラー: 算術エラーが発生しました");
        }

        // マルチキャッチ（Java 7以降）
        System.out.println("\n--- マルチキャッチ ---");
        try {
            randomException();
        } catch (IOException | SQLException e) {
            System.out.println("I/OまたはSQL例外が発生: " + e.getClass().getSimpleName());
        }

        // 例外の階層を利用
        System.out.println("\n--- 例外の階層 ---");
        try {
            methodThatThrowsException();
        } catch (FileNotFoundException e) {
            System.out.println("ファイルが見つかりません: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/Oエラー: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("その他のエラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * finally節の使用
     */
    private static void demonstrateFinallyBlock() {
        System.out.println("=== finally節 ===");

        // リソースのクリーンアップ
        BufferedReader reader = null;
        try {
            System.out.println("ファイルを開いています...");
            reader = new BufferedReader(new StringReader("テストデータ"));
            String line = reader.readLine();
            System.out.println("読み込んだデータ: " + line);

            // 意図的に例外を発生させる
            if (line != null) {
                throw new RuntimeException("テスト例外");
            }
        } catch (IOException e) {
            System.out.println("I/Oエラー: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("実行時エラー: " + e.getMessage());
        } finally {
            System.out.println("finally節: リソースをクリーンアップ");
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("リーダーをクローズしました");
                } catch (IOException e) {
                    System.out.println("クローズ時のエラー: " + e.getMessage());
                }
            }
        }

        // try-finallyパターン（catchなし）
        System.out.println("\n--- try-finally（catchなし） ---");
        try {
            System.out.println("重要な処理を実行");
            // return;  // returnしてもfinallyは実行される
        } finally {
            System.out.println("必ず実行される処理");
        }

        System.out.println();
    }

    /**
     * 例外の再スロー
     */
    private static void demonstrateRethrow() {
        System.out.println("=== 例外の再スロー ===");

        try {
            processData("invalid");
        } catch (DataProcessingException e) {
            System.out.println("データ処理エラーをキャッチ: " + e.getMessage());
            System.out.println("元の原因: " + e.getCause().getMessage());
        }

        System.out.println();
    }

    /**
     * チェック例外と非チェック例外
     */
    private static void demonstrateCheckedVsUnchecked() {
        System.out.println("=== チェック例外 vs 非チェック例外 ===");

        System.out.println("チェック例外（コンパイル時チェック）:");
        System.out.println("- IOException");
        System.out.println("- SQLException");
        System.out.println("- ClassNotFoundException");
        System.out.println("→ 必ずtry-catchまたはthrows宣言が必要");

        System.out.println("\n非チェック例外（実行時例外）:");
        System.out.println("- NullPointerException");
        System.out.println("- ArrayIndexOutOfBoundsException");
        System.out.println("- IllegalArgumentException");
        System.out.println("→ try-catchは任意");

        // チェック例外の例
        System.out.println("\n--- チェック例外の処理 ---");
        try {
            readFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("ファイル読み込みエラー: " + e.getMessage());
        }

        // 非チェック例外の例
        System.out.println("\n--- 非チェック例外の処理 ---");
        String text = "Hello";
        try {
            char ch = text.charAt(10);  // IndexOutOfBoundsException
        } catch (IndexOutOfBoundsException e) {
            System.out.println("インデックスエラー（任意でキャッチ）");
        }

        System.out.println();
    }

    /**
     * PythonとJavaの例外処理比較
     */
    private static void comparePythonJavaExceptions() {
        System.out.println("=== Python vs Java 例外処理 ===");

        System.out.println("構文の比較:");
        System.out.println("Python:");
        System.out.println("try:");
        System.out.println("    result = 10 / 0");
        System.out.println("except ZeroDivisionError as e:");
        System.out.println("    print(f'Error: {e}')");
        System.out.println("finally:");
        System.out.println("    print('Cleanup')");

        System.out.println("\nJava:");
        System.out.println("try {");
        System.out.println("    int result = 10 / 0;");
        System.out.println("} catch (ArithmeticException e) {");
        System.out.println("    System.out.println(\"Error: \" + e);");
        System.out.println("} finally {");
        System.out.println("    System.out.println(\"Cleanup\");");
        System.out.println("}");

        System.out.println("\n主な違い:");
        System.out.println("1. Javaにはチェック例外がある（Pythonにはない）");
        System.out.println("2. Javaはthrows宣言が必要（チェック例外の場合）");
        System.out.println("3. Pythonのelse節に相当するものはJavaにない");
        System.out.println("4. Java 7以降はtry-with-resourcesが使える");

        System.out.println();
    }

    // ===== ヘルパーメソッド =====

    private static void randomException() throws IOException, SQLException {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            throw new IOException("I/O例外");
        } else {
            throw new SQLException("SQL例外");
        }
    }

    private static void methodThatThrowsException() throws IOException {
        Random rand = new Random();
        int value = rand.nextInt(3);
        switch (value) {
            case 0:
                throw new FileNotFoundException("ファイルが見つかりません");
            case 1:
                throw new IOException("I/Oエラー");
            default:
                throw new RuntimeException("実行時エラー");
        }
    }

    private static void processData(String data) throws DataProcessingException {
        try {
            if ("invalid".equals(data)) {
                throw new IllegalArgumentException("無効なデータ");
            }
            // データ処理
        } catch (IllegalArgumentException e) {
            // 例外をラップして再スロー
            throw new DataProcessingException("データ処理に失敗しました", e);
        }
    }

    private static void readFile(String filename) throws IOException {
        throw new FileNotFoundException("ファイル '" + filename + "' が見つかりません");
    }
}

// カスタム例外クラス
class DataProcessingException extends Exception {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

// SQL例外のダミー
class SQLException extends Exception {
    public SQLException(String message) {
        super(message);
    }
}
