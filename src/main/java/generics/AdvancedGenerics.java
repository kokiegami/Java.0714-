package generics;

import java.util.*;
import java.util.function.*;
import java.util.Optional;
import java.util.Comparator;

/**
 * 高度なジェネリクスの使い方
 * ワイルドカード、型推論、共変性と反変性
 */
public class AdvancedGenerics {
    public static void main(String[] args) {
        System.out.println("=== 高度なジェネリクス ===\n");

        // ワイルドカードの使い方
        demonstrateWildcards();

        // 型推論
        demonstrateTypeInference();

        // 共変性と反変性
        demonstrateVariance();

        // 実践的な例
        demonstratePracticalExample();
    }

    private static void demonstrateWildcards() {
        System.out.println("=== ワイルドカード ===");

        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);
        List<Number> numberList = Arrays.asList(1, 2.5, 3L);

        System.out.println("整数リストの合計: " + sumOfList(intList));
        System.out.println("小数リストの合計: " + sumOfList(doubleList));
        System.out.println("数値リストの合計: " + sumOfList(numberList));

        List<Number> numbers = new ArrayList<>();
        addNumbers(numbers);
        System.out.println("\n追加後の数値リスト: " + numbers);

        List<Object> objects = new ArrayList<>();
        addNumbers(objects);
        System.out.println("オブジェクトリスト: " + objects);

        List<?> unknownList = getRandomList();
        System.out.println("\n未知のリスト: " + unknownList);
        printList(unknownList);

        demonstratePECS();
        System.out.println();
    }

    private static void demonstrateTypeInference() {
        System.out.println("=== 型推論 ===");

        Map<String, List<Integer>> oldStyle = new HashMap<String, List<Integer>>();
        Map<String, List<Integer>> newStyle = new HashMap<>();

        List<String> emptyList = Collections.emptyList();
        List<String> singletonList = Collections.singletonList("Hello");

        Map<String, List<Pair<Integer, String>>> complexMap = new HashMap<>();
        complexMap.put("data", Arrays.asList(
                new Pair<>(1, "one"),
                new Pair<>(2, "two")
        ));

        String first = getFirst(Arrays.asList("A", "B", "C"));
        Integer firstNum = getFirst(Arrays.asList(1, 2, 3));
        System.out.println("最初の文字: " + first);
        System.out.println("最初の数値: " + firstNum);

        List<String> strings = Collections.<String>emptyList();

        Function<String, Integer> lengthFunc = s -> s.length();
        System.out.println("\n\"Hello\"の長さ: " + lengthFunc.apply("Hello"));

        System.out.println();
    }

    private static void demonstrateVariance() {
        System.out.println("=== 共変性と反変性 ===");

        Number[] numbers = new Integer[3];
        numbers[0] = 1;

        List<? extends Number> covariantList = new ArrayList<Integer>();
        List<? super Integer> contravariantList = new ArrayList<Number>();
        contravariantList.add(1);
        contravariantList.add(2);

        List<Dog> dogs = Arrays.asList(new Dog("ポチ"), new Dog("タロー"));
        List<Cat> cats = Arrays.asList(new Cat("タマ"), new Cat("ミケ"));

        System.out.println("\n犬の鳴き声:");
        makeAllAnimalsSpeak(dogs);

        System.out.println("\n猫の鳴き声:");
        makeAllAnimalsSpeak(cats);

        Comparator<Animal> animalComparator = (a1, a2) -> a1.name.compareTo(a2.name);
        List<Dog> sortedDogs = new ArrayList<>(dogs);
        sortedDogs.sort(animalComparator);
        System.out.println("\nソート後の犬: " + sortedDogs);

        System.out.println();
    }

    private static void demonstratePracticalExample() {
        System.out.println("=== 実践例：ジェネリックリポジトリ ===");

        Repository<User,Long> userRepo = new InMemoryRepository<>();
        User user1 = new User(1L, "山田太郎", "yamada@example.com");
        User user2 = new User(2L, "鈴木花子", "suzuki@example.com");

        userRepo.save(user1);
        userRepo.save(user2);

        System.out.println("全ユーザー: " + userRepo.findAll());
        System.out.println("ID=1のユーザー: " + userRepo.findById(1L));

        Repository<Product,Long> productRepo = new InMemoryRepository<>();
        Product product1 = new Product(1L, "ノートPC", 100000);
        Product product2 = new Product(2L, "マウス", 3000);

        productRepo.save(product1);
        productRepo.save(product2);

        System.out.println("\n全商品: " + productRepo.findAll());

        List<User> activeUsers = userRepo.findByPredicate(
                u -> u.getEmail().endsWith("example.com")
        );
        System.out.println("\nexample.comユーザー: " + activeUsers);

        List<Product> expensiveProducts = productRepo.findByPredicate(
                p -> p.getPrice() > 50000
        );
        System.out.println("高額商品: " + expensiveProducts);

        System.out.println("\n=== バッチ処理 ===");
        List<User> newUsers = Arrays.asList(
                new User(3L, "佐藤次郎", "sato@example.com"),
                new User(4L, "田中三郎", "tanaka@example.com")
        );
        userRepo.saveAll(newUsers);
        System.out.println("バッチ保存後: " + userRepo.findAll());
    }

    private static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }

    private static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    private static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println("要素: " + item);
        }
    }

    private static List<?> getRandomList() {
        return new Random().nextBoolean()
                ? Arrays.asList("A", "B", "C")
                : Arrays.asList(1, 2, 3);
    }

    private static void demonstratePECS() {
        System.out.println("\n--- PECS原則 ---");

        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<? extends Number> producer = integers;
        Number num = producer.get(0);

        List<Number> numbers = new ArrayList<>();
        List<? super Integer> consumer = numbers;
        consumer.add(4);

        System.out.println("Producer（extends）: 読み取り専用");
        System.out.println("Consumer（super）: 書き込み可能");
    }

    private static <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }

    private static void makeAllAnimalsSpeak(List<? extends Animal> animals) {
        for (Animal animal : animals) {
            animal.speak();
        }
    }

    // ===== クラス定義 =====

    static class Animal {
        protected String name;

        public Animal(String name) {
            this.name = name;
        }

        public void speak() {
            System.out.println(name + "が鳴いています");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Dog extends Animal {
        public Dog(String name) {
            super(name);
        }

        @Override
        public void speak() {
            System.out.println(name + "：ワンワン！");
        }
    }

    static class Cat extends Animal {
        public Cat(String name) {
            super(name);
        }

        @Override
        public void speak() {
            System.out.println(name + "：ニャーニャー！");
        }
    }

    interface Entity<ID> {
        ID getId();
        void setId(ID id);
    }

    static class User implements Entity<Long> {
        private Long id;
        private String name;
        private String email;

        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        @Override public Long getId() { return id; }
        @Override public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public String getEmail() { return email; }

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }

    static class Product implements Entity<Long> {
        private Long id;
        private String name;
        private double price;

        public Product(Long id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override public Long getId() { return id; }
        @Override public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public double getPrice() { return price; }

        @Override
        public String toString() {
            return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
        }
    }

    interface Repository<T extends Entity<ID>, ID> {
        void save(T entity);
        void saveAll(List<T> entities);
        Optional<T> findById(ID id);
        List<T> findAll();
        List<T> findByPredicate(Predicate<T> predicate);
        void deleteById(ID id);
    }

    static class InMemoryRepository<T extends Entity<Long>> implements Repository<T, Long> {
        private Map<Long, T> storage = new HashMap<>();

        @Override public void save(T entity) {
            storage.put(entity.getId(), entity);
        }

        @Override public void saveAll(List<T> entities) {
            entities.forEach(this::save);
        }

        @Override public Optional<T> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override public List<T> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override public List<T> findByPredicate(Predicate<T> predicate) {
            return storage.values().stream()
                    .filter(predicate)
                    .toList();
        }

        @Override public void deleteById(Long id) {
            storage.remove(id);
        }
    }

    // ✅ 追加：Pairクラス定義（自作）
    static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }

        @Override
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }
}
