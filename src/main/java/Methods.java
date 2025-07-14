public class Methods {
    public static void main(String[] args) {
        System.out.println("=== メソッドの基礎 ===\n");

        // メソッドの呼び出し
        greet();
        greetWithName("太郎");

        // 戻り値のあるメソッド
        int sum = add(5, 3);
        System.out.println("5 + 3 = " + sum);

        double area = calculateCircleArea(5.0);
        System.out.println("半径5の円の面積: " + area);

        // 複数の引数
        String fullName = createFullName("山田", "太郎");
        System.out.println("フルネーム: " + fullName);

        // 配列を引数に取るメソッド
        int[] numbers = {1, 2, 3, 4, 5};
        int total = sumArray(numbers);
        System.out.println("配列の合計: " + total);

        // 可変長引数
        int sum1 = sumNumbers(1, 2, 3);
        int sum2 = sumNumbers(1, 2, 3, 4, 5);
        System.out.println("可変長引数の合計1: " + sum1);
        System.out.println("可変長引数の合計2: " + sum2);

        // メソッドのオーバーロード
        System.out.println("\nメソッドのオーバーロード:");
        print("Hello");
        print(123);
        print(3.14);
    }

    // 引数なし、戻り値なしのメソッド
    public static void greet() {
        System.out.println("こんにちは！");
    }

    // 引数あり、戻り値なしのメソッド
    public static void greetWithName(String name) {
        System.out.println("こんにちは、" + name + "さん！");
    }

    // 引数あり、戻り値ありのメソッド
    public static int add(int a, int b) {
        return a + b;
    }

    // double型を返すメソッド
    public static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;
    }

    // 文字列を返すメソッド
    public static String createFullName(String lastName, String firstName) {
        return lastName + " " + firstName;
    }

    // 配列を引数に取るメソッド
    public static int sumArray(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    // 可変長引数
    public static int sumNumbers(int... numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    // メソッドのオーバーロード（同じ名前、異なる引数）
    public static void print(String str) {
        System.out.println("文字列: " + str);
    }

    public static void print(int num) {
        System.out.println("整数: " + num);
    }

    public static void print(double num) {
        System.out.println("小数: " + num);
    }
}