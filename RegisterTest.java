/**
 * MORLOT PINTA Louis MORL18010200
 * MAKELE Loïck MAKE17020200
 * LARA Esaie Guerson LARE10039600
 * TIA Gbongue Joel TIAG02068400
 */

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import stev.kwikemart.AmountException;
import stev.kwikemart.InvalidQuantityException;
import stev.kwikemart.Item;
import stev.kwikemart.PaperRoll;
import stev.kwikemart.Register;
import stev.kwikemart.RegisterException;
import stev.kwikemart.Upc;
import stev.register.*;

public class RegisterTest {

	static Register register = Register.getRegister();
	
	@BeforeAll
    public static void setup() {
        register.changePaper(PaperRoll.LARGE_ROLL);
    }

	@Test
	public void valide() {
		
		List<Item> grocery = new ArrayList<Item>();

		// Même CUP mais quantité négative pour annuler
		grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
		grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", -1, 1.5));

		// Quantité fractionnaire avec UPC débutant par 2
		grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));

		// UPC de 11 chiffres, quantités non nulles et montants strictements positifs, descriptions non vides
		grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 1, 1));
		grocery.add(new Item(Upc.generateCode("44348225996"), "Gobstoppers", 1, 1));
		grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 1, 1));

		// Coupon avec UPC débutant par 5 et montant positif
		grocery.add(new Item(Upc.generateCode("54323432343"), "Doritos Club", 1, 0.5));
		grocery.add(new Item(Upc.generateCode("54323432343"), "Doritos Club", -1, 0.5));
		grocery.add(new Item(Upc.generateCode("54323432343"), "Doritos Club", 1, 1));
		grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1));

		System.out.println(register.print(grocery));
	}
	
	@Test
	public void quantitéNulle() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 0, 1));
		assertThrows(InvalidQuantityException.class, () -> System.out.println(register.print(grocery)));
	}
	
	@Test
	public void listeVide() {
		List<Item> grocery = new ArrayList<Item>();
		assertThrows(RegisterException.EmptyGroceryListException.class, () -> System.out.println(register.print(grocery)));
	}
	
	@Test
	public void listePlusDe10Items() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 2, 0.99));
		grocery.add(new Item(Upc.generateCode("44348225996"), "Gobstoppers", 1, 0.99));
		grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 2, 1.44));
		grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1.25));
		grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));
		grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
		grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1));
		assertThrows(RegisterException.TooManyItemsException.class, () -> System.out.println(register.print(grocery)));
	}

}
