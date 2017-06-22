package herdenmanagement.model;

/**
 * Im Muster Model View Controller sind Objekte dieser Klasse Bestandteil des Model.
 */
public class Gras extends PositionsElement {

    public void wachseUndGedeihe(Acker acker, Position position) {
        setzeAcker(acker);
        setzePosition(position);
    }
}
