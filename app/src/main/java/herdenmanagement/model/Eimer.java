package herdenmanagement.model;


/**
 * Im Muster Model View Controller sind Objekte dieser Klasse Bestandteil des Model.
 */

public class Eimer extends PositionsElement {

    public void positioniereDich(Acker acker, Position position) {
        setzeAcker(acker);
        setzePosition(position);
    }
}
