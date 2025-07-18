package optional;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;  

/**
 * Optional型とnull安全なプログラミングの基礎を学ぶためのサンプルコードです。
 * Optional型は、nullの可能性がある値をラップし、NullPointerException (NPE) を防ぎながら、
 * コードの意図を明確にするJava 8で導入された機能です。
 */
public class OptionalUsage {

    // 例1: nullを返す可能性があるメソッド (Optionalを使わない場合)
    public static String findItemLegacy(String itemName) {
        if ("apple".equals(itemName)) {
            return "Delicious Apple";
        }
        return null; // アイテムが見つからない場合、nullを返す
    }

    // 例2: Optionalを返すメソッド (推奨されるパターン)
    public static Optional<String> findItemOptional(String itemName) {
        if ("banana".equals(itemName)) {
            return Optional.of("Sweet Banana"); // 値が存在する場合、Optional.of()でラップ
        }
        return Optional.empty(); // 値が存在しない場合、Optional.empty()を返す
    }

    public static void main(String[] args) {
        System.out.println("--- Optional型とnull安全性 ---");

        // --- 1. nullを扱う従来の方法の問題点 ---
        System.out.println("\n--- 1. nullを扱う従来の方法 ---");
        String item1 = findItemLegacy("apple");
        String item2 = findItemLegacy("orange");

        // nullチェックを忘れるとNullPointerExceptionが発生する危険性がある
        System.out.println("Item 1 (apple): " + item1); // nullではないのでOK

        // if (item2 != null) { // nullチェックがないと次の行でNPE
        //     System.out.println("Item 2 (orange): " + item2.length()); // item2がnullなのでNPE発生！
        // } else {
        //     System.out.println("Item 2 (orange): 見つかりませんでした。");
        // }
        System.out.println("Item 2 (orange): " + item2); // nullと表示される

        // --- 2. Optional型の基本と生成 ---
        System.out.println("\n--- 2. Optional型の基本と生成 ---");

        // Optional.of(value): nullでない値でOptionalを生成 (valueがnullだとNPE)
        Optional<String> optApple = Optional.of("Red Apple");
        System.out.println("Optional.of(\"Red Apple\"): " + optApple);

        // Optional.empty(): 空のOptionalを生成
        Optional<String> optEmpty = Optional.empty();
        System.out.println("Optional.empty(): " + optEmpty);

        // Optional.ofNullable(value): valueがnullならOptional.empty()、非nullならOptional.of()を返す (安全な生成方法)
        String nullableValue1 = "Grape";
        String nullableValue2 = null;
        Optional<String> optGrape = Optional.ofNullable(nullableValue1);
        Optional<String> optNull = Optional.ofNullable(nullableValue2);
        System.out.println("Optional.ofNullable(\"Grape\"): " + optGrape);
        System.out.println("Optional.ofNullable(null): " + optNull);


        // --- 3. Optional値の取得と処理 ---
        System.out.println("\n--- 3. Optional値の取得と処理 ---");
        Optional<String> banana = findItemOptional("banana");
        Optional<String> melon = findItemOptional("melon");

        // (1) isPresent() と get(): 値の存在を確認し、存在すれば取得 (非推奨: get()はNPEのリスクあり)
        System.out.println("\n  -- isPresent() と get() (get()は注意して使用) --");
        if (banana.isPresent()) {
            System.out.println("バナナが見つかりました: " + banana.get()); // get()を使う前にisPresent()で必ず確認する
        }
        if (!melon.isPresent()) {
            System.out.println("メロンは見つかりませんでした。");
        }
        // melon.get(); // ここで NoSuchElementException が発生する！ (コメントアウトしてNPE回避)

        // (2) ifPresent(): 値が存在する場合のみ処理を実行 (Consumerを使用)
        System.out.println("\n  -- ifPresent(): 値が存在する場合のみ処理 --");
        banana.ifPresent(val -> System.out.println("ifPresent: 見つかったバナナ: " + val)); // ラムダ式 (Consumer)
        melon.ifPresent(val -> System.out.println("ifPresent: 見つかったメロン: " + val)); // 何も表示されない

        // (3) orElse(): 値が存在しない場合のデフォルト値を指定
        System.out.println("\n  -- orElse(): 値がない場合のデフォルト値 --");
        String result1 = banana.orElse("デフォルトの果物");
        String result2 = melon.orElse("デフォルトの果物");
        System.out.println("orElse (バナナ): " + result1);
        System.out.println("orElse (メロン): " + result2);

        // (4) orElseGet(): 値が存在しない場合にSupplierからデフォルト値を生成 (遅延評価)
        System.out.println("\n  -- orElseGet(): 値がない場合のデフォルト値生成 (Supplier) --");
        // Supplierが実際に呼ばれるのはOptionalが空の場合のみ
        String result3 = banana.orElseGet(() -> {
            System.out.println("  orElseGet: バナナは存在するのでSupplierは呼ばれません。");
            return "生成されたデフォルト値";
        });
        String result4 = melon.orElseGet(() -> {
            System.out.println("  orElseGet: メロンは存在しないのでSupplierが呼ばれます。");
            return "生成されたデフォルト値";
        });
        System.out.println("orElseGet (バナナ): " + result3);
        System.out.println("orElseGet (メロン): " + result4);

        // (5) orElseThrow(): 値が存在しない場合に例外をスロー
        System.out.println("\n  -- orElseThrow(): 値がない場合に例外をスロー --");
        try {
            // String result5 = melon.orElseThrow(() -> new IllegalStateException("メロンが見つかりません！")); // 例外発生の例
            // System.out.println("orElseThrow (メロン): " + result5);
            System.out.println("orElseThrowの例はコメントアウトされています。（例外が発生するため）");
        } catch (IllegalStateException e) {
            System.out.println("例外をキャッチしました: " + e.getMessage());
        }

        // --- 4. Optionalを使った変換操作 (map, flatMap, filter) ---
        System.out.println("\n--- 4. Optionalを使った変換操作 ---");

        // (1) map(): Optional内の値を別の型に変換 (Functionを使用)
        System.out.println("\n  -- map(): 値の変換 --");
        Optional<Integer> bananaLength = banana.map(String::length); // StringからIntegerへ変換 (メソッド参照)
        Optional<Integer> melonLength = melon.map(String::length);
        bananaLength.ifPresent(len -> System.out.println("バナナの長さ: " + len));
        melonLength.ifPresent(len -> System.out.println("メロンの長さ: " + len)); // 何も表示されない

        // (2) flatMap(): Optional内のOptionalな値をフラット化
        // 処理の結果がさらにOptionalを返す場合に使う
        System.out.println("\n  -- flatMap(): OptionalのOptionalをフラット化 --");
        // 例: ユーザー名からOptionalなユーザーオブジェクトを取得し、さらにOptionalな住所を取得するような場合
        Optional<String> someValue = Optional.of("Hello");
        Optional<String> nestedOptional = someValue.flatMap(s -> {
            if (s.length() > 3) {
                return Optional.of("Long: " + s);
            } else {
                return Optional.empty();
            }
        });
        nestedOptional.ifPresent(s -> System.out.println("flatMap結果: " + s));

        Optional<String> shortValue = Optional.of("Hi");
        Optional<String> nestedOptional2 = shortValue.flatMap(s -> {
            if (s.length() > 3) {
                return Optional.of("Long: " + s);
            } else {
                return Optional.empty();
            }
        });
        nestedOptional2.ifPresent(s -> System.out.println("flatMap結果2: " + s)); // 何も表示されない

        // (3) filter(): Optional内の値が条件を満たすかフィルタリング
        System.out.println("\n  -- filter(): 値の条件判定 --");
        Optional<String> longBanana = banana.filter(s -> s.length() > 10);
        Optional<String> shortBanana = banana.filter(s -> s.length() < 10);
        longBanana.ifPresent(s -> System.out.println("長いバナナ: " + s)); // 何も表示されない (Sweet Bananaは長さ12なので表示されるはず)
        // ※ 補足: "Sweet Banana"の長さは12なので、longBananaは表示されるはず
        // 実際には12 > 10 は true なので "長いバナナ: Sweet Banana" が出力されます。
        // shortBanana.ifPresent(s -> System.out.println("短いバナナ: " + s)); // 何も表示されない

        // 再確認: "Sweet Banana".length() = 12
        // longBanana = banana.filter(s -> s.length() > 10); // 12 > 10 は true なのでOptional<"Sweet Banana">が返る
        longBanana.ifPresent(s -> System.out.println("filter (長さ > 10)の結果: " + s));

        // shortBanana = banana.filter(s -> s.length() < 10); // 12 < 10 は false なのでOptional.empty()が返る
        shortBanana.ifPresent(s -> System.out.println("filter (長さ < 10)の結果: " + s)); // 出力されない

        System.out.println("\n--- Optional型学習完了 ---");
    }
}