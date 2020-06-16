package ru.dima;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread ownerThread = new Thread(new Owner("Белый Негр", 5000));
        ownerThread.start();
        Thread ownerThread2 = new Thread(new Owner("Водитель маршрутки", 1000));
        ownerThread2.start();
        Thread ownerThread3 = new Thread(new Owner("Ингуш", 10000));
        ownerThread3.start();
        
        Thread thiefThread = new Thread(new Thief("Нафаня", 1000));
        thiefThread.start();
        Thread thiefThread2 = new Thread(new Thief("Амораптор", 10000));
        thiefThread2.start();
        Thread thiefThread3 = new Thread(new Thief("Хренодёр", 4000));
        thiefThread3.start();
    }
}
