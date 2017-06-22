package herdenmanagement;


import herdenmanagement.model.Acker;
import herdenmanagement.model.KreisendesRindvieh;
import herdenmanagement.model.Rindvieh;
import herdenmanagement.view.AckerView;

/**
 * Die Klasse dient der Orginisation von Rinderherden. Hierzu werden auf einem {@link Acker}
 * Objekte der Klase {@link herdenmanagement.model.Eimer} und {@link herdenmanagement.model.Gras}
 * positioniert. Objekte der Klasse {@link Rindvieh} könen sich auf einem Acker bewegen
 * und das Gras fressen oder rauchen. Steht auf der aktuellen Position einer Kuh ein Eimer,
 * kann diese auch gemolken werden.
 * <p>
 * Durch die Methode {@link #setzeAcker(Acker)} wird ein erzeugter Acker auch grafisch angezeigt.
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
 * Die Basisklasse HerdenController organisiert den eigentlichen Umgang den Acker und dessen
 * Anzeige. Sie kann ebenfalls mittels {@link #zeigeNachricht(String)} Nachrichten anzeigen.
 */
public class HerdenManager extends HerdenController {

    /**
     * Diese Methode lässt Gras wachsen und Rinder weiden.
     */
    public void manageHerde() {
        Acker mcPom = new Acker(5, 7);
        setzeAcker(mcPom);

        Rindvieh vera = new Rindvieh("Vera");
        mcPom.lassRindWeiden(vera);

        // KreisendesRindvieh erna = new KreisendesRindvieh("Erna");
        // erna.setzePosition(3,3);
        // mcPom.lassRindWeiden(erna);
        // erna.beginneBewegung();

        mcPom.lassGrasWachsen(1, 1);
        mcPom.stelleEimerAuf(2, 2);
        mcPom.lassGrasWachsen(2, 4);

        for (int k = 0; k < 4; k++) {
            for (int m = 0; m < 2; m++) {
                for (int o = 0; o < 2; o++) {
                    for (int i = 0; i < 4 + (o * 2); i++) {
                        vera.geheVor();
                    }
                    vera.dreheDichRechtsRum();
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < 4; i++) {
                vera.geheVor();
            }
            vera.dreheDichRechtsRum();

            for (int i = 0; i < 6; i++) {
                vera.geheVor();
            }
            vera.dreheDichRechtsRum();

            for (int i = 0; i < 4; i++) {
                vera.geheVor();
            }
            vera.dreheDichRechtsRum();

            for (int i = 0; i < 6; i++) {
                vera.geheVor();
            }
            vera.dreheDichRechtsRum();
        }

        /* vera.dreheDichRechtsRum();
        vera.geheVor();
        vera.dreheDichLinksRum();
        vera.geheVor();

        vera.frissGras();

        vera.dreheDichRechtsRum();
        vera.geheVor();
        vera.dreheDichLinksRum();
        vera.geheVor();

        int milch = vera.gibMilch();
        this.zeigeNachricht("Die Kuh gab " + milch + " Liter Milch!");

        vera.dreheDichRechtsRum();
        vera.geheVor();
        vera.geheVor();
        vera.raucheGras();

        this.zeigeNachricht("Vera ist jetzt satt und zufrieden.");*/
    }
}
