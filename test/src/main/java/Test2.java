import java.util.concurrent.Semaphore;

/**
 * 输出ABC
 * Created by youzhihao on 2019/2/19.
 */
public class Test2 {

    private static Semaphore semaphoresA = new Semaphore(1);

    private static Semaphore semaphoresB = new Semaphore(0);

    private static Semaphore semaphoresC = new Semaphore(0);


    public static void main(String[] args) {
        MajusculeABC maj = new MajusculeABC();
        Thread t_a = new Thread(new Thread_ABC(maj, 'A'));
        Thread t_b = new Thread(new Thread_ABC(maj, 'B'));
        Thread t_c = new Thread(new Thread_ABC(maj, 'C'));
        t_a.start();
        t_b.start();
        t_c.start();
    }


    static class MajusculeABC {

        public void print(char c) {
            System.out.println(c);
        }
    }

    static class Thread_ABC implements Runnable {

        private char c;

        private MajusculeABC majusculeABC;

        public Thread_ABC(MajusculeABC majusculeABC, char c) {
            this.c = c;
            this.majusculeABC = majusculeABC;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    switch (c) {
                        case 'A':
                            semaphoresA.acquire(1);
                            majusculeABC.print(c);
                            semaphoresB.release(1);
                            break;
                        case 'B':
                            semaphoresB.acquire(1);
                            majusculeABC.print(c);
                            semaphoresC.release(1);
                            break;
                        case 'C':
                            semaphoresC.acquire(1);
                            majusculeABC.print(c);
                            semaphoresA.release(1);
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
