package kadai0715;

import java.time.LocalDate;

// 図書館にある「本」「雑誌」などの共通の性質をまとめた抽象クラス
public abstract class LibraryItem {
    protected String id;              // ISBNや雑誌番号など識別子
    protected String title;           // タイトル
    protected String publisher;       // 出版社
    protected String category;        // カテゴリ（例: プログラミング、技術など）
    protected boolean isAvailable;    // 貸出可能かどうか
    protected LocalDate addedDate;    // 図書館に追加した日

    // コンストラクタ（初期化）
    public LibraryItem(String id, String title, String publisher, String category) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.category = category;
        this.isAvailable = true;               // 最初は貸出可能とする
        this.addedDate = LocalDate.now();     // 登録日は今日の日付
    }

    // 具体的な種類ごとに定義する必要があるメソッド（abstractなので必ずオーバーライド）
    public abstract String getItemType();     // 「書籍」「雑誌」などの種類名
    public abstract int getBorrowDays();      // 貸出可能な日数

    // アイテムを借りるときの処理
    public void borrowItem() {
        if (!isAvailable) {
            throw new IllegalStateException("このアイテムは貸出中です");
        }
        isAvailable = false;    // 貸出中に設定
    }

    // アイテムを返却するときの処理
    public void returnItem() {
        isAvailable = true;     // 貸出可能に戻す
    }

    // ゲッター（外部から情報を取得できるように）
    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }

    // アイテムの情報を見やすく文字列で返す
    @Override
    public String toString() {
        return String.format("[%s] %s - %s",
                getItemType(),
                title,
                isAvailable ? "貸出可能" : "貸出中");
    }
}
