import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Termostat {
    Scanner scanner = new Scanner(System.in);
    private double aktualnaTemperatura = 0;
    private double ustawionaTemperatura = 0;
    private double zmiana = 0.5;
    File log = new File("log.txt");
    FileWriter file = new FileWriter("log.txt");

    Termostat() throws Exception { // uruchomienie termostatu i stworzenie pliku z logami

        try {
            log.createNewFile();
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas tworzenia pliku z logami");
        }

        menu();
    }

    public void menu() throws Exception { // główne menu termostatu
        int wybor;

        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println("Wybierz opcje: ");
        System.out.println("[1] Rozpocznij chłodzenie/grzanie");
        System.out.println("[2] Wprowadź temperature");
        System.out.println("[3] Zmień tempo zmiany temperatury");
        System.out.println("[4] Wyłącz termostat");
        System.out.println("-----------------------------------");

        System.out.print("Proszę wprowadź opcje: ");
        wybor = scanner.nextInt();

        switch (wybor) {
            case 1:
                sprawdzTemperature();
                break;

            case 2:
                ustawTemperature();
                break;

            case 3:
                zmienTempo();
                break;
            case 4:
                file.close();
                System.out.println();
                System.out.println("Do zobaczenia!");
                break;

            default:
                System.out.println();
                System.out.println("Wybrana opcja jest nie poprawna.");

                menu();
                break;
        }
    }

    public void ustawTemperature() throws Exception { // ustawiwanie temperatury

        if (aktualnaTemperatura != 0) {
            String wybor;
            System.out.print("Aktualna temperatora to: " + aktualnaTemperatura + " czy chcesz ją zmienic? [T/N]: ");
            wybor = scanner.nextLine();

            switch (wybor) {
                case "T":
                    System.out.print("Wprowadź aktualną temperature: "); // wprowadzanie aktualnej temperatury
                    aktualnaTemperatura = scanner.nextDouble();
                    break;

                case "N":
                    break;

                default:
                    System.out.println("Nie wprowadzono odpowiedzi");
                    ustawTemperature();
                    break;

            }

        } else {

            System.out.print("Wprowadź aktualną temperature: "); // wprowadzanie aktualnej temperatury
            aktualnaTemperatura = scanner.nextDouble();

            file.append("Użytkownik ustawił aktualną temperature na: " + aktualnaTemperatura + "\n");
        }

        System.out.print("Ustaw temperature: "); // ustawianie rządanej temperatury
        ustawionaTemperatura = scanner.nextDouble();

        file.append("Użytkownik ustawił żądaną temperature na: " + ustawionaTemperatura + "\n");

        if ((ustawionaTemperatura > 30) || (ustawionaTemperatura < 14)) { // Zabezpieczenie przed przegrzaniem lub
                                                                          // zmrożeniem pomieszczenia
            System.out.println("Ustawiona temperatura nie może być wyższa niż 30 stopni ani, niższa niż 14 stopni");
            ustawTemperature();

        }
        System.out.println();
        System.out.println("Zapisano. Powracam do menu.");
        Thread.sleep(1000);
        menu();
    }

    public void zmienTempo() throws Exception {
        System.out.print("Wprowadź tempo zmiany: ");
        zmiana = scanner.nextDouble();

        file.append("Użytkownik ustawił tempo zmiany na: " + zmiana + "\n");

        System.out.println();
        System.out.println("Zapisano. Powracam do menu.");
        Thread.sleep(1000);
        menu();
    }

    public void sprawdzTemperature() throws Exception { // automatyczne włączenie chłodzenia lub grzania w zależności od
                                                        // temperatury

        if (aktualnaTemperatura == 0) {
            System.out.println();
            System.out.println(
                    "Nie ustawiono temperatury! Wróć do menu i przed włączeniem chłodzenia/grzania wybierz opcje 2");
            Thread.sleep(1000);
            menu();
        }

        if (aktualnaTemperatura > ustawionaTemperatura) {

            wlaczChlodzenie(zmiana);

        }

        if (aktualnaTemperatura < ustawionaTemperatura) {
            wlaczOgrzewanie(zmiana);

        }

        if (aktualnaTemperatura == ustawionaTemperatura) {
            System.out.println();
            System.out.println("Ukończono. Powracam do menu");
        }

        Thread.sleep(1000);
        menu();
    }

    public void wlaczOgrzewanie(double zmiana) throws InterruptedException, IOException { // obsługa ogrzewania

        System.out.println("Rozpoczynam ogrzewanie...");
        file.append("Włączono ogrzewanie \n");

        while (aktualnaTemperatura != ustawionaTemperatura) {
            aktualnaTemperatura = aktualnaTemperatura + zmiana;
            Thread.sleep(1000);
            System.out.println("Aktualna temperatura: " + aktualnaTemperatura + "C");
            file.append("Aktualna temperatura: " + aktualnaTemperatura + "C \n");
        }

    }

    public void wlaczChlodzenie(double zmiana) throws InterruptedException, IOException { // obsługa chłodzenia

        System.out.println("Rozpoczynam chłodzenie...");
        file.append("Włączono chłodzenie" + "\n");

        while (aktualnaTemperatura != ustawionaTemperatura) {
            aktualnaTemperatura = aktualnaTemperatura - zmiana;
            Thread.sleep(1000);
            System.out.println("Aktualna temperatura: " + aktualnaTemperatura + "C");
            file.append("Aktualna temperatura: " + aktualnaTemperatura + "C \n");
        }
    }

}
