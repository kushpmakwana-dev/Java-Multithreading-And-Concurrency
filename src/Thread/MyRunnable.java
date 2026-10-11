package Thread;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Hello I am calling you from RUNNABLE");
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable);

        t1.start();
    }
}
