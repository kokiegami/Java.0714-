public class CompareWithPython {
    public static void main(String[] args) {
        System.out.println("=== Python vs Java 比較 ===\n");

        // 変数宣言の違い
        System.out.println("1. 変数宣言:");
        System.out.println("Python: name = \"太郎\"");
        System.out.println("Java:   String name = \"太郎\";");

        String name = "太郎";
        System.out.println("結果: " + name);

        // リストの違い
        System.out.println("\n2. リスト/配列:");
        System.out.println("Python: numbers = [1, 2, 3, 4, 5]");
        System.out.println("Java:   int[] numbers = {1, 2, 3, 4, 5};");

        int[] numbers = {1, 2, 3, 4, 5};
        System.out.print("結果: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        // None vs null
        System.out.println("\n3. None vs null:");
        System.out.println("Python: value = None");
        System.out.println("Java:   String value = null;");

        String value = null;
        System.out.println("結果: " + value);

        // 文字列フォーマット
        System.out.println("\n4. 文字列フォーマット:");
        System.out.println("Python: f\"Hello, {name}!\"");
        System.out.println("Java:   String.format(\"Hello, %s!\", name)");

        String formatted1 = String.format("Hello, %s!", name);
        String formatted2 = "Hello, " + name + "!";
        System.out.println("結果1: " + formatted1);
        System.out.println("結果2: " + formatted2);

        // インデックスアクセス
        System.out.println("\n5. インデックスアクセス:");
        System.out.println("Python: first = numbers[0]");
        System.out.println("Java:   int first = numbers[0];");

        int first = numbers[0];
        System.out.println("結果: " + first);
    }
}