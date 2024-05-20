import java.util.Scanner;

public class Prostokat {
    Scanner scanner = new Scanner(System.in);

    private float a, b;

    Prostokat() {
        float _a;
        float _b;
        System.out.print("Wprowadź wysokość: ");
        _a = scanner.nextFloat();
        a = _a;

        System.out.print("Wprowadź podstawę boku: ");
        _b = scanner.nextFloat();
        b = _b;

        calculateArea();
    }

    private void calculateArea() {

        float area = a * b;
        System.out.println();
        System.out.println("Pole prostokątu: " + area);
    }
}
