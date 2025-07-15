package kadai0715;

// LibraryItemを継承して「雑誌」の特徴を持つクラス
public class Magazine extends LibraryItem {
    private int issueNumber;  // 号数

    public Magazine(String id, String title, String publisher, String category, int issueNumber) {
        super(id, title, publisher, category);
        this.issueNumber = issueNumber;
    }

    @Override
    public String getItemType() {
        return "雑誌";
    }

    @Override
    public int getBorrowDays() {
        return 7;    // 雑誌は1週間貸出可能
    }

    public int getIssueNumber() { return issueNumber; }
}
