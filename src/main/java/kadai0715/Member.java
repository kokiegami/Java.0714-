package kadai0715;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 会員の共通部分をまとめた抽象クラス。貸出可能インターフェースを実装。
public abstract class Member implements Borrowable {
    protected String memberId;                  // 会員ID
    protected String name;                      // 会員名
    protected String email;                     // メールアドレス
    protected List<BorrowRecord> borrowRecords; // 貸出履歴
    protected LocalDate registrationDate;       // 登録日

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowRecords = new ArrayList<>();
        this.registrationDate = LocalDate.now();
    }

    // 会員ごとに借りられる最大数を指定（継承先で実装）
    public abstract int getMaxBorrowCount();

    // 会員の種類を返す（継承先で実装）
    public abstract String getMemberType();

    @Override
    public boolean canBorrow(LibraryItem item) {
        // 現在借りている数をカウントし、上限と比較
        long count = borrowRecords.stream()
                .filter(r -> r.getReturnDate() == null)  // 返却していないもの
                .count();
        return count < getMaxBorrowCount();
    }

    @Override
    public void borrowItem(LibraryItem item) {
        if (!canBorrow(item)) {
            System.out.println("貸出上限に達しています");
            return;
        }
        // 新しい貸出記録を作成し、アイテムの貸出処理を呼ぶ
        BorrowRecord record = new BorrowRecord(item, this);
        borrowRecords.add(record);
        item.borrowItem();
        System.out.println(name + "が「" + item.getTitle() + "」を借りました");
    }

    @Override
    public void returnItem(LibraryItem item) {
        // 貸出履歴から該当の貸出記録を探す
        for (BorrowRecord record : borrowRecords) {
            if (record.getItem().equals(item) && record.getReturnDate() == null) {
                record.returnItem();      // 返却日を記録
                item.returnItem();        // アイテムの返却処理
                System.out.println(name + "が「" + item.getTitle() + "」を返却しました");
                return;
            }
        }
        System.out.println("該当する貸出記録が見つかりません");
    }

    @Override
    public void showBorrowedItems() {
        System.out.println("\n" + name + "の貸出状況:");
        boolean hasItems = false;
        for (BorrowRecord record : borrowRecords) {
            if (record.getReturnDate() == null) {
                System.out.println("- " + record.getItem().getTitle() +
                        " (返却期限: " + record.getDueDate() + ")");
                hasItems = true;
            }
        }
        if (!hasItems) {
            System.out.println("現在借りている本はありません");
        }
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
}
