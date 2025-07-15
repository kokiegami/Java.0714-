package kadai0715;

import java.time.LocalDate;

// いつ誰が何を借りて、いつ返したかを記録するクラス
public class BorrowRecord {
    private LibraryItem item;      // 借りた図書アイテム
    private Member member;         // 借りた会員
    private LocalDate borrowDate;  // 借りた日
    private LocalDate dueDate;     // 返却期限
    private LocalDate returnDate;  // 返した日。nullなら未返却

    public BorrowRecord(LibraryItem item, Member member) {
        this.item = item;
        this.member = member;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(item.getBorrowDays());
        this.returnDate = null;  // 最初は返却していない
    }

    // 返却時に返却日を記録
    public void returnItem() {
        this.returnDate = LocalDate.now();
    }

    public LibraryItem getItem() { return item; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
}
