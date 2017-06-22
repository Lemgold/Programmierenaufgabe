package herdenmanagement.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import herdenmanagement.model.Position;
import herdenmanagement.model.PositionsElement;

public class PositionElementView extends AppCompatImageView implements PropertyChangeListener {

    private PositionsElement positionsElement;

    // private ImageView imageView;

    public PositionElementView(Context context, PositionsElement positionsElement) {
        super(context);

        // PositionsElement merken und observieren
        this.positionsElement = positionsElement;
        this.positionsElement.fuegeBeobachterHinzu(this);
        // ID des PositionsElement übernehmen
        setId(positionsElement.gibId());

        // Bild setzen
        setPadding(4, 4, 4, 4);
        aktualisiereBild();
    }

    public PositionsElement getPositionsElement() {
        return positionsElement;
    }

    public Position getPosition() {
        return positionsElement.gibPosition();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Nachricht anzeigen
                if (PositionsElement.PROPERTY_NACHRICHT.equals(evt.getPropertyName())) {
                    Toast.makeText(getContext(), (String) evt.getNewValue(), Toast.LENGTH_LONG).show();
                    return;
                }

                // Bei Änderungen der Position, muss ein neues Layout berechnet werden
                if (PositionsElement.PROPERTY_POSITION.equals(evt.getPropertyName())) {
                    // Bild aktualisieren
                    aktualisiereBild();

                    // neues Layout beantragen
                    if (getParent() != null) {
                        getParent().requestLayout();
                    }

                    return;
                }


                // Bild aktualisieren
                aktualisiereBild();
                // neu zeichnen veranlassen
                invalidate();

                if (getParent() != null) {
                    getParent().requestLayout();
                }
            }
        });
    }

    protected Bitmap getAktuellesBild() {
        return null;
    }

    protected void aktualisiereBild() {
        Bitmap bitmap = getAktuellesBild();

        // image animiert setzen
        if (getParent() instanceof ViewGroup) {
            TransitionManager.beginDelayedTransition((ViewGroup) getParent());
        }

        setImageBitmap(bitmap);
    }
}
