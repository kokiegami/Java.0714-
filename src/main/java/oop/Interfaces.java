package oop;

/**
 * インターフェースの実装例
 */
public class Interfaces {
    public static void main(String[] args) {
        System.out.println("=== インターフェースの実装 ===\n");

        // スマートフォンの例
        SmartPhone phone = new SmartPhone("iPhone 15");
        phone.makeCall("090-1234-5678");
        phone.browseWeb("www.example.com");
        phone.takePhoto();
        phone.playMusic("お気に入りの曲");

        System.out.println("\n--- 充電機能 ---");
        chargeDevice(phone);

        // ノートパソコンの例
        System.out.println("\n=== ノートパソコン ===");
        Laptop laptop = new Laptop("MacBook Pro");
        laptop.browseWeb("www.google.com");
        laptop.writeDocument("report.docx");
        chargeDevice(laptop);

        // 多重実装の例
        System.out.println("\n=== 水陸両用車 ===");
        AmphibiousVehicle vehicle = new AmphibiousVehicle();
        vehicle.drive();
        vehicle.sail();
        vehicle.honk();
    }

    // インターフェース型を引数に取るメソッド
    public static void chargeDevice(Chargeable device) {
        System.out.println("充電を開始します...");
        device.charge();
    }
}

// インターフェース定義
interface Phone {
    // インターフェースの定数（public static final が自動的に付く）
    int MAX_VOLUME = 100;

    // 抽象メソッド（public abstract が自動的に付く）
    void makeCall(String number);
    void receiveCall(String number);

    // デフォルトメソッド（Java 8以降）
    default void sendSMS(String number, String message) {
        System.out.println(number + "にSMSを送信: " + message);
    }

    // 静的メソッド（Java 8以降）
    static void showPhoneInfo() {
        System.out.println("これは電話インターフェースです");
    }
}

interface WebBrowser {
    void browseWeb(String url);
    void bookmarkPage(String url);

    default void clearHistory() {
        System.out.println("閲覧履歴をクリアしました");
    }
}

interface Camera {
    void takePhoto();
    void recordVideo();

    default void applyFilter(String filterName) {
        System.out.println(filterName + "フィルターを適用しました");
    }
}

interface MusicPlayer {
    void playMusic(String song);
    void pauseMusic();
    void stopMusic();
}

interface Chargeable {
    void charge();

    default void checkBatteryLevel() {
        System.out.println("バッテリー残量を確認中...");
    }
}

// 複数のインターフェースを実装
class SmartPhone implements Phone, WebBrowser, Camera, MusicPlayer, Chargeable {
    private String model;

    public SmartPhone(String model) {
        this.model = model;
    }

    // Phone インターフェースの実装
    @Override
    public void makeCall(String number) {
        System.out.println(model + "で" + number + "に電話をかけています");
    }

    @Override
    public void receiveCall(String number) {
        System.out.println(number + "から着信があります");
    }

    // WebBrowser インターフェースの実装
    @Override
    public void browseWeb(String url) {
        System.out.println(url + "を閲覧しています");
    }

    @Override
    public void bookmarkPage(String url) {
        System.out.println(url + "をブックマークしました");
    }

    // Camera インターフェースの実装
    @Override
    public void takePhoto() {
        System.out.println("写真を撮影しました");
    }

    @Override
    public void recordVideo() {
        System.out.println("動画を録画しています");
    }

    // MusicPlayer インターフェースの実装
    @Override
    public void playMusic(String song) {
        System.out.println(song + "を再生しています");
    }

    @Override
    public void pauseMusic() {
        System.out.println("音楽を一時停止しました");
    }

    @Override
    public void stopMusic() {
        System.out.println("音楽を停止しました");
    }

    // Chargeable インターフェースの実装
    @Override
    public void charge() {
        System.out.println(model + "を充電しています");
    }
}

// 別の実装例
class Laptop implements WebBrowser, Chargeable {
    private String model;

    public Laptop(String model) {
        this.model = model;
    }

    @Override
    public void browseWeb(String url) {
        System.out.println("ノートパソコンで" + url + "を表示しています");
    }

    @Override
    public void bookmarkPage(String url) {
        System.out.println(url + "をお気に入りに追加しました");
    }

    @Override
    public void charge() {
        System.out.println(model + "のバッテリーを充電中（急速充電対応）");
    }

    // Laptop特有のメソッド
    public void writeDocument(String filename) {
        System.out.println(filename + "を作成しています");
    }
}

// 多重実装の例
interface Drivable {
    void drive();
    void brake();
}

interface Sailable {
    void sail();
    void anchor();
}

interface Honkable {
    void honk();
}

class AmphibiousVehicle implements Drivable, Sailable, Honkable {
    @Override
    public void drive() {
        System.out.println("陸上を走行しています");
    }

    @Override
    public void brake() {
        System.out.println("ブレーキをかけました");
    }

    @Override
    public void sail() {
        System.out.println("水上を航行しています");
    }

    @Override
    public void anchor() {
        System.out.println("錨を下ろしました");
    }

    @Override
    public void honk() {
        System.out.println("プップー！");
    }
}

