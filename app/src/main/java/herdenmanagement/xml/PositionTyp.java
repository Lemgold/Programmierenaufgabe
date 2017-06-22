package herdenmanagement.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class PositionTyp {

    @Attribute
    private int x;

    @Attribute
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
