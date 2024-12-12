package org.example;

public class Lucky {
    static int x = 0;
    static int count = 0;

    static class LuckyThread extends Thread {
        private Object lock;

        public LuckyThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true) {
                int current;
                synchronized (lock) {
                    if (x > 999999) {
                        break;
                    }
                    current = x++;
                }

                if ((current % 10) + (current / 10) % 10 + (current / 100) % 10 ==
                        (current / 1000) % 10 + (current / 10000) % 10 + (current / 100000) % 10) {
                    synchronized (lock) {
                        count++;
                        System.out.println(Thread.currentThread().getName() + " " + current);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new LuckyThread(lock);
        Thread t2 = new LuckyThread(lock);
        Thread t3 = new LuckyThread(lock);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Total: " + count);
    }
}
