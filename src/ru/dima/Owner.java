package ru.dima;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Owner implements Runnable {
    private List<Item> inventory = new ArrayList<>();
    private String name;
    private int aspeed;

    public Owner(String name, int aspeed) {
        this.name = name;
        this.aspeed = aspeed;
    }

    @Override
    public void run() {
        try {
            buy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void buy() throws InterruptedException {
        while (true) {
            Flat.lock.lock();
            inventory.add(shop.getRandomItem());
            Flat.getContent().addAll(inventory);
            System.out.println("Хозяин " + name + " положил: " + inventory);
            inventory.clear();
            Flat.sortItems();
            Flat.lock.unlock();
            Thread.sleep(aspeed);
        }
    }

    private static class shop {
        static String[] names = {"Часы", "Телефон", "Телевизор", "Лампочка", "Пакет огурцов", "Рыба", "Мусорный пакет", "Кошка", "Аспарагус", "Шруповёрт", "Нафаня"};

        public static Item getRandomItem() {
            Random random = new Random();
            return new Item(random.nextInt(20), random.nextInt(20),getRandomName());
        }

        private static String getRandomName() {
            Random random = new Random();
            return names[random.nextInt(names.length - 1)];
        }
    }
}
