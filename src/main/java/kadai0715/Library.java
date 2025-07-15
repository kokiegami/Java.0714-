package kadai0715;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;                    // 図書館名
    private List<LibraryItem> items;        // 所蔵アイテムのリスト
    private List<Member> members;           // 会員リスト
    private List<BorrowRecord> allBorrowRecords; // 全貸出履歴（今回は未使用ですが拡張用）

    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.members = new ArrayList<>();
        this.allBorrowRecords = new ArrayList<>();
    }

    // 図書館にアイテムを追加
    public void addItem(LibraryItem item) {
        items.add(item);
        System.out.println("「" + item.getTitle() + "」を図書館に追加しました");
    }

    // 会員を登録
    public void registerMember(Member member) {
        members.add(member);
        System.out.println(member.getName() + "を会員登録しました（" +
                member.getMemberType() + "）");
    }

    // 会員がアイテムを借りる処理
    public void borrowItem(Member member, LibraryItem item) {
        // 図書館にアイテムがあるか確認
        if (!items.contains(item)) {
            System.out.println("指定されたアイテムは図書館にありません");
            return;
        }
        // アイテムが貸出可能か確認
        if (!item.isAvailable()) {
            System.out.println("「" + item.getTitle() + "」は貸出中です");
            return;
        }
        // 会員に貸出処理を委譲
        member.borrowItem(item);
    }

    // 会員がアイテムを返す処理
    public void returnItem(Member member, LibraryItem item) {
        member.returnItem(item);
    }

    // 図書館の統計情報表示
    public void showStatistics() {
        System.out.println("図書館名: " + name);
        System.out.println("蔵書数: " + items.size());
        System.out.println("会員数: " + members.size());

        long borrowedCount = items.stream()
                .filter(item -> !item.isAvailable())
                .count();
        System.out.println("貸出中: " + borrowedCount + "点");
        System.out.println("貸出可能: " + (items.size() - borrowedCount) + "点");
    }
}
