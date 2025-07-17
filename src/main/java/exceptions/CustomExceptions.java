package exceptions;

import java.util.*;
import java.time.LocalDateTime;

/**
 * カスタム例外の作成とベストプラクティス
 */
public class CustomExceptions {
    public static void main(String[] args) {
        System.out.println("=== カスタム例外とベストプラクティス ===\n");

        // ビジネスロジック例外
        demonstrateBusinessExceptions();

        // バリデーション例外
        demonstrateValidationExceptions();

        // try-with-resources
        demonstrateTryWithResources();

        // 例外処理のベストプラクティス
        demonstrateBestPractices();
    }

    /**
     * ビジネスロジック例外のデモ
     */
    private static void demonstrateBusinessExceptions() {
        System.out.println("=== ビジネスロジック例外 ===");

        BankAccount account = new BankAccount("12345", 1000);

        try {
            System.out.println("初期残高: " + account.getBalance());

            // 正常な引き出し
            account.withdraw(500);
            System.out.println("500円引き出し後: " + account.getBalance());

            // 残高不足
            account.withdraw(600);

        } catch (InsufficientFundsException e) {
            System.out.println("エラー: " + e.getMessage());
            System.out.println("不足額: " + e.getShortfall() + "円");
        } catch (AccountException e) {
            System.out.println("口座エラー: " + e.getMessage());
        }

        // 口座凍結の例
        System.out.println("\n--- 口座凍結の例 ---");
        BankAccount frozenAccount = new BankAccount("67890", 5000);
        frozenAccount.freeze("不正な取引の疑い");

        try {
            frozenAccount.withdraw(100);
        } catch (AccountFrozenException e) {
            System.out.println("エラー: " + e.getMessage());
            System.out.println("凍結理由: " + e.getReason());
            System.out.println("凍結日時: " + e.getFrozenAt());
        } catch (AccountException e) {
            System.out.println("口座エラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * バリデーション例外のデモ
     */
    private static void demonstrateValidationExceptions() {
        System.out.println("=== バリデーション例外 ===");

        UserRegistration registration = new UserRegistration();

        // 無効なメールアドレス
        try {
            registration.registerUser("user", "invalid-email", "pass123");
        } catch (ValidationException e) {
            System.out.println("バリデーションエラー:");
            e.getErrors().forEach((field, error) ->
                    System.out.println("  " + field + ": " + error)
            );
        }

        // 複数のバリデーションエラー
        System.out.println("\n--- 複数のエラー ---");
        try {
            registration.registerUser("ab", "test@", "123");
        } catch (ValidationException e) {
            System.out.println("バリデーションエラー（" + e.getErrorCount() + "件）:");
            e.getErrors().forEach((field, error) ->
                    System.out.println("  " + field + ": " + error)
            );
        }

        // 成功例
        System.out.println("\n--- 成功例 ---");
        try {
            User user = registration.registerUser("testuser", "test@example.com", "SecurePass123");
            System.out.println("ユーザー登録成功: " + user);
        } catch (ValidationException e) {
            System.out.println("エラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * try-with-resourcesのデモ
     */
    private static void demonstrateTryWithResources() {
        System.out.println("=== try-with-resources ===");

        // 単一リソース
        System.out.println("--- 単一リソース ---");
        try (DatabaseConnection conn = new DatabaseConnection("localhost", "testdb")) {
            conn.executeQuery("SELECT * FROM users");
            System.out.println("クエリ実行成功");
        } catch (DatabaseException e) {
            System.out.println("データベースエラー: " + e.getMessage());
        }
        // 自動的にcloseが呼ばれる

        // 複数リソース
        System.out.println("\n--- 複数リソース ---");
        try (
                DatabaseConnection conn = new DatabaseConnection("localhost", "testdb");
                FileLogger logger = new FileLogger("app.log")
        ) {
            conn.executeQuery("INSERT INTO logs VALUES ('test')");
            logger.log("データ挿入完了");
            System.out.println("処理完了");
        } catch (Exception e) {
            System.out.println("エラー: " + e.getMessage());
        }

        // リソースの初期化に失敗した場合
        System.out.println("\n--- リソース初期化エラー ---");
        try (
                DatabaseConnection conn = new DatabaseConnection("invalid-host", "testdb")
        ) {
            conn.executeQuery("SELECT 1");
        } catch (DatabaseException e) {
            System.out.println("接続エラー: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * 例外処理のベストプラクティス
     */
    private static void demonstrateBestPractices() {
        System.out.println("=== 例外処理のベストプラクティス ===");

        // 1. 具体的な例外を使用
        System.out.println("1. 具体的な例外を使用:");
        System.out.println("   悪い例: throw new Exception(\"エラー\");");
        System.out.println("   良い例: throw new IllegalArgumentException(\"年齢は0以上である必要があります\");");

        // 2. 早期リターン
        System.out.println("\n2. 早期リターンで入れ子を減らす:");
        demonstrateEarlyReturn(null);
        demonstrateEarlyReturn("valid data");

        // 3. 例外の連鎖
        System.out.println("\n3. 例外の連鎖で原因を保持:");
        try {
            performComplexOperation();
        } catch (ServiceException e) {
            System.out.println("サービスエラー: " + e.getMessage());
            System.out.println("根本原因: " + getRootCause(e).getMessage());
        }

        // 4. リソース管理
        System.out.println("\n4. リソース管理:");
        System.out.println("   常にtry-with-resourcesを使用");
        System.out.println("   AutoCloseableを実装");

        // 5. ログ記録
        System.out.println("\n5. 適切なログ記録:");
        try {
            riskyOperation();
        } catch (Exception e) {
            // 実際のアプリケーションではロガーを使用
            System.err.println("[ERROR] " + LocalDateTime.now() +
                    " - 操作に失敗: " + e.getMessage());
            // スタックトレースは開発環境でのみ出力
            if (isDebugMode()) {
                e.printStackTrace();
            }
        }

        System.out.println();
    }

    // ===== ヘルパーメソッド =====

    private static void demonstrateEarlyReturn(String input) {
        try {
            if (input == null) {
                throw new IllegalArgumentException("入力がnullです");
            }
            // 正常処理
            System.out.println("   処理成功: " + input);
        } catch (IllegalArgumentException e) {
            System.out.println("   " + e.getMessage());
        }
    }

    private static void performComplexOperation() throws ServiceException {
        try {
            // 低レベルの操作
            connectToExternalService();
        } catch (ConnectionException e) {
            // 例外を高レベルの例外でラップ
            throw new ServiceException("外部サービスの操作に失敗しました", e);
        }
    }

    private static void connectToExternalService() throws ConnectionException {
        throw new ConnectionException("接続がタイムアウトしました");
    }

    private static Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

    private static void riskyOperation() throws Exception {
        throw new Exception("リスクのある操作が失敗しました");
    }

    private static boolean isDebugMode() {
        return System.getProperty("debug", "false").equals("true");
    }
}

// ===== カスタム例外クラス =====

// 基底例外クラス
abstract class AccountException extends Exception {
    private final String accountNumber;

    public AccountException(String message, String accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }

    public AccountException(String message, String accountNumber, Throwable cause) {
        super(message, cause);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

// 残高不足例外
class InsufficientFundsException extends AccountException {
    private final double shortfall;

    public InsufficientFundsException(String accountNumber, double shortfall) {
        super("残高不足: " + shortfall + "円不足しています", accountNumber);
        this.shortfall = shortfall;
    }

    public double getShortfall() {
        return shortfall;
    }
}

// 口座凍結例外
class AccountFrozenException extends AccountException {
    private final String reason;
    private final LocalDateTime frozenAt;

    public AccountFrozenException(String accountNumber, String reason, LocalDateTime frozenAt) {
        super("口座が凍結されています", accountNumber);
        this.reason = reason;
        this.frozenAt = frozenAt;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getFrozenAt() {
        return frozenAt;
    }
}

// バリデーション例外
class ValidationException extends Exception {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("バリデーションエラーが発生しました");
        this.errors = new HashMap<>(errors);
    }

    public Map<String, String> getErrors() {
        return new HashMap<>(errors);
    }

    public int getErrorCount() {
        return errors.size();
    }
}

// その他の例外
class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}

class ConnectionException extends Exception {
    public ConnectionException(String message) {
        super(message);
    }
}

class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

// ===== ビジネスロジッククラス =====

class BankAccount {
    private String accountNumber;
    private double balance;
    private boolean isFrozen;
    private String freezeReason;
    private LocalDateTime frozenAt;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isFrozen = false;
    }

    public void withdraw(double amount) throws AccountException {
        if (isFrozen) {
            throw new AccountFrozenException(accountNumber, freezeReason, frozenAt);
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("引き出し額は正の数である必要があります");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(accountNumber, amount - balance);
        }

        balance -= amount;
    }

    public void freeze(String reason) {
        this.isFrozen = true;
        this.freezeReason = reason;
        this.frozenAt = LocalDateTime.now();
    }

    public double getBalance() {
        return balance;
    }
}

class UserRegistration {
    public User registerUser(String username, String email, String password)
            throws ValidationException {
        Map<String, String> errors = new HashMap<>();

        // ユーザー名の検証
        if (username == null || username.length() < 3) {
            errors.put("username", "ユーザー名は3文字以上である必要があります");
        }

        // メールアドレスの検証
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "有効なメールアドレスを入力してください");
        }

        // パスワードの検証
        if (password == null || password.length() < 8) {
            errors.put("password", "パスワードは8文字以上である必要があります");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return new User(username, email);
    }
}

class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', email='" + email + "'}";
    }
}

// ===== AutoCloseableリソース =====

class DatabaseConnection implements AutoCloseable {
    private String host;
    private String database;
    private boolean isConnected;

    public DatabaseConnection(String host, String database) throws DatabaseException {
        this.host = host;
        this.database = database;

        if ("invalid-host".equals(host)) {
            throw new DatabaseException("ホスト '" + host + "' に接続できません");
        }

        this.isConnected = true;
        System.out.println("データベース接続: " + host + "/" + database);
    }

    public void executeQuery(String query) throws DatabaseException {
        if (!isConnected) {
            throw new DatabaseException("データベースに接続されていません");
        }
        System.out.println("クエリ実行: " + query);
    }

    @Override
    public void close() {
        if (isConnected) {
            System.out.println("データベース接続をクローズ");
            isConnected = false;
        }
    }
}

class FileLogger implements AutoCloseable {
    private String filename;
    private boolean isOpen;

    public FileLogger(String filename) {
        this.filename = filename;
        this.isOpen = true;
        System.out.println("ログファイルを開く: " + filename);
    }

    public void log(String message) {
        if (isOpen) {
            System.out.println("[LOG] " + message);
        }
    }

    @Override
    public void close() {
        if (isOpen) {
            System.out.println("ログファイルをクローズ: " + filename);
            isOpen = false;
        }
    }
}
