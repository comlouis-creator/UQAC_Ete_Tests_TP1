package Question2;

/****************
 Monitoring d'appels de méthodes
 Cours: 8INF958
 Auteur: Sylvain Hallé
****************/
import java.io.*;

public class Teller
{
  public static void main(String[] args)
  {
    Bank b = new Bank();
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int account_no = 0, amount = 0;
    
    while (true)
    {
    	try
    	{
    		System.out.println("Select operation:\n1. Open account\n2. Close account\n3. Switch to account\n4. Request approval\n5. Withdraw\n6. Exit\n");
    		String line = in.readLine();
    		if (line.compareTo("1") == 0)
    		{
    			// Open account
    			System.out.print("Enter account number: ");
    			line = in.readLine();
    			account_no = new Integer(line).intValue();
    			System.out.println("Opening account " + account_no + "\n");
    			b.open(account_no);
    		}
    		else if (line.compareTo("2") == 0)
    		{
    			// Close account
    			System.out.print("Enter account number: ");
    			line = in.readLine();
    			account_no = new Integer(line).intValue();
    			System.out.println("Closing account " + account_no + "\n");
    			b.close(account_no);
    		}
    		else if (line.compareTo("3") == 0)
    		{
    			// Switch to account
    			System.out.print("Enter account number: ");
    			line = in.readLine();
    			account_no = new Integer(line).intValue();
    			System.out.println("Switched to account " + account_no + "\n");
    		}
    		else if (line.compareTo("4") == 0)
    		{
    			// Request approval
    			System.out.print("Enter amount: ");
    			line = in.readLine();
    			amount = new Integer(line).intValue();
    			boolean response = b.isApproved(account_no, amount);
    			if (response)
    			{
    				System.out.println("Approval granted\n");
    			}
    			else
    			{
    				System.out.println("Approval denied\n");
    			}
    		}
    		else if (line.compareTo("5") == 0)
    		{
    			// Withdraw
    			System.out.print("Enter amount: ");
    			line = in.readLine();
    			amount = new Integer(line).intValue();
    			System.out.println("Withdrawing " + amount + "from account " + account_no + "\n");
    			b.withdraw(account_no, amount);
    		}
    		else if (line.compareTo("6") == 0)
    		{
    			// Exit
    			break;
    		}
    		else
    		{
    			System.out.println("Invalid choice\n\n");
    		}
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		break;
    	}
    }
    System.out.println("Good bye!\n\n");
  }
}

