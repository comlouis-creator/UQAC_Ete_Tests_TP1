/**
 * MORLOT PINTA Louis MORL18010200
 * MAKELE Loïck MAKE17020200
 * LARA Esaie Guerson LARE10039600
 * TIA Gbongue Joel TIAG02068400
 */

/**
 * Classes d'équivalence invalides :
 * 
 * Liste de plus de 10 items (intervalle > 10)
 * Liste vide (Spécifique)
 * Liste avec un item dans la liste. Il a une quantité négative. (Spécifique)
 * UPC avec moins de 11 chiffres (intervalle de 1 chiffre inclus à 10 inclus)
 * UPC avec plus de 11 chiffres (intervalle > 11)
 * UPC avec une lettre (Spécifique)
 * UPC vide (Spécifique)
 * Prix plus grand que 35 pour un item (intervalle > 35)
 * Prix négatif (Spécifique)
 * Prix nul (Spécifique)
 * 2 items avec même UPC sans annulation du second item (Unique)
 * 
 * Classes d'équivalence valides :
 * 
 * Nombre items avec le même UPC et quantités négatives symétriques retirés (Spécifique)
 * Rabais de 1$ avec 5 à 10 items inclus avec UPC différents et avec total avant taxes d'au moins 2$ (Groupe)
 * UPC valide avec 11 chiffres (Spécifique)
 * Nombre items entre 1 et 10 inclus dans la liste (intervalle)
 * Prix non nuls (intervalles)
 */

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import stev.kwikemart.AmountException;
import stev.kwikemart.CouponException;
import stev.kwikemart.InvalidQuantityException;
import stev.kwikemart.InvalidUpcException;
import stev.kwikemart.Item;
import stev.kwikemart.PaperRoll;
import stev.kwikemart.Register;
import stev.kwikemart.RegisterException;
import stev.kwikemart.Upc;

public class RegisterTest {

	static Register register = Register.getRegister();
	
	@BeforeAll
    public static void setup() {
        register.changePaper(PaperRoll.LARGE_ROLL);
    }

	/**
	 * Nombre items entre 1 et 10 inclus dans la liste + CUP dupliqué avec annulation + Quantité fractionnaire avec UPC débutant par 2
	 * Résultat attendu : Impression facture sans exceptions
	 */
	@Test
	@DisplayName("Classes d'équivalences valides")
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

		assertDoesNotThrow(() -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Liste avec 0 item
	 * Résultat attendu : Pas d'impression, EmptyGroceryListException 
	 */
	@Test
	@DisplayName("Liste vide")
	public void listeVide() {
		List<Item> grocery = new ArrayList<Item>();
		assertThrows(RegisterException.EmptyGroceryListException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Liste avec plus de 10 items + UPC valides 11 chiffres + prix non nuls
	 * Résultat attendu : Pas d'impression, TooManyItemsException
	 */
	@Test
	@DisplayName("Liste avec plus de 10 items")
	public void listePlusDe10Items() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 2, 0.99));
		grocery.add(new Item(Upc.generateCode("44348225996"), "Gobstoppers", 1, 0.99));
		grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 2, 1.44));
		grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1.25));
		grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));
		grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
		grocery.add(new Item(Upc.generateCode("01519314158"), "Pineapples", 1, 1));
		grocery.add(new Item(Upc.generateCode("71519314159"), "Kiwis", 1, 1));
		grocery.add(new Item(Upc.generateCode("81519314159"), "Watermelons", 1, 1));
		grocery.add(new Item(Upc.generateCode("91519314159"), "Blueberries", 1, 1));
		grocery.add(new Item(Upc.generateCode("91519314158"), "Strawberries", 1, 1));
		assertThrows(RegisterException.TooManyItemsException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Prix négatif + Nombre item compris entre 1 et 10 inclus + CUP 11 chiffres
	 * Résultat attendu : Pas d'impression, NegativeAmountException
	 */
	@Test
	@DisplayName("Prix négatif")
	public void prixNegatif() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("01519314158"), "Pineapples", 1, -1));
		assertThrows(AmountException.NegativeAmountException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Prix nul + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, AmountException
	 */
	@Test
	@DisplayName("Prix nul")
	public void prixNul() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("11519314158"), "Apricots", 1, 0));
		assertThrows(AmountException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Prix d'un item supérieur à 35 + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, AmountTooLargeException
	 */
	@Test
	@DisplayName("Prix entre 35 exclus et 100 exclus")
	public void prixPlusGrand35PlusPetit100() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("01519314158"), "Pineapples", 1, 36));
		assertThrows(AmountException.AmountTooLargeException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * UPC avec String vide + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, NoUpcException
	 */
	@Test
	@DisplayName("UPC vide")
	public void UPCVide() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode(""), "Pineapples", 1, 1));
		assertThrows(InvalidUpcException.NoUpcException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * UPC avec moins de 11 chiffres + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, UpcTooShortException
	 */
	@Test
	@DisplayName("UPC de longueur plus petite que 11")
	public void UPCPlusPetitQue11() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("0151931415"), "Pineapples", 1, 1));
		assertThrows(InvalidUpcException.UpcTooShortException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * UPC avec plus de 11 chiffres + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, UpcTooLongException
	 */
	@Test
	@DisplayName("UPC de longueur plus grande que 11")
	public void UPCPlusGrandQue11() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("015193141567"), "Pineapples", 1, 1));
		assertThrows(InvalidUpcException.UpcTooLongException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * UPC avec une lettre + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, InvalidDigitException
	 */
	@Test
	@DisplayName("UPC avec une lettre")
	public void UPCAvecLettre() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("0151931415A"), "Pineapples", 1, 1));
		assertThrows(InvalidUpcException.InvalidDigitException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Doublon d'items + Nombre item dans la liste compris entre 1 et 10 inclus
	 * Résultat attendu : Impression que d'un item, DuplicateItemException
	 */
	@Test
	@DisplayName("2 items avec le même UPC sans quantité négative sur le deuxième item")
	public void DeuxItemsMemeUPCQuantitésPositives() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("01519314158"), "Pineapples", 1, 1));
		grocery.add(new Item(Upc.generateCode("01519314158"), "Pineapples", 1, 1));
		assertThrows(Register.DuplicateItemException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Item avec quantité fractionnaire dont UPC ne débute pas par 2 + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, InvalidQuantityException
	 */
	@Test
	@DisplayName("Un item avec une quantité fractionnaire et dont l'UPC ne débute pas par 2")
	public void QuantitéFractionnaireUPCDebutantPasPar2() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("12804918500"), "Beef", 0.5, 5.75));
		assertThrows(InvalidQuantityException.class, () -> System.out.println(register.print(grocery)));
	}
	
	/**
	 * Item avec quantité négative sans annulation + Nombre item compris entre 1 et 10 inclus
	 * Résultat attendu : Pas d'impression, NoSuchItemException
	 */
	@Test
	@DisplayName("Il n'y a qu'un item dans la liste. Il a une quantité négative")
	public void QuantitéNégativeListeAvecUnSeulItem() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", -1, 1));
		assertThrows(Register.NoSuchItemException.class, () -> System.out.println(register.print(grocery)));
	}
	
	@Test
	@DisplayName("Un coupon est plus élevé que le total des achats")
	public void Coupon() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 1, 0.495));
		grocery.add(new Item(Upc.generateCode("54323432343"), "Chewing gum Club", 1, 0.53));
		assertThrows(CouponException.InvalidCouponQuantityException.class, () -> System.out.println(register.print(grocery)));
	}
	
	@Test
	@DisplayName("Prix avec 3 chiffres et plus")
	public void prix3ChiffresEtPlus() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("01519314158"), "Pineapples", 1, 100));
		assertThrows(AmountException.AmountTooLargeException.class, () -> System.out.println(register.print(grocery)));
	}
	
	@Test
	@DisplayName("Quantité nulle")
	public void quantiteNulle() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 0, 1));
		assertThrows(InvalidQuantityException.class, () -> System.out.println(register.print(grocery)));
	}

}
