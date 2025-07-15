package oop;

import java.time.LocalDate;

/**
 * コンストラクタの様々な使い方
 */
public class Constructors {
    public static void main(String[] args) {
        System.out.println("=== コンストラクタの活用 ===\n");

        // デフォルトコンストラクタ
        Book book1 = new Book();
        System.out.println("Book1: " + book1);

        // 引数付きコンストラクタ
        Book book2 = new Book("Java入門", "山田太郎");
        System.out.println("Book2: " + book2);

        // すべての引数を持つコンストラクタ
        Book book3 = new Book("978-4-123456-78-9", "Spring Framework入門", "鈴木花子", 3500);
        System.out.println("Book3: " + book3);

        // コンストラクタチェーン
        System.out.println("\n--- コンストラクタチェーンの例 ---");
        Car car1 = new Car("Toyota");
        Car car2 = new Car("Honda", "Civic");
        Car car3 = new Car("Nissan", "Skyline", 2024);

        // thisの使用例
        System.out.println("\n--- thisキーワードの使用 ---");
        Person person = new Person("佐藤", "次郎", 25);
        person.printInfo();
        person.updateName("佐藤", "三郎");
        person.printInfo();
    }
}

// Bookクラス：複数のコンストラクタ
class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;
    private LocalDate publishDate;

    // デフォルトコンストラクタ
    public Book() {
        this("未設定", "未設定", "未設定", 0);
        System.out.println("デフォルトコンストラクタが呼ばれました");
    }

    // 部分的なコンストラクタ
    public Book(String title, String author) {
        this("", title, author, 0);
        System.out.println("2引数コンストラクタが呼ばれました");
    }

    // フルコンストラクタ
    public Book(String isbn, String title, String author, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishDate = LocalDate.now();
        System.out.println("フルコンストラクタが呼ばれました");
    }

    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, タイトル=%s, 著者=%s, 価格=%.0f円]",
                isbn, title, author, price);
    }
}

// Carクラス：コンストラクタチェーン
class Car {
    private String brand;
    private String model;
    private int year;

    // コンストラクタ1
    public Car(String brand) {
        this(brand, "Unknown", 2024);  // 他のコンストラクタを呼び出し
    }

    // コンストラクタ2
    public Car(String brand, String model) {
        this(brand, model, 2024);  // 他のコンストラクタを呼び出し
    }

    // コンストラクタ3（メイン）
    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        System.out.println("Car created: " + this);
    }

    @Override
    public String toString() {
        return String.format("%d %s %s", year, brand, model);
    }
}

// Personクラス：thisの様々な使い方
class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;  // this.フィールド = パラメータ
        this.lastName = lastName;
        this.age = age;
    }

    // thisを使ってメソッドを呼び出す
    public void printInfo() {
        System.out.println(this.getFullName() + " (" + this.age + "歳)");
    }

    // thisを使って現在のオブジェクトを返す
    public Person updateName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        return this;  // メソッドチェーンを可能にする
    }

    // thisを使って他のメソッドを呼び出す
    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }
}

