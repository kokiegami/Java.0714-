public class Operators {
    public static void main(String[] args) {
        System.out.println("=== Java の演算子 ===\n");

        // 算術演算子
        int a = 10, b = 3;
        System.out.println("算術演算子:");
        // 加算
        System.out.println("a + b = " + (a + b));
        // 減算
        System.out.println("a - b = " + (a - b));
        // 乗算
        System.out.println("a * b = " + (a * b));
        // 除算（整数同士は整数除算）
        System.out.println("a / b = " + (a / b));
        // 剰余
        System.out.println("a % b = " + (a % b));

        // 浮動小数点の除算
        double x = 10.0, y = 3.0;
        System.out.println("x / y = " + (x / y));    // 浮動小数点除算

        // インクリメント・デクリメント
        System.out.println("\nインクリメント・デクリメント:");
        int count = 5;
        System.out.println("count++ = " + count++);  // 5を表示してから6に
        System.out.println("count = " + count);      // 6
        System.out.println("++count = " + ++count);  // 7にしてから表示

        // 比較演算子
        System.out.println("\n比較演算子:");
        System.out.println("a == b: " + (a == b));   // 等しい
        System.out.println("a != b: " + (a != b));   // 等しくない
        System.out.println("a > b: " + (a > b));     // より大きい
        System.out.println("a < b: " + (a < b));     // より小さい
        System.out.println("a >= b: " + (a >= b));   // 以上
        System.out.println("a <= b: " + (a <= b));   // 以下

        // 論理演算子
        System.out.println("\n論理演算子:");
        boolean isTrue = true;
        boolean isFalse = false;
        System.out.println("true && false: " + (isTrue && isFalse));  // AND
        System.out.println("true || false: " + (isTrue || isFalse));  // OR
        System.out.println("!true: " + (!isTrue));                    // NOT

        // ビット演算子
        System.out.println("\nビット演算子:");
        int bit1 = 5;  // 0101
        int bit2 = 3;  // 0011
        System.out.println("5 & 3 = " + (bit1 & bit2));  // AND: 0001 = 1
        System.out.println("5 | 3 = " + (bit1 | bit2));  // OR:  0111 = 7
        System.out.println("5 ^ 3 = " + (bit1 ^ bit2));  // XOR: 0110 = 6
        System.out.println("~5 = " + (~bit1));           // NOT

        // 三項演算子
        System.out.println("\n三項演算子:");
        int age = 20;
        String status = (age >= 18) ? "成人" : "未成年";
        System.out.println("年齢: " + age + " → " + status);

        // 代入演算子
        System.out.println("\n複合代入演算子:");
        int num = 10;
        num += 5;   // num = num + 5
        System.out.println("num += 5: " + num);
        num -= 3;   // num = num - 3
        System.out.println("num -= 3: " + num);
        num *= 2;   // num = num * 2
        System.out.println("num *= 2: " + num);
        num /= 4;   // num = num / 4
        System.out.println("num /= 4: " + num);
    }
}
