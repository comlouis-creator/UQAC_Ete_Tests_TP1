/**
 * MORLOT PINTA Louis MORL18010200
 * MAKELE Loïck MAKE17020200
 * LARA Esaie Guerson LARE10039600
 * TIA Gbongue Joel TIAG02068400
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.*;

import stev.bowling.*;

public class BowlingTest {
	
	private Game game = new Game();
	
	/**
     * Teste lors du dernier carreau si on fait un strike lors du premier roll si on peut faire 2 rolls ensuite.
     */
    @Test
    public void testLastFrameFirstRollStrike() {
    	Frame lastframe = new LastFrame(10);
    	lastframe.setPinsDown(1, 10).setPinsDown(2, 10).setPinsDown(3, 10);
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
     * Test de la réinitialisation d'un carreau.
     * Ce test vérifie que les quilles abattues et le nombre de lancers
     * sont correctement réinitialisés après l'appel à la méthode reset().
     */
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
     * Teste la levée d'exception pour les lancers invalides. Ici on a un nombre de quille supérieur à 10 sur 1 roll
     */
    @Test
    public void testInvalidRoll() {
        Frame frame = new NormalFrame(1);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(1, 11));
    }

    /**
     * Teste la levée d'exception pour les lancers invalides. Ici on a un 2eme roll alors qu'on a pas de 1er roll
     */
    @Test
    public void testInvalidRollSequence() {
        Frame frame = new NormalFrame(1);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(2, 5));
    }

    /**
     * Teste la levée d'exception pour les lancers invalides. Ici on a un nombre de quille supérieur à 10 mais sur 2 rolls
     */
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
     * Teste un cas particulier du dernier frame avec un comptage au bout de 2 rolls avant de lancer le troisième.
     */
    @Test
    public void testLastFrame() {
        Frame frame = new LastFrame(10);
        frame.setPinsDown(1, 10);
        frame.setPinsDown(2, 10);
        assertEquals(2, frame.countRolls());
    }

    /**
     * Teste l'ajout de frames et le calcul du score pour un jeu avec plusieurs strikes.
     */
    @Test
    public void testMultipleStrikesGame() {
        Game game = new Game();
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(i).setPinsDown(1, 10));
        }
        game.addFrame(new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 10));

        for (int i = 1; i <= 9; i++) {
            assertEquals(i * 30, game.getCumulativeScore(i));
        }
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
     * Teste le calcul du score pour un jeu avec des scores variés, incluant un spare dans le dernier carreau.
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
     * Teste si le numéro d'un carreau peut être une fraction ne donnant pas un entier
     */
    @Test
    public void testFrameNumberFraction() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(2/3));
    }
    
    /**
     * Teste si le numéro d'un carreau peut être une fraction négative ne donnant pas un entier
     */
    @Test
    public void testFrameNumberNegativeFraction() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(-2/3));
    }
    
    /**
     * Teste si le numéro d'un carreau peut être plus grand que 10
     */
    @Test
    public void testFrameNumberBiggerThan10() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(11));
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être négatif
     */
    @Test
    public void testLastFrameNumberNegative() {
    	assertThrows(BowlingException.class, () -> new LastFrame(-1));
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être une fraction qui ne donne pas un entier
     */
    @Test
    public void testLastFrameNumberFraction() {
    	assertThrows(BowlingException.class, () -> new LastFrame(2/3));
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être une fraction négative ne donnant pas un entier 
     */
    @Test
    public void testLastFrameNumberNegativeFraction() {
    	assertThrows(BowlingException.class, () -> new LastFrame(-2/3));
    }
    
    /**
     * Teste si le numéro d'un lancer peut être nul
     */
    @Test
    public void testRollZero() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(0, 10));
    }
    
    /**
     * Teste si le score peut être une fraction qui ne donne pas un entier
     */
    @Test
    public void testPinsFraction() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(1, 2/3));
    }
    
    /**
     * Teste si le numéro d'un lancer peut être une fraction ne donnant pas un entier
     */
    @Test
    public void testRollFraction() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(2/3, 10));
    }
    
    /**
     * Teste si le numéro d'un lancer pour le dernier carreau peut être une fraction ne donnant pas un entier
     */
    @Test
    public void testRollFractionLastFrame() {
    	assertThrows(BowlingException.class, () -> new LastFrame(10).setPinsDown(2/3, 10));
    }
    
    /**
     * Teste si le numéro d'un lancer pour le dernier carreau peut être une fraction négative ne donnant pas un entier
     */
    @Test
    public void testRollNegativeFractionLastFrame() {
    	assertThrows(BowlingException.class, () -> new LastFrame(10).setPinsDown(-2/3, 10));
    }
    
    /**
     * Teste si le numéro d'un lancer peut être une fraction négative ne donnant pas un entier
     */
    @Test
    public void testRollFractionNegative() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(-2/3, 10));
    }
    
    /**
     * Teste si le numéro d'un lancer peut être négatif
     */
    @Test
    public void testRollNegative() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(-1, 10));
    }
    
    /**
     * Teste si le numéro d'un lancer d'un carreau normal peut être supérieur à 2
     */
    @Test
    public void testRollNormalFrameBiggerThan2() {
    	assertThrows(BowlingException.class, () -> new NormalFrame(1).setPinsDown(3, 0));
    }
    
    /**
     * Teste si le numéro du dernier carreau peut être plus grand que 10
     */
    @Test
    public void testLastFrameNumberBiggerThan10() {
    	assertThrows(BowlingException.class, () -> new LastFrame(11));
    }

    /**
     * Test du score avec un strike
     */
    @Test
    public void testStrike() {
        Frame frame1 = new NormalFrame(1);
        frame1.setPinsDown(1, 10);
        game.addFrame(frame1);

        assertEquals(30, game.getCumulativeScore(1)); // 10 + (10+10)
    }
    
    /**
     * Test d'un lancer nul suivi d'un comptage valide de quilles.
     */
    @Test
    public void testGutterFollowedByPins() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 0);
        frame.setPinsDown(2, 7);
        assertEquals(7, frame.countPinsDown());
        assertEquals("-7", frame.toString());
    }
    
    /**
     * Test de la récupération des quilles abattues avant un lancer.
     */
    @Test
    public void testGetPinsDownBeforeRoll() {
        Frame frame = new NormalFrame(1);
        assertEquals(-1, frame.getPinsDown(1));
    }
    
    /**
     * Test de la récupération du score cumulatif pour un carreau invalide.
     */
    @Test
    public void testGetCumulativeScoreInvalidFrame() {
        Game game = new Game();
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 4);
        frame.setPinsDown(2, 5);
        game.addFrame(frame);
        assertThrows(BowlingException.class, () -> game.getCumulativeScore(2));
    }
    
    /**
     * Test de l'ajout d'un carreau après le dernier carreau.
     */
    @Test
    public void testAddFrameAfterLast() {
        Game game = new Game();
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(i));
        }
        game.addFrame(new LastFrame(10));
        assertThrows(BowlingException.class, () -> game.addFrame(new NormalFrame(11)));
    }
    
    /**
     * Test de l'exception sur le troisième lancer dans un carreau normal.
     */
    @Test
    public void testExceptionOnThirdRollInNormalFrame() {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, 4);
        frame.setPinsDown(2, 5);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(3, 2));
    }
    
    /** 
     * Test des quilles abattues avec une valeur négative.
     */
    @Test
    public void testNegativePinsDown() {
        Frame frame = new NormalFrame(1);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(1, -1));
    }
    
    /**
     * Test de divers scénarios où la somme des quilles abattues dans un carreau normal (différent de dix) dépasse dix.
     */
    @ParameterizedTest
    @CsvSource({
        "5, 6",
        "7, 4",
        "8, 5",
        "9, 2",
        "10, 1"
    })
    public void testSumOfPinsMoreThanTenInNormalFrame(int firstRoll, int secondRoll) {
        Frame frame = new NormalFrame(1);
        frame.setPinsDown(1, firstRoll);
        assertThrows(BowlingException.class, () -> frame.setPinsDown(2, secondRoll));
    }
    
    /**
     * Teste la récupération des quilles d'un lancer non effectué dans le dernier carreau.
     */
    @Test
    void testLastFrameGetPinsDownNonExistentRoll() {
        assertEquals(-1, new LastFrame(10).getPinsDown(1));
    }
    
    
    
}
