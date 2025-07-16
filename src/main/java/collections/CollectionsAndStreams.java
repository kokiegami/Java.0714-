package collections;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Collections utilityクラスとStream API入門
 * Pythonの内包表記やfilter/map/reduceとの比較
 */
public class CollectionsAndStreams {
    public static void main(String[] args) {
        System.out.println("=== Collections Utility と Stream API ===\n");

        // Collections utilityクラス
        demonstrateCollectionsUtility();

        // Stream APIの基礎
        demonstrateStreamBasics();

        // Pythonとの比較
        pythonVsJavaStreams();

        // 実践的なStream操作
        demonstratePracticalStreams();
    }

    /**
     * Collections utilityクラスの使用例
     */
    private static void demonstrateCollectionsUtility() {
        System.out.println("=== Collections Utility クラス ===");

        // ソート
        List<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));
        Collections.sort(numbers);
        System.out.println("昇順ソート: " + numbers);

        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("降順ソート: " + numbers);

        // カスタムオブジェクトのソート
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("山田", 25, 60000),
                new Person("鈴木", 30, 80000),
                new Person("佐藤", 22, 50000)
        ));

        Collections.sort(people, Comparator.comparing(Person::getAge));
        System.out.println("\n年齢順: " + people);

        Collections.sort(people, Comparator.comparing(Person::getSalary).reversed());
        System.out.println("給与順（降順）: " + people);

        // シャッフル
        List<String> cards = new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5"));
        Collections.shuffle(cards);
        System.out.println("\nシャッフル後: " + cards);

        // 検索
        Collections.sort(numbers);
        int index = Collections.binarySearch(numbers, 5);
        System.out.println("\n5の位置: " + index);

        // 最大値・最小値
        System.out.println("最大値: " + Collections.max(numbers));
        System.out.println("最小値: " + Collections.min(numbers));

        Person richest = Collections.max(people, Comparator.comparing(Person::getSalary));
        System.out.println("最高給与: " + richest);

        // 頻度
        List<String> words = Arrays.asList("Java", "Python", "Java", "C++", "Java", "Python");
        int javaCount = Collections.frequency(words, "Java");
        System.out.println("\n\"Java\"の出現回数: " + javaCount);

        // 不変コレクション
        List<String> immutableList = Collections.unmodifiableList(
                new ArrayList<>(Arrays.asList("固定", "値", "リスト"))
        );
        // immutableList.add("新規");  // UnsupportedOperationException

        // 同期コレクション
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

        System.out.println();
    }

    /**
     * Stream APIの基礎
     */
    private static void demonstrateStreamBasics() {
        System.out.println("=== Stream API の基礎 ===");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter（フィルタリング）
        System.out.println("--- filter（条件に合う要素を抽出） ---");
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("偶数のみ: " + evenNumbers);

        // map（変換）
        System.out.println("\n--- map（要素を変換） ---");
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println("二乗: " + squares);

        // reduce（畳み込み）
        System.out.println("\n--- reduce（集約） ---");
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("合計: " + sum);

        Optional<Integer> product = numbers.stream()
                .reduce((a, b) -> a * b);
        System.out.println("積: " + product.orElse(0));

        // メソッド参照
        int sumWithMethodRef = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("合計（メソッド参照）: " + sumWithMethodRef);

        // 複数の操作を連鎖
        System.out.println("\n--- 操作の連鎖 ---");
        List<String> result = numbers.stream()
                .filter(n -> n % 2 == 0)           // 偶数のみ
                .map(n -> n * n)                   // 二乗
                .map(n -> "値: " + n)              // 文字列に変換
                .collect(Collectors.toList());
        System.out.println("偶数の二乗: " + result);

        // forEach（副作用のある処理）
        System.out.println("\n--- forEach ---");
        numbers.stream()
                .filter(n -> n <= 5)
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        // anyMatch, allMatch, noneMatch
        System.out.println("\n--- マッチング操作 ---");
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        boolean noNegative = numbers.stream().noneMatch(n -> n < 0);

        System.out.println("偶数が存在する？: " + hasEven);
        System.out.println("全て正の数？: " + allPositive);
        System.out.println("負の数は存在しない？: " + noNegative);

        // findFirst, findAny
        Optional<Integer> firstEven = numbers.stream()
                .filter(n -> n % 2 == 0)
                .findFirst();
        System.out.println("\n最初の偶数: " + firstEven.orElse(-1));

        // count, min, max
        long evenCount = numbers.stream()
                .filter(n -> n % 2 == 0)
                .count();
        System.out.println("偶数の個数: " + evenCount);

        System.out.println();
    }

    /**
     * PythonとJavaのStream比較
     */
    private static void pythonVsJavaStreams() {
        System.out.println("=== Python vs Java Stream 比較 ===");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // リスト内包表記 vs Stream
        System.out.println("--- リスト内包表記 vs Stream ---");
        System.out.println("Python: squares = [x**2 for x in numbers]");
        System.out.println("Java:");
        List<Integer> squares = numbers.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());
        System.out.println("結果: " + squares);

        // filter + map
        System.out.println("\n--- filter + map ---");
        System.out.println("Python: even_squares = [x**2 for x in numbers if x % 2 == 0]");
        System.out.println("Java:");
        List<Integer> evenSquares = numbers.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x * x)
                .collect(Collectors.toList());
        System.out.println("結果: " + evenSquares);

        // 辞書内包表記 vs Collectors.toMap
        System.out.println("\n--- 辞書内包表記 vs Collectors.toMap ---");
        System.out.println("Python: squares_dict = {x: x**2 for x in numbers}");
        System.out.println("Java:");
        Map<Integer, Integer> squaresMap = numbers.stream()
                .collect(Collectors.toMap(
                        x -> x,           // キー
                        x -> x * x        // 値
                ));
        System.out.println("結果: " + squaresMap);

        // reduce操作
        System.out.println("\n--- reduce操作 ---");
        System.out.println("Python: from functools import reduce");
        System.out.println("        sum = reduce(lambda a, b: a + b, numbers)");
        System.out.println("Java:");
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("結果: " + sum);

        // zip操作
        System.out.println("\n--- zip操作 ---");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> ages = Arrays.asList(25, 30, 35);

        System.out.println("Python: list(zip(names, ages))");
        System.out.println("Java (Stream):");
        List<Pair<String, Integer>> zipped = IntStream.range(0, Math.min(names.size(), ages.size()))
                .mapToObj(i -> new Pair<>(names.get(i), ages.get(i)))
                .collect(Collectors.toList());
        System.out.println("結果: " + zipped);

        System.out.println();
    }

    /**
     * 実践的なStream操作
     */
    private static void demonstratePracticalStreams() {
        System.out.println("=== 実践的なStream操作 ===");

        // 従業員データ
        List<Employee> employees = Arrays.asList(
                new Employee("山田太郎", "営業部", 30, 400000),
                new Employee("鈴木花子", "開発部", 28, 500000),
                new Employee("佐藤次郎", "営業部", 35, 450000),
                new Employee("田中三郎", "開発部", 25, 380000),
                new Employee("高橋四郎", "人事部", 40, 550000),
                new Employee("伊藤五郎", "開発部", 32, 520000)
        );

        // 部署ごとにグループ化
        System.out.println("--- 部署ごとのグループ化 ---");
        Map<String, List<Employee>> byDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        byDepartment.forEach((dept, emps) -> {
            System.out.println(dept + ": " + emps.size() + "名");
            emps.forEach(e -> System.out.println("  - " + e.getName()));
        });

        // 部署ごとの平均給与
        System.out.println("\n--- 部署ごとの平均給与 ---");
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        avgSalaryByDept.forEach((dept, avg) ->
                System.out.printf("%s: ¥%,.0f\n", dept, avg)
        );

        // 給与上位3名
        System.out.println("\n--- 給与上位3名 ---");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .forEach(e -> System.out.printf("%s: ¥%,d\n", e.getName(), e.getSalary()));

        // 年齢層別の人数
        System.out.println("\n--- 年齢層別の人数 ---");
        Map<String, Long> ageGroups = employees.stream()
                .collect(Collectors.groupingBy(
                        e -> {
                            int age = e.getAge();
                            if (age < 30) return "20代";
                            else if (age < 40) return "30代";
                            else return "40代以上";
                        },
                        Collectors.counting()
                ));

        ageGroups.forEach((group, count) ->
                System.out.println(group + ": " + count + "名")
        );

        // 複雑な集計
        System.out.println("\n--- 部署別統計情報 ---");
        Map<String, DoubleSummaryStatistics> deptStats = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summarizingDouble(Employee::getSalary)
                ));

        deptStats.forEach((dept, stats) -> {
            System.out.println(dept + ":");
            System.out.printf("  人数: %d名\n", stats.getCount());
            System.out.printf("  最高給与: ¥%,.0f\n", stats.getMax());
            System.out.printf("  最低給与: ¥%,.0f\n", stats.getMin());
            System.out.printf("  平均給与: ¥%,.0f\n", stats.getAverage());
        });

        // パーティショニング（条件による2分割）
        System.out.println("\n--- 高給与者と一般給与者の分類 ---");
        Map<Boolean, List<Employee>> partitioned = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 500000));

        System.out.println("高給与者（50万円以上）:");
        partitioned.get(true).forEach(e ->
                System.out.println("  - " + e.getName())
        );

        System.out.println("一般給与者:");
        partitioned.get(false).forEach(e ->
                System.out.println("  - " + e.getName())
        );

        // カスタムコレクタ
        System.out.println("\n--- 名前の文字列結合 ---");
        String names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("全従業員: " + names);

        // 並列ストリーム
        System.out.println("\n--- 並列処理 ---");
        long startTime = System.currentTimeMillis();

        // 通常のストリーム
        double sumSerial = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        long serialTime = System.currentTimeMillis() - startTime;

        // 並列ストリーム
        startTime = System.currentTimeMillis();
        double sumParallel = employees.parallelStream()
                .mapToDouble(Employee::getSalary)
                .sum();

        long parallelTime = System.currentTimeMillis() - startTime;

        System.out.println("給与合計: ¥" + String.format("%,.0f", sumSerial));
        System.out.println("処理時間 - 通常: " + serialTime + "ms, 並列: " + parallelTime + "ms");
    }

    // ===== クラス定義 =====

    static class Person {
        private String name;
        private int age;
        private double salary;

        public Person(String name, int age, double salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public double getSalary() { return salary; }

        @Override
        public String toString() {
            return name + "(" + age + "歳, ¥" + String.format("%,.0f", salary) + ")";
        }
    }

    static class Employee {
        private String name;
        private String department;
        private int age;
        private int salary;

        public Employee(String name, String department, int age, int salary) {
            this.name = name;
            this.department = department;
            this.age = age;
            this.salary = salary;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public int getAge() { return age; }
        public int getSalary() { return salary; }

        @Override
        public String toString() {
            return name + " (" + department + ")";
        }
    }

    static class Pair<F, S> {
        private F first;
        private S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }
}