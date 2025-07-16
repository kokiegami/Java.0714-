package generics;

import java.util.*;

/**
 * ジェネリクスの基礎
 * 型安全性とコードの再利用性を高める仕組み
 */
public class GenericsBasics {
    public static void main(String[] args) {
        System.out.println("=== ジェネリクスの基礎 ===\n");

        // ジェネリクスを使わない場合の問題
        demonstrateWithoutGenerics();

        // ジェネリクスを使った型安全なコード
        demonstrateWithGenerics();

        // ジェネリッククラスの作成
        demonstrateGenericClass();

        // ジェネリックメソッド
        demonstrateGenericMethod();

        // 境界付きジェネリクス
        demonstrateBoundedGenerics();
    }

    /**
     * ジェネリクスを使わない場合の問題点
     */
    private static void demonstrateWithoutGenerics() {
        System.out.println("=== ジェネリクスなしの問題点 ===");

        // Java 5以前のスタイル（raw type）
        List oldList = new ArrayList();
        oldList.add("文字列");
        oldList.add(123);  // 異なる型も追加可能
        oldList.add(true);

        // 取り出し時にキャストが必要
        try {
            String str = (String) oldList.get(0);  // OK
            String num = (String) oldList.get(1);  // ClassCastException!
        } catch (ClassCastException e) {
            System.out.println("エラー: 型キャストに失敗しました");
            System.out.println("原因: " + e.getMessage());
        }

        System.out.println("問題: コンパイル時に型エラーを検出できない\n");
    }

    /**
     * ジェネリクスを使った型安全なコード
     */
    private static void demonstrateWithGenerics() {
        System.out.println("=== ジェネリクスを使った型安全性 ===");

        // 型パラメータを指定
        List<String> stringList = new ArrayList<>();
        stringList.add("Java");
        stringList.add("Python");
        // stringList.add(123);  // コンパイルエラー！

        // キャスト不要
        String language = stringList.get(0);
        System.out.println("最初の言語: " + language);

        // 異なる型のリスト
        List<Integer> numberList = new ArrayList<>();
        numberList.add(10);
        numberList.add(20);
        numberList.add(30);

        // 型安全な操作
        int sum = 0;
        for (Integer num : numberList) {
            sum += num;  // 自動アンボクシング
        }
        System.out.println("合計: " + sum);

        // ダイヤモンド演算子（Java 7以降）
        Map<String, List<Integer>> scoreMap = new HashMap<>();  // <>で型推論
        scoreMap.put("数学", Arrays.asList(80, 85, 90));
        scoreMap.put("英語", Arrays.asList(75, 80, 85));
        System.out.println("\n成績マップ: " + scoreMap);

        System.out.println();
    }

    /**
     * ジェネリッククラスの作成と使用
     */
    private static void demonstrateGenericClass() {
        System.out.println("=== ジェネリッククラスの作成 ===");

        // 文字列を格納するBox
        Box<String> stringBox = new Box<>();
        stringBox.setContent("重要な書類");
        System.out.println("文字列Box: " + stringBox.getContent());

        // 整数を格納するBox
        Box<Integer> intBox = new Box<>();
        intBox.setContent(42);
        System.out.println("整数Box: " + intBox.getContent());

        // ペアクラスの使用
        Pair<String, Integer> nameAge = new Pair<>("山田太郎", 25);
        System.out.println("\nペア: " + nameAge);
        System.out.println("名前: " + nameAge.getFirst());
        System.out.println("年齢: " + nameAge.getSecond());

        // 複数の型パラメータ
        Pair<String, List<String>> personHobbies =
                new Pair<>("鈴木花子", Arrays.asList("読書", "映画", "料理"));
        System.out.println("\n趣味リスト: " + personHobbies);

        // ジェネリックスタック
        GenericStack<String> stack = new GenericStack<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        System.out.println("\nスタックから取り出し:");
        while (!stack.isEmpty()) {
            System.out.println("- " + stack.pop());
        }

        System.out.println();
    }

    /**
     * ジェネリックメソッドの使用
     */
    private static void demonstrateGenericMethod() {
        System.out.println("=== ジェネリックメソッド ===");

        // 配列から配列リストへの変換
        String[] stringArray = {"A", "B", "C"};
        List<String> stringList = arrayToList(stringArray);
        System.out.println("文字列リスト: " + stringList);

        Integer[] intArray = {1, 2, 3, 4, 5};
        List<Integer> intList = arrayToList(intArray);
        System.out.println("整数リスト: " + intList);

        // 2つのリストの結合
        List<String> list1 = Arrays.asList("Hello", "World");
        List<String> list2 = Arrays.asList("Java", "Programming");
        List<String> combined = combineLists(list1, list2);
        System.out.println("\n結合されたリスト: " + combined);

        // 最大値の検索
        List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 1, 5);
        Integer maxNum = findMax(numbers);
        System.out.println("\n最大値: " + maxNum);

        List<String> words = Arrays.asList("apple", "zebra", "banana", "mango");
        String maxWord = findMax(words);
        System.out.println("辞書順最大: " + maxWord);

        // 型の入れ替え
        Pair<String, Integer> original = new Pair<>("Age", 30);
        Pair<Integer, String> swapped = swap(original);
        System.out.println("\n元のペア: " + original);
        System.out.println("入れ替え後: " + swapped);

        System.out.println();
    }

    /**
     * 境界付きジェネリクス
     */
    private static void demonstrateBoundedGenerics() {
        System.out.println("=== 境界付きジェネリクス ===");

        // Number型とそのサブクラスのみ
        NumberBox<Integer> intBox = new NumberBox<>(100);
        NumberBox<Double> doubleBox = new NumberBox<>(3.14);
        // NumberBox<String> stringBox = new NumberBox<>("text");  // コンパイルエラー

        System.out.println("整数Box: " + intBox.getValue());
        System.out.println("小数Box: " + doubleBox.getValue());
        System.out.println("整数の2倍: " + intBox.doubleValue());
        System.out.println("小数の2倍: " + doubleBox.doubleValue());

        // Comparableを実装したクラスのみ
        List<Student> students = Arrays.asList(
                new Student("山田", 85),
                new Student("鈴木", 92),
                new Student("佐藤", 78)
        );

        Student topStudent = findMax(students);
        System.out.println("\n最高得点の学生: " + topStudent);

        // ワイルドカードの使用
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);

        System.out.println("\n整数リストの合計: " + sumNumbers(intList));
        System.out.println("小数リストの合計: " + sumNumbers(doubleList));

        System.out.println();
    }

    // ===== ジェネリッククラスの定義 =====

    /**
     * 単純なジェネリックコンテナ
     */
    static class Box<T> {
        private T content;

        public void setContent(T content) {
            this.content = content;
        }

        public T getContent() {
            return content;
        }
    }

    /**
     * 2つの値を保持するペアクラス
     */
    static class Pair<F, S> {
        private F first;
        private S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public F getFirst() { return first; }
        public S getSecond() { return second; }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    /**
     * ジェネリックスタック
     */
    static class GenericStack<E> {
        private List<E> elements = new ArrayList<>();

        public void push(E element) {
            elements.add(element);
        }

        public E pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return elements.remove(elements.size() - 1);
        }

        public boolean isEmpty() {
            return elements.isEmpty();
        }
    }

    /**
     * 境界付きジェネリッククラス
     */
    static class NumberBox<T extends Number> {
        private T value;

        public NumberBox(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public double doubleValue() {
            return value.doubleValue() * 2;
        }
    }

    /**
     * Comparableを実装したStudentクラス
     */
    static class Student implements Comparable<Student> {
        private String name;
        private int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(Student other) {
            return Integer.compare(this.score, other.score);
        }

        @Override
        public String toString() {
            return name + " (" + score + "点)";
        }
    }

    // ===== ジェネリックメソッドの定義 =====

    /**
     * 配列をリストに変換
     */
    private static <T> List<T> arrayToList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * 2つのリストを結合
     */
    private static <T> List<T> combineLists(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }

    /**
     * Comparableな要素の最大値を返す
     */
    private static <T extends Comparable<T>> T findMax(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }

    /**
     * ペアの要素を入れ替える
     */
    private static <F, S> Pair<S, F> swap(Pair<F, S> pair) {
        return new Pair<>(pair.getSecond(), pair.getFirst());
    }

    /**
     * ワイルドカードを使った数値リストの合計
     */
    private static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }
}
