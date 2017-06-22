package herdenmanagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import herdenmanagement.model.Acker;
import herdenmanagement.model.Position;
import herdenmanagement.model.PositionsElement;
import herdenmanagement.model.Rindvieh;

import static org.junit.Assert.*;

/**
 * Testet die Grundfunktionen des Ackers
 */
public class AckerUnitTest {

    @Before
    public void setUp() {
        PositionsElement.WARTEZEIT = 0;
    }

    @After
    public void shutDown() {
        // Daten löschen
    }

    @Test
    public void bewegeRind() {
        Acker acker = new Acker(5, 5);

        Rindvieh rindvieh = new Rindvieh("Rindvieh");
        assertEquals("Rindvieh", rindvieh.gibName());
        acker.lassRindWeiden(rindvieh);

        Position position = rindvieh.gibPosition();
        rindvieh.geheVor();
        rindvieh.geheVor();

        // y bleibt gleich, x erhöht sich
        assertEquals(position.x + 2, rindvieh.gibPosition().x);
        assertEquals(position.y, rindvieh.gibPosition().y);
    }

    @Test
    public void zubehoer() {
        Acker acker = new Acker(5, 5);

        acker.lassGrasWachsen(2, 2);
        assertEquals(false, acker.istDaGras(new Position(1, 2)));
        assertEquals(true, acker.istDaGras(new Position(2, 2)));

        acker.entferneGras(new Position(2, 2));
        assertEquals(false, acker.istDaGras(new Position(2, 2)));

        acker.stelleEimerAuf(4, 0);
        assertEquals(false, acker.istDaEinEimer(new Position(4, 3)));
        assertEquals(true, acker.istDaEinEimer(new Position(4, 0)));
    }

    @Test
    public void melkeRind() {
        Acker acker = new Acker(5, 5);

        Rindvieh rindvieh = new Rindvieh("Rindvieh");
        acker.lassRindWeiden(rindvieh);

        acker.lassGrasWachsen(0, 0);
        rindvieh.frissGras();

        // dort steht kein Eimer, kann also nicht funktionieren
        int milch = rindvieh.gibMilch();
        assertEquals(0, milch);

        // Eimer aufstellen
        acker.stelleEimerAuf(0, 0);
        milch = rindvieh.gibMilch();
        assertEquals(1, milch);
    }

    @Test(expected = RuntimeException.class)
    public void divisionDurchNull () {
        float result = 100 / 0;
    }
}