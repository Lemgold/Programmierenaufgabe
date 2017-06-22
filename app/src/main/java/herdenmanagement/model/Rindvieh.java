package herdenmanagement.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Ein Rindvieh kann sich auf einem {@link Acker} bewegen. Hierzu erbt es die Eigenschaft,
 * eine Position auf einem Acker zu besitzen von {@link PositionsElement}. Zusätlich kann
 * eine Kuh diese Position aber auch ändern, zum Beispiel mit {@link #geheVor()}.
 * <p>
 * Es wird sichergestellt, dass die Kuh nicht über den Rand des Ackers hinaus gehen kann.
 * Ist ein Zielfeld der Bewegung ungültig, speichert die Kuh eine Nachricht in der Eigenschaft
 * {@link #nachricht}.
 * <p>
 * Im Muster Model View Controller sind Objekte dieser Klasse Bestandteil des Model.
 * Die beobachtete Kuh bietet einen Mechanismus, um Beobachter
 * mit {@link #fuegeBeobachterHinzu(PropertyChangeListener)}
 * an- und mit {@link #entferneBeobachter(PropertyChangeListener)} abzumelden und diese
 * über Änderungen mittels {@link PropertyChangeListener#propertyChange(PropertyChangeEvent)}
 * zu informieren.
 * <p>
 * Eigenschaften, die beobachtet werden können sind die des PositionsElements sowie
 * {@link #nachricht}, {@link #richtung}, {@link #status} und {@link #milchImEuter}.
 */
public class Rindvieh extends PositionsElement {

    /**
     * Schlüssel zur Kommunikation mit einem {@link PropertyChangeListener}.
     * Der Schlüssel wird als property der Methode {@link #informiereBeobachter(String, Object, Object)}
     * übergeben.
     * <p>
     * Der Schlüssel dient für Nachrichten zum Property {@link #richtung}.
     */
    public final static String PROPERTY_RICHTUNG = "herdenmanagement.model.Rindvieh.richtung";

    /**
     * Schlüssel zur Kommunikation mit einem {@link PropertyChangeListener}.
     * Der Schlüssel wird als property der Methode {@link #informiereBeobachter(String, Object, Object)}
     * übergeben.
     * <p>
     * Der Schlüssel dient für Nachrichten zum Property {@link #milchImEuter}.
     */
    public final static String PROPERTY_MILCH = "herdenmanagement.model.Rindvieh.milch";

    /**
     * Schlüssel zur Kommunikation mit einem {@link PropertyChangeListener}.
     * Der Schlüssel wird als property der Methode {@link #informiereBeobachter(String, Object, Object)}
     * übergeben.
     * <p>
     * Der Schlüssel dient für Nachrichten zum Property {@link #status}.
     */
    public final static String PROPERTY_STATUS = "herdenmanagement.model.Rindvieh.status";

    /**
     * Schlüssel zur Kommunikation mit einem {@link PropertyChangeListener}.
     * Der Schlüssel wird als property der Methode {@link #informiereBeobachter(String, Object, Object)}
     * übergeben.
     * <p>
     * Der Schlüssel dient für Nachrichten zum Property {@link #richtung}.
     */
    private RichtungsTyp richtung;

    /**
     * Name des Rindviehs. Kühe können nur bei Ihrer Erzeugung benannt werden.
     * Ein späteres Umbenennen ist nicht möglich.
     */
    private String name;

    /**
     * Anzahl (Liter) Milch im Euter. Die Zahl erhöht sich durch {@link #frissGras()}. Sie
     * wird reduziert durch {@link #gibMilch()}. Das Melken funktioniert jedoch nur,
     * wenn auf dem Acker an dieser Stelle ein Eimer steht.
     */
    private int milchImEuter;

    /**
     * Status der Kuh. Sie kann sich bewegen, fressen, rauchen oder warten.
     */
    private StatusTyp status;

    /**
     * Mögliche Richtungen, in die die Kuh schauen kann
     */
    public enum RichtungsTyp {
        /**
         * Konstante Werte für die Ausrichtung der Kuh
         */
        NORD(0),
        OST(1),
        SUED(2),
        WEST(3);

        /**
         * interer Speicher für die Richtung
         */
        final int nativeInt;

        /**
         * Von den konstanten Werten verwendeter Constructor
         *
         * @param ni Ausrichtung
         */
        RichtungsTyp(int ni) {
            nativeInt = ni;
        }
    }

    /**
     * Möglicher Status der Kuh, wichtig für die Anzeige von Bildern
     */
    public enum StatusTyp {
        /**
         * Konstante Werte für die Ausrichtung der Kuh
         */
        WARTET(0),
        FRISST(1),
        RAUCHT(2);

        /**
         * interer Speicher für den Status
         */
        final int nativeInt;

        /**
         * Von den konstanten Werten verwendeter Constructor
         *
         * @param ni Status
         */
        StatusTyp(int ni) {
            nativeInt = ni;
        }
    }

    /**
     * Setzt den Namen des Rindviehs, setzt den Status {@link StatusTyp#WARTET} und ruft
     * den geerbeten Constructor auf. Initial wird die Kuh zur Morgensonne ausgerichtet.
     *
     * @param name Name der Kuh
     */
    public Rindvieh(String name) {
        this.name = name;
        this.status = StatusTyp.WARTET;
        this.milchImEuter = 0;
        this.richtung = RichtungsTyp.OST;
    }

    /**
     * @return Aktueller Status der Kuh, zum Beispiel {@link StatusTyp#FRISST}
     */
    public StatusTyp gibStatus() {
        return status;
    }

    /**
     * Setzt den Status der Kuh. Dies ist von außen nicht möglich, nur ein Aufruf von
     * zum Beispiel {@link #frissGras()} oder {@link #gibMilch()} ändert den Status.
     * <p>
     * Die Observer werden bei Änderungen des Stauts informiert.
     *
     * @param status Neuer Status der Kuh
     */
    private void setzeStatus(StatusTyp status) {
        StatusTyp oldStatus = this.status;
        this.status = status;

        informiereBeobachter(PROPERTY_STATUS, oldStatus, status);
    }

    /**
     * Prüft die Milchmenge im Euter. Natürlich wird diese nicht während der Prüfung reduziert.
     * Dies ist nur mit {@link #gibMilch()} möglich.
     *
     * @return Anzahl Milch im Euter
     */
    public Integer messeMilchImEuter() {
        return milchImEuter;
    }

    /**
     * Setzt den Status der Kuh. Dies ist von außen nicht möglich, nur ein Aufruf von
     * {@link #frissGras()} oder {@link #gibMilch()} ändert die Milchmenge.
     * <p>
     * Die Observer werden bei Änderungen des Milchstandes informiert.
     *
     * @param milchImEuter Neue Milchmenge
     */
    private void setMilchImEuter(Integer milchImEuter) {
        Integer oldMilchImEuter = this.milchImEuter;
        this.milchImEuter = milchImEuter;

        informiereBeobachter(PROPERTY_MILCH, oldMilchImEuter, milchImEuter);
    }

    /**
     * Liefert die Richtung, in die der Kopf der Kuh zeigt. Bei Erzeugt einer Kuh schaut diese
     * nach {@link RichtungsTyp#OST}.
     *
     * @return Blickrichtung der Kuh
     */
    public RichtungsTyp gibRichtung() {
        return richtung;
    }

    /**
     * Setzt die Blickrichtung der Kuh. Dies ist von außen nicht möglich, nur ein Aufruf von
     * {@link #dreheDichLinksRum()} oder {@link #dreheDichRechtsRum()} ändert die Blickrichtung.
     *
     * @param richtung Neue Richtung
     */
    protected void setRichtung(RichtungsTyp richtung) {
        RichtungsTyp oldRichtung = this.richtung;
        this.richtung = richtung;

        warte();

        informiereBeobachter(PROPERTY_RICHTUNG, oldRichtung, richtung);
    }

    /**
     * Auf Basis der aktuellen Blickrichtung und Position der Kuh wird das Feld vor oder hinter
     * der Kuh berechnet. Hier erfolgt noch keine Prüfung, ob diese Position auf dem {@link Acker}
     * auch wirklich existiert.
     *
     * @param vor true = Prüfung in Blickrichtung der Kuh, false = Prüfung entgegen der Blickrichtung
     * @return Position vor oder hinter Kuh
     */
    protected Position gibNaechstePosition(boolean vor) {
        Position position = gibPosition();

        switch (gibRichtung()) {
            case NORD:
                position.y = position.y + (vor ? -1 : 1);
                break;
            case OST:
                position.x = position.x + (vor ? 1 : -1);
                break;
            case SUED:
                position.y = position.y + (vor ? 1 : -1);
                break;
            case WEST:
                position.x = position.x + (vor ? -1 : 1);
        }

        return position;
    }

    /**
     * Die Kuh wird in Blickrichtung (siehe {@link #richtung}) bewegt. Die Bewegung ist nur
     * möglich, wenn vor der Kuh auf dem {@link Acker} noch ein Feld existiert.
     */
    public void geheVor() {
        if (gehtsDaWeiter(true)) {
            Position newPosition = gibNaechstePosition(true);
            setzePosition(newPosition);
        } else {
            setzeNachricht(name + " steht da und schüttelt den Kopf: Vor mir ist kein Acker!");
        }
    }

    /**
     * Die Kuh wird entgegen der Blickrichtung (siehe {@link #richtung}) bewegt. Experten nennnen
     * dies eine "rückwärtige Bewegung", Kinder sagen gern auch "nach hinten". Die Bewegung ist nur
     * möglich, wenn hinter der Kuh auf dem {@link Acker} noch ein Feld existiert.
     */
    public void geheZurueck() {
        if (gehtsDaWeiter(false)) {
            Position newPosition = gibNaechstePosition(false);
            setzePosition(newPosition);
        } else {
            setzeNachricht(name + " steht da und schüttelt den Kopf: Hinter mir ist kein Acker!");
        }
    }

    /**
     * Diese Methode ändert die Blickrichtung der Kuh. Um die neue Richtung zu verstehen, muss
     * man sich in die Position der Kuh versetzen. Blickt sie momentan nach
     * {@link RichtungsTyp#OST}, so wird sie nach dem Aufruf dieser Methoden nach
     * {@link RichtungsTyp#NORD} schauen.
     *
     * Die {@link #position} der Kuh ändert sich durch die Drehbewegung nicht.
     */
    public void dreheDichLinksRum() {
        if (gibRichtung() == RichtungsTyp.OST) {
            setRichtung(RichtungsTyp.NORD);
        } else if (gibRichtung() == RichtungsTyp.NORD) {
            setRichtung(RichtungsTyp.WEST);
        } else if (gibRichtung() == RichtungsTyp.WEST) {
            setRichtung(RichtungsTyp.SUED);
        } else if (gibRichtung() == RichtungsTyp.SUED) {
            setRichtung(RichtungsTyp.OST);
        }
    }

    /**
     * Kühe drehen sich zwar lieber links herum, zur Not aber auch nach rechts. Diese Methode
     * ändert entsprechend die Blickrichtung der Kuh. Um die neue Richtung zu verstehen, muss
     * man sich in die Position der Kuh versetzen. Blickt sie momentan nach
     * {@link RichtungsTyp#OST}, so wird sie nach dem Aufruf dieser Methoden nach
     * {@link RichtungsTyp#SUED} schauen.
     *
     * Die {@link #position} der Kuh ändert sich durch die Drehbewegung nicht.
     */
    public void dreheDichRechtsRum() {
        if (gibRichtung() == RichtungsTyp.OST) {
            setRichtung(RichtungsTyp.SUED);
        } else if (gibRichtung() == RichtungsTyp.SUED) {
            setRichtung(RichtungsTyp.WEST);
        } else if (gibRichtung() == RichtungsTyp.WEST) {
            setRichtung(RichtungsTyp.NORD);
        } else if (gibRichtung() == RichtungsTyp.NORD) {
            setRichtung(RichtungsTyp.OST);
        }
    }

    /**
     * {@link Gras} kann man nicht nur fressen, sondern auch rauchen. Leider wird dabei keine Milch
     * erzeugt und ungesund ist es auch. Gerauchtes Gras wird vom {@link Acker} entfernt.
     * Während des Rauchens ist der Status der Kuh {@link StatusTyp#RAUCHT}.
     */
    public void raucheGras() {
        if (gibAcker().istDaGras(gibPosition())) {
            gibAcker().entferneGras(gibPosition());
            setzeStatus(StatusTyp.RAUCHT);

            warteLange();

            setzeStatus(StatusTyp.WARTET);
        } else {
            setzeNachricht(name + " steht da und schüttelt den Kopf: Hier ist nix zu rauchen!");
        }
    }

    /**
     * Der Hauptzweck von {@link Gras} ist es, von Kühen gefressen zu werden. Die Kuh prüft
     * zunächst, ob an der aktuellen Position (Abfrage mit {@link #gibPosition()}) Gras wächst.
     * Wenn ja, wird dies gefressen (also auch vom Acker entfernt). Wenn nein, speichert die Kuh
     * eine Fehlermeldung im Attribut {@link #nachricht}.
     */
    public void frissGras() {
        if (gibAcker().istDaGras(gibPosition())) {
            gibAcker().entferneGras(gibPosition());
            setzeStatus(StatusTyp.FRISST);

            warteLange();
            setMilchImEuter(messeMilchImEuter() + 1);

            setzeStatus(StatusTyp.WARTET);
        } else {
            setzeNachricht(name + " steht da und schüttelt den Kopf: Da ist gar kein Gras!");
        }
    }

    /**
     * Steht an der aktuellen Position auf dem {@link Acker} auch ein {@link Eimer}, kann die
     * Kuh gemolken werden. Nach dem Melken ist die Anzahl in {@link #milchImEuter} natürlich 0.
     * Soll eine Kuh ohne Milch im Euter gemolken werden, wird auch eine Fehlernachricht im
     * Attribut {@link #nachricht} gespeichert.
     *
     * Die Oberserver werden über die Reduzierung der Milchmenge informiert. Zusätzlich wird
     * der Erfolg oder Nicht-Erfolg des melkens als Nachricht gespeichert.
     *
     * @return Milch, die sich im Euter befand
     */
    public int gibMilch() {
        int result = messeMilchImEuter();

        if (gibAcker().istDaEinEimer(gibPosition())) {
            if (istMilchImEuter()) {
                setMilchImEuter(0);
            } else {
                setzeNachricht(name + " steht da und schüttelt den Kopf: Ich muss erst fressen!");
            }
        } else {
            result = 0;
            setzeNachricht(name + " steht da und schüttelt den Kopf: Da steht kein Eimer!");
        }
        return result;
    }

    /**
     * @return true, wenn {@link #milchImEuter} > 0
     */
    public boolean istMilchImEuter() {
        return messeMilchImEuter() > 0;
    }

    /**
      * @return Name der Kuh
     */
    public String gibName() {
        return name;
    }

    /**
     * Bei der Bewegung nach vorn oder hinten darf die Kuh nicht die Grenzen des Ackers
     * überschreiten. Diese werden hier geprüft. Die eigentliche Prüfung erfolgt durch
     * {@link Acker#istPositionGueltig(Position)}.
     *
     * @param vor true = Prüfung einer Bwegung um ein Feld vorwärts, false = rückwärts
     * @return true = Bewegung ist möglich
     */
    private boolean gehtsDaWeiter(boolean vor) {
        Position naechstePosition = gibNaechstePosition(vor);
        return gibAcker().istPositionGueltig(naechstePosition);
    }

    /**
     * Bei der Bewegung nach vorn darf die Kuh nicht die Grenzen des Ackers
     * überschreiten. Diese werden hier geprüft. Die eigentliche Prüfung erfolgt durch
     * {@link Acker#istPositionGueltig(Position)}.
     *
     * @return true = Bewegung ist möglich
     */
    public boolean gehtsDaWeiterVor() {
        return gehtsDaWeiter(true);
    }

    /**
     * Bei der Rückwärtsbewegung darf die Kuh nicht die Grenzen des Ackers
     * überschreiten. Diese werden hier geprüft. Die eigentliche Prüfung erfolgt durch
     * {@link Acker#istPositionGueltig(Position)}.
     *
     * @return true = Bewegung ist möglich
     */
    public boolean gehtsDaWeiterZurueck() {
        return gehtsDaWeiter(false);
    }
}
