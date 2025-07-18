package kadai0718;

import java.time.LocalDateTime;

// 売上データを表現するクラス (POJO: Plain Old Java Object)
public class SaleRecord {
    private String productName;         // 商品名
    private String category;            // カテゴリ
    private int quantity;               // 売上個数
    private int unitPrice;              // 単価
    private LocalDateTime saleDateTime; // 販売日時

    // コンストラクタ: SaleRecordオブジェクトを初期化する
    public SaleRecord(String productName, String category, int quantity, int unitPrice, LocalDateTime saleDateTime) {
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.saleDateTime = saleDateTime;
    }

    // 各フィールドのゲッターメソッド
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public int getUnitPrice() { return unitPrice; }
    public LocalDateTime getSaleDateTime() { return saleDateTime; }

    // オブジェクトの内容を文字列として表現するためのメソッド (デバッグや表示用)
    @Override
    public String toString() {
        return "SaleRecord{" +
                "商品名='" + productName + '\'' +
                ", カテゴリ='" + category + '\'' +
                ", 個数=" + quantity +
                ", 単価=" + unitPrice +
                ", 販売日時=" + saleDateTime +
                '}';
    }
}