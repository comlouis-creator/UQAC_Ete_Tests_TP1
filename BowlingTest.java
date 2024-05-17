import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import stev.bowling.Game;
import stev.bowling.NormalFrame;

import static org.junit.Assert.*;

import stev.bowling.*;

public class BowlingTest {

	@Test
	public void test1() {
		Game g = new Game(); //new Game();
		g.addFrame(new NormalFrame(1).setPinsDown(1,4));
		assertEquals(4, g.getCumulativeScore(1));
	}
	
	@Test
	public void test2() {
		Game g = new Game();
		g.addFrame(new NormalFrame(1).setPinsDown(1,4));
		assertEquals(4, g.getCumulativeScore(1));
		System.out.println("BONJOUR");
	}
	
	@Test
	public void test3() {
		Game g = new Game(); //new Game();
		g.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
		assertEquals(9, g.getCumulativeScore(1));
	}
	
	/**
     * Teste l'ajout de quilles pour un frame normal.
     */
    @Test
    public void testSetPinsDown() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 3);
        assertEquals(3, frame.getPinsDown(1));
        frame.setPinsDown(2, 6);
        assertEquals(6, frame.getPinsDown(2));
    }

    /**
     * Teste la réinitialisation d'un frame.
     */
    @Test
    public void testReset() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 3);
        frame.reset();
        assertEquals(-1, frame.getPinsDown(1));
        assertEquals(-1, frame.getPinsDown(2));
    }

    /**
     * Teste le comptage des lancers pour un frame normal.
     */
    @Test
    public void testCountRolls() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 3);
        assertEquals(1, frame.countRolls());
        frame.setPinsDown(2, 6);
        assertEquals(2, frame.countRolls());
    }

    /**
     * Teste le comptage des quilles abattues pour un frame normal.
     */
    @Test
    public void testCountPinsDown() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 3);
        assertEquals(3, frame.countPinsDown());
        frame.setPinsDown(2, 6);
        assertEquals(9, frame.countPinsDown());
    }

    /**
     * Teste la méthode toString pour un frame normal.
     */
    @Test
    public void testToStringNormalFrame() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 3);
        frame.setPinsDown(2, 6);
        assertEquals("36", frame.toString());

        frame.reset();
        frame.setPinsDown(1, 10);
        assertEquals("X ", frame.toString());

        frame.reset();
        frame.setPinsDown(1, 0);
        frame.setPinsDown(2, 0);
        assertEquals("--", frame.toString());
    }

    /**
     * Teste la méthode toString pour le dernier frame.
     */
    @Test
    public void testToStringLastFrame() {
        Frame frame = new LastFrame(10);
        frame.setPinsDown(1, 10);
        frame.setPinsDown(2, 10);
        frame.setPinsDown(3, 10);
        assertEquals("XXX", frame.toString());

        frame.reset();
        frame.setPinsDown(1, 10);
        frame.setPinsDown(2, 5);
        frame.setPinsDown(3, 5);
        assertEquals("X55", frame.toString());

        frame.reset();
        frame.setPinsDown(1, 1);
        frame.setPinsDown(2, 9);
        frame.setPinsDown(3, 10);
        assertEquals("1/X", frame.toString());
    }

    /**
     * Teste l'ajout de frames et le calcul du score cumulatif dans une partie.
     */
    @Test
    public void testGameScoring() {
        Game game = new Game();
        game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
        game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
        game.addFrame(new NormalFrame(3).setPinsDown(1, 5).setPinsDown(2, 0));
        game.addFrame(new NormalFrame(4).setPinsDown(1, 1).setPinsDown(2, 9));
        game.addFrame(new NormalFrame(5).setPinsDown(1, 10));
        game.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 0));
        game.addFrame(new NormalFrame(7).setPinsDown(1, 0).setPinsDown(2, 6));
        game.addFrame(new NormalFrame(8).setPinsDown(1, 10));
        game.addFrame(new NormalFrame(9).setPinsDown(1, 2).setPinsDown(2, 8));
        game.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 3));

        assertEquals(9, game.getCumulativeScore(1));
        assertEquals(24, game.getCumulativeScore(2));
        assertEquals(29, game.getCumulativeScore(3));
        assertEquals(49, game.getCumulativeScore(4));
        assertEquals(59, game.getCumulativeScore(5));
        assertEquals(59, game.getCumulativeScore(6));
        assertEquals(65, game.getCumulativeScore(7));
        assertEquals(85, game.getCumulativeScore(8));
        assertEquals(96, game.getCumulativeScore(9));
        assertEquals(109, game.getCumulativeScore(10));
    }

    /**
     * Teste la levée d'exception pour les lancers invalides.
     */
    @Test
    public void testInvalidRoll() {
        Frame frame = new NormalFrame(1);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(1, 11));
    }

    @Test
    public void testInvalidRollSequence() {
        Frame frame = new NormalFrame(1);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(2, 5));
    }

    @Test
    public void testInvalidRollExceedingPins() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 5);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(2, 6));
    }

    /**
     * Teste un cas particulier du dernier frame avec réserve et troisième lancer.
     */
    @Test
    public void testLastFrameSpare() {
        Frame frame = new LastFrame(10);
        frame.setPinsDown(1, 5);
        frame.setPinsDown(2, 5);
        frame.setPinsDown(3, 3);
        assertEquals("5/3", frame.toString());
    }

    /**
     * Teste un cas particulier du dernier frame avec un seul lancer.
     */
    @Test
    public void testLastFrameSingleRoll() {
        Frame frame = new LastFrame(10);
        frame.setPinsDown(1, 10);
        frame.setPinsDown(2, 10);
        frame.setPinsDown(3, 10);
        assertEquals(3, frame.countRolls());
    }

    /**
     * Teste l'ajout de frames et le calcul du score pour un jeu parfait.
     */
    @Test
    public void testPerfectGame() {
        Game game = new Game();
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(i).setPinsDown(1, 10));
        }
        game.addFrame(new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 10).setPinsDown(3, 10));

        for (int i = 1; i <= 9; i++) {
            assertEquals(i * 30, game.getCumulativeScore(i));
        }
        assertEquals(300, game.getCumulativeScore(10));
    }

    /**
     * Teste un cas où il y a des dalots.
     */
    @Test
    public void testGutterGame() {
        Game game = new Game();
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(i).setPinsDown(1, 0).setPinsDown(2, 0));
        }
        game.addFrame(new LastFrame(10).setPinsDown(1, 0).setPinsDown(2, 0).setPinsDown(3, 0));

        for (int i = 1; i <= 10; i++) {
            assertEquals(0, game.getCumulativeScore(i));
        }
    }
	
}
