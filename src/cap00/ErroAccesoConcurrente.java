package cap00;

public class ErroAccesoConcurrente {
    public static void main(String[] args) throws InterruptedException {
        Thread hilo1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Hilo 1 - Iteración " + i);
                try {
                    Thread.sleep(1000); // Simula una tarea que lleva tiempo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread hilo2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Hilo 2 - Iteración " + i);
                try {
                    Thread.sleep(1000); // Simula una tarea que lleva tiempo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        hilo1.start(); // Iniciar el primer hilo
        hilo2.start(); // Iniciar el segundo hilo

        try {
            hilo1.join(); // Esperar a que el primer hilo termine
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        	hilo2.join();
        System.out.println("Hilo principal continúa después de esperar a hilo1.");

        // Puedes agregar hilo2.join() si también quieres esperar a que el segundo hilo termine.
    }
}
