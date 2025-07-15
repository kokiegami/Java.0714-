package kadai0715;

// 「借りる」「返す」「借りているものを表示する」機能を持つことを示すインターフェース
public interface Borrowable {
    boolean canBorrow(LibraryItem item);       // 借りられるかどうか判定
    void borrowItem(LibraryItem item);         // 借りる処理
    void returnItem(LibraryItem item);         // 返す処理
    void showBorrowedItems();                   // 借りているアイテム一覧表示
}
