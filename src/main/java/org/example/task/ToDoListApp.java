package org.example.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoListApp {

    //region Поля

    /**
     * Переменные-константы имён файлов с расширениями .json, .xml, .bin
     * используются для записи объекта в файлы с соответствующими форматами записи
     */
    public static final String FILE_JSON = "task.json";
    public static final String FILE_XML = "task.xml";
    public static final String FILE_BIN = "task.bin";

    /**
     * Создание объекта для сериализации и десериализации в формате JSON
     * при использовании библиотеки JACKSON
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Создание объекта для сериализации и десериализации в формате XML
     * при использовании библиотеки JACKSON
     */
    private static final XmlMapper xmlMapper = new XmlMapper();

    //endregion


    //region Методы

    /**
     * Метод добавления новой задачи в список задач для выполнения
     * и запись списка в файлы с разными форматами записи
     * @param scanner объект сканера для ввода новой задачи пользователем
     * @param tasks список задач для выполнения
     */
    public static void addNewTask(Scanner scanner, List<ToDoV2> tasks) {
        System.out.println("Введите название новой задачи:");
        String newTaskTitle = scanner.nextLine();
        tasks.add(new ToDoV2(newTaskTitle));
        saveTasksToFile(FILE_JSON, tasks);
        saveTasksToFile(FILE_XML, tasks);
        saveTasksToFile(FILE_BIN, tasks);
        System.out.println("Новая задача добавлена");
    }

    /**
     * Метод для вывода задач для выполнения в консоль
     * @param tasks список задач для выполнения
     */
    public static void displayTasks(List<ToDoV2> tasks) {
        System.out.println("Список задач:");
        for (int i = 0; i < tasks.size(); i++) {
            ToDoV2 task = tasks.get(i);
            String status = task.isDone() ? "[x]" : "[ ]";
            System.out.println(i + 1 + ". " + status + " " + task.getTitle());
        }
    }

    /**
     * Метод для отметки задачи, как выполненной
     * @param scanner объект сканера для ввода пользователем
     * @param tasks список задач для выполнения
     */
    public static void markTaskAsDone(Scanner scanner, List<ToDoV2> tasks) {
        System.out.println("Введите порядковый номер задачи для отметки, как выполненной: ");
        String input = scanner.nextLine();

        try {
            int taskNumber = Integer.parseInt(input) - 1;
            if (taskNumber >= 0 && taskNumber < tasks.size()) {
                tasks.get(taskNumber).setDone(true);
                saveTasksToFile(FILE_JSON, tasks);
                saveTasksToFile(FILE_XML, tasks);
                saveTasksToFile(FILE_BIN, tasks);
                System.out.println("Задача отмечена, как выполненная");
            } else {
                System.out.println("Некорректный номер задачи. Попробуйте снова.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Попробуйте снова.");
        }
    }

    /**
     * Метод сериализации списка задач в файлы с форматами JSON, XML и BIN
     * @param fileName имя файла для сериализации
     * @param tasks список задач для выполнения
     */
    public static void saveTasksToFile(String fileName, List<ToDoV2> tasks) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), tasks);
            } else if (fileName.endsWith(".xml")) {
                xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                xmlMapper.writeValue(new File(fileName), tasks);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(tasks);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод десериализации из файлов с форматами JSON, XML и BIN в список задач
     * @param fileName имя файла для десериализации
     * @return список задач
     */
    public static List<ToDoV2> loadTasksFromFile(String fileName) {
        List<ToDoV2> tasks = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    tasks = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, ToDoV2.class));
                } else if (fileName.endsWith(".xml")) {
                    tasks = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, ToDoV2.class));
                } else if (fileName.endsWith(".bin")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
                        tasks = (List<ToDoV2>) ois.readObject();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    //endregion
}
