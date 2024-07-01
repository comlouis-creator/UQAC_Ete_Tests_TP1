package Question2;

import java.util.HashMap;
import java.util.Map;

public aspect AspectProperty3 {
	Map<Integer, Boolean> approvalStatus = new HashMap<>();

    // Pointcut pour isApproved
    pointcut isApproved(int accountNo, int amount): execution(boolean Bank.isApproved(int, int)) && args(accountNo, amount);

    // Pointcut pour withdraw
    pointcut withdraw(int accountNo, int amount): execution(void Bank.withdraw(int, int)) && args(accountNo, amount);

    // Advice après isApproved pour capturer la valeur de retour
    //after(int accountNo, int amount, boolean approved): isApproved(accountNo, amount) && returning(approved) {
      //  approvalStatus.put(accountNo, approved);
    //}

    // Advice avant withdraw pour vérifier la condition
    before(int accountNo, int amount): withdraw(accountNo, amount) {
        if (amount > 1000 && !approvalStatus.getOrDefault(accountNo, false)) {
            System.out.println("Erreur de la propriété 3: Tentative de retrait de plus de 1000 $ sans approbation.");
        }
    }
}
