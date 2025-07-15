package oop;

/**
 * カプセル化の実装例
 * データ（フィールド）を隠蔽し、メソッドを通じてアクセスする
 */
public class Encapsulation {
    public static void main(String[] args) {
        System.out.println("=== カプセル化の実装 ===\n");

        // 悪い例：フィールドに直接アクセス
        BadBankAccount badAccount = new BadBankAccount();
        badAccount.balance = 1000;  // 直接アクセス可能（危険）
        badAccount.balance = -500;  // 不正な値も設定可能
        System.out.println("悪い例の残高: " + badAccount.balance);

        // 良い例：カプセル化されたクラス
        System.out.println("\n--- カプセル化された銀行口座 ---");
        BankAccount goodAccount = new BankAccount("12345", "山田太郎", 1000);

        // 残高の確認（getterを使用）
        System.out.println("残高: " + goodAccount.getBalance() + "円");

        // 入金
        goodAccount.deposit(5000);

        // 出金
        goodAccount.withdraw(2000);

        // 不正な操作は防がれる
        goodAccount.deposit(-1000);  // エラーメッセージが表示される
        goodAccount.withdraw(10000); // 残高不足

        // 取引履歴の表示
        goodAccount.printTransactionHistory();
    }
}

// 悪い例：カプセル化されていないクラス
class BadBankAccount {
    public double balance;  // publicフィールド（誰でもアクセス可能）
}

// 良い例：カプセル化されたクラス
class BankAccount {
    // privateフィールド（外部から直接アクセス不可）
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private java.util.List<String> transactionHistory;

    // コンストラクタ
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.transactionHistory = new java.util.ArrayList<>();

        // 初期残高の検証
        if (initialBalance >= 0) {
            this.balance = initialBalance;
            addTransaction("口座開設", initialBalance);
        } else {
            this.balance = 0;
            addTransaction("口座開設", 0);
        }
    }

    // Getter（読み取り専用アクセス）
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // ビジネスロジックを含むメソッド
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("エラー: 入金額は正の数である必要があります");
            return;
        }

        balance += amount;
        addTransaction("入金", amount);
        System.out.println(amount + "円を入金しました。残高: " + balance + "円");
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("エラー: 出金額は正の数である必要があります");
            return false;
        }

        if (amount > balance) {
            System.out.println("エラー: 残高不足です（残高: " + balance + "円）");
            return false;
        }

        balance -= amount;
        addTransaction("出金", -amount);
        System.out.println(amount + "円を出金しました。残高: " + balance + "円");
        return true;
    }

    // プライベートメソッド（内部処理用）
    private void addTransaction(String type, double amount) {
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date());
        transactionHistory.add(timestamp + " - " + type + ": " + amount + "円");
    }

    // 取引履歴の表示
    public void printTransactionHistory() {
        System.out.println("\n=== 取引履歴 ===");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

