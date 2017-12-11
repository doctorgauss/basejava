package com.urise.webapp;

import static java.lang.Thread.sleep;

public class MainDeadlock {
    static Integer val1 = 5;
    static Integer val2 = 7;

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            try {
                int sum = sumNumber(val1, val2);
                System.out.println(sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                int sum = sumNumber(val2, val1);
                System.out.println(sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
    }

    static int sumNumber(Integer a, Integer b) throws InterruptedException {
        synchronized (a){
            System.out.println("Синхронизация по переменной a = " + a + ", " + Thread.currentThread().getName().toString());
            sleep(1000);
            synchronized (b){
                System.out.println("Синхронизация по переменной b = " + b + ", " + Thread.currentThread().getName().toString());
                return a + b;
            }
        }
    }
}
