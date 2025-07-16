package kadai0716;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 学生の情報を管理するシステムです。
 * HashMapを使用して学生IDと学生オブジェクトをマッピングします。
 */
// StudentManagementSystemクラスを定義します
public class StudentManagementSystem {
    // 学生ID（Integer）をキー、Studentオブジェクトを値とするMapを宣言します
    // これにより、IDによる学生の検索や追加・削除を高速に行うことができます
    private Map<Integer, Student> students;

    /**
     * StudentManagementSystemの新しいインスタンスを作成します。
     * システムが起動する際に、学生情報を格納するMapを初期化します。
     */
    // StudentManagementSystemクラスのコンストラクタを定義します
    public StudentManagementSystem() {
        // 新しいHashMapを生成し、学生情報を格納するMapを初期化します
        this.students = new HashMap<>();
    }

    /**
     * 新しい学生をシステムに追加します。
     * @param student 追加する学生オブジェクト
     * @return 成功した場合はtrue、同じIDの学生が既に存在する場合はfalse
     */
    // 学生を追加するメソッドを定義します
    public boolean addStudent(Student student) {
        // すでに同じIDの学生がMapに存在するかチェックします
        if (students.containsKey(student.getId())) {
            // 存在する場合はエラーメッセージを出力します
            System.out.println("エラー: ID " + student.getId() + " の学生は既に存在します。");
            // 追加に失敗したことを示すfalseを返します
            return false;
        }
        // Mapに学生IDをキー、学生オブジェクトを値として追加します
        students.put(student.getId(), student);
        // 追加成功メッセージを出力します
        System.out.println("学生を追加しました: " + student.getName());
        // 追加に成功したことを示すtrueを返します
        return true;
    }

    /**
     * 学生IDに基づいて学生を取得します。
     * 学生が見つからない可能性があるので、Optionalを返します。
     * @param id 検索する学生ID
     * @return 見つかった場合はStudentオブジェクトを含むOptional、見つからない場合は空のOptional
     */
    // 学生IDに基づいて学生を取得するメソッドを定義します
    public Optional<Student> getStudentById(int id) {
        // students.get(id) でMapから学生を取得し、nullの可能性があるためOptional.ofNullableでラップして返します
        return Optional.ofNullable(students.get(id));
    }

    /**
     * 既存の学生情報を更新します。
     * @param updatedStudent 更新する学生オブジェクト (IDは既存の学生と一致する必要があります)
     * @return 成功した場合はtrue、学生が見つからない場合はfalse
     */
    // 学生情報を更新するメソッドを定義します
    public boolean updateStudent(Student updatedStudent) {
        // 更新対象の学生がMapに存在するかチェックします
        if (!students.containsKey(updatedStudent.getId())) {
            // 存在しない場合はエラーメッセージを出力します
            System.out.println("エラー: ID " + updatedStudent.getId() + " の学生は見つかりませんでした。更新できません。");
            // 更新に失敗したことを示すfalseを返します
            return false;
        }
        // Mapはキーが重複すると新しい値で上書きされるため、これで更新します
        students.put(updatedStudent.getId(), updatedStudent);
        // 更新成功メッセージを出力します
        System.out.println("学生情報を更新しました: " + updatedStudent.getName());
        // 更新に成功したことを示すtrueを返します
        return true;
    }

    /**
     * 学生IDに基づいて学生を削除します。
     * @param id 削除する学生ID
     * @return 成功した場合はtrue、学生が見つからない場合はfalse
     */
    // 学生IDに基づいて学生を削除するメソッドを定義します
    public boolean deleteStudent(int id) {
        // 削除対象の学生がMapに存在するかチェックします
        if (!students.containsKey(id)) {
            // 存在しない場合はエラーメッセージを出力します
            System.out.println("エラー: ID " + id + " の学生は見つかりませんでした。削除できません。");
            // 削除に失敗したことを示すfalseを返します
            return false;
        }
        // Mapから指定されたIDの学生を削除し、削除された学生オブジェクトを取得します
        Student removedStudent = students.remove(id);
        // 削除成功メッセージを出力します
        System.out.println("学生を削除しました: " + removedStudent.getName());
        // 削除に成功したことを示すtrueを返します
        return true;
    }

    /**
     * システム内の全ての学生のリストを返します。
     * @return 全学生のリスト
     */
    // システム内の全ての学生のリストを返すメソッドを定義します
    public List<Student> getAllStudents() {
        // Mapのvalues()メソッドで全ての学生オブジェクトのCollection（集合）を取得し、
        // それを元に新しいArrayListを作成して返します。
        return new ArrayList<>(students.values());
    }

    /**
     * 指定された成績範囲内の学生をStream APIを使って検索します。
     * @param minScore 最小成績
     * @param maxScore 最大成績
     * @return 条件に一致する学生のリスト
     */
    // 指定された成績範囲内の学生を検索するメソッドを定義します
    public List<Student> findStudentsByScoreRange(int minScore, int maxScore) {
        // 検索条件を出力します
        System.out.println("\n--- 成績範囲 (" + minScore + "~" + maxScore + ") の学生 ---");
        // Mapの値（学生オブジェクト）のStreamを生成します
        return students.values().stream()
                // 各学生の成績が指定範囲内かフィルタリングします
                .filter(s -> s.getScore() >= minScore && s.getScore() <= maxScore)
                // フィルタリングされた学生を新しいListに集めます
                .collect(Collectors.toList());
    }

    /**
     * 学生を名前の昇順でソートしたリストをStream APIを使って返します。
     * @return 名前でソートされた学生のリスト
     */
    // 学生を名前の昇順でソートしたリストを返すメソッドを定義します
    public List<Student> sortStudentsByName() {
        // ソート処理の開始を出力します
        System.out.println("\n--- 名前順の学生リスト ---");
        // 全学生のStreamを生成します
        return students.values().stream()
                // 学生の名前を基準にソートします
                .sorted(Comparator.comparing(Student::getName))
                // ソートされた学生を新しいListに集めます
                .collect(Collectors.toList());
    }

    /**
     * 全学生の平均成績をStream APIを使って計算します。
     * @return 平均成績
     */
    // 全学生の平均成績を計算するメソッドを定義します
    public double getAverageScore() {
        // 平均成績計算の開始を出力します
        System.out.println("\n--- 全学生の平均成績 ---");
        // students.values().stream() で学生のStreamを生成します
        // .mapToInt(Student::getScore) でStudentオブジェクトのStreamを成績（int）のStreamに変換します
        // .average() で成績の平均を計算します（結果はOptionalDoubleで返されます）
        // .orElse(0.0) は、学生が一人もいない場合に0.0を返すようにします
        return students.values().stream()
                .mapToInt(Student::getScore)
                .average()
                .orElse(0.0);
    }

    /**
     * メインメソッド：学生管理システムのデモンストレーション
     * このメソッドを実行することで、システムの動作を確認できます。
     */
    // プログラムの実行開始点となるメインメソッドを定義します
    public static void main(String[] args) {
        // システムの開始メッセージを出力します
        System.out.println("=== 学生管理システムを開始します ===");
        // StudentManagementSystemの新しいインスタンスを作成します
        StudentManagementSystem system = new StudentManagementSystem();

        // 1. 学生の追加
        // 学生追加のセクション開始を出力します
        System.out.println("\n--- 学生の追加 ---");
        // ID 101 の学生を追加します
        system.addStudent(new Student(101, "山田太郎", 18, 75));
        // ID 102 の学生を追加します
        system.addStudent(new Student(102, "鈴木花子", 19, 88));
        // ID 103 の学生を追加します
        system.addStudent(new Student(103, "佐藤次郎", 18, 62));
        // ID 104 の学生を追加します
        system.addStudent(new Student(104, "田中美咲", 20, 95));
        // ID 102 は既に存在するため、追加に失敗するはずです
        system.addStudent(new Student(102, "重複IDの学生", 21, 50));

        // 2. 全学生の表示
        // 全学生表示のセクション開始を出力します
        System.out.println("\n--- 現在の全学生 ---");
        // 全ての学生をリストで取得し、それぞれ出力します
        system.getAllStudents().forEach(System.out::println);

        // 3. 学生の検索
        // 学生検索のセクション開始を出力します
        System.out.println("\n--- 学生の検索 (ID: 102) ---");
        // ID 102 の学生を検索します
        Optional<Student> foundStudent = system.getStudentById(102);
        // 検索結果が存在するかどうかで処理を分岐します
        foundStudent.ifPresentOrElse(
                // 学生が見つかった場合、その情報を出力します
                s -> System.out.println("見つかりました: " + s),
                // 学生が見つからなかった場合、メッセージを出力します
                () -> System.out.println("学生ID 102 は見つかりませんでした。")
        );

        // 存在しないIDの検索テストです
        System.out.println("\n--- 学生の検索 (ID: 999 - 存在しないID) ---");
        // ID 999 の学生を検索します
        Optional<Student> notFoundStudent = system.getStudentById(999);
        // 検索結果が存在するかどうかで処理を分岐します
        notFoundStudent.ifPresentOrElse(
                // 学生が見つかった場合（この場合は実行されない）
                s -> System.out.println("見つかりました: " + s),
                // 学生が見つからなかった場合、メッセージを出力します
                () -> System.out.println("学生ID 999 は見つかりませんでした。")
        );

        // 4. 学生情報の更新
        // 学生情報更新のセクション開始を出力します
        System.out.println("\n--- 学生情報の更新 (ID: 101) ---");
        // ID 101 の山田さんの年齢と成績を更新した新しいオブジェクトを作成します
        Student updatedYamada = new Student(101, "山田太郎", 19, 80);
        // 山田さんの情報を更新します
        system.updateStudent(updatedYamada);
        // 更新後の山田さんの情報を表示します
        system.getStudentById(101).ifPresent(System.out::println);

        // 存在しないIDの更新テストです
        System.out.println("\n--- 学生情報の更新 (ID: 999 - 存在しないID) ---");
        // 存在しない学生の情報を更新しようとします
        system.updateStudent(new Student(999, "存在しない学生", 0, 0));

        // 5. Stream APIを使った高度な操作
        // 成績が80点から100点の学生を検索します
        List<Student> highScorers = system.findStudentsByScoreRange(80, 100);
        // 検索結果の学生をそれぞれ出力します
        highScorers.forEach(System.out::println);

        // 学生を名前の昇順でソートします
        List<Student> sortedByName = system.sortStudentsByName();
        // ソートされた学生をそれぞれ出力します
        sortedByName.forEach(System.out::println);

        // 全学生の平均成績を計算します
        double avgScore = system.getAverageScore();
        // 平均成績を小数点以下2桁まで表示します
        System.out.printf("平均成績: %.2f\n", avgScore);

        // 6. 学生の削除
        // 学生削除のセクション開始を出力します
        System.out.println("\n--- 学生の削除 (ID: 103) ---");
        // ID 103 の学生を削除します
        system.deleteStudent(103);
        // 削除後の全学生表示のセクション開始を出力します
        System.out.println("\n--- 削除後の全学生 ---");
        // 削除後の全学生をリストで取得し、それぞれ出力します
        system.getAllStudents().forEach(System.out::println);

        // 存在しないIDの削除テストです
        System.out.println("\n--- 学生の削除 (ID: 999 - 存在しないID) ---");
        // 存在しない学生を削除しようとします
        system.deleteStudent(999);

        // システムの終了メッセージを出力します
        System.out.println("\n=== 学生管理システムを終了します ===");
    }
}
