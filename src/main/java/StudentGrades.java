//スキャナーインポート
import java.util.Scanner;


public class StudentGrades {
    public static void main(String[] args) {
        // キーボードからの入力を受け取るためのScannerを作成
        Scanner scanner = new Scanner(System.in);

        // タイトルを表示
        System.out.println("=== 学生成績管理システム ===");

        // 学生数の入力を求める
        System.out.print("学生数を入力してください: ");
        int studentCount = scanner.nextInt();

        // nextInt()の後の改行
        scanner.nextLine();

        // 各学生の名前、教科ごとの点数を保存する配列を用意
        String[] names = new String[studentCount];
        int[] mathScores = new int[studentCount];
        int[] englishScores = new int[studentCount];
        int[] scienceScores = new int[studentCount];

        // 学生ごとのデータを繰り返し入力
        for (int i = 0; i < studentCount; i++) {
            // 学生の番号を表示（1からスタート）
            System.out.println("\n学生 " + (i + 1) + " のデータ入力:");

            // 名前を入力
            System.out.print("名前: ");
            names[i] = scanner.nextLine();

            // 数学の点数を入力
            System.out.print("数学の点数: ");
            mathScores[i] = scanner.nextInt();

            // 英語の点数を入力
            System.out.print("英語の点数: ");
            englishScores[i] = scanner.nextInt();

            // 理科の点数を入力
            System.out.print("理科の点数: ");
            scienceScores[i] = scanner.nextInt();

            // 改行を読み飛ばして次の入力へ
            scanner.nextLine();
        }

        // 成績表を表示するメソッドを呼び出し
        displayGradeReport(names, mathScores, englishScores, scienceScores);

        // 各教科の統計情報を表示するメソッドを呼び出し
        displayStatistics(mathScores, englishScores, scienceScores);

        // Scannerを閉じる（リソース解放）
        scanner.close();
    }

    // 成績表を出力するメソッド
    private static void displayGradeReport(String[] names, int[] math, int[] english, int[] science) {
        // タイトル行を表示
        System.out.println("\n=== 成績表 ===");
        System.out.println("名前\t\t数学\t英語\t理科\t合計\t平均\t評価");
        System.out.println("-".repeat(60));

        // 各学生の成績を表示
        for (int i = 0; i < names.length; i++) {
            // 合計点を計算
            int total = math[i] + english[i] + science[i];

            // 平均点を計算
            double average = total / 3.0;

            // 評価（A〜F）を計算
            String grade = getGrade(average);

            // 成績情報を整形して表示
            System.out.printf("%-10s\t%d\t%d\t%d\t%d\t%.1f\t%s%n",
                    names[i], math[i], english[i], science[i], total, average, grade);
        }
    }

    // 各教科の統計情報を表示するメソッド
    private static void displayStatistics(int[] math, int[] english, int[] science) {
        // タイトルを表示
        System.out.println("\n=== 統計情報 ===");

        // 各教科の平均点を計算
        double mathAvg = calculateAverage(math);
        double englishAvg = calculateAverage(english);
        double scienceAvg = calculateAverage(science);

        // 平均点を表示
        System.out.printf("数学の平均点: %.1f%n", mathAvg);
        System.out.printf("英語の平均点: %.1f%n", englishAvg);
        System.out.printf("理科の平均点: %.1f%n", scienceAvg);

        // 各教科の最高点を表示
        System.out.println("\n各科目の最高点:");
        System.out.printf("数学: %d, 英語: %d, 理科: %d%n",
                findMax(math), findMax(english), findMax(science));

        // 各教科の最低点を表示
        System.out.println("\n各科目の最低点:");
        System.out.printf("数学: %d, 英語: %d, 理科: %d%n",
                findMin(math), findMin(english), findMin(science));
    }

    // 平均点に応じて成績評価を返すメソッド
    private static String getGrade(double average) {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }

    // 平均点を計算するメソッド
    private static double calculateAverage(int[] scores) {
        int sum = 0;

        // 配列内のすべての点数を合計
        for (int score : scores) {
            sum += score;
        }

        // 合計を人数で割って平均を返す
        return (double) sum / scores.length;
    }

    // 配列の中の最大値（最高点）を返すメソッド
    private static int findMax(int[] scores) {
        int max = scores[0];

        // 全体から最大の値を見つける
        for (int score : scores) {
            if (score > max) {
                max = score;
            }
        }

        return max;
    }

    // 配列の中の最小値（最低点）を返すメソッド
    private static int findMin(int[] scores) {
        int min = scores[0];

        // 全体から最小の値を見つける
        for (int score : scores) {
            if (score < min) {
                min = score;
            }
        }

        return min;
    }
}
