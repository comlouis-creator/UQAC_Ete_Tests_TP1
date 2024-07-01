package Question2;

import java.util.HashSet;
import java.util.Set;

public aspect AspectProperty1 {

	Set<Integer> openedAccounts = new HashSet<>();

	pointcut openAccount(int accountNo): execution(void Bank.open(int)) && args(accountNo);
	pointcut closeAccount(int accountNo): execution(void Bank.close(int)) && args(accountNo);
	pointcut isApproved(int accountNo, int amount): execution(boolean Bank.isApproved(int, int)) && args(accountNo, amount);
	pointcut withdraw(int accountNo, int amount): execution(void Bank.withdraw(int, int)) && args(accountNo, amount);

	after(int accountNo): openAccount(accountNo) {
		openedAccounts.add(accountNo);
	}

	after(int accountNo): closeAccount(accountNo) {
		openedAccounts.remove(accountNo);
	}

	before(int accountNo, int amount): isApproved(accountNo, amount) {
		if (!openedAccounts.contains(accountNo)) {
			System.out.println("Erreur de la propriété 1: Appel de isApproved sur un compte non ouvert.");
		}
	}

	before(int accountNo, int amount): withdraw(accountNo, amount) {
		if (!openedAccounts.contains(accountNo)) {
			System.out.println("Erreur de la propriété 1: Appel de withdraw sur un compte non ouvert.");
		}
	}

}
