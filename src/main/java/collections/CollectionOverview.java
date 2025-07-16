package collections;

import java.util.*;

/**
 * Javaコレクションフレームワークの概要
 * PythonのリストやタプルとJavaのListの比較
 */
public class CollectionOverview {
    public static void main(String[] args) {
        System.out.println("=== コレクションフレームワーク概要 ===\n");

        // コレクションの階層構造
        System.out.println("コレクションの主要インターフェース:");
        System.out.println("Collection");
        System.out.println("├── List（順序付きコレクション）");
        System.out.println("├── Set（重複なしコレクション）");
        System.out.println("└── Queue（キュー）");
        System.out.println("Map（キー・値のペア）※Collectionを継承しない\n");

        // ArrayListの使用例
        demonstrateArrayList();

        // LinkedListの使用例
        demonstrateLinkedList();

        // ArrayList vs LinkedList パフォーマンス比較
        compareListPerformance();

        // Pythonとの比較
        pythonVsJavaList();
    }

    /**
     * ArrayListの基本操作
     */
    private static void demonstrateArrayList() {
        System.out.println("=== ArrayList の使用例 ===");

        // 作成と初期化
        List<String> fruits = new ArrayList<>();

        // 要素の追加
        fruits.add("りんご");
        fruits.add("バナナ");
        fruits.add("オレンジ");
        System.out.println("初期リスト: " + fruits);

        // インデックス指定で追加
        fruits.add(1, "ぶどう");
        System.out.println("インデックス1に追加: " + fruits);

        // 要素の取得
        String firstFruit = fruits.get(0);
        System.out.println("最初の要素: " + firstFruit);

        // 要素の更新
        fruits.set(2, "メロン");
        System.out.println("インデックス2を更新: " + fruits);

        // 要素の削除
        fruits.remove("ぶどう");
        System.out.println("ぶどうを削除: " + fruits);

        fruits.remove(0);
        System.out.println("インデックス0を削除: " + fruits);

        // サイズと空チェック
        System.out.println("リストのサイズ: " + fruits.size());
        System.out.println("リストは空？: " + fruits.isEmpty());

        // 検索
        System.out.println("メロンを含む？: " + fruits.contains("メロン"));
        System.out.println("メロンのインデックス: " + fruits.indexOf("メロン"));

        // イテレーション
        System.out.println("\n--- for-each ループ ---");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }

        // インデックス付きループ
        System.out.println("\n--- インデックス付きループ ---");
        for (int i = 0; i < fruits.size(); i++) {
            System.out.println(i + ": " + fruits.get(i));
        }

        // リストの初期化（Java 9以降）
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);  // 不変リスト
        System.out.println("\n不変リスト: " + numbers);
        // numbers.add(6);  // UnsupportedOperationException

        // 可変リストに変換
        List<Integer> mutableNumbers = new ArrayList<>(numbers);
        mutableNumbers.add(6);
        System.out.println("可変リスト: " + mutableNumbers);

        System.out.println();
    }

    /**
     * LinkedListの基本操作
     */
    private static void demonstrateLinkedList() {
        System.out.println("=== LinkedList の使用例 ===");

        LinkedList<String> tasks = new LinkedList<>();

        // Deque（両端キュー）としての操作
        tasks.addFirst("タスク1");  // 先頭に追加
        tasks.addLast("タスク2");   // 末尾に追加
        tasks.push("緊急タスク");    // スタックのpush（先頭に追加）
        tasks.offer("タスク3");      // キューのoffer（末尾に追加）

        System.out.println("タスクリスト: " + tasks);

        // 要素の取得（削除なし）
        System.out.println("最初のタスク: " + tasks.peekFirst());
        System.out.println("最後のタスク: " + tasks.peekLast());

        // 要素の取得と削除
        String urgent = tasks.poll();  // 先頭を取得して削除
        System.out.println("処理したタスク: " + urgent);
        System.out.println("残りのタスク: " + tasks);

        // スタックとしての使用
        System.out.println("\n--- スタック操作 ---");
        LinkedList<String> stack = new LinkedList<>();
        stack.push("プレート1");
        stack.push("プレート2");
        stack.push("プレート3");

        while (!stack.isEmpty()) {
            System.out.println("取り出し: " + stack.pop());
        }

        System.out.println();
    }

    /**
     * ArrayList vs LinkedList パフォーマンス比較
     */
    private static void compareListPerformance() {
        System.out.println("=== ArrayList vs LinkedList パフォーマンス ===");

        int size = 10000;
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // 末尾への追加
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }
        long arrayListAddTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            linkedList.add(i);
        }
        long linkedListAddTime = System.nanoTime() - startTime;

        System.out.println("末尾への追加（" + size + "要素）:");
        System.out.println("ArrayList: " + arrayListAddTime / 1_000_000.0 + " ms");
        System.out.println("LinkedList: " + linkedListAddTime / 1_000_000.0 + " ms");

        // ランダムアクセス
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(size / 2);
        }
        long arrayListGetTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(size / 2);
        }
        long linkedListGetTime = System.nanoTime() - startTime;

        System.out.println("\nランダムアクセス（1000回）:");
        System.out.println("ArrayList: " + arrayListGetTime / 1_000_000.0 + " ms");
        System.out.println("LinkedList: " + linkedListGetTime / 1_000_000.0 + " ms");

        System.out.println("\n使い分けの指針:");
        System.out.println("- ArrayList: ランダムアクセスが多い場合");
        System.out.println("- LinkedList: 先頭/末尾の追加削除が多い場合");
        System.out.println();
    }

    /**
     * PythonのリストとJavaのListの比較
     */
    private static void pythonVsJavaList() {
        System.out.println("=== Python vs Java リスト比較 ===");

        // Python: mixed_list = [1, "hello", 3.14, True]
        // Java: 型安全性のため、通常は同じ型のみ
        System.out.println("Python: 異なる型を混在可能");
        System.out.println("mixed_list = [1, \"hello\", 3.14, True]");

        System.out.println("\nJava: ジェネリクスで型を指定");
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("List<Integer> intList = " + intList);

        // スライス操作
        System.out.println("\n--- スライス操作 ---");
        System.out.println("Python: sublist = mylist[1:4]");
        System.out.println("Java: sublist = mylist.subList(1, 4)");

        List<String> colors = Arrays.asList("赤", "青", "緑", "黄", "紫");
        List<String> subColors = colors.subList(1, 4);
        System.out.println("元のリスト: " + colors);
        System.out.println("部分リスト: " + subColors);

        // リスト内包表記
        System.out.println("\n--- リスト内包表記 ---");
        System.out.println("Python: squares = [x**2 for x in range(5)]");
        System.out.println("Java: Stream APIを使用");

        List<Integer> squares = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            squares.add(i * i);
        }
        System.out.println("従来の方法: " + squares);

        // 負のインデックス
        System.out.println("\n--- 負のインデックス ---");
        System.out.println("Python: last = mylist[-1]");
        System.out.println("Java: last = mylist.get(mylist.size() - 1)");

        List<String> items = Arrays.asList("A", "B", "C", "D");
        String last = items.get(items.size() - 1);
        System.out.println("最後の要素: " + last);
    }
}
