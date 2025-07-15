package oop;

import java.util.ArrayList;
import java.util.List;

/**
 * ポリモーフィズムの実装例
 */
public class Polymorphism {
    public static void main(String[] args) {
        System.out.println("=== ポリモーフィズムの実装 ===\n");

        // 図形のリスト（ポリモーフィズム）
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(5));
        shapes.add(new Rectangle(4, 6));
        shapes.add(new Triangle(3, 4, 5));

        // すべての図形の面積と周囲長を計算
        double totalArea = 0;
        double totalPerimeter = 0;

        for (Shape shape : shapes) {
            System.out.println("--- " + shape.getName() + " ---");
            shape.displayInfo();

            double area = shape.calculateArea();
            double perimeter = shape.calculatePerimeter();

            System.out.printf("面積: %.2f\n", area);
            System.out.printf("周囲長: %.2f\n", perimeter);
            System.out.println();

            totalArea += area;
            totalPerimeter += perimeter;
        }

        System.out.printf("全図形の総面積: %.2f\n", totalArea);
        System.out.printf("全図形の総周囲長: %.2f\n", totalPerimeter);

        // 動的バインディングの例
        System.out.println("\n=== 動的バインディング ===");
        Shape myShape = new Circle(10);  // Shape型の変数にCircleオブジェクトを代入
        myShape.displayInfo();  // Circleのメソッドが呼ばれる（動的バインディング）

        // 型変換とinstanceof
        System.out.println("\n=== 型チェックと型変換 ===");
        processShape(new Circle(7));
        processShape(new Rectangle(5, 5));
    }

    // ポリモーフィズムを活用したメソッド
    public static void processShape(Shape shape) {
        System.out.println("図形: " + shape.getName());

        // instanceof演算子で型をチェック
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;  // ダウンキャスト
            System.out.println("半径: " + circle.getRadius());
        } else if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            System.out.println("幅: " + rect.getWidth() + ", 高さ: " + rect.getHeight());
            if (rect.isSquare()) {
                System.out.println("これは正方形です！");
            }
        }
    }
}

// 抽象クラス：図形
abstract class Shape {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    // 抽象メソッド（サブクラスで実装必須）
    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    // 具象メソッド（共通実装）
    public void displayInfo() {
        System.out.println("図形の種類: " + name);
    }

    public String getName() {
        return name;
    }
}

// 円クラス
class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super("円");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("半径: " + radius);
    }

    public double getRadius() {
        return radius;
    }
}

// 長方形クラス
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("長方形");
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("幅: " + width + ", 高さ: " + height);
    }

    // 正方形かどうかを判定
    public boolean isSquare() {
        return width == height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

// 三角形クラス
class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        super("三角形");

        // 三角形の成立条件をチェック
        if (sideA + sideB <= sideC || sideB + sideC <= sideA || sideC + sideA <= sideB) {
            throw new IllegalArgumentException("三角形の成立条件を満たしていません");
        }

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double calculateArea() {
        // ヘロンの公式
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("辺の長さ: " + sideA + ", " + sideB + ", " + sideC);
    }
}
