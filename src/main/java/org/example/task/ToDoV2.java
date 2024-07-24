package org.example.task;

import java.io.*;

/**
 * Класс с реализацией интерфейса Externalizable
 */
public class ToDoV2 implements Externalizable {

    //region Поля

    /**
     * Наименование задачи
     */
    private String title;

    /**
     * Статус задачи
     */
    private boolean isDone;

    //endregion


    //region Конструкторы

    public ToDoV2() {}

    public ToDoV2(String title) {
        this.title = title;
        isDone = false;
    }

    //endregion


    //region Методы

    /**
     * Получить наименование задачи
     * @return наименование задачи
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получить статус выполнения задачи
     * @return статус выполнения задачи
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Изменить статус выполнения задачи
     * @param done новый статус задачи
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Переопределенный метод записи объекта в файл
     * @param out the stream to write the object to file
     * @throws IOException
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeBoolean(isDone);
    }

    /**
     * Переопределенный метод чтения объекта из файла
     * @param in the stream to read data from in order to restore the object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        isDone = in.readBoolean();
    }

    //endregion
}
