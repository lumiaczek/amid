import java.util.Scanner;

public class Menu {

    Menu() {
        menu();
    }

    public void menu() {
        int choice;

        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------");
        System.out.println("1. Kwadrat");
        System.out.println("2. Prostokat");
        System.out.println("3. Trójkąt");
        System.out.println("4. Wyjście");
        System.out.println("-----------------------");

        System.out.println();
        System.out.print("Wprowadź opcje: ");

        choice = scanner.nextInt();
        System.out.println();

        switch (choice) {
            case 1:
                Kwadrat kwadrat = new Kwadrat();
                menu();
                break;

            case 2:
                Prostokat prostokat = new Prostokat();
                menu();
                break;

            case 3:
                Trojkat trojkat = new Trojkat();
                menu();
                break;

            case 4:
                break;

            default:
                System.out.println("Wprowadzono złą opcje");
                break;
        }
    }
}
