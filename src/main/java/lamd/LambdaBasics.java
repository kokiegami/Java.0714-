package lamd;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ラムダ式と関数型インターフェースの基礎を学ぶためのサンプルコードです。
 * Java 8で導入されたこれらの機能は、より簡潔で表現豊かなコード記述を可能にします。
 */
public class LambdaBasics {

    public static void main(String[] args) {
        System.out.println("--- ラムダ式と関数型インターフェースの基礎 ---");

        // --- 1. 匿名クラスとの比較によるラムダ式の簡潔性 ---
        System.out.println("\n--- 1. 匿名クラス vs. ラムダ式 ---");

        // (1) 匿名クラスを使ったRunnableの実装
        // 冗長な記述が多く、何をするか（runメソッドの中身）が分かりにくい
        Runnable oldWay = new Runnable() {
            @Override
            public void run() {
                System.out.println("旧来の匿名クラスで実行中...");
            }
        };
        new Thread(oldWay).start();

        // (2) ラムダ式を使ったRunnableの実装
        // 非常に簡潔で、やりたいこと（println）が明確
        Runnable newWay = () -> System.out.println("ラムダ式で実行中...");
        new Thread(newWay).start();

        // 少し待機して、スレッドの実行が確認できるようにする
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // --- 2. 主要な標準関数型インターフェースの利用 ---
        System.out.println("\n--- 2. 標準関数型インターフェース ---");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // (1) Predicate<T>: 引数Tを受け取り、booleanを返す (条件判定)
        // 例: 文字列が"A"で始まるか判定
        Predicate<String> startsWithA = (name) -> name.startsWith("A");
        System.out.println("\n-- Predicate (条件判定): 名前が 'A' で始まる --");
        for (String name : names) {
            if (startsWithA.test(name)) { // Predicateの抽象メソッドは 'test'
                System.out.println(name + "は'A'で始まります。");
            }
        }
        // Stream APIと組み合わせるとより強力
        names.stream()
                .filter(startsWithA) // Predicateをfilterに直接渡せる
                .forEach(name -> System.out.println("Streamでフィルタリング: " + name));


        // (2) Consumer<T>: 引数Tを受け取り、何も返さない (消費、副作用)
        // 例: 文字列を表示する
        Consumer<String> printName = (name) -> System.out.println("Consumer (表示): " + name);
        System.out.println("\n-- Consumer (消費): 各名前を表示 --");
        names.forEach(printName); // ListのforEachメソッドにConsumerを渡せる

        // (3) Function<T, R>: 引数Tを受け取り、Rを返す (変換)
        // 例: 文字列の長さを返す
        Function<String, Integer> getNameLength = (name) -> name.length();
        System.out.println("\n-- Function (変換): 各名前の長さを取得 --");
        for (String name : names) {
            Integer length = getNameLength.apply(name); // Functionの抽象メソッドは 'apply'
            System.out.println(name + "の長さは " + length + " です。");
        }
        // Stream APIと組み合わせ
        names.stream()
                .map(getNameLength) // Functionをmapに直接渡せる
                .forEach(length -> System.out.println("Streamで長さに変換: " + length));


        // (4) Supplier<T>: 引数を取らず、Tを返す (供給、生成)
        // 例: ランダムな数値を生成する
        Supplier<Double> getRandomNumber = () -> Math.random();
        System.out.println("\n-- Supplier (生成): ランダムな数値を生成 --");
        System.out.println("生成された乱数: " + getRandomNumber.get()); // Supplierの抽象メソッドは 'get'
        System.out.println("生成された乱数: " + getRandomNumber.get());


        // --- 3. 自作の関数型インターフェース ---
        System.out.println("\n--- 3. 自作の関数型インターフェース ---");

        // @FunctionalInterface アノテーションは省略可能だが、付けることでコンパイラがチェックしてくれる
        // 抽象メソッドを一つだけ持つインターフェースは自動的に関数型インターフェースとみなされる
        // (例) MyCalculatorインターフェースを定義（ここではコード内に直接定義）
        interface MyCalculator {
            int calculate(int a, int b);
        }

        // 足し算の実装
        MyCalculator adder = (x, y) -> x + y;
        System.out.println("10 + 5 = " + adder.calculate(10, 5));

        // 掛け算の実装
        MyCalculator multiplier = (x, y) -> x * y;
        System.out.println("10 * 5 = " + multiplier.calculate(10, 5));

        System.out.println("\n--- 基礎学習完了 ---");
    }
}