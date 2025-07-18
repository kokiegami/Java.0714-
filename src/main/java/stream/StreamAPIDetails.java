package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream APIの詳細な使い方を学ぶためのサンプルコードです。
 * Stream APIは、関数型プログラミングスタイルでコレクションや配列などのデータ処理を
 * 宣言的かつ効率的に行うための強力なツールです。
 */
public class StreamAPIDetails {

    public static void main(String[] args) {
        System.out.println("--- Stream APIの詳細な使い方 ---");

        List<String> words = Arrays.asList("apple", "banana", "apricot", "grape", "blueberry", "kiwi");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // --- 1. Streamの生成 ---
        System.out.println("\n--- 1. Streamの生成 ---");
        // (1) コレクションから
        System.out.println("  ListからStream: " + words.stream().count() + "要素");
        // (2) 配列から
        String[] fruits = {"orange", "mango"};
        System.out.println("  配列からStream: " + Arrays.stream(fruits).count() + "要素");
        // (3) 指定された値から
        System.out.println("  of()からStream: " + Stream.of("A", "B", "C").count() + "要素");
        // (4) range/rangeClosed (プリミティブStream)
        System.out.println("  IntStream.range(1, 5): "); // 1, 2, 3, 4 (5を含まない)
        IntStream.range(1, 5).forEach(n -> System.out.print(n + " "));
        System.out.println("\n  IntStream.rangeClosed(1, 5): "); // 1, 2, 3, 4, 5 (5を含む)
        IntStream.rangeClosed(1, 5).forEach(n -> System.out.print(n + " "));
        System.out.println();
        // (5) ファイルから (例: sales_data.csvを読み込む)
        // CSVファイルがプロジェクトのルートディレクトリにある前提
        String csvFilePath = "sales_data.csv";
        System.out.println("  ファイルからStream (CSV): ");
        try (Stream<String> lines = Files.lines(Paths.get(csvFilePath))) {
            lines.limit(3).forEach(System.out::println); // 最初の3行のみ表示
        } catch (IOException e) {
            System.err.println("ファイルの読み込みエラー: " + e.getMessage());
            System.err.println("sales_data.csvがプロジェクトルートに存在することを確認してください。");
        }


        // --- 2. 中間操作 (Intermediate Operations) ---
        // 中間操作はStreamを返し、遅延評価される
        System.out.println("\n--- 2. 中間操作 (遅延評価) ---");

        // (1) filter(): 条件に合う要素をフィルタリング
        System.out.println("\n  -- filter(): 'a' を含む単語 --");
        words.stream()
                .filter(word -> word.contains("a")) // ラムダ式で条件指定
                .forEach(System.out::println);       // 結果を出力 (終端操作)

        // (2) map(): 各要素を別の形式に変換
        System.out.println("\n  -- map(): 単語を大文字に変換 --");
        words.stream()
                .map(String::toUpperCase) // メソッド参照で大文字に変換
                .forEach(System.out::println);

        // (3) flatMap(): StreamのStreamを単一のStreamにフラット化
        System.out.println("\n  -- flatMap(): 単語のリストから全ての文字を抽出 --");
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d", "e")
        );
        listOfLists.stream()
                .flatMap(List::stream) // List<String>をStream<String>に変換し、フラット化
                .forEach(System.out::print); // ここはSystem.out.printで改行なし
        System.out.println(); // その後に改行

        // (4) distinct(): 重複する要素を削除
        System.out.println("\n  -- distinct(): 重複を削除 --");
        List<Integer> duplicateNumbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        duplicateNumbers.stream()
                .distinct()
                .forEach(n -> System.out.print(n + " ")); // ここはSystem.out.printで改行なし
        System.out.println(); // その後に改行

        // (5) sorted(): 要素をソート
        System.out.println("\n  -- sorted(): 自然順序でソート --");
        words.stream()
                .sorted() // 自然順序（アルファベット順）
                .forEach(System.out::println);

        System.out.println("\n  -- sorted(Comparator): 長さで逆順にソート --");
        words.stream()
                .sorted(Comparator.comparing(String::length).reversed()) // 長さで降順ソート
                .forEach(System.out::println);

        // (6) limit(): 最初のN個の要素に制限
        System.out.println("\n  -- limit(): 最初の3つの単語 --");
        words.stream()
                .limit(3)
                .forEach(System.out::println);

        // (7) skip(): 最初のN個の要素をスキップ
        System.out.println("\n  -- skip(): 最初の2つをスキップした後の単語 --");
        words.stream()
                .skip(2)
                .forEach(System.out::println);

        // (8) peek(): 各要素を消費せずにデバッグやログ出力 (副作用を伴う中間操作)
        System.out.println("\n  -- peek(): 処理中に各要素を覗き見る --");
        numbers.stream()
                .filter(n -> n % 2 == 0) // 偶数にフィルタリング
                .peek(n -> System.out.println("  フィルタ後 (peek): " + n)) // 各要素を覗き見る
                .map(n -> n * 2) // 2倍にする
                .peek(n -> System.out.println("  マップ後 (peek): " + n))
                .limit(2) // 最初の2つに制限
                .forEach(System.out::println); // 最終結果
        // peekは終端操作がないと実行されないことに注意（遅延評価のため）


        // --- 3. 終端操作 (Terminal Operations) ---
        // 終端操作はStreamを消費し、結果を返すか副作用を引き起こす
        System.out.println("\n--- 3. 終端操作 (Streamを消費) ---");

        // (1) forEach(): 各要素に対して操作を実行 (副作用)
        System.out.println("\n  -- forEach(): リストの要素を表示 --");
        numbers.stream()
                .filter(n -> n > 5)
                .forEach(n -> System.out.print(n + " ")); // ここはSystem.out.printで改行なし
        System.out.println(); // その後に改行


        // (2) collect(): Streamの要素をコレクションやMapに収集
        System.out.println("\n  -- collect(): 結果をList, Set, Mapに収集 --");
        List<String> filteredList = words.stream()
                .filter(word -> word.length() > 5)
                .collect(Collectors.toList()); // Listに収集
        System.out.println("長さが5より長い単語 (List): " + filteredList);

        Set<String> uniqueCategories = Arrays.asList("fruit", "vegetable", "fruit", "dairy").stream()
                .collect(Collectors.toSet()); // Setに収集
        System.out.println("ユニークなカテゴリ (Set): " + uniqueCategories);

        Map<String, Integer> wordLengthMap = words.stream()
                .collect(Collectors.toMap(
                        word -> word, // キー: 単語自体
                        String::length // 値: 単語の長さ (メソッド参照)
                ));
        System.out.println("単語と長さのマップ: " + wordLengthMap);

        // (3) count(): 要素の数を数える
        long count = numbers.stream().filter(n -> n % 2 != 0).count(); // 奇数の数
        System.out.println("\n  -- count(): 奇数の数 --");
        System.out.println("奇数の数: " + count);

        // (4) min(), max(): 最小値、最大値を見つける (Optionalを返す)
        System.out.println("\n  -- min(), max(): 最小値と最大値 --");
        Optional<Integer> minNum = numbers.stream().min(Comparator.naturalOrder());
        Optional<Integer> maxNum = numbers.stream().max(Comparator.naturalOrder());
        minNum.ifPresent(val -> System.out.println("最小値: " + val)); // Optionalの安全な使い方
        maxNum.ifPresent(val -> System.out.println("最大値: " + val));

        // (5) findFirst(), findAny(): 最初の要素、任意の要素を見つける (Optionalを返す)
        System.out.println("\n  -- findFirst(), findAny(): 最初の要素と任意の要素 --");
        Optional<String> firstApple = words.stream().filter(word -> word.startsWith("a")).findFirst();
        firstApple.ifPresent(val -> System.out.println("最初の'a'で始まる単語: " + val));

        Optional<String> anyGrape = words.stream().filter(word -> word.contains("grape")).findAny();
        anyGrape.ifPresent(val -> System.out.println("任意の'grape'を含む単語: " + val));
        // findAnyは並列処理の際に効率的

        // (6) allMatch(), anyMatch(), noneMatch(): 条件の全て/いずれか/なしが一致するか (booleanを返す)
        System.out.println("\n  -- allMatch(), anyMatch(), noneMatch(): 条件の確認 --");
        boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0); // 全て偶数か
        System.out.println("全ての数が偶数か？: " + allEven);

        boolean anyGreaterThanSeven = numbers.stream().anyMatch(n -> n > 7); // 7より大きい数が一つでもあるか
        System.out.println("7より大きい数は一つでもあるか？: " + anyGreaterThanSeven);

        boolean noNegative = numbers.stream().noneMatch(n -> n < 0); // 負の数がないか
        System.out.println("負の数がないか？: " + noNegative);

        // (7) reduce(): 要素を単一の結果に結合 (Optionalを返す)
        System.out.println("\n  -- reduce(): 要素の結合 --");
        Optional<Integer> sum = numbers.stream().reduce((a, b) -> a + b); // 全ての数の合計
        sum.ifPresent(val -> System.out.println("全要素の合計: " + val));

        String combinedWords = words.stream().reduce("", (acc, word) -> acc + " " + word); // 全ての単語を結合
        System.out.println("結合された単語: " + combinedWords.trim());

        System.out.println("\n--- Stream APIの詳細学習完了 ---");
    }
}