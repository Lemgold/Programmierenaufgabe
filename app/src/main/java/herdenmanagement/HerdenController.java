package herdenmanagement;

import android.app.Activity;
import android.widget.Toast;

import herdenmanagement.model.Acker;
import herdenmanagement.view.AckerView;

/**
 * Diese Klasse wurde eingefügt, um komplexere Aufrufe im Rahmen des Herdenmanagements
 * zunächst auzublenden. Die erbende Klasse {@link HerdenManager} muss ihre Ablauflogik
 * nur noch in {@link HerdenManager#manageHerde()} abbilden.
 * <p>
 * Diee Klasse verknüpft im Wesentlichen einer {@link Acker} (= Model im MVC Muster) mit seiner
 * {@link AckerView} (= View im MVC Muster). Das Sie diese und andere Vorgänge
 * (insbesondere Änderungen auf Acker) organisiert, ist sie der COntroller im MVC Muster.
 * <p>
 * Nach Erzeugen einer Instanz sollte {@link #setzeAckerView(AckerView)} aufgerufen werden.
 * Innerhalb der hier noch abstrakten Methode {@link #manageHerde()} sollte zunächst ein
 * Acker erzeugt und mit {@link #setzeAcker(Acker)} dem HerdenController übergeben werden.
 */
public abstract class HerdenController {

    private AckerView ackerView;

    public void setzeAckerView(AckerView ackerView) {
        this.ackerView = ackerView;
    }

    public abstract void manageHerde();

    public void setzeAcker(Acker acker) {
        ackerView.setAcker(acker);
        acker.fuegeBeobachterHinzu(ackerView);
    }

    public void zeigeNachricht(final String nachricht) {
        ((Activity) ackerView.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ackerView.getContext(), nachricht, Toast.LENGTH_LONG).show();
            }
        });
    }
}
