import java.util.Scanner;

public class Kwadrat {

    Scanner scanner = new Scanner(System.in);

    private float a;

    Kwadrat() {
        float _a;
        System.out.print("Wprowadź długość boku: ");
        _a = scanner.nextFloat();
        a = _a;

        calculateArea();
    }

    private void calculateArea() {

        float area = a * a;
        System.out.println();
        System.out.println("Pole kwadratu: " + area);
    }
}