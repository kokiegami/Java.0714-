public class BasicClass {
    public static void main(String[] args) {
        System.out.println("=== クラスの基礎 ===\n");

        // オブジェクトの作成（インスタンス化）
        //Personクラスのインスタンスを生成
        Person person1 = new Person();
        person1.name = "山田太郎";
        person1.age = 25;

        Person person2 = new Person();
        person2.name = "佐藤花子";
        person2.age = 30;

        // introduce()メソッドの呼び出し
        person1.introduce();
        person2.introduce();

        // コンストラクタを使った初期化
        Person person3 = new Person("鈴木一郎", 28);
        person3.introduce();

        // メソッドの使用
        person1.birthday();
        person1.introduce();

        // 静的メソッドの呼び出し
        int total = Person.getTotalCount();
        System.out.println("\n総人数: " + total);

        // Calculatorクラスの使用
        Calculator calc = new Calculator();
        System.out.println("\n電卓の使用:");
        System.out.println("10 + 5 = " + calc.add(10, 5));
        System.out.println("10 - 5 = " + calc.subtract(10, 5));
        System.out.println("10 * 5 = " + calc.multiply(10, 5));
        System.out.println("10 / 5 = " + calc.divide(10, 5));
    }
}

// Personクラスの定義
class Person {
    // インスタンス変数（フィールド）
    String name;
    int age;

    // クラス変数（static）
    private static int totalCount = 0;

    // デフォルトコンストラクタ
    public Person() {
        totalCount++;
    }

    // 引数付きコンストラクタ
    public Person(String name, int age) {
        this.name = name;  // thisは現在のインスタンスを指す
        this.age = age;
        totalCount++;
    }

    // 自己紹介の出力 インスタンスメソッド
    public void introduce() {
        System.out.println("私は" + name + "です。" + age + "歳です。");
    }
    //年齢を1つ増やす
    public void birthday() {
        age++;
        System.out.println(name + "さんの誕生日！" + age + "歳になりました。");
    }

    // 現在までに作られた人数を返す 静的メソッド
    public static int getTotalCount() {
        return totalCount;
    }
}

// Calculatorクラス
class Calculator {
    // 四則演算のメソッド
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            System.out.println("エラー: ゼロで除算はできません");
            return 0;
        }
        return (double) a / b;
    }
}
