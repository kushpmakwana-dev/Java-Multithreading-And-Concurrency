package Thread;

public class MyThreads extends Thread{
    @Override
    public void run() {
        System.out.println(currentThread().getName() + " is entering the RUN");
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(currentThread().getName() + " is executing for the: " + i + "th time");
                Thread.sleep(500);
            }
        } catch (InterruptedException e){
            System.out.println("Thread is interrupted");
        }
    }

    public static void main(String[] args) {
        MyThreads thread1 = new MyThreads();
        MyThreads thread2 = new MyThreads();

        thread1.start();
        thread2.start();
    }
}
