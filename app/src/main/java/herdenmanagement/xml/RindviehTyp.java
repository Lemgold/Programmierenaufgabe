package herdenmanagement.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class RindviehTyp {

    @Element
    private PositionTyp position;

    public PositionTyp getPosition() {
        return position;
    }

    public void setPosition(PositionTyp position) {
        this.position = position;
    }
}
