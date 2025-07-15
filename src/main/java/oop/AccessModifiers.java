package oop;

/**
 * Javaの4つのアクセス修飾子
 * private, default(package-private), protected, public
 */
public class AccessModifiers {
    public static void main(String[] args) {
        System.out.println("=== アクセス修飾子の理解 ===\n");

        Employee emp = new Employee("E001", "山田太郎", 30, 300000);

        // publicメソッド：どこからでもアクセス可能
        emp.displayPublicInfo();

        // protectedとdefaultは同じパッケージからアクセス可能
        emp.displayInternalInfo();
        emp.updateAge(31);

        // privateメソッドは外部からアクセス不可
        // emp.calculateBonus(); // コンパイルエラー

        // publicメソッドを通じて内部処理を実行
        double salary = emp.getAnnualSalary();
        System.out.println("年収: " + salary + "円");
    }
}

class Employee {
    // private: クラス内からのみアクセス可能
    private String employeeId;
    private String name;
    private int age;
    private double monthlySalary;

    // public: どこからでもアクセス可能
    public Employee(String employeeId, String name, int age, double monthlySalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.monthlySalary = monthlySalary;
    }

    // public メソッド
    public void displayPublicInfo() {
        System.out.println("=== 公開情報 ===");
        System.out.println("社員名: " + name);
        System.out.println("年齢: " + age + "歳");
    }

    // protected メソッド（同じパッケージまたはサブクラスからアクセス可能）
    protected void displayInternalInfo() {
        System.out.println("\n=== 内部情報 ===");
        System.out.println("社員ID: " + employeeId);
        System.out.println("月給: " + monthlySalary + "円");
    }

    // default（package-private）メソッド（同じパッケージからアクセス可能）
    void updateAge(int newAge) {
        if (newAge > age) {
            this.age = newAge;
            System.out.println("\n年齢を更新しました: " + age + "歳");
        }
    }

    // private メソッド（クラス内からのみアクセス可能）
    private double calculateBonus() {
        return monthlySalary * 2; // ボーナスは月給の2ヶ月分
    }

    // publicメソッドから privateメソッドを呼び出す
    public double getAnnualSalary() {
        double annualBase = monthlySalary * 12;
        double bonus = calculateBonus();
        return annualBase + bonus;
    }

    // Getter/Setter（必要なものだけ公開）
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Setterには検証ロジックを含める
    public void setAge(int age) {
        if (age > 0 && age < 100) {
            this.age = age;
        } else {
            System.out.println("エラー: 年齢は1〜99の範囲で設定してください");
        }
    }

    // 給与情報は読み取り専用（Setterを提供しない）
    public double getMonthlySalary() {
        return monthlySalary;
    }
}

