package herdenmanagement.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.ba.herdenmanagement.R;

import herdenmanagement.model.Gras;

public class GrasView extends PositionElementView {

    public GrasView(Context context, Gras gras) {
        super(context, gras);
    }

    protected Bitmap getAktuellesBild() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gras);
    }

    public Gras getGras() {
        return (Gras) getPositionsElement();
    }
}