package ru.dima;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Flat {
    private static List<Item> content = new ArrayList<>();
    public static Lock lock = new ReentrantLock();

    public static List<Item> getContent() {
        return content;
    }

    public static void beRobbed(int i) {
        content.remove(i);
    }

    public static void sortItems() {
        content.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare(o2.getValue(), o1.getValue());
            }
        });
    }

    public static void setContent(List<Item> content) {
        Flat.content = content;
    }
}
