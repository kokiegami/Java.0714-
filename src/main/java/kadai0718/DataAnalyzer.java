package kadai0718;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// メインのデータ分析クラス
public class DataAnalyzer {

    /**
     * CSVの1行を解析し、SaleRecordオブジェクトに変換するメソッド。
     * 解析に失敗した場合はOptional.empty()を返すことで、nullを返さないようにします (null安全性)。
     *
     * @param line CSVファイルの1行の文字列
     * @return 解析成功時はSaleRecordをラップしたOptional、失敗時は空のOptional
     */
    public static Optional<SaleRecord> parseSaleRecord(String line) {
        // カンマで文字列を分割し、各フィールドの配列を得る
        String[] parts = line.split(",");
        // フィールド数が期待通りでない場合は解析失敗とみなす
        if (parts.length != 5) {
            System.err.println("WARN: 無効な行フォーマットのためスキップ: " + line);
            // Optional型を使い、空のOptionalを返す
            return Optional.empty();
        }
        try {
            // 各フィールドの値を適切な型に変換する
            String productName = parts[0];
            String category = parts[1];
            // 文字列を整数に変換
            int quantity = Integer.parseInt(parts[2]);
            // 文字列を整数に変換
            int unitPrice = Integer.parseInt(parts[3]);
            // 文字列をLocalDateTimeに変換 (Date and Time APIを使用)
            LocalDateTime saleDateTime = LocalDateTime.parse(parts[4]);

            // 正常に解析できた場合は、SaleRecordオブジェクトをOptionalでラップして返す
            return Optional.of(new SaleRecord(productName, category, quantity, unitPrice, saleDateTime));
        } catch (NumberFormatException e) {
            // 数値変換エラーが発生した場合
            System.err.println("WARN: 数値変換エラーのため行をスキップ (" + line + "): " + e.getMessage());
            return Optional.empty();
        } catch (DateTimeParseException e) {
            // 日付時刻の解析エラーが発生した場合
            System.err.println("WARN: 日付時刻解析エラーのため行をスキップ (" + line + "): " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            // その他の予期せぬエラーが発生した場合
            System.err.println("ERROR: 行の解析中に予期せぬエラーが発生しました (" + line + "): " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 指定されたファイルパスからCSVデータを読み込み、SaleRecordのリストとして返すメソッド。
     *
     * @param filePath 読み込むCSVファイルのパス
     * @return SaleRecordオブジェクトのリスト
     */
    public static List<SaleRecord> loadData(String filePath) {
        // データを格納するリスト
        List<SaleRecord> records = new ArrayList<>();
        // try-with-resources文を使用し、BufferedReaderが自動的に閉じられるようにする
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            // ヘッダー行をスキップする場合（CSVの1行目がヘッダーなら）
            br.readLine();

            // ファイルの最後まで1行ずつ読み込む
            while ((line = br.readLine()) != null) {
                // 各行を解析し、Optional.ifPresentを使って、解析成功した場合のみリストに追加
                parseSaleRecord(line).ifPresent(records::add);
            }
        } catch (IOException e) {
            // ファイルの読み込み中にエラーが発生した場合
            System.err.println("ERROR: ファイルの読み込み中にエラーが発生しました: " + e.getMessage());
            e.printStackTrace();
        }
        return records;
    }

    // メインメソッド: プログラムのエントリポイント
    public static void main(String[] args) {
        // CSVファイルのパスを指定 (sales_data.csvがプロジェクトのルートディレクトリにあることを想定)
        String csvFilePath = "sales_data.csv";
        // データを読み込む
        List<SaleRecord> sales = loadData(csvFilePath);

        // --- 全データの表示 ---
        System.out.println("--- 全売上データ ---");
        // forEachとメソッド参照を使って、リストの全要素を表示
        sales.forEach(System.out::println);
        System.out.println("\n--------------------\n");

        // --- Stream APIを使った分析例 ---

        //カテゴリ別の総売上を計算
        System.out.println("--- カテゴリ別総売上 ---");
        Map<String, Integer> categorySales = sales.stream() // Streamを生成
                .collect(Collectors.groupingBy(
                        SaleRecord::getCategory,
                        // 各カテゴリの総売上（個数×単価）を計算 (ラムダ式)
                        Collectors.summingInt(s -> s.getQuantity() * s.getUnitPrice())
                ));
        categorySales.forEach((category, total) -> System.out.println(category + ": " + total + "円")); // 結果を表示
        System.out.println("\n--------------------\n");

        //2024年7月2日以降の売上を抽出し、販売日時が新しい順に表示
        System.out.println("--- 2024年7月2日以降の売上 (新しい順) ---");
        LocalDateTime cutoffDate = LocalDateTime.of(2024, 7, 2, 0, 0, 0); // 基準日時を設定 (Date and Time API)
        List<SaleRecord> salesAfterCutoff = sales.stream() // Streamを生成
                .filter(s -> s.getSaleDateTime().isAfter(cutoffDate) || s.getSaleDateTime().isEqual(cutoffDate)) // フィルタリング (ラムダ式, Date and Time API)
                .sorted(Comparator.comparing(SaleRecord::getSaleDateTime).reversed()) // 販売日時で降順にソート (メソッド参照, ラムダ式, Comparator)
                .collect(Collectors.toList()); // 結果をリストに収集
        salesAfterCutoff.forEach(System.out::println); // 結果を表示
        System.out.println("\n--------------------\n");

        //最も売上個数が多い商品名をOptional型で安全に取得
        System.out.println("--- 最も売れた商品 ---");
        Optional<SaleRecord> topSellingProduct = sales.stream() // Streamを生成
                .max(Comparator.comparingInt(SaleRecord::getQuantity)); // 個数で最大値を見つける (ラムダ式, Comparator)

        // Optional.ifPresentOrElseを使って、結果が存在する場合としない場合で異なる処理を行う (Optional型)
        // もしOptionalが値を含んでいたら
        topSellingProduct.ifPresentOrElse(
                // その値を使って表示
                s -> System.out.println("最も売れた商品: " + s.getProductName() + " (" + s.getQuantity() + "個)"),
                // 値が空（データがなかった）場合
                () -> System.out.println("データがありませんでした。")
        );
        System.out.println("\n--------------------\n");

        //特定のカテゴリ（例: "飲料"）の売上のみを抽出し、商品名と合計売上額を表示
        System.out.println("--- 飲料カテゴリの売上詳細 ---");
        sales.stream()
                // カテゴリが"飲料"のものをフィルタリング
                .filter(s -> "飲料".equals(s.getCategory()))
                // 商品名と売上額の文字列に変換 (ラムダ式)
                .map(s -> s.getProductName() + ": " + (s.getQuantity() * s.getUnitPrice()) + "円")
                // 重複する結果があれば排除
                .distinct()
                .forEach(System.out::println);
        System.out.println("\n--------------------\n");
    }
}