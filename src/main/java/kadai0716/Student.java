package kadai0716;

import java.util.Objects;

/**
 * 学生情報を表すクラスです。
 * Comparableインターフェースを実装することで、学生IDに基づいてソートできるようになります。
 */
// Studentクラスを定義し、Comparableインターフェースを実装します
class Student implements Comparable<Student> {
    // 学生の識別子となるIDを保持します
    private int id;
    // 学生の名前を保持します
    private String name;
    // 学生の年齢を保持します
    private int age;
    // 学生の成績や点数を保持します
    private int score;

    /**
     * Studentクラスの新しいインスタンス（学生オブジェクト）を作成します。
     *
     * @param id    学生ID
     * @param name  学生名
     * @param age   年齢
     * @param score 成績
     */
    // Studentクラスのコンストラクタを定義します
    public Student(int id, String name, int age, int score) {
        // 引数で受け取ったIDをこのオブジェクトのIDに設定します
        this.id = id;
        // 引数で受け取った名前をこのオブジェクトの名前に設定します
        this.name = name;
        // 引数で受け取った年齢をこのオブジェクトの年齢に設定します
        this.age = age;
        // 引数で受け取った成績をこのオブジェクトの成績に設定します
        this.score = score;
    }

    // 各フィールドのゲッターメソッド（値を取得するためのメソッド）

    // 学生IDを取得するメソッドを定義します
    public int getId() {
        // このオブジェクトのIDを返します
        return id;
    }

    // 学生の名前を取得するメソッドを定義します
    public String getName() {
        // このオブジェクトの名前を返します
        return name;
    }

    // 学生の年齢を取得するメソッドを定義します
    public int getAge() {
        // このオブジェクトの年齢を返します
        return age;
    }

    // 学生の成績を取得するメソッドを定義します
    public int getScore() {
        // このオブジェクトの成績を返します
        return score;
    }

    // 各フィールドのセッターメソッド（値を変更するためのメソッド、必要に応じて）

    // 学生の名前を設定するメソッドを定義します
    public void setName(String name) {
        // 引数で受け取った名前をこのオブジェクトの名前に設定します
        this.name = name;
    }

    // 学生の年齢を設定するメソッドを定義します
    public void setAge(int age) {
        // 引数で受け取った年齢をこのオブジェクトの年齢に設定します
        this.age = age;
    }

    // 学生の成績を設定するメソッドを定義します
    public void setScore(int score) {
        // 引数で受け取った成績をこのオブジェクトの成績に設定します
        this.score = score;
    }

    /**
     * オブジェクトの文字列表現を返します。
     * System.out.println()などでオブジェクトを表示する際に、この形式で出力されます。
     */
    // 親クラス（Object）のtoStringメソッドを上書きします
    @Override
    // オブジェクトの文字列表現を返すメソッドを定義します
    public String toString() {
        // 文字列の開始部分を生成します
        return "Student{" +
                // 学生IDを文字列に含めます
                "ID=" + id +
                // 学生の名前を文字列に含めます
                ", 名前='" + name + '\'' +
                // 学生の年齢を文字列に含めます
                ", 年齢=" + age +
                // 学生の成績を文字列に含めます
                ", 成績=" + score +
                // 文字列の終了部分を生成します
                '}';
    }

    /**
     * オブジェクトの等価性を比較します。
     * 通常、IDが同じであれば同じ学生とみなします。
     */
    // 親クラス（Object）のequalsメソッドを上書きします
    @Override
    // オブジェクトの等価性を比較するメソッドを定義します
    public boolean equals(Object o) {
        // 同じオブジェクトであればtrueを返します
        if (this == o) return true;
        // nullまたはクラスが異なればfalseを返します
        if (o == null || getClass() != o.getClass()) return false;
        // 比較対象をStudent型にキャストします
        Student student = (Student) o;
        // 学生IDが同じであればtrueを返します
        return id == student.id;
    }

    /**
     * オブジェクトのハッシュコードを返します。
     * equalsメソッドを上書きした場合は、hashCodeメソッドも上書きする必要があります（HashMapなどで正しく動作するため）。
     */
    // 親クラス（Object）のhashCodeメソッドを上書きします
    @Override
    // オブジェクトのハッシュコードを返すメソッドを定義します
    public int hashCode() {
        // 学生IDに基づいてハッシュコードを生成して返します
        return Objects.hash(id);
    }

    /**
     * 学生IDに基づいてStudentオブジェクトを比較します。
     * Collections.sort()などでStudentオブジェクトをソートできるようになります。
     *
     * @param other 比較対象のStudentオブジェクト
     * @return このオブジェクトのIDがotherのIDより小さい場合は負の整数、大きい場合は正の整数、等しい場合は0
     */
    // ComparableインターフェースのcompareToメソッドを実装します
    @Override
    // 学生IDに基づいてStudentオブジェクトを比較するメソッドを定義します
    public int compareTo(Student other) {
        // 自身のIDと引数で受け取った学生のIDを比較して結果を返します
        return Integer.compare(this.id, other.id);
    }
}
