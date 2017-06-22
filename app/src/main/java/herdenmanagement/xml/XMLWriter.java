package herdenmanagement.xml;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;

import herdenmanagement.model.Acker;
import herdenmanagement.model.Eimer;
import herdenmanagement.model.Gras;
import herdenmanagement.model.Rindvieh;

/**
 * Die Klasse schreibt Acker-Objekte aus dem Modell in eine XML Datei.
 */
public class XMLWriter {

    private Serializer serializer;


    public XMLWriter() {
        serializer = new Persister();
    }

    public void exportAcker(Context context, Acker acker) throws Exception {

        // create a file and export all data
        String shortName = "acker.xml";

        // get a cache dir, we prefer the external cache, since we do not delete it's content
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null || cacheDir.length() == 0 || cacheDir.getAbsolutePath().equals("/")) {
            cacheDir = context.getCacheDir();
        }

        File file = new File(cacheDir, shortName);
        FileOutputStream fos = new FileOutputStream(file);

        AckerTyp ackerTyp = exportAcker(acker);
        serializer.write(ackerTyp, fos);

        // make it readable for sharing it
        file.setReadable(true, false);

        // Log.d("written to " + file.getAbsolutePath());

        // create the intent for sharing
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/xml");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(Intent.createChooser(intent, null));
    }

    private AckerTyp exportAcker(Acker acker) {
        AckerTyp ackerTyp = new AckerTyp();

        ackerTyp.setSpalten(acker.zaehleSpalten());
        ackerTyp.setZeilen(acker.zaehleZeilen());

        for (Rindvieh rindvieh : acker.getViecher()) {
            RindviehTyp rindviehTyp = exportRindvieh(rindvieh);
            ackerTyp.getRindvieh().add(rindviehTyp);
        }

        for (Eimer eimer : acker.getEimer()) {
            EimerTyp eimerTyp = exportEimer(eimer);
            ackerTyp.getEimer().add(eimerTyp);
        }

        for (Gras gras : acker.getGraeser()) {
            GrasTyp grasTyp = exportGras(gras);
            ackerTyp.getGras().add(grasTyp);
        }

        return ackerTyp;
    }

    private RindviehTyp exportRindvieh(Rindvieh rindvieh) {
        RindviehTyp rindviehTyp = new RindviehTyp();

        PositionTyp positionTyp = new PositionTyp();
        positionTyp.setX(rindvieh.gibPosition().x);
        positionTyp.setY(rindvieh.gibPosition().y);
        rindviehTyp.setPosition(positionTyp);

        return rindviehTyp;
    }

    private GrasTyp exportGras(Gras gras) {
        GrasTyp grasTyp = new GrasTyp();

        PositionTyp positionTyp = new PositionTyp();
        positionTyp.setX(gras.gibPosition().x);
        positionTyp.setY(gras.gibPosition().y);
        grasTyp.setPosition(positionTyp);

        return grasTyp;
    }

    private EimerTyp exportEimer(Eimer eimer) {
        EimerTyp eimerTyp = new EimerTyp();

        PositionTyp positionTyp = new PositionTyp();
        positionTyp.setX(eimer.gibPosition().x);
        positionTyp.setY(eimer.gibPosition().y);
        eimerTyp.setPosition(positionTyp);

        return eimerTyp;
    }
}
