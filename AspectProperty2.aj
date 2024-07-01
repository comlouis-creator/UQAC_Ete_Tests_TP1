package Question2;

public aspect AspectProperty2 {

	boolean accountOpened = false;

	pointcut openAccount(int accountNo): execution(void Bank.open(int)) && args(accountNo);
	pointcut closeAccount(int accountNo): execution(void Bank.close(int)) && args(accountNo);

	before(int accountNo): openAccount(accountNo) {
		if (accountOpened) {
			System.out.println("Erreur de la propriété 2: Tentative d'ouverture de plus d'un compte simultanément.");
		}
	}

	after(int accountNo): openAccount(accountNo) {
		accountOpened = true;
	}

	after(int accountNo): closeAccount(accountNo) {
		accountOpened = false;
	}

}
