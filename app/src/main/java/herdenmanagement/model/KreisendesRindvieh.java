package herdenmanagement.model;


public class KreisendesRindvieh extends Rindvieh implements Runnable {

    public KreisendesRindvieh(String name) {
        super(name);
    }


    private Position altePosition = new Position(1, 1);

    private boolean mussAufhoeren = false;

    private boolean kurzUnterbrechen = false;


    public void beginneBewegung() {
        new Thread(this).start();
    }


    public void beendeBewegung() {
        mussAufhoeren = true;
    }


    public void run() {
        while (!mussAufhoeren) {
            for (int i = 0; (i < 4) && (!mussAufhoeren); i++) {
                int j = 0;
                while ((j < 2) && (!mussAufhoeren)) {
                    while (kurzUnterbrechen) {
                        // sleep 50ms
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ignored) {
                        }
                    }


                    geheVor();
                    j++;
                }
                dreheDichRechtsRum();
            }
        }
    }


    public void setzeUrsprung(int x, int y) {
        kurzUnterbrechen = true;

        Position neuePosition = new Position(
                gibPosition().x + (x - altePosition.x),
                gibPosition().y + (y - altePosition.y));

        if (gibAcker().istPositionGueltig(neuePosition)) {
            setzePosition(neuePosition);
            altePosition = new Position(x, y);
        }

        if (gibAcker().istPositionGueltig(new Position(x - 1, y - 1)) && gibAcker().istPositionGueltig(new Position(x + 1, y + 1))) {

            kurzUnterbrechen = false;

        } else {
            setzeNachricht(gibName() + " steht da und schüttelt den Kopf: So kann ich nicht arbeiten!\nIch kann keine Position am äußersten Feldrand beziehen.");
        }
    }
}
