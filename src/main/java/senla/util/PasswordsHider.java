package senla.util;

public class PasswordsHider extends Thread {
    public PasswordsHider(String prompt) {
        super("Hiding passwords thread");
        System.out.print(prompt);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.print("\010*");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
