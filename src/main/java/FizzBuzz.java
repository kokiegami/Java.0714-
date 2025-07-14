public class FizzBuzz {
    public static void main(String[] args) {
        System.out.println("=== FizzBuzz（通常版） ===");

        //for文の実装
        for (int i = 1; i <= 30; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.print("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.print("Fizz");
            } else if (i % 5 == 0) {
                System.out.print("Buzz");
            } else {
                //上記条件に当てはまらない場合はそのままの数字を出力
                System.out.print(i + " ");

            }
            //10行ごとに改行
            if (i % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println("\n\n=== FizzBuzz（メソッド版） ===");

        // メソッドを使った実装
        for (int i = 1; i <= 30; i++) {
            System.out.print(fizzBuzz(i) + " ");
            if (i % 10 == 0) {
                System.out.println();
            }
        }

        System.out.println("\n\n=== FizzBuzz（Switch式版）Java 14+ ===");

        // Switch式を使った実装
        for (int i = 1; i <= 30; i++) {
            System.out.print(fizzBuzzSwitch(i) + " ");
            if (i % 10 == 0) {
                System.out.println();
            }
        }
    }

    // メソッドとして実装
    public static String fizzBuzz(int n) {
        if (n % 15 == 0) {
            return "FizzBuzz";
        } else if (n % 3 == 0) {
            return "Fizz";
        } else if (n % 5 == 0) {
            return "Buzz";
        } else {
            return String.valueOf(n);
        }
    }

    // Switch式を使った実装
    public static String fizzBuzzSwitch(int n) {
        return switch (n % 15) {
            case 0 -> "FizzBuzz";
            case 3, 6, 9, 12 -> "Fizz";
            case 5, 10 -> "Buzz";
            default -> String.valueOf(n);
        };
    }
}







