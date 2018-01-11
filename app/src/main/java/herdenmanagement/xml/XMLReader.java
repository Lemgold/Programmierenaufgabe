package herdenmanagement.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

import herdenmanagement.model.Acker;
import herdenmanagement.model.Position;
import herdenmanagement.model.Rindvieh;

public class XMLReader {

    private Serializer serializer;

    public XMLReader() {
        serializer = new Persister();
    }

    public Acker readAcker(InputStream inputStream) throws Exception {
        AckerTyp ackerTyp = serializer.read(AckerTyp.class, inputStream);
        Acker acker = new Acker(ackerTyp.getSpalten(), ackerTyp.getZeilen());

        for (RindviehTyp rindviehTyp: ackerTyp.getRindvieh()) {
            importRindvieh(rindviehTyp, acker);
        }

        for (EimerTyp eimerTyp: ackerTyp.getEimer()) {
            importEimer(eimerTyp, acker);
        }

        for (GrasTyp grasTyp: ackerTyp.getGras()) {
            importGras(grasTyp, acker);
        }

        return acker;
    }

    private void importGras(GrasTyp grasTyp, Acker acker) {
        acker.lassGrasWachsen(new Position(grasTyp.getPosition().getX(), grasTyp.getPosition().getY()));
    }

    private void importEimer(EimerTyp eimerTyp, Acker acker) {
        acker.stelleEimerAuf(new Position(eimerTyp.getPosition().getX(), eimerTyp.getPosition().getY()));
    }

    private void importRindvieh(RindviehTyp rindviehTyp, Acker acker) {
        Rindvieh rindvieh = new Rindvieh("");
        rindvieh.setzePosition(new Position(rindviehTyp.getPosition().getX(), rindviehTyp.getPosition().getY()));
        acker.lassRindWeiden(rindvieh);
    }
}
