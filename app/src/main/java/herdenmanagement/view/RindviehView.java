package herdenmanagement.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.ba.herdenmanagement.R;

import herdenmanagement.model.Rindvieh;

public class RindviehView extends PositionElementView {

    public RindviehView(Context context, Animator animator, Rindvieh rindvieh) {
        super(context, animator, rindvieh);
    }

    protected Bitmap getAktuellesBild() {
        if (Rindvieh.StatusTyp.FRISST.equals(getRindvieh().gibStatus())) {
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kuh_gras);
        } else if (Rindvieh.StatusTyp.RAUCHT.equals(getRindvieh().gibStatus())) {
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kuh_rauch);
        }

        if (getRindvieh().gibRichtung() == Rindvieh.RichtungsTyp.NORD) {
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kuh_hinten);
        } else if (getRindvieh().gibRichtung() == Rindvieh.RichtungsTyp.WEST) {
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kuh_links);
        } else if (getRindvieh().gibRichtung() == Rindvieh.RichtungsTyp.SUED) {
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kuh_vorn);
        } else if (getRindvieh().gibRichtung() == Rindvieh.RichtungsTyp.OST) {
            return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kuh_rechts);
        }

        return null;
    }

    public Rindvieh getRindvieh() {
        return (Rindvieh) getPositionsElement();
    }
}
