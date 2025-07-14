public class DataTypes {

    public static void main(String[] args) {
        //基本データ型

        //整数型
        //8ビット(-128~127)
        byte byteValue = 127;
        //16ビット
        short shortValue = 32767;
        //32ビット(最も良く使う)
        int intValue = 2147483647;
        //64ビット(Lを付ける)
        long longValue = 9223372036854775807L;

        System.out.println("整数型の例");
        System.out.println("int:" + intValue);
        System.out.println("long:" + longValue);

        //浮動小数点型
        //32ビット(fを付ける)
        float floatValue = 3.14f;
        double doubleValue = 3.14159265359;

        System.out.println("\n浮動小数点型の例:");
        System.out.println("float:" + floatValue);
        System.out.println("double:" + doubleValue);

        //文字型
        //16ビット　Unicode文字
        char charValue = 'A';
        //日本語も格納可能
        char JapaneseChar ='あ';

        System.out.println("\n文字列型の例:");
        System.out.println("char" + charValue);
        System.out.println("日本語:" + JapaneseChar);

        //真偽値
        //true or false
        boolean isJavaFun = true;
        boolean isPythonBetter = false;

        System.out.println("\n真偽値型の例:");
        System.out.println("Javaは楽しい？:" + isJavaFun);

        //=====参照型=====
        //文字列(String)
        String message = "Hello, Java";
        String name = "山田太郎";

        System.out.println("\n文字列の例:");
        System.out.println("メッセージ:" + message);
        System.out.println("名前:" + name);

        //文字列の連結
        String firstName ="太郎";
        String lastName ="山田";
        //+で連結
        String fullName = lastName + firstName;
        System.out.println("フルネーム:" + fullName);

        //=====型変換=====
        System.out.println("\n型変換の例:");

        //暗黙的な型変換(小から大)
        int intNum = 100;
        //自動的に変換
        double doubleNum = intNum;
        System.out.println("int → double:" + doubleNum);

        //明示的な型変換(大から小)
        double pi = 3.14159;
        //キャスト必要(変換後の型)　変換前の変数名
        int intPi = (int) pi;
        System.out.println("double → int:" + intPi);

        //文字列への変換
        String strNum = String.valueOf(123);
        String strBool =String.valueOf(true);
        System.out.println("数値→文字列;:" + strNum);
        System.out.println("真偽値→文字列:" + strBool);

        //文字列からの変換
        int parsedInt = Integer.parseInt("456");
        double parsedDouble = Double.parseDouble("3.14");
        System.out.println("文字列→int:" + parsedInt);
        System.out.println("文字列→double:" + parsedDouble);

        // ===== 定数 =====
        // finalで定数を定義
        // PI = 3.14; // エラー：定数は変更不可
        final double PI = 3.14159265359;


        // ===== var（型推論）Java 10以降 =====
        var autoString = "型推論された文字列";
        var autoInt = 42;
        var autoDouble = 3.14;

        System.out.println("\n型推論（var）の例:");
        System.out.println("var string: " + autoString);
        System.out.println("var int: " + autoInt);
        System.out.println("var double: " + autoDouble);
    }
}


