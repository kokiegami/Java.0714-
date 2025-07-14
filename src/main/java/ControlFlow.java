import java.util.Scanner;

public class ControlFlow {
    public static void main(String[] args) {
        System.out.println("=== 制御構文 ===\n");

        // if-else文
        System.out.println("1. if-else文:");
        int score = 85;

        if (score >= 90) {
            System.out.println("成績: A");
        } else if (score >= 80) {
            System.out.println("成績: B");
        } else if (score >= 70) {
            System.out.println("成績: C");
        } else if (score >= 60) {
            System.out.println("成績: D");
        } else {
            System.out.println("成績: F");
        }

        // switch文（従来の書き方）
        System.out.println("\n2. switch文（従来）:");
        int dayOfWeek = 3;
        String dayName;

        switch (dayOfWeek) {
            case 1:
                dayName = "月曜日";
                break;
            case 2:
                dayName = "火曜日";
                break;
            case 3:
                dayName = "水曜日";
                break;
            case 4:
                dayName = "木曜日";
                break;
            case 5:
                dayName = "金曜日";
                break;
            case 6:
                dayName = "土曜日";
                break;
            case 7:
                dayName = "日曜日";
                break;
            default:
                dayName = "不明";
        }
        System.out.println("今日は" + dayName + "です");

        // switch式（Java 14以降）
        System.out.println("\n3. switch式（新しい書き方）:");
        String dayType = switch (dayOfWeek) {
            case 1, 2, 3, 4, 5 -> "平日";
            case 6, 7 -> "週末";
            default -> "不明";
        };
        System.out.println("今日は" + dayType + "です");

        // for文
        System.out.println("\n4. for文:");
        System.out.println("通常のfor文:");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // 拡張for文（for-each）
        System.out.println("\n拡張for文:");
        int[] numbers = {10, 20, 30, 40, 50};
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        // while文
        System.out.println("\n5. while文:");
        int count = 0;
        while (count < 5) {
            System.out.print(count + " ");
            count++;
        }
        System.out.println();

        // do-while文
        System.out.println("\n6. do-while文:");
        int num = 0;
        do {
            System.out.print(num + " ");
            num++;
        } while (num < 5);
        System.out.println();

        // break と continue
        System.out.println("\n7. break と continue:");
        System.out.println("break の例:");
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                break;  // ループを抜ける
            }
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("\ncontinue の例:");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                continue;  // 偶数をスキップ
            }
            System.out.print(i + " ");
        }
        System.out.println();

        // ネストしたループ
        System.out.println("\n8. ネストしたループ（九九の一部）:");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.printf("%d×%d=%2d  ", i, j, i * j);
            }
            System.out.println();
        }
    }
}
