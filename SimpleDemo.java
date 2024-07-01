package Question2;

/****************
 Monitoring d'appels de méthodes: exemple de la pile
 Cours: 8INF958
 Auteur: Klaus Havelund
****************/
public class SimpleDemo
{
    public static void main(String[] args)
    {
	Bank b = new Bank();
	
	// Error #1
	b.close(123);
	
	// Error #2
	b.open(123); b.withdraw(123, 2000); b.close(123);
	
	// Error #3
	b.open(123); b.isApproved(123, 2000); b.withdraw(456, 2000);
    }
}
