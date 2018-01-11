package herdenmanagement.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.ba.herdenmanagement.R;

import herdenmanagement.model.Eimer;

public class EimerView extends PositionElementView {

    public EimerView(Context context, Animator animator, Eimer eimer) {
        super(context, animator, eimer);
    }

    protected Bitmap getAktuellesBild() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.eimer);
    }
}
