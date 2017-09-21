package herdenmanagement;

import android.view.View;
import android.widget.Toast;

import de.ba.herdenmanagement.R;
import herdenmanagement.model.Acker;
import herdenmanagement.model.Rindvieh;
import herdenmanagement.view.AckerView;

/**
 * Die Klasse dient der Orginisation von Rinderherden. Hierzu werden auf einem {@link Acker}
 * Objekte der Klase {@link herdenmanagement.model.Eimer} und {@link herdenmanagement.model.Gras}
 * positioniert. Objekte der Klasse {@link Rindvieh} könen sich auf einem Acker bewegen
 * und das Gras fressen oder rauchen. Steht auf der aktuellen Position einer Kuh ein Eimer,
 * kann diese auch gemolken werden.
 * <p>
 * Mit einer {@link AckerView} wird ein erzeugter Acker auch grafisch angezeigt.
 * Auf diesem können Instanzen von {@link Rindvieh}, {@link herdenmanagement.model.Eimer} und
 * {@link herdenmanagement.model.Gras} eingefügt werden.
 * <p>
 * Im Muster Model View Controller (MVC) entsprechen Objekte dieser Klasse dem Controller.
 * {@link Acker}, {@link Rindvieh}, {@link herdenmanagement.model.Eimer} und
 * {@link herdenmanagement.model.Gras} bilden im MVC Muster das Model. Im Muster Observer
 * stellen sie die beobachtbaren Objekte dar. Die eigentliche grafische Darstellung des Models
 * erfolgt in den View-Klassen des MVC Musters (also zum Beispiel in der Klasse
 * {@link herdenmanagement.view.RindviehView}. Diese View-Klassen sind gleichzeit Beobachter
 * gemäß des Observer Muster. Wenn man also Veränderungen an einer Kuh vornimmt, wird diese
 * Ihre Beaobachter informieren und diese passen ihre grafische Darstellung an.
 * <p>
 * Die Klasse verknüpft im Wesentlichen einer {@link Acker} (= Model im MVC Muster) mit seiner
 * {@link AckerView} (= View im MVC Muster). Das Sie diese und andere Vorgänge
 * (insbesondere Änderungen auf Acker) organisiert, ist sie der COntroller im MVC Muster.
 */
public class HerdenManager {

    /**
     * Grafische Darstellung des Ackers
     */
    private AckerView ackerView;

    /**
     * Zu steuerndes Rindvieh
     */
    private Rindvieh vera;

    /**
     * Diese Methode lässt Gras wachsen und Rinder weiden.
     */
    public void manageHerde(final MainActivity mainActivity) {
        // Acker erzeugen
        Acker mcPom = new Acker(5, 7);

        // AckerView mit Acker verknüpfen
        ackerView = (AckerView) mainActivity.findViewById(R.id.acker_view);
        ackerView.setAcker(mcPom);
        mcPom.fuegeBeobachterHinzu(ackerView);

        // Rinder erzeugen
        vera = new Rindvieh("Vera");
        mcPom.lassRindWeiden(vera);

        mcPom.lassGrasWachsen(1, 1);
        mcPom.stelleEimerAuf(2, 2);
        mcPom.lassGrasWachsen(2, 4);
    }
}
