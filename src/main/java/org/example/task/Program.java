package org.example.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.task.ToDoListApp.*;

public class Program {
    public static void main(String[] args) {

        List<ToDoV2> tasks;

        File file = new File(FILE_JSON);
        if (file.exists() && !file.isDirectory()) {
            tasks = loadTasksFromFile(FILE_JSON);
        } else {
            tasks = prepareTasks();
            saveTasksToFile(FILE_JSON,tasks);
            saveTasksToFile(FILE_XML, tasks);
            saveTasksToFile(FILE_BIN, tasks);
        }

        displayTasks(tasks);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить новую задачу");
            System.out.println("2. Отметить задачу, как выполненную");
            System.out.println("3. Выйти");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addNewTask(scanner, tasks);
                    break;
                case "2":
                    markTaskAsDone(scanner, tasks);
                    break;
                case "3":
                    saveTasksToFile(FILE_JSON, tasks);
                    saveTasksToFile(FILE_XML, tasks);
                    saveTasksToFile(FILE_BIN, tasks);
                    System.out.println("Список задач сохранен");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }

            displayTasks(tasks);
        }
    }

    /**
     * Метод начальная инициализация списка задач для выполнения
     * @return инициализированный список
     */
    public static List<ToDoV2> prepareTasks() {
        ArrayList<ToDoV2> list = new ArrayList<>();
        list.add(new ToDoV2("Посмотреть лекцию"));
        list.add(new ToDoV2("Приготовить ужин"));
        list.add(new ToDoV2("Сходить в магазин"));
        return list;
    }
}
