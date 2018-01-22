package herdenmanagement.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import herdenmanagement.model.PositionsElement;

/**
 * Basisklasse für die Darstellung von Eimer, Kühen, etc. Diese View kann auf Änderungen
 * an den Properties ihres PositionsElements reagieren, in der regel durch Anpassung der Anzeige.
 * <p>
 * Im Muster Model View Controller sind Objekte dieser Klasse Bestandteil des Model.
 * <p>
 * Im Muster Obersever ist die Klasse ein ConcreteObserer, der PropertyChangeListener
 * ist das implementierte Observer Interface.
 */
public class PositionElementView extends AppCompatImageView implements PropertyChangeListener {

    /**
     * Animator für die Aktualisierung der GUI
     */
    private Animator animator;

    /**
     * {@link PositionsElement}, welches hier dargestellt wird. Das {@link PositionsElement}
     * kennt seine View nicht, nur die View kennt ihr Modell.
     */
    private PositionsElement positionsElement;

    /**
     * Erzeugt die Ansicht für ein PositionsElement.
     *
     * @param context          Context der App
     * @param positionsElement Darzustellendes Element
     */
    public PositionElementView(Context context, Animator animator, PositionsElement positionsElement) {
        super(context);

        this.animator = animator;

        // PositionsElement merken und observieren
        this.positionsElement = positionsElement;
        this.positionsElement.fuegeBeobachterHinzu(this);
        // ID des PositionsElement übernehmen
        setId(positionsElement.gibId());

        // Bild setzen
        setPadding(4, 4, 4, 4);
        setImageBitmap(getAktuellesBild());
    }

    /**
     * @return Dargestelltes PositionsElement
     */
    public PositionsElement getPositionsElement() {
        return positionsElement;
    }

    /**
     * Die Klassen im Modell des HerdenManagers senden ihren View-Klassen ein
     * PropertyChangeEvent, wenn sich an einer Eigenschaft etwas geändert hat.
     * Die View-Klassen reagieren in der Regel mit der Anpssung ihrer grafischen Darstellung.
     *
     * @param evt PropertyChangeEvent, das die Änderung (Value) und die Art der Änderung (Property Name) beschreibt
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {

        // Nachricht anzeigen
        if (PositionsElement.PROPERTY_NACHRICHT.equals(evt.getPropertyName())) {
            Object nachricht = evt.getNewValue();

            // Ist die Nachricht ein String, wird dieser direkt angezeigt
            if (nachricht instanceof String) {
                toast((String) evt.getNewValue());
            }

            // Ist die Nachricht eine Zahl, wird sie zunächst als ID im Ressourcen-Bundle interpretiert
            // Klappt das nicht, wird die Zahl gezeigt
            if (nachricht instanceof Number) {
                try {
                    String text = getContext().getResources().getString(((Number) nachricht).intValue(), positionsElement.gibName());
                    toast(text);
                } catch (Resources.NotFoundException e) {
                    toast("" + nachricht);
                }
            }

            return;
        }

        final Bitmap bitmap = getAktuellesBild();

        // Bei Änderungen der Position, muss ein neues Layout berechnet werden
        animator.performAction(new Animator.Action() {
            @Override
            public void run() {
                // image animiert setzen
                if (getParent() instanceof ViewGroup) {
                    TransitionManager.beginDelayedTransition((ViewGroup) getParent());
                }

                // Bild aktualisieren
                setImageBitmap(bitmap);
            }
        });
    }

    /**
     * Zeigt den Toast mittels Animator, dadurch wird auch stets der
     * UI Thread zur Anzeige verwendet.
     *
     * @param text Anzuzeigender Text
     */
    private void toast(final String text) {
        animator.performAction(new Animator.Action() {
            @Override
            public void run() {
                Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Diese Methode sollte von allen erbenden Klassen überladen werden, um entsprechend des
     * PositionsElements ein passendes Bild zu liefern.
     *
     * @return Bild auf Basis des {@link PositionsElement}
     */
    protected Bitmap getAktuellesBild() {
        return null;
    }
}
