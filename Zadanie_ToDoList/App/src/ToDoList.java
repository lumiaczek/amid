import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;

public class ToDoList {

    JSONArray database;

    Scanner scanner = new Scanner(System.in);

    ToDoList() {
        initDB();
        menu();
    }

    private void initDB() {
        JSONParser parser = new JSONParser();
        Object file;

        try {
            file = parser.parse(new FileReader("./list.json"));
            database = (JSONArray) file;

        } catch (IOException | ParseException e) {
            try {
                FileWriter db = new FileWriter("./list.json"); // tworzenie bazy danych jeżeli nie istnieje
                db.write("[]");
                db.close();
                initDB();
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    // ! Zapis aktualnej kopi listy do pliku JSON

    private void writeDB() {
        try {
            FileWriter db = new FileWriter("./list.json");
            db.write(database.toString());
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ! ------------------------------------------

    // ! Menu

    private void menu() {
        int choice;

        System.out.println("---------------To-Do-List-----------------");
        System.out.println("[1]. Dodaj nowe zadanie");
        System.out.println("[2]. Oznacz zadanie jako wykonane");
        System.out.println("[3]. Usuń zadanie");
        System.out.println("[4]. Wyświetl liste zadanń");
        System.out.println("[5]. Wyjście");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.print("Wybrana opcja: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        switch (choice) {
            case 1:
                addTask();
                break;

            case 2:
                markDone();
                break;

            case 3:
                delTask();
                break;

            case 4:
                showList();
                break;
            case 5:
                System.exit(0);
                break;

            default:
                System.out.println();
                System.out.println("Wprowadzona opcja jest nie poprawana!");
                System.out.println();
                menu();
                break;
        }

    }

    // ! ---------------------------------------------

    // ! Dodawanie zadania

    private void addTask() {
        long isDone = 0;
        String name;

        System.out.println();
        System.out.print("Wprowadź tytuł: ");

        name = scanner.nextLine();

        JSONObject task = new JSONObject();
        task.put("name", name);
        task.put("isDone", isDone);

        database.add(task);

        writeDB();

        System.out.println();
        System.out.println("Zadanie zostało dodane!");
        System.out.println();

        menu();

    }

    // ! -----------------

    // ! Zaznacz jako zaliczone

    private void markDone() {

        int choice;

        checkTasks();

        System.out.println("Lista zadań:");

        for (int i = 0; i < database.size(); i++) {
            JSONObject task = (JSONObject) database.get(i);
            try {
                long isDone = (long) task.get("isDone");
                if (isDone == 1) {
                    System.out.println(i + ": [X] " + task.get("name"));
                } else {
                    System.out.println(i + ": [ ] " + task.get("name"));
                }
            } catch (ClassCastException e) {
                int isDone = (int) task.get("isDone");
                if (isDone == 1) {
                    System.out.println(i + ": [X] " + task.get("name"));
                } else {
                    System.out.println(i + ": [ ] " + task.get("name"));
                }
            }

        }
        System.out.println();

        System.out.print("Które zadanie chcesz zakończyć?: ");
        choice = scanner.nextInt();

        System.out.println();

        for (int i = 0; i <= database.size(); i++) {
            JSONObject task = (JSONObject) database.get(i);
            if (choice == i) {
                task.replace("isDone", 1);
                writeDB();

                System.out.println();
                System.out.println("Zadanie zostało zakończone");
                System.out.println();

                menu();
            } else {
                continue;
            }
        }

    }

    // ! -----------------

    // ! Usuwanie zadania

    private void delTask() {
        checkTasks();

        int choice;

        System.out.println("Lista zadań:");

        for (int i = 0; i < database.size(); i++) {
            JSONObject task = (JSONObject) database.get(i);
            try {
                long isDone = (long) task.get("isDone");
                if (isDone == 1) {
                    System.out.println(i + ": [X] " + task.get("name"));
                } else {
                    System.out.println(i + ": [ ] " + task.get("name"));
                }
            } catch (ClassCastException e) {
                int isDone = (int) task.get("isDone");
                if (isDone == 1) {
                    System.out.println(i + ": [X] " + task.get("name"));
                } else {
                    System.out.println(i + ": [ ] " + task.get("name"));
                }
            }
        }
        System.out.println();

        System.out.print("Które zadanie chcesz usunąć?: ");
        choice = scanner.nextInt();

        if (choice > database.size()) {

            System.out.println();
            System.out.println("Nie można usunąć nie istniejącego zadania!");
            System.out.println();

            menu();
        } else {
            database.remove(choice);
            writeDB();

            System.out.println();
            System.out.println("Zadanie zostało usunięte");
            System.out.println();

        }

        menu();

    }

    // ! -----------------

    // ! Wyświetlenie listy

    private void showList() {

        checkTasks();

        for (int i = 0; i < database.size(); i++) {
            JSONObject task = (JSONObject) database.get(i);
            try {
                long isDone = (long) task.get("isDone");
                if (isDone == 1) {
                    System.out.println(i + ": [X] " + task.get("name"));
                } else {
                    System.out.println(i + ": [ ] " + task.get("name"));
                }
            } catch (ClassCastException e) {
                int isDone = (int) task.get("isDone");
                if (isDone == 1) {
                    System.out.println(i + ": [X] " + task.get("name"));
                } else {
                    System.out.println(i + ": [ ] " + task.get("name"));
                }
            }
        }
        System.out.println();

        menu();
    }

    // ! -----------------

    // ! Sprawdzanie czy jakieś zadanie istnieje

    private void checkTasks() {
        if (database.size() == 0) {
            System.out.println();
            System.out.println("Brak zadań!");
            System.out.println();

            menu();
        }
    }

    // ! ----------------------------------------
}
