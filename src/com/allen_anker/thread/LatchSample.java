package com.allen_anker.thread;

import java.util.concurrent.CountDownLatch;

public class LatchSample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new FirstBatchWorker(latch));
            t.start();
        }
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new SecondBatchWorker(latch));
            t.start();
        }
        while (latch.getCount() != 1) {
            Thread.sleep(100L);
        }
        System.out.println("Wait for first batch to finish");
        latch.countDown();
    }
}

class FirstBatchWorker implements Runnable {
    private CountDownLatch latch;

    public FirstBatchWorker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("First Batch Executed");
        latch.countDown();
    }
}

class SecondBatchWorker implements Runnable {
    private CountDownLatch latch;

    public SecondBatchWorker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("Second Batch Executed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}