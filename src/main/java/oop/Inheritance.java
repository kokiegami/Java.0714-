package oop;
/**
 * 継承の基本的な実装
 */
public class Inheritance {
    public static void main(String[] args) {
        System.out.println("=== 継承の基礎 ===\n");

        // 基底クラスのインスタンス
        Animal animal = new Animal("動物", 5);
        animal.eat();
        animal.sleep();
        animal.displayInfo();

        System.out.println("\n--- 犬クラス（継承） ---");
        Dog dog = new Dog("ポチ", 3, "柴犬");
        dog.eat();        // 継承されたメソッド
        dog.sleep();      // 継承されたメソッド
        dog.bark();       // 独自のメソッド
        dog.displayInfo(); // オーバーライドされたメソッド

        System.out.println("\n--- 猫クラス（継承） ---");
        Cat cat = new Cat("タマ", 2, "白");
        cat.eat();        // 継承されたメソッド
        cat.sleep();      // オーバーライドされたメソッド
        cat.meow();       // 独自のメソッド
        cat.displayInfo(); // オーバーライドされたメソッド

        // ポリモーフィズムの例
        System.out.println("\n--- ポリモーフィズム ---");
        Animal myPet = new Dog("マックス", 4, "ゴールデンレトリバー");
        myPet.eat();      // Dogのeatメソッドが呼ばれる
        myPet.sleep();    // Animalのsleepメソッドが呼ばれる
        // myPet.bark();  // コンパイルエラー（Animalクラスにbarkメソッドはない）
    }
}

// 基底クラス（親クラス、スーパークラス）
class Animal {
    // protectedフィールド（サブクラスからアクセス可能）
    protected String name;
    protected int age;

    // コンストラクタ
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Animalコンストラクタが呼ばれました");
    }

    // 基本的なメソッド
    public void eat() {
        System.out.println(name + "が食事をしています");
    }

    public void sleep() {
        System.out.println(name + "が眠っています");
    }

    // 情報表示メソッド
    public void displayInfo() {
        System.out.println("名前: " + name + ", 年齢: " + age + "歳");
    }
}

// 派生クラス（子クラス、サブクラス）
class Dog extends Animal {
    private String breed;  // 犬種

    // コンストラクタ
    public Dog(String name, int age, String breed) {
        super(name, age);  // 親クラスのコンストラクタを呼び出す
        this.breed = breed;
        System.out.println("Dogコンストラクタが呼ばれました");
    }

    // 独自のメソッド
    public void bark() {
        System.out.println(name + "がワンワン吠えています！");
    }

    // メソッドのオーバーライド
    @Override
    public void eat() {
        System.out.println(name + "がドッグフードを食べています");
    }

    // 情報表示メソッドのオーバーライド
    @Override
    public void displayInfo() {
        super.displayInfo();  // 親クラスのメソッドを呼び出す
        System.out.println("犬種: " + breed);
    }
}

// もう一つの派生クラス
class Cat extends Animal {
    private String color;  // 毛色

    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
        System.out.println("Catコンストラクタが呼ばれました");
    }

    // 独自のメソッド
    public void meow() {
        System.out.println(name + "がニャーニャー鳴いています！");
    }

    // メソッドのオーバーライド
    @Override
    public void sleep() {
        System.out.println(name + "が丸くなって眠っています");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("毛色: " + color);
    }
}
