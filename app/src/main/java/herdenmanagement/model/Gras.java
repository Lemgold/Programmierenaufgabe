package herdenmanagement.model;

/**
 * Im Muster Model View Controller sind Objekte dieser Klasse Bestandteil des Model.
 */
public class Gras extends PositionsElement {

    /**
     * Er sprach "Wachse und gedeihe" und es wuchs und gedieh. Die Methode
     * verknüpft das Gras mit dem Acker (siehe {@link Acker#graeser}) und setzt
     * die Position für das Gras. Eine Prüfung der Position erfolgt nicht.
     * Theoretisch kann man also auch Gras außerhalb des Ackers platzieren.
     * Aber warum?
     *
     * @param acker Acker, auf dem das Gras wachsen soll
     * @param position Position auf dem Acker für das Gras
     */
    public void wachseUndGedeihe(Acker acker, Position position) {
        setzeAcker(acker);
        setzePosition(position);
    }
}
