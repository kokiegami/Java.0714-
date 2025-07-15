package kadai0715;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        System.out.println("=== 図書管理システム ===\n");

        // 図書館の初期化
        Library library = new Library("中央図書館");

        // 書籍・雑誌の追加
        Book book1 = new Book("978-4-123456-78-9", "Java入門", "山田太郎", "プログラミング");
        Book book2 = new Book("978-4-123456-79-6", "Spring Framework", "鈴木花子", "プログラミング");
        Magazine magazine1 = new Magazine("123-456", "月刊プログラミング", "Tech出版", "技術", 202401);

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(magazine1);

        // 会員登録
        Member member1 = new RegularMember("M001", "佐藤次郎", "sato@example.com");
        Member member2 = new PremiumMember("P001", "田中三郎", "tanaka@example.com");

        library.registerMember(member1);
        library.registerMember(member2);

        // 貸出処理
        System.out.println("--- 貸出処理 ---");
        library.borrowItem(member1, book1);
        library.borrowItem(member2, book2);
        library.borrowItem(member2, magazine1);

        // 貸出状況の確認
        System.out.println("\n--- 貸出状況 ---");
        member1.showBorrowedItems();
        member2.showBorrowedItems();

        // 返却処理
        System.out.println("\n--- 返却処理 ---");
        library.returnItem(member1, book1);

        // 統計情報
        System.out.println("\n--- 図書館統計 ---");
        library.showStatistics();
    }
}
