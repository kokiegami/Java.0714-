package fileio;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Javaのシリアライゼーションとデータ永続化
 * 注：JSON処理は標準ライブラリには含まれないため、概念のみ説明
 */
public class SerializationDemo {
    public static void main(String[] args) {
        System.out.println("=== シリアライゼーションとデータ永続化 ===\n");

        // 基本的なシリアライゼーション
        demonstrateBasicSerialization();

        // カスタムシリアライゼーション
        demonstrateCustomSerialization();

        // 大規模データの永続化
        demonstrateLargeDataPersistence();

        // プロパティファイルの使用
        demonstratePropertiesFile();

        // CSV形式でのデータ保存
        demonstrateCSVPersistence();

        // JSON風の簡易実装
        demonstrateSimpleJSON();
    }

    /**
     * 基本的なシリアライゼーション
     */
    private static void demonstrateBasicSerialization() {
        System.out.println("=== 基本的なシリアライゼーション ===");

        // シリアライズするオブジェクトの作成
        Person person = new Person("山田太郎", 30, "tokyo@example.com");
        person.addSkill("Java");
        person.addSkill("Python");
        person.addSkill("SQL");

        String filename = "person.ser";

        // オブジェクトのシリアライズ（保存）
        System.out.println("--- シリアライズ ---");
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {

            oos.writeObject(person);
            System.out.println("オブジェクトを保存しました: " + person);

        } catch (IOException e) {
            System.err.println("シリアライズエラー: " + e.getMessage());
        }

        // オブジェクトのデシリアライズ（読み込み）
        System.out.println("\n--- デシリアライズ ---");
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {

            Person loadedPerson = (Person) ois.readObject();
            System.out.println("オブジェクトを読み込みました: " + loadedPerson);
            System.out.println("スキル: " + loadedPerson.getSkills());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("デシリアライズエラー: " + e.getMessage());
        }

        // 複数オブジェクトのシリアライズ
        System.out.println("\n--- 複数オブジェクトの保存 ---");
        List<Person> people = Arrays.asList(
                new Person("鈴木花子", 25, "suzuki@example.com"),
                new Person("佐藤次郎", 35, "sato@example.com"),
                new Person("田中美咲", 28, "tanaka@example.com")
        );

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("people.ser"))) {

            oos.writeObject(people);
            System.out.println("リストを保存しました（" + people.size() + "人）");

        } catch (IOException e) {
            System.err.println("リスト保存エラー: " + e.getMessage());
        }

        // クリーンアップ
        new File(filename).delete();
        new File("people.ser").delete();

        System.out.println();
    }

    /**
     * カスタムシリアライゼーション
     */
    private static void demonstrateCustomSerialization() {
        System.out.println("=== カスタムシリアライゼーション ===");

        // カスタムシリアライゼーションを持つオブジェクト
        UserAccount account = new UserAccount("user123", "password123",
                "admin@example.com");
        account.login();
        account.updateLastActivity();

        String filename = "account.ser";

        // 保存（パスワードは暗号化される）
        System.out.println("--- カスタム保存 ---");
        System.out.println("保存前: " + account);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {

            oos.writeObject(account);
            System.out.println("アカウントを保存しました（パスワードは暗号化）");

        } catch (IOException e) {
            System.err.println("保存エラー: " + e.getMessage());
        }

        // 読み込み
        System.out.println("\n--- カスタム読み込み ---");
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {

            UserAccount loadedAccount = (UserAccount) ois.readObject();
            System.out.println("読み込み後: " + loadedAccount);
            System.out.println("パスワード検証: " +
                    loadedAccount.verifyPassword("password123"));

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("読み込みエラー: " + e.getMessage());
        }

        new File(filename).delete();

        System.out.println();
    }

    /**
     * 大規模データの永続化
     */
    private static void demonstrateLargeDataPersistence() {
        System.out.println("=== 大規模データの永続化 ===");

        // 大量のデータを生成
        System.out.println("--- データ生成 ---");
        List<Transaction> transactions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            transactions.add(new Transaction(
                    "TRX" + String.format("%04d", i),
                    random.nextDouble() * 10000,
                    LocalDateTime.now().minusDays(random.nextInt(365))
            ));
        }

        System.out.println("トランザクション数: " + transactions.size());

        // 効率的な保存（バッファリング）
        String filename = "transactions.dat";
        long startTime = System.currentTimeMillis();

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(filename), 8192))) {

            oos.writeObject(transactions);

        } catch (IOException e) {
            System.err.println("保存エラー: " + e.getMessage());
        }

        long saveTime = System.currentTimeMillis() - startTime;
        System.out.println("保存時間: " + saveTime + "ms");

        // ファイルサイズの確認
        File file = new File(filename);
        System.out.println("ファイルサイズ: " +
                (file.length() / 1024) + "KB");

        // 効率的な読み込み
        startTime = System.currentTimeMillis();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(filename), 8192))) {

            @SuppressWarnings("unchecked")
            List<Transaction> loadedTransactions =
                    (List<Transaction>) ois.readObject();

            System.out.println("読み込んだトランザクション数: " +
                    loadedTransactions.size());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("読み込みエラー: " + e.getMessage());
        }

        long loadTime = System.currentTimeMillis() - startTime;
        System.out.println("読み込み時間: " + loadTime + "ms");

        file.delete();

        System.out.println();
    }

    /**
     * プロパティファイルの使用
     */
    private static void demonstratePropertiesFile() {
        System.out.println("=== プロパティファイル ===");

        Properties props = new Properties();
        props.setProperty("app.name", "MyApplication");
        props.setProperty("app.version", "2.0.0");
        props.setProperty("database.host", "localhost");
        props.setProperty("database.port", "3306");
        props.setProperty("database.name", "mydb");
        props.setProperty("cache.enabled", "true");
        props.setProperty("cache.size", "1000");

        Path propsFile = Paths.get("application.properties");

        // プロパティの保存
        System.out.println("--- プロパティ保存 ---");
        try (Writer writer = Files.newBufferedWriter(propsFile)) {
            props.store(writer, "Application Configuration");
            System.out.println("プロパティファイルを保存しました");
        } catch (IOException e) {
            System.err.println("保存エラー: " + e.getMessage());
        }

        // プロパティの読み込み
        System.out.println("\n--- プロパティ読み込み ---");
        Properties loadedProps = new Properties();
        try (Reader reader = Files.newBufferedReader(propsFile)) {
            loadedProps.load(reader);

            System.out.println("読み込んだプロパティ:");
            loadedProps.forEach((key, value) ->
                    System.out.println("  " + key + " = " + value));

        } catch (IOException e) {
            System.err.println("読み込みエラー: " + e.getMessage());
        }

        // XML形式での保存
        Path xmlFile = Paths.get("application.xml");
        try (OutputStream os = Files.newOutputStream(xmlFile)) {
            props.storeToXML(os, "Application Configuration (XML)");
            System.out.println("\nXML形式でも保存しました");
        } catch (IOException e) {
            System.err.println("XML保存エラー: " + e.getMessage());
        }

        // クリーンアップ
        try {
            Files.deleteIfExists(propsFile);
            Files.deleteIfExists(xmlFile);
        } catch (IOException e) {
            // 無視
        }

        System.out.println();
    }

    /**
     * CSV形式でのデータ永続化
     */
    private static void demonstrateCSVPersistence() {
        System.out.println("=== CSV形式でのデータ保存 ===");

        // データの準備
        List<Employee> employees = Arrays.asList(
                new Employee(1, "山田太郎", "営業部", 400000),
                new Employee(2, "鈴木花子", "開発部", 500000),
                new Employee(3, "佐藤次郎", "人事部", 450000)
        );

        Path csvFile = Paths.get("employees.csv");

        // CSVへの書き込み
        System.out.println("--- CSV書き込み ---");
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(csvFile))) {

            // ヘッダー
            writer.println("ID,Name,Department,Salary");

            // データ
            for (Employee emp : employees) {
                writer.printf("%d,%s,%s,%d%n",
                        emp.getId(),
                        emp.getName(),
                        emp.getDepartment(),
                        emp.getSalary()
                );
            }

            System.out.println("CSVファイルを作成しました");

        } catch (IOException e) {
            System.err.println("CSV書き込みエラー: " + e.getMessage());
        }

        // CSVからの読み込み
        System.out.println("\n--- CSV読み込み ---");
        List<Employee> loadedEmployees = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 4) {
                    Employee emp = new Employee(
                            Integer.parseInt(fields[0]),
                            fields[1],
                            fields[2],
                            Integer.parseInt(fields[3])
                    );
                    loadedEmployees.add(emp);
                }
            }

            System.out.println("読み込んだ従業員データ:");
            loadedEmployees.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("CSV読み込みエラー: " + e.getMessage());
        }

        // クリーンアップ
        try {
            Files.deleteIfExists(csvFile);
        } catch (IOException e) {
            // 無視
        }

        System.out.println();
    }

    /**
     * JSON風の簡易実装（実際のJSONライブラリを使わない例）
     */
    private static void demonstrateSimpleJSON() {
        System.out.println("=== JSON風データ形式 ===");
        System.out.println("（注：実際のJSON処理にはJacksonやGsonなどのライブラリを使用）");

        // データオブジェクト
        Map<String, Object> jsonData = new LinkedHashMap<>();
        jsonData.put("name", "田中太郎");
        jsonData.put("age", 28);
        jsonData.put("email", "tanaka@example.com");
        jsonData.put("active", true);

        List<String> hobbies = Arrays.asList("読書", "映画", "プログラミング");
        jsonData.put("hobbies", hobbies);

        // 簡易JSON形式で保存
        Path jsonFile = Paths.get("data.json");
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(jsonFile))) {

            writer.println("{");
            int index = 0;
            for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                writer.print("  \"" + key + "\": ");

                if (value instanceof String) {
                    writer.print("\"" + value + "\"");
                } else if (value instanceof List) {
                    writer.print("[");
                    List<?> list = (List<?>) value;
                    for (int i = 0; i < list.size(); i++) {
                        writer.print("\"" + list.get(i) + "\"");
                        if (i < list.size() - 1) writer.print(", ");
                    }
                    writer.print("]");
                } else {
                    writer.print(value);
                }

                if (++index < jsonData.size()) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            writer.println("}");

            System.out.println("JSON風ファイルを作成しました");

            // ファイルの内容を表示
            System.out.println("\nファイル内容:");
            Files.lines(jsonFile).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("JSON書き込みエラー: " + e.getMessage());
        }

        // クリーンアップ
        try {
            Files.deleteIfExists(jsonFile);
        } catch (IOException e) {
            // 無視
        }

        System.out.println("\n実際のプロジェクトでは、JacksonやGsonを使用してください");

        System.out.println();
    }
}

// ===== シリアライズ可能なクラス =====

class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private transient String email;  // transientフィールドはシリアライズされない
    private List<String> skills;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.skills = new ArrayList<>();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, email='%s'}",
                name, age, email);
    }
}

class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private transient String password;  // パスワードは直接保存しない
    private String passwordHash;        // 代わりにハッシュを保存
    private String email;
    private Date lastLogin;
    private transient String sessionToken;  // セッション情報は保存しない

    public UserAccount(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.passwordHash = hashPassword(password);
        this.email = email;
    }

    // カスタムシリアライゼーション
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();  // デフォルトのシリアライゼーション
        // 追加の処理（必要に応じて）
        oos.writeObject(new Date());  // シリアライズ時刻を記録
    }

    // カスタムデシリアライゼーション
    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();  // デフォルトのデシリアライゼーション
        Date serializeTime = (Date) ois.readObject();
        // パスワードフィールドは復元されない（transient）
    }

    public void login() {
        this.lastLogin = new Date();
        this.sessionToken = UUID.randomUUID().toString();
    }

    public void updateLastActivity() {
        // アクティビティの更新
    }

    public boolean verifyPassword(String password) {
        return hashPassword(password).equals(passwordHash);
    }

    private String hashPassword(String password) {
        // 実際にはBCryptなどを使用すべき
        return "HASH:" + password.hashCode();
    }

    @Override
    public String toString() {
        return String.format("UserAccount{username='%s', email='%s', lastLogin=%s}",
                username, email, lastLogin);
    }
}

class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String id, double amount, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Transaction{id='%s', amount=%.2f, timestamp=%s}",
                id, amount, timestamp);
    }
}

class Employee {
    private int id;
    private String name;
    private String department;
    private int salary;

    public Employee(int id, String name, String department, int salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', department='%s', salary=%d}",
                id, name, department, salary);
    }
}
