//スキャナーをインポート
import java.util.Scanner;

public class SimpleCalculator {
    //入力を受け取るためのScannerオブジェクトを作成
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== シンプル電卓 ===");
        System.out.println("使用可能な演算: +, -, *, /, % (剰余), ^ (べき乗)");
        System.out.println("終了するには 'quit' を入力してください\n");
        //ユーザーが終了と入力するまで繰り返すループ
        while (true) {
            System.out.print("計算式を入力 (例: 10 + 5): ");
            //入力した文字を受け取る
            String input = scanner.nextLine();
            //入力が"quit"だった場合
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("電卓を終了します。");
                break;
            }
            //例外処理
            try {
                calculate(input);
                //入力が正しくない場合エラーメッセージを表示
            } catch (Exception e) {
                System.out.println("エラー: " + e.getMessage());
                System.out.println("正しい形式で入力してください。\n");
            }
        }
        //スキャナーを閉じる
        scanner.close();
    }
    //計算を実行するメソッド
    private static void calculate(String input) {
        // 空白で入力を分割
        String[] parts = input.trim().split("\\s+");
        //入力が不正の場合
        if (parts.length != 3) {
            throw new IllegalArgumentException("入力形式が正しくありません");
        }
        //1つ目の数字をdouble型に変換
        double num1 = Double.parseDouble(parts[0]);
        //演算子を取り出す
        String operator = parts[1];
        //２つ目の数字をdouble型に変換
        double num2 = Double.parseDouble(parts[2]);
        // 計算結果を取得（別メソッドで演算を実行）
        double result = performOperation(num1, operator, num2);

        // 結果の表示
        // 結果が整数と同じなら（小数なし）
        if (result == (int) result) {
            // 整数として表示
            System.out.printf("%s = %d%n%n", input, (int) result);
        } else {
            // 小数点第2位まで表示
            System.out.printf("%s = %.2f%n%n", input, result);
        }

        // 計算履歴を保存（オプション）
        saveToHistory(input, result);
    }
    // 実際の計算処理を行うメソッド
    private static double performOperation(double num1, String operator, double num2) {
        // switch式で演算子ごとに処理を分ける
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    // 0で割ろうとしたらエラー
                    throw new ArithmeticException("ゼロで除算はできません");
                }
                // 割り算の結果を返す
                yield num1 / num2;
            }
            case "%" -> {
                // 0で剰余を取るのもエラー
                if (num2 == 0) {
                    throw new ArithmeticException("ゼロで除算はできません");
                }
                // 剰余（あまり）を返す
                yield num1 % num2;
            }
            // べき乗（例: 2 ^ 3 = 8）
            case "^" -> Math.pow(num1, num2);
            // 想定外の演算子が入力された場合のエラー
            default -> throw new IllegalArgumentException("不明な演算子: " + operator);
        };
    }

    private static void saveToHistory(String expression, double result) {
        // 実際のアプリケーションでは、ファイルやデータベースに保存
        System.out.println("[履歴に保存しました]");
    }
}
