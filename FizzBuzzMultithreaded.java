package FizzBuzzMultithreaded;

import java.util.concurrent.*;


public class FizzBuzzMultithreaded {
    private int n;

    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private CyclicBarrier barrier = new CyclicBarrier(4);

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public void fizz() throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                queue.put("fizz");
            }
            barrier.await();
        }
    }

    public void buzz() throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i++) {
            if (i % 5 == 0 && i % 3 != 0) {
                queue.put("buzz");
            }
            barrier.await();
        }
    }

    public void fizzbuzz() throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i++) {

            if (i % 3 == 0 && i % 5 == 0) {
                queue.put("fizzbuzz");
            }
            barrier.await();
        }
    }

    public void number() throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i++) {
            barrier.await();
            String output = queue.poll();
            if (output == null) {
                output = String.valueOf(i);
            }
            System.out.print(output);
            if (i < n) System.out.print(", ");
        }
    }

    public void run() {
        Thread threadA = new Thread(() -> {
            try {
                fizz();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                buzz();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzbuzz();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                number();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(30);
        fizzBuzz.run();
    }
}

