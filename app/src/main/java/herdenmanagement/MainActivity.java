package herdenmanagement;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.InputStream;

import de.ba.herdenmanagement.R;

import herdenmanagement.model.Acker;
import herdenmanagement.view.AckerView;
import herdenmanagement.view.Animator;
import herdenmanagement.xml.XMLReader;
import herdenmanagement.xml.XMLWriter;

/**
 * Die Klasse wurde vom Android angelegt und sorgt für die Anzeige der App auf dem Handy.
 * Hierzu wird unser {@link HerdenManager} initialisiert und mit einer {@link AckerView}
 * verknüpft.
 */
public class MainActivity extends Activity {

    private HerdenManager herdenManager;

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(android.net.Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // read a file from the startup intent
        Intent intent = getIntent();
        if (intent != null && !Intent.ACTION_MAIN.equals(intent.getAction())) {
            readFileFromIntent(intent);
        }
    }

    /**
     * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
     * the activity had been stopped, but is now again being displayed to the
     * user.  It will be followed by {@link #onResume}.
     * <p>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onCreate
     * @see #onStop
     * @see #onResume
     */
    protected void onStart() {
        super.onStart();

        // erzeugt einen HerdenManager
        herdenManager = new HerdenManager();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Während manageHerde möchten wir alle Aktionen sehen
                AckerView ackerView = (AckerView)findViewById(R.id.acker_view);

                // Acker einrichten, dies soll in einem "Rutsch" passieren,
                // die einzelnen Aktionen werden nicht animiert
                ackerView.setThreading(Animator.Threading.ASYNCHRONOUS_NO_WAIT);
                herdenManager.richteAckerEin(MainActivity.this);

                // Während manageHerde möchten wir alle Aktionen einzeln nachvollziehen
                ackerView.setThreading(Animator.Threading.SYNCHRONOUS);

                // bewegt ein Rind oder mehrer auf dem Acker
                herdenManager.manageHerde(MainActivity.this);

                // Alle Aktionen auf dem Acker, die jetzt folgen, werden direkt asynchron
                // ausgeführt. Betroffen sind vor allem Button-Clicks
                ackerView.setThreading(Animator.Threading.ASYNCHRONOUS);
            }
        }).start();
    }

    /**
     * Called by the system when the device configuration changes while your
     * activity is running.  Note that this will <em>only</em> be called if
     * you have selected configurations you would like to handle with the
     * {@link android.R.attr#configChanges} attribute in your manifest.  If
     * any configuration change occurs that is not selected to be reported
     * by that attribute, then instead of reporting it the system will stop
     * and restart the activity (to have it launched with the new
     * configuration).
     *
     * <p>At the time that this function has been called, your Resources
     * object will have been updated to return resource values matching the
     * new configuration.
     *
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Den Acker der aktuellen AckerView ermitteln
        AckerView ackerView = (AckerView)findViewById(R.id.acker_view);
        Acker acker = ackerView.getAcker();

        Animator.Threading currentThreading = ackerView.getThreading();
        ackerView.setThreading(Animator.Threading.ASYNCHRONOUS_NO_WAIT);

        ackerView.setAcker(null);

        // Das neue Layout setzen
        // Damit wird auch ein eventuell für das Querformat definiertes Layout verwendet
        setContentView(R.layout.activity_main);

        // Die AckerView dürfte sich durch die vorhergehende Anweisung geändert haben
        // Diese wird zukünftig vom vorhandenen Acker genutzt
        ackerView = (AckerView)findViewById(R.id.acker_view);
        ackerView.setThreading(Animator.Threading.ASYNCHRONOUS_NO_WAIT);
        ackerView.setAcker(acker);

        // Den Zustand des Threadings wieder herstellen
        ackerView.setThreading(currentThreading);
    }

    /**
     * This is called for activities that set launchMode to "singleTop" in
     * their package, or if a client used the {@link Intent#FLAG_ACTIVITY_SINGLE_TOP}
     * flag when calling {@link #startActivity}.  In either case, when the
     * activity is re-launched while at the top of the activity stack instead
     * of a new instance of the activity being started, onNewIntent() will be
     * called on the existing instance with the Intent that was used to
     * re-launch it.
     * <p>
     * <p>An activity will always be paused before receiving a new intent, so
     * you can count on {@link #onResume} being called after this method.
     * <p>
     * <p>Note that {@link #getIntent} still returns the original Intent.  You
     * can use {@link #setIntent} to update it to this new Intent.
     *
     * @param intent The new intent that was started for the activity.
     * @see #getIntent
     * @see #setIntent
     * @see #onResume
     */
    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);

        // paranoia, cancel to avoid NPE
        if (intent == null) {
            return;
        }

        // check action of intent
        if (!Intent.ACTION_MAIN.equals(intent.getAction())) {
            readFileFromIntent(intent);
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p>
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_export) {
            // Acker ermitteln
            AckerView ackerView = (AckerView) findViewById(R.id.acker_view);
            Acker acker = ackerView.getAcker();

            // Acker in XML-Datei schreiben
            XMLWriter xmlWriter = new XMLWriter();
            try {
                xmlWriter.exportAcker(this, acker);
            } catch (Exception e) {
                android.util.Log.wtf("error writing xml", e);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Wandelt die URI im übergebenen Intent in eine Datei und liest aus dieser
     * die XML Daten.
     *
     * @param intent Benutzeraktion mit der App (z.B. Starten oder Öffnen einer Datei aus einer anderen App heraus)
     */
    private void readFileFromIntent(@NonNull Intent intent) {
        // for Build < Build.VERSION_CODES.JELLY_BEAN and "direct" uri
        Uri uri = intent.getData();
        if (uri != null) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());

            if ("xml".equals(extension) || "application/xml".equals(intent.getType()) || "text/xml".equals(intent.getType())) {
                readFileFromURI(uri);
            }
        } else {

            // get clip data
            ClipData d = intent.getClipData();

            // avoid NPE
            if (d == null) {
                return;
            }

            // iterate clip data items
            for (int i = 0; i < d.getItemCount(); i++) {
                ClipData.Item item = d.getItemAt(i);
                if (item == null) {
                    continue;
                }

                uri = item.getUri();
                if (uri == null) {
                    continue;
                }

                String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());

                if ("xml".equals(extension) || "XML".equals(extension)) {
                    readFileFromURI(uri);

                    // we have to stop after the first task
                    return;
                }
            }
        }
    }

    /**
     * Erzeu einen neuen Acker, dessen Daten in der XML Datei gespeichert sind,
     * und zeigt diesen an.
     *
     * @param uri URI zu einer Datei
     */
    private void readFileFromURI(Uri uri) {
        XMLReader xmlReader = new XMLReader();

        try {
            // Acker lesen
            InputStream is = getContentResolver().openInputStream(uri);
            Acker acker = xmlReader.readAcker(is);

            // Acker ermitteln
            AckerView ackerView = (AckerView) findViewById(R.id.acker_view);
            ackerView.setAcker(acker);

            // Nachricht zeigen
            Toast.makeText(this, "XML Datei erfolgreich importiert!", Toast.LENGTH_LONG).show();

        } catch (Throwable t) {
            Log.wtf("error reading xml file", t);
        }
    }
}

