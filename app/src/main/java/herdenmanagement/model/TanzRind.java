package herdenmanagement.model;

import android.media.MediaPlayer;

import de.ba.herdenmanagement.R;

/**
 * Tanzrinder erben alle Eigenschaften der Klasse {@link Rindvieh}. Sie können sich also
 * genauso auf einem Acker bewegen. Tanzrinder können zusätzlich seitwärts gehen.
 * Dies ist wichtig, zur Beherrschung für die meisten Standardtänze und lateinamerikanische
 * Bewegungsabläufe.
 * <p>
 * Es wird sichergestellt, dass die Kuh nicht über den Rand des Ackers hinaus gehen kann.
 * <p>
 * Im Muster Model View Controller sind Objekte dieser Klasse Bestandteil des Model.
 */
public class TanzRind extends Rindvieh {

    /**
     * Erzeugt ein TanzRind. Es wird nur der geebrte Contructor aufgerufen.
     *
     * @param name Name des Rindviehs
     */
    public TanzRind(String name) {
        super(name);
    }

    /**
     * @return Nächste Position rechts von der Kuh
     */
    protected Position gibNaechstePositionRechts() {
        Position position = gibPosition();

        if (gibRichtung() == RichtungsTyp.NORD) {
            position.x = position.x + 1;
            return position;
        } else if (gibRichtung() == RichtungsTyp.OST) {
            position.y = position.y - 1;
            return position;
        } else if (gibRichtung() == RichtungsTyp.SUED) {
            position.x = position.x - 1;
            return position;
        } else if (gibRichtung() == RichtungsTyp.WEST) {
            position.y = position.y + 1;
            return position;
        }

        // Da die Kuh stets in eine der obigen Richtungen schauen sollte,
        // kommen wir hier nur dann an, wenn die Richtung der Kuh nicht definiert ist.
        return position;
    }

    /**
     * @return Nächste Position links von der Kuh
     */
    protected Position gibNaechstePositionLinks() {
        Position position = gibPosition();

        if (gibRichtung() == RichtungsTyp.NORD) {
            position.x = position.x - 1;
            return position;
        } else if (gibRichtung() == RichtungsTyp.OST) {
            position.y = position.y + 1;
            return position;
        } else if (gibRichtung() == RichtungsTyp.SUED) {
            position.x = position.x + 1;
            return position;
        } else if (gibRichtung() == RichtungsTyp.WEST) {
            position.y = position.y - 1;
            return position;
        }

        // Da die Kuh stets in eine der obigen Richtungen schauen sollte,
        // kommen wir hier nur dann an, wenn die Richtung der Kh nicht definiert ist.
        return position;
    }

    /**
     * Bewegt das Rind seitwärts nach links
     */
    public void geheSeitwaertsNachLinks() {
        Position naechstePosition = gibNaechstePositionLinks();
        if (gehtsDaLinksWeiter()) {
            setzePosition(naechstePosition);
        } else {
            setzeNachricht(R.string.rindvieh_nix_acker);
        }
    }

    /**
     * Bewegt das Rind seitwärts nach rechts
     */
    public void geheSeitwaertsNachRechts() {
        Position naechstePosition = gibNaechstePositionRechts();
        Acker acker = gibAcker();
        if (acker.istPositionGueltig(naechstePosition)) {
            setzePosition(naechstePosition);
        } else {
            setzeNachricht(R.string.rindvieh_nix_acker);
        }
    }

    /**
     * Prüft die Grenzen des Ackers.
     *
     * @return true, wenn die Kuh auf dem Acker weiter nach lnks gehen kann
     */
    public boolean gehtsDaLinksWeiter() {
        Position naechstePosition = gibNaechstePositionLinks();
        Acker acker = gibAcker();
        return acker.istPositionGueltig(naechstePosition);
    }

    /**
     * Prüft die Grenzen des Ackers.
     *
     * @return true, wenn die Kuh auf dem Acker weiter nach links gehen kann
     */
    public boolean gehtsDaRechtsWeiter() {
        Position naechstePosition = gibNaechstePositionRechts();
        Acker acker = gibAcker();
        return acker.istPositionGueltig(naechstePosition);
    }

    public void chaChaCha(MediaPlayer player) {
        player.start();

        for (int i = 0; i < 10; i++) {
            geheVor();
            geheZurueck();
            geheSeitwaertsNachLinks();
            geheSeitwaertsNachLinks();
            geheZurueck();
            geheVor();
            geheSeitwaertsNachRechts();
            geheSeitwaertsNachRechts();
        }
    }
}
