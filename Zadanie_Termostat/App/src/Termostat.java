import java.util.Scanner;

public class Termostat {
    private double aktualnaTemperatura;
    private double ustawionaTemperatura;

    Termostat() throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Wprowadź aktualną temperature: ");
        aktualnaTemperatura = scanner.nextInt();

        if (aktualnaTemperatura == 0) {
            throw new Exception("Aktualna temperatura nie może być zerem");
        }

        System.out.print("Ustaw temperature (przeskok co 0.5 stopnia C): ");
        ustawionaTemperatura = scanner.nextDouble();

        if ((ustawionaTemperatura > 30) || (ustawionaTemperatura < 14)) {
            throw new Exception("Ustawiona temperatura nie może być wyższa niż 30 stopni ani, niższa niż 14 stopni");

        }

    }

    public void wlaczOgrzewanie(double zmiana) {
        zmiana = 0.5;
        aktualnaTemperatura = aktualnaTemperatura + zmiana;

    }

    public void wlaczChlodzenie(double zmiana) {

        zmiana = 0.5;

        aktualnaTemperatura = aktualnaTemperatura - zmiana;

    }

    public void sprawdzTemperature() throws InterruptedException {
        if (aktualnaTemperatura > ustawionaTemperatura) {

            System.out.println("Rozpoczynam chłodzenie...");

            double roznica = ustawionaTemperatura - aktualnaTemperatura;

            roznica = Math.abs(roznica);

            for (int i = 0; i < roznica * 2; i++) {
                Thread.sleep(1000);
                wlaczChlodzenie(0.5);
                System.out.println("Aktualna temperatura: " + aktualnaTemperatura + "C");
            }
        }

        if (aktualnaTemperatura < ustawionaTemperatura) {

            System.out.println("Rozpoczynam grzanie...");

            double roznica = ustawionaTemperatura - aktualnaTemperatura;

            roznica = Math.abs(roznica);

            for (int i = 0; i < roznica * 2; i++) {
                Thread.sleep(1000);
                wlaczOgrzewanie(0.5);
                System.out.println("Aktualna temperatura: " + aktualnaTemperatura + "C");
            }
        }
    }
}
