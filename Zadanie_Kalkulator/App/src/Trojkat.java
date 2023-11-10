import java.util.Scanner;

public class Trojkat {
    Scanner scanner = new Scanner(System.in);

    private float a, h;

    Trojkat() {
        float _a;
        float _h;
        System.out.print("Wprowadź wysokość: ");
        _a = scanner.nextFloat();
        a = _a;

        System.out.print("Wprowadź podstawę: ");
        _h = scanner.nextFloat();
        h = _h;

        calculateArea();
    }

    private void calculateArea() {

        float area = (a * h) / 2;
        System.out.println();
        System.out.println("Pole trójkąta: " + area);
    }
}