import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    JSONArray database;

    public Menu() {
        initDB();
        menu();
    }

    private void initDB() { // inicjalizacja bazy danych sal

        JSONParser parser = new JSONParser();
        Object file;

        try {
            file = parser.parse(new FileReader("./db.json"));
            database = (JSONArray) file;

        } catch (IOException | ParseException e) {
            try {
                FileWriter db = new FileWriter("./db.json"); // tworzenie bazy danych jeżeli nie istnieje
                db.write("[]");
                db.close();
                initDB();
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    private void writeDB() { // inicjalizacja bazy danych sal

        try {
            FileWriter db = new FileWriter("./db.json");
            db.write(database.toString());
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void menu() {

        int choice;

        System.out.println("------------------------------------");
        System.out.println("[1]: Zarezerwuj sale");
        System.out.println("[2]: Sprawdź dostępność sali");
        System.out.println("[3]: Zmień pojemność sali");
        System.out.println("[4]: Dodaj sale");
        System.out.println("[5]: Wyjdź");
        System.out.println("------------------------------------");

        System.out.println();
        System.out.print("Wybór opcji: ");

        choice = scanner.nextInt();
        System.out.println();

        switch (choice) {
            case 1:
                reservation();
                break;

            case 2:
                checkRoom();
                break;

            case 3:
                changeSize();
                break;

            case 4:
                addRoom();
                break;

            case 5:
                break;

            default:
                System.out.println("Wpisana opcja jest nie poprawna!");
                menu();
                break;
        }
    }

    private void reservation() {

        int choice;
        String date;

        System.out.println("Lista wprowadzonych do bazy pokoi:");
        System.out.println();
        for (int i = 0; i < database.size(); i++) {
            JSONObject room = (JSONObject) database.get(i);
            System.out.print("[" + room.get("id") + "] ");
        }
        System.out.println();
        System.out.println();

        System.out.print("Jaki pokój chcesz zarezerwować?: ");
        choice = scanner.nextInt();
        scanner.nextLine(); // sztuczka pochłaniająca pusty znak po intcie
        System.out.println();

        for (int i = 0; i < database.size(); i++) {
            JSONObject room = (JSONObject) database.get(i);
            long roomID = (long) room.get("id");
            if (roomID == choice) {
                break;
            } else {
                System.out.println();
                System.out.println("Taki pokój nie istnieje");
                System.out.println();
            }
            menu();
        }

        while (true) {
            System.out
                    .print("Na kiedy chcesz zarezerwować pokój? (Format = [DD-MM-YYYY HH-HH] np. 10-09-2023 10-11): ");
            date = scanner.nextLine();

            final Pattern pattern = Pattern.compile("([0-9]+(-[0-9]+)+)\\s([0-9]+(-[0-9]+)+)",
                    Pattern.CASE_INSENSITIVE);

            final Matcher matcher = pattern.matcher(date);

            boolean matchFound = matcher.find();
            if (matchFound) {
                for (int i = 0; i < database.size(); i++) {

                    JSONObject room = (JSONObject) database.get(i);
                    long roomID = (long) room.get("id");

                    if (roomID == choice) {
                        List occupancy = (ArrayList) room.get("occupancy");
                        for (int j = 0; j < occupancy.size(); j++) {
                            int godzinaOD, godzinaDO;
                            int _godzinaOD, _godzinaDO;
                            String data = (String) occupancy.get(j);

                            godzinaOD = Integer.parseInt(date.substring(11, 12));
                            godzinaDO = Integer.parseInt(date.substring(14, 15));

                            _godzinaOD = Integer.parseInt(data.substring(11, 12));
                            _godzinaDO = Integer.parseInt(data.substring(14, 15));

                            if ((data.substring(0, 9).compareTo(date.substring(0, 9)) == 0)) {
                                if ((godzinaDO <= _godzinaOD) || (godzinaOD >= _godzinaDO)) {
                                    occupancy.add(date);
                                    writeDB();

                                    System.out.println();
                                    System.out.println("Pokój został zarezerwowany na " + date);
                                    System.out.println();
                                    break;
                                }
                            }

                            if ((data.substring(0, 9).compareTo(date.substring(0, 9)) > 0
                                    || (data.substring(0, 9).compareTo(date.substring(0, 9)) < 0))) {
                                occupancy.add(date);
                                writeDB();

                                System.out.println();
                                System.out.println("Pokój został zarezerwowany na " + date);
                                System.out.println();
                            } else {
                                System.out.println();
                                System.out.println("Pokój jest wtedy zajęty!");
                                System.out.println();
                                menu();

                            }
                        }

                        break;
                    } else {
                        continue;
                    }
                }

                break;
            } else {
                System.out.println("Wpisana data jest nie poprawna!");
            }
        }

        menu();

    }

    private void checkRoom() {

        int sala;

        System.out.println("Lista wprowadzonych do bazy pokoi:");
        System.out.println();
        for (int i = 0; i < database.size(); i++) {
            JSONObject room = (JSONObject) database.get(i);
            System.out.print("[" + room.get("id") + "] ");
        }
        System.out.println();
        System.out.println();
        System.out.print("Rezerwacje jakiej sali chcesz sprawdzić?: ");
        sala = scanner.nextInt();
        System.out.println();

        for (int i = 0; i < database.size(); i++) {
            JSONObject room = (JSONObject) database.get(i);
            long roomID = (long) room.get("id");
            if (roomID == sala) {
                List occupancy = (ArrayList) room.get("occupancy");
                System.out.println("Rezerwacje pokoju numer: " + roomID + ":");
                System.out.println();
                for (int j = 0; j < occupancy.size(); j++) {
                    System.out.println(occupancy.get(j));
                }
                System.out.println();
                menu();
            } else {
                System.out.println();
                System.out.println("Taki pokój nie istnieje");
                System.out.println();
            }
        }
        menu();

    }

    private void changeSize() {

        int sala;
        int pojemnosc;

        System.out.print("Której sali chcesz zmienić pojemność?: ");
        sala = scanner.nextInt();

        for (int i = 0; i < database.size(); i++) {
            JSONObject room = (JSONObject) database.get(i);
            long roomID = (long) room.get("id");
            if (roomID == sala) {
                System.out.print("Wprowadź nową pojemność: ");
                pojemnosc = scanner.nextInt();

                room.replace("capacity", pojemnosc);
                writeDB();

                System.out.println();
                System.out.println("Zmieniono pojemność!");
                System.out.println();

                menu();
                break;
            } else {
                System.out.println();
                System.out.println("Nie znaleziono takiej sali!");
                System.out.println();

                menu();
            }
        }

    }

    private void addRoom() {

        int id, capacity;
        List occupany = new ArrayList<String>();

        System.out.println();
        System.out.print("Wprowadź numer sali: ");
        id = scanner.nextInt();

        System.out.print("Wprowadź pojemność sali: ");
        capacity = scanner.nextInt();

        JSONObject room = new JSONObject();

        room.put("id", id);
        room.put("capacity", capacity);
        room.put("occupancy", occupany);

        database.add(room);

        writeDB();
        System.out.println();
        System.out.println("Sala została dodana!");
        System.out.println();
        menu();
    }
}
