package herdenmanagement.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "acker")
public class AckerTyp {

    @Attribute
    protected int spalten;

    @Attribute
    protected int zeilen;

    @ElementList(inline = true, required = false, entry = "eimer")
    protected List<EimerTyp> eimer;

    @ElementList(inline = true, required = false, entry = "gras")
    protected List<GrasTyp> gras;

    @ElementList(inline = true, required = false, entry = "rindvieh")
    protected List<RindviehTyp> rindvieh;

    public int getSpalten() {
        return spalten;
    }

    public void setSpalten(int spalten) {
        this.spalten = spalten;
    }

    public int getZeilen() {
        return zeilen;
    }

    public void setZeilen(int zeilen) {
        this.zeilen = zeilen;
    }

    public List<EimerTyp> getEimer() {
        if (eimer == null) {
            eimer = new ArrayList<>();
        }

        return eimer;
    }

    public void setEimer(List<EimerTyp> eimer) {
        this.eimer = eimer;
    }

    public List<GrasTyp> getGras() {
        if (gras == null) {
            gras = new ArrayList<>();
        }

        return gras;
    }

    public void setGras(List<GrasTyp> gras) {
        this.gras = gras;
    }

    public List<RindviehTyp> getRindvieh() {
        if (rindvieh == null) {
            rindvieh = new ArrayList<>();
        }

        return rindvieh;
    }

    public void setRindvieh(List<RindviehTyp> rindvieh) {
        this.rindvieh = rindvieh;
    }
}
