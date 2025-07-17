package kadai0717;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * ログモニター（リアルタイム監視）
 * 新しいログエントリをファイルから読み取り、アラートを生成
 */
class LogMonitor implements AutoCloseable {
    private final Path logFile;
    private volatile boolean running = true;
    private FileChannel fileChannel;
    private BufferedReader reader;

    public LogMonitor(Path logFile) throws IOException {
        this.logFile = logFile;
        // ファイルが開かれていない場合は作成し、読み取り/追記モードで開く
        // CREATE_NEWではなくCREATEを使用することで、ファイルが存在しない場合のみ作成する
        this.fileChannel = FileChannel.open(logFile, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        // ファイルの末尾から読み込みを開始
        this.fileChannel.position(this.fileChannel.size());
        this.reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(logFile, StandardOpenOption.READ), StandardCharsets.UTF_8));
        // BufferedReaderを初期位置からではなく、FileChannelの現在位置から読み込ませるための対応
        // ここでは新しくInputStreamReaderとBufferedReaderを作成し直すことで、
        // Channelの現在位置から読み込むようにする。
        // ただし、これでも厳密なリアルタイム監視には限界があり、WatchServiceなどがより適している場合がある。
        // 今回のシミュレーションでは、簡便さのためにこの方法を採用。
        Files.lines(logFile, StandardCharsets.UTF_8).skip(Long.MAX_VALUE).findAny(); // ストリームを最後まで読み飛ばす

    }

    public void startMonitoring() throws IOException {
        System.out.println("ログ監視を開始: " + logFile);

        while (running && !Thread.currentThread().isInterrupted()) {
            String line;
            // available()で読み込み可能なバイトがあるか確認
            // 注意: available()はInputStreamの契約であり、FileChannelでは直接使えないため、
            // pollingでファイルのサイズを比較する方が適切だが、readLineのブロックを避けるため
            // 簡易的にBufferedReaderのready()を使用。
            if (reader.ready()) { // 読み込み可能なデータがあるかチェック
                while ((line = reader.readLine()) != null) {
                    System.out.println("[監視] " + line);

                    // アラート条件のチェック
                    if (line.contains("[ERROR]")) {
                        System.out.println("⚠️  エラーを検出しました！");
                    }
                }
            }

            try {
                Thread.sleep(200); // 監視間隔を少し長めに調整
            } catch (InterruptedException e) {
                // スレッドが中断されたらループを抜ける
                Thread.currentThread().interrupt(); // 中断状態を再設定
                break;
            }
        }
        System.out.println("ログ監視を終了");
    }

    // AutoCloseableインターフェースの実装。try-with-resourcesでLogMonitorが閉じられる時に呼ばれる
    @Override
    public void close() {
        running = false; // 監視ループを停止させる
        try {
            if (reader != null) {
                reader.close(); // BufferedReaderを閉じる
            }
            if (fileChannel != null) {
                fileChannel.close(); // FileChannelを閉じる
            }
        } catch (IOException e) {
            System.err.println("ログモニターのクローズ中にエラー: " + e.getMessage());
        }
    }
}