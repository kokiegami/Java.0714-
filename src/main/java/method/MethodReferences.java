package method;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * メソッド参照とコンストラクタ参照の基礎を学ぶためのサンプルコードです。
 * これらはラムダ式をさらに簡潔に記述するための機能です。
 */
public class MethodReferences {

    // 静的メソッドの例
    public static void printMessage(String message) {
        System.out.println("静的メソッド: " + message);
    }

    // インスタンスメソッドの例
    public void printUpperCase(String text) {
        System.out.println("インスタンスメソッド: " + text.toUpperCase());
    }

    // 特定の型の任意のオブジェクトのインスタンスメソッドの例
    // String::length など、引数として渡されるオブジェクトのメソッドを呼び出す場合に使われる
    // このサンプルでは直接的な例示が難しいため、Stream.map()などで示します。

    public static void main(String[] args) {
        System.out.println("--- メソッド参照とコンストラクタ参照の基礎 ---");

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // --- 1. 静的メソッド参照 (ClassName::staticMethod) ---
        // ラムダ式: (String s) -> System.out.println(s)
        // メソッド参照: System.out::println
        System.out.println("\n--- 1. 静的メソッド参照 ---");
        System.out.println("  1-1. System.out::println を使用");
        names.forEach(System.out::println); // 各要素を標準出力に表示

        System.out.println("  1-2. 自作の静的メソッド printMessage を使用");
        names.forEach(MethodReferences::printMessage); // クラス名::静的メソッド名


        // --- 2. 特定のオブジェクトのインスタンスメソッド参照 (object::instanceMethod) ---
        // ラムダ式: (String text) -> new MethodReferences().printUpperCase(text)
        // メソッド参照: myProcessor::printUpperCase
        System.out.println("\n--- 2. 特定のオブジェクトのインスタンスメソッド参照 ---");
        MethodReferences processor = new MethodReferences(); // メソッドを持つオブジェクトをインスタンス化
        names.forEach(processor::printUpperCase); // オブジェクト::インスタンスメソッド名


        // --- 3. 特定の型の任意のオブジェクトのインスタンスメソッド参照 (ClassName::instanceMethod) ---
        // この形式は、ラムダ式の第一引数が、呼び出すインスタンスメソッドの対象となる場合に利用
        // 例: (String s) -> s.length() は String::length と書ける
        System.out.println("\n--- 3. 特定の型の任意のオブジェクトのインスタンスメソッド参照 ---");

        // ラムダ式: name -> name.length()
        // メソッド参照: String::length
        List<Integer> nameLengths = names.stream()
                .map(String::length) // Stringクラスの任意のインスタンスのlength()メソッドを呼び出す
                .collect(Collectors.toList());
        System.out.println("各名前の長さ: " + nameLengths);

        // ラムダ式: str -> str.isEmpty()
        // メソッド参照: String::isEmpty
        boolean anyEmpty = names.stream()
                .anyMatch(String::isEmpty); // Listに空文字列が含まれるか
        System.out.println("空の名前はありますか？: " + anyEmpty);


        // --- 4. コンストラクタ参照 (ClassName::new) ---
        // ラムダ式: () -> new String()
        // コンストラクタ参照: String::new
        System.out.println("\n--- 4. コンストラクタ参照 ---");

        // (1) 引数なしのコンストラクタ参照
        Supplier<String> stringSupplier = String::new; // Stringのデフォルトコンストラクタ
        System.out.println("生成された空文字列: '" + stringSupplier.get() + "'");

        // (2) 引数を取るコンストラクタ参照
        // 例: Streamの要素をListに集める (ArrayListのコンストラクタ)
        // ラムダ式: (Collection<String> c) -> new ArrayList<>(c)
        // コンストラクタ参照: ArrayList::new
        List<String> copiedNames = names.stream()
                .collect(Collectors.toCollection(java.util.ArrayList::new)); // ArrayListのコンストラクタ参照
        System.out.println("コピーされた名前リスト (ArrayList::new): " + copiedNames);

        // 例: Streamの要素をSetに集める (HashSetのコンストラクタ)
        Set<String> uniqueNames = names.stream()
                .collect(Collectors.toCollection(java.util.HashSet::new)); // HashSetのコンストラクタ参照
        System.out.println("ユニークな名前セット (HashSet::new): " + uniqueNames);

        // (3) 配列コンストラクタ参照
        // ラムダ式: (int size) -> new String[size]
        // コンストラクタ参照: String[]::new
        Function<Integer, String[]> stringArrayCreator = String[]::new;
        String[] newArray = stringArrayCreator.apply(5); // 長さ5のString配列を作成
        System.out.println("生成された配列の長さ: " + newArray.length);


        System.out.println("\n--- メソッド参照とコンストラクタ参照の学習完了 ---");
    }
}