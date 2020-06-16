package ru.dima;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Thief implements Runnable {
    private Backpack backpack;
    private int moneys;
    private String name;
    private int aspeed;

    public Thief(String name, int aspeed) {
        this.name = name;
        this.aspeed = aspeed;
    }

    @Override
    public void run() {
        backpack = new Backpack();
        while (true) {
            Flat.lock.lock();
            System.out.println("В квартире: " + Flat.getContent());
            steal();
            System.out.println("Вор " + name + ": " + backpack);
            Flat.lock.unlock();
            try {
                Thread.sleep(aspeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void steal() {
        int trade = 0;

        for (int i = 0; i < Flat.getContent().size(); i++) {
            Item thisItem = Flat.getContent().get(i);
            if (backpack.newItem(thisItem)) {
                Flat.beRobbed(i);
                System.out.println("Вор " + name + " украл: " + thisItem);
                return;
            }
        }
        for (int i = 0; i < backpack.items.size(); i++) {
            moneys += backpack.items.get(i).getValue();
            trade += backpack.items.get(i).getValue();
        }
        System.out.println("Вор " + name + " продал награбленное на сумму " + trade + " рублей (всего у него " + moneys + " рублей)");
        trade = 0;
        backpack.size = 0;
        backpack.items.clear();
    }

    private static class Backpack {
        private final int spaciousness = 100;
        private int size = 0;
        private List<Item> items = new ArrayList<>();

        public int getSpaciousness() {
            return spaciousness;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public boolean newItem(Item newItem) {
            if (isFits(newItem)) {
                items.add(newItem);
                size += newItem.getWeight();
                return true;
            } else {
                return false;
            }
        }

        public boolean isFullness() {
            return size == spaciousness;
        }

        private boolean isFits(Item item) {
            return item.getWeight() <= spaciousness - size;
        }

        @Override
        public String toString() {
            if (items.isEmpty()) {
                return "Рюкзак пуст";
            }
            StringBuilder list = new StringBuilder("Вещи внутри рюкзака - " + items.get(0));
            for (int i = 1; i < items.size(); i++) {
                list.append(", ").append(items.get(i));
            }
            return list.toString();
        }
    }
}
