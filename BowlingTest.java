/**
 * MORLOT PINTA Louis MORL18010200
 * MARCIL Loïck 
 * Esaie
 * TIA Joel 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stev.bowling.Game;
import stev.bowling.NormalFrame;

import static org.junit.Assert.*;

import stev.bowling.*;

public class BowlingTest {
	
	private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @AfterEach
    public void tearDown() {
        game = null;
    }

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
    
    @Test
    public void testFrameReset() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 5);
        frame.reset();
        assertEquals(0, frame.countPinsDown());
        assertEquals(0, frame.countRolls());
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
        assertEquals("X5", frame.toString());

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
        assertEquals(2, frame.countRolls());
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
        game.addFrame(new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 10));

        for (int i = 1; i <= 9; i++) {
            assertEquals(i * 30, game.getCumulativeScore(i));
        }
        assertEquals(290, game.getCumulativeScore(10));
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
        game.addFrame(new LastFrame(10).setPinsDown(1, 0).setPinsDown(2, 0));

        for (int i = 1; i <= 10; i++) {
            assertEquals(0, game.getCumulativeScore(i));
        }
    }
    
    /**
     * Teste un jeu où le joueur obtient un spare dans le dernier carreau et effectue un troisième lancer.
     */
    @Test
    public void testLastFrameSpareWithThirdRoll() {
        Frame frame = new LastFrame(10);
        frame.setPinsDown(1, 7);
        frame.setPinsDown(2, 3); // Spare
        frame.setPinsDown(3, 6);
        assertEquals("7/6", frame.toString());
        assertEquals(3, frame.countRolls());
        assertEquals(16, frame.countPinsDown());
    }
    
    /**
     * Teste un jeu où le joueur n'obtient pas de strike ou de spare dans le dernier carreau.
     */
    @Test
    public void testLastFrameNoStrikeOrSpare() {
        Frame frame = new LastFrame(10);
        frame.setPinsDown(1, 8);
        frame.setPinsDown(2, 1);
        assertEquals("81", frame.toString());
        assertEquals(2, frame.countRolls());
        assertEquals(9, frame.countPinsDown());
    }
    
    /**
     * Teste le calcul du score pour un jeu avec des scores variés, incluant des spares et des strikes dans le dernier carreau.
     */
    @Test
    public void testMixedGameScoring() {
        Game game = new Game();
        game.addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 4));
        game.addFrame(new NormalFrame(2).setPinsDown(1, 4).setPinsDown(2, 5));
        game.addFrame(new NormalFrame(3).setPinsDown(1, 6).setPinsDown(2, 4)); // Spare
        game.addFrame(new NormalFrame(4).setPinsDown(1, 5).setPinsDown(2, 5)); // Spare
        game.addFrame(new NormalFrame(5).setPinsDown(1, 10)); // Strike
        game.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 1));
        game.addFrame(new NormalFrame(7).setPinsDown(1, 7).setPinsDown(2, 3)); // Spare
        game.addFrame(new NormalFrame(8).setPinsDown(1, 6).setPinsDown(2, 4)); // Spare
        game.addFrame(new NormalFrame(9).setPinsDown(1, 10)); // Strike
        game.addFrame(new LastFrame(10).setPinsDown(1, 2).setPinsDown(2, 8).setPinsDown(3, 6)); // Spare

        assertEquals(5, game.getCumulativeScore(1));
        assertEquals(14, game.getCumulativeScore(2));
        assertEquals(29, game.getCumulativeScore(3));
        assertEquals(49, game.getCumulativeScore(4));
        assertEquals(60, game.getCumulativeScore(5));
        assertEquals(61, game.getCumulativeScore(6));
        assertEquals(77, game.getCumulativeScore(7));
        assertEquals(97, game.getCumulativeScore(8));
        assertEquals(117, game.getCumulativeScore(9));
        assertEquals(133, game.getCumulativeScore(10));
    }
	
    /**
     * Teste un jeu où le joueur obtient des spares consécutifs jusqu'au dernier carreau.
     */
    @Test
    public void testConsecutiveSparesToEnd() {
        Game game = new Game();
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(i).setPinsDown(1, 9).setPinsDown(2, 1)); // Spare
        }
        game.addFrame(new LastFrame(10).setPinsDown(1, 9).setPinsDown(2, 1).setPinsDown(3, 9)); // Spare

        assertEquals(19, game.getCumulativeScore(1));
        assertEquals(38, game.getCumulativeScore(2));
        assertEquals(57, game.getCumulativeScore(3));
        assertEquals(76, game.getCumulativeScore(4));
        assertEquals(95, game.getCumulativeScore(5));
        assertEquals(114, game.getCumulativeScore(6));
        assertEquals(133, game.getCumulativeScore(7));
        assertEquals(152, game.getCumulativeScore(8));
        assertEquals(171, game.getCumulativeScore(9));
        assertEquals(190, game.getCumulativeScore(10));
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être différent de 10
     */
    @Test
    public void testLastFrameNumberDifferentOfTen() {
    	assertThrows(BowlingException.class, () -> new LastFrame(9));
    }
    
    @Test
    public void testOpenFrameNoSpareNoStrike() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 4);
        frame.setPinsDown(2, 5);
        assertEquals(9, frame.countPinsDown());
        assertEquals("45", frame.toString());
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être nul
     */
    @Test
    public void testLastFrameNumberZero() {
    	assertThrows(BowlingException.class, () -> new LastFrame(0));
    }
    
    /**
     * Teste si le numéro d'un carreau peut être nul
     */
    @Test
    public void testFrameNumberZero() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(0));
    }
    
    /**
     * Teste si le numéro d'un carreau peut être négatif
     */
    @Test
    public void testFrameNumberNegative() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(-1));
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être négatif
     */
    @Test
    public void testLastFrameNumberNegative() {
    	assertThrows(BowlingException.class, () -> new LastFrame(-1));
    }
    
    /**
     * Teste si le numéro d'un lancer peut être nul
     */
    @Test
    public void testRollZero() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(0, 10));
    }
    
    /**
     * Teste si le numéro d'un lancer peut être négatif
     */
    @Test
    public void testRollNegative() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(-1, 10));
    }
    
    @Test
    public void testFrameWithGutterBall() {
        Frame frame = new NormalFrame(2);
        frame.setPinsDown(1, 0);
        frame.setPinsDown(2, 8);
        assertEquals(8, frame.countPinsDown());
        assertEquals("-8", frame.toString());
    }
    
    @Test
    public void testCumulativeScoreWithOpenFrames() {
        Frame frame1 = new NormalFrame(1);
        frame1.setPinsDown(1, 3);
        frame1.setPinsDown(2, 6);
        game.addFrame(frame1);

        Frame frame2 = new NormalFrame(2);
        frame2.setPinsDown(1, 2);
        frame2.setPinsDown(2, 7);
        game.addFrame(frame2);

        assertEquals(9, game.getCumulativeScore(1));
        assertEquals(18, game.getCumulativeScore(2));
    }
    
    @Test
    public void testStrikeFollowedByOpenFrame() {
        Frame frame1 = new NormalFrame(1);
        frame1.setPinsDown(1, 10);
        game.addFrame(frame1);

        Frame frame2 = new NormalFrame(2);
        frame2.setPinsDown(1, 3);
        frame2.setPinsDown(2, 4);
        game.addFrame(frame2);

        assertEquals(17, game.getCumulativeScore(1)); // 10 + (3+4)
        assertEquals(24, game.getCumulativeScore(2)); // 17 + (3+4)
    }

    @Test
    public void testSpareFollowedByOpenFrame() {
        Frame frame1 = new NormalFrame(1);
        frame1.setPinsDown(1, 6);
        frame1.setPinsDown(2, 4); // Spare
        game.addFrame(frame1);

        Frame frame2 = new NormalFrame(2);
        frame2.setPinsDown(1, 5);
        frame2.setPinsDown(2, 2);
        game.addFrame(frame2);

        assertEquals(15, game.getCumulativeScore(1)); // 10 + 5
        assertEquals(22, game.getCumulativeScore(2)); // 15 + (5+2)
    }

    @Test
    public void testMultipleStrikes() {
        Frame frame1 = new NormalFrame(1);
        frame1.setPinsDown(1, 10);
        game.addFrame(frame1);

        Frame frame2 = new NormalFrame(2);
        frame2.setPinsDown(1, 10);
        game.addFrame(frame2);

        Frame frame3 = new NormalFrame(3);
        frame3.setPinsDown(1, 10);
        game.addFrame(frame3);

        assertEquals(30, game.getCumulativeScore(1)); // 10 + (10+10)
        assertEquals(60, game.getCumulativeScore(2)); // 30 + (10+10)
        assertEquals(60, game.getCumulativeScore(3)); // 60 + 10 (game continues)
    }

    @Test
    public void testStrikeFollowedBySpare() {
        Frame frame1 = new NormalFrame(1);
        frame1.setPinsDown(1, 10);
        game.addFrame(frame1);

        Frame frame2 = new NormalFrame(2);
        frame2.setPinsDown(1, 6);
        frame2.setPinsDown(2, 4); // Spare
        game.addFrame(frame2);

        Frame frame3 = new NormalFrame(3);
        frame3.setPinsDown(1, 5);
        frame3.setPinsDown(2, 3);
        game.addFrame(frame3);

        assertEquals(20, game.getCumulativeScore(1)); // 10 + (6+4)
        assertEquals(35, game.getCumulativeScore(2)); // 20 + (6+4+5)
        assertEquals(43, game.getCumulativeScore(3)); // 35 + (5+3)
    }
    
    @Test
    public void testGutterFollowedByPins() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 0);
        frame.setPinsDown(2, 7);
        assertEquals(7, frame.countPinsDown());
        assertEquals("-7", frame.toString());
    }
    
    @Test
    public void testGetPinsDownBeforeRoll() {
        Frame frame = new NormalFrame(1);
        assertEquals(-1, frame.getPinsDown(1));
    }
    
    @Test
    public void testGetCumulativeScoreInvalidFrame() {
        Game game = new Game();
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 4);
        frame.setPinsDown(2, 5);
        game.addFrame(frame);
        assertThrows(BowlingException.class, () -> game.getCumulativeScore(2));
    }
    
    @Test
    public void testAddFrameAfterLast() {
        Game game = new Game();
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(i));
        }
        game.addFrame(new LastFrame(10));
        assertThrows(BowlingException.class, () -> game.addFrame(new NormalFrame(11)));
    }
    
    @Test
    public void testExceptionOnThirdRollInNormalFrame() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 4);
        frame.setPinsDown(2, 5);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(3, 2));
    }
    
    @Test
    public void testNegativePinsDown() {
        Frame frame = new NormalFrame(1);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(1, -1));
    }
    
}
