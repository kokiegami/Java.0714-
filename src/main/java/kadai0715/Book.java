package kadai0715;

// LibraryItemを継承して「書籍」の特徴を持つクラス
public class Book extends LibraryItem {
    private String author;   // 著者
    private String isbn;     // ISBN番号

    // コンストラクタ（親クラスのコンストラクタを呼び出しつつ初期化）
    public Book(String isbn, String title, String author, String category) {
        super(isbn, title, "Unknown", category);  // 出版社はUnknownで初期化
        this.isbn = isbn;
        this.author = author;
    }

    @Override
    public String getItemType() {
        return "書籍";
    }

    @Override
    public int getBorrowDays() {
        return 14;    // 書籍は2週間貸出可能
    }

    public String getAuthor() { return author; }
}
