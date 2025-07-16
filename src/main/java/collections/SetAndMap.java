package collections;

import java.util.*;

/**
 * SetとMapの使い方
 * Pythonのset、dictとの比較
 */
public class SetAndMap {
    public static void main(String[] args) {
        System.out.println("=== Set と Map の使い方 ===\n");

        // Setの実装
        demonstrateHashSet();
        demonstrateTreeSet();
        demonstrateLinkedHashSet();

        // Mapの実装
        demonstrateHashMap();
        demonstrateTreeMap();
        demonstrateLinkedHashMap();

        // PythonとJavaの比較
        pythonVsJavaSetMap();
    }

    /**
     * HashSetの使用例（順序保証なし、高速）
     */
    private static void demonstrateHashSet() {
        System.out.println("=== HashSet の使用例 ===");

        Set<String> languages = new HashSet<>();

        // 要素の追加
        languages.add("Java");
        languages.add("Python");
        languages.add("JavaScript");
        languages.add("Java");  // 重複は無視される

        System.out.println("プログラミング言語: " + languages);
        System.out.println("要素数: " + languages.size());

        // 要素の存在確認
        System.out.println("Javaを含む？: " + languages.contains("Java"));
        System.out.println("C++を含む？: " + languages.contains("C++"));

        // 要素の削除
        languages.remove("JavaScript");
        System.out.println("JavaScript削除後: " + languages);

        // 集合演算
        Set<String> backendLangs = new HashSet<>(Arrays.asList("Java", "Python", "Ruby", "Go"));
        Set<String> frontendLangs = new HashSet<>(Arrays.asList("JavaScript", "TypeScript", "Dart"));

        // 和集合（Union）
        Set<String> allLangs = new HashSet<>(backendLangs);
        allLangs.addAll(frontendLangs);
        System.out.println("\n和集合: " + allLangs);

        // 積集合（Intersection）
        Set<String> commonLangs = new HashSet<>(backendLangs);
        commonLangs.retainAll(languages);
        System.out.println("積集合: " + commonLangs);

        // 差集合（Difference）
        Set<String> backendOnly = new HashSet<>(backendLangs);
        backendOnly.removeAll(languages);
        System.out.println("差集合（バックエンドのみ）: " + backendOnly);

        System.out.println();
    }

    /**
     * TreeSetの使用例（ソート済み）
     */
    private static void demonstrateTreeSet() {
        System.out.println("=== TreeSet の使用例（自動ソート） ===");

        // 数値のTreeSet
        Set<Integer> numbers = new TreeSet<>();
        numbers.addAll(Arrays.asList(5, 1, 9, 3, 7, 2));
        System.out.println("数値（自動ソート）: " + numbers);

        // 文字列のTreeSet
        Set<String> words = new TreeSet<>();
        words.addAll(Arrays.asList("banana", "apple", "cherry", "date"));
        System.out.println("単語（辞書順）: " + words);

        // カスタムコンパレータ
        Set<String> reverseWords = new TreeSet<>(Comparator.reverseOrder());
        reverseWords.addAll(words);
        System.out.println("単語（逆順）: " + reverseWords);

        // NavigableSetの機能
        TreeSet<Integer> scores = new TreeSet<>(Arrays.asList(60, 70, 80, 90, 100));
        System.out.println("\nテストスコア: " + scores);
        System.out.println("80以上の最小値: " + scores.ceiling(80));
        System.out.println("80より小さい最大値: " + scores.floor(79));
        System.out.println("70-90の部分集合: " + scores.subSet(70, true, 90, true));

        System.out.println();
    }

    /**
     * LinkedHashSetの使用例（挿入順序を保持）
     */
    private static void demonstrateLinkedHashSet() {
        System.out.println("=== LinkedHashSet の使用例（順序保持） ===");

        Set<String> visitedPages = new LinkedHashSet<>();
        visitedPages.add("ホーム");
        visitedPages.add("製品一覧");
        visitedPages.add("詳細ページ");
        visitedPages.add("カート");
        visitedPages.add("ホーム");  // 重複は無視されるが、順序は保持

        System.out.println("訪問ページ履歴: " + visitedPages);

        // 順序が保持されていることを確認
        System.out.println("訪問順:");
        int order = 1;
        for (String page : visitedPages) {
            System.out.println(order++ + ". " + page);
        }

        System.out.println();
    }

    /**
     * HashMapの使用例
     */
    private static void demonstrateHashMap() {
        System.out.println("=== HashMap の使用例 ===");

        Map<String, Integer> studentScores = new HashMap<>();

        // 要素の追加
        studentScores.put("山田", 85);
        studentScores.put("鈴木", 92);
        studentScores.put("佐藤", 78);
        studentScores.put("田中", 88);

        System.out.println("学生の成績: " + studentScores);

        // 値の取得
        Integer yamadaScore = studentScores.get("山田");
        System.out.println("山田さんの点数: " + yamadaScore);

        // 存在しないキー
        Integer unknownScore = studentScores.get("高橋");
        System.out.println("高橋さんの点数: " + unknownScore);  // null

        // getOrDefault
        int defaultScore = studentScores.getOrDefault("高橋", 0);
        System.out.println("高橋さんの点数（デフォルト）: " + defaultScore);

        // 値の更新
        studentScores.put("山田", 90);  // 上書き
        System.out.println("山田さんの更新後の点数: " + studentScores.get("山田"));

        // キーの存在確認
        System.out.println("\n佐藤さんは存在する？: " + studentScores.containsKey("佐藤"));
        System.out.println("100点の人はいる？: " + studentScores.containsValue(100));

        // 要素の削除
        studentScores.remove("田中");
        System.out.println("田中さん削除後: " + studentScores);

        // イテレーション
        System.out.println("\n--- 全エントリーの表示 ---");
        for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "点");
        }

        // キーのみ、値のみの取得
        System.out.println("\n学生名一覧: " + studentScores.keySet());
        System.out.println("点数一覧: " + studentScores.values());

        // Java 8以降の便利なメソッド
        System.out.println("\n--- Java 8+ の機能 ---");

        // computeIfAbsent
        studentScores.computeIfAbsent("高橋", k -> 75);
        System.out.println("高橋さん追加: " + studentScores);

        // merge
        studentScores.merge("山田", 5, (oldVal, newVal) -> oldVal + newVal);
        System.out.println("山田さんに5点加算: " + studentScores);

        // forEach
        System.out.println("\n成績一覧:");
        studentScores.forEach((name, score) ->
                System.out.println(name + ": " + score + "点 - " + getGrade(score))
        );

        System.out.println();
    }

    /**
     * TreeMapの使用例（ソート済みMap）
     */
    private static void demonstrateTreeMap() {
        System.out.println("=== TreeMap の使用例（ソート済み） ===");

        // 月ごとの売上
        Map<String, Integer> monthlySales = new TreeMap<>();
        monthlySales.put("2024-03", 1000000);
        monthlySales.put("2024-01", 800000);
        monthlySales.put("2024-02", 900000);
        monthlySales.put("2024-04", 1100000);

        System.out.println("月別売上（自動ソート）:");
        monthlySales.forEach((month, sales) ->
                System.out.println(month + ": " + String.format("%,d", sales) + "円")
        );

        // NavigableMapの機能
        TreeMap<Integer, String> rankings = new TreeMap<>();
        rankings.put(1, "金メダル");
        rankings.put(2, "銀メダル");
        rankings.put(3, "銅メダル");
        rankings.put(4, "入賞");
        rankings.put(5, "入賞");

        System.out.println("\nランキング: " + rankings);
        System.out.println("3位以下: " + rankings.tailMap(3));
        System.out.println("2位以上: " + rankings.headMap(3));

        System.out.println();
    }

    /**
     * LinkedHashMapの使用例（挿入順序を保持）
     */
    private static void demonstrateLinkedHashMap() {
        System.out.println("=== LinkedHashMap の使用例（順序保持） ===");

        // LRUキャッシュの実装例
        final int CACHE_SIZE = 3;
        Map<String, String> lruCache = new LinkedHashMap<String, String>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > CACHE_SIZE;
            }
        };

        // データの追加
        lruCache.put("user1", "山田");
        lruCache.put("user2", "鈴木");
        lruCache.put("user3", "佐藤");
        System.out.println("初期状態: " + lruCache);

        // user1にアクセス
        lruCache.get("user1");
        System.out.println("user1アクセス後: " + lruCache);

        // 新しいユーザーを追加（最も使われていないものが削除される）
        lruCache.put("user4", "田中");
        System.out.println("user4追加後: " + lruCache);

        System.out.println();
    }

    /**
     * 成績から評価を返す
     */
    private static String getGrade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }

    /**
     * PythonとJavaのSet/Map比較
     */
    private static void pythonVsJavaSetMap() {
        System.out.println("=== Python vs Java Set/Map 比較 ===");

        // Set比較
        System.out.println("--- Set ---");
        System.out.println("Python: my_set = {1, 2, 3}");
        System.out.println("Java:   Set<Integer> mySet = new HashSet<>(Arrays.asList(1, 2, 3));");

        Set<Integer> javaSet = new HashSet<>(Arrays.asList(1, 2, 3));
        System.out.println("Javaの結果: " + javaSet);

        // Map/Dict比較
        System.out.println("\n--- Map/Dictionary ---");
        System.out.println("Python: my_dict = {'key1': 'value1', 'key2': 'value2'}");
        System.out.println("Java:   Map<String, String> myMap = new HashMap<>();");

        Map<String, String> javaMap = new HashMap<>();
        javaMap.put("key1", "value1");
        javaMap.put("key2", "value2");
        System.out.println("Javaの結果: " + javaMap);

        // 内包表記
        System.out.println("\n--- 内包表記 ---");
        System.out.println("Python: squares = {x: x**2 for x in range(5)}");
        System.out.println("Java:   従来のループまたはStream APIを使用");

        Map<Integer, Integer> squares = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            squares.put(i, i * i);
        }
        System.out.println("Javaの結果: " + squares);

        // get()の動作の違い
        System.out.println("\n--- get()の動作 ---");
        System.out.println("Python: dict.get('missing_key', 'default')");
        System.out.println("Java:   map.getOrDefault('missing_key', 'default')");

        String pythonStyle = javaMap.getOrDefault("missing", "デフォルト値");
        System.out.println("存在しないキーの取得: " + pythonStyle);
    }
}
