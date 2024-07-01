package Question2;

/****************
 Monitoring d'appels de méthodes
 Cours: 8INF958
 Auteur: Sylvain Hallé
****************/
import java.util.*;

public class Bank
{
    Vector<AccountInfo> m_accounts = new Vector<AccountInfo>();
    
    public void open(int accountNo)
    {
	AccountInfo ai = new AccountInfo(accountNo);
	if (m_accounts.contains(ai))
	{
	    // This should not happen (account already open)
	    System.err.println("ERROR: account already open");
	}
	else
	{
	    m_accounts.add(ai);
	}
    }

    public void close(int accountNo)
    {
	AccountInfo ai = new AccountInfo(accountNo);
	if (!m_accounts.contains(ai))
	{
	    // This should not happen (account not open)
	    System.err.println("ERROR: account not open");
	}
	else
	{
	    m_accounts.remove(ai);
	}
    }

    public void withdraw(int accountNo, int amount)
    {
	if (!m_accounts.contains(new AccountInfo(accountNo)))
	{
	    // This should not happen (account not open)
	    System.err.println("ERROR: account not open");
	    return;
	}
	if (amount < 1000)
	{
	    return;
	}
	for (AccountInfo ai : m_accounts)
	{
	    if (ai.m_accountNo == accountNo)
	    {
		if (ai.m_authorizedFor < amount)
		{
		    // This should not happen: withdrawing more than approved
		    if (ai.m_authorizedFor == 0)
		    {
			System.err.println("ERROR: authorization required first");
		    }
		    else
		    {
			System.err.println("ERROR: withdrawing more than was approved");
		    }
		}
		else
		{
		    // Reset need for authorization after withdrawal
		    ai.m_authorizedFor = 0;
		}
	    }
	}
    }

    public boolean isApproved(int accountNo, int amount)
    {
	for (AccountInfo ai : m_accounts)
	{
	    if (ai.m_accountNo == accountNo && ai.m_authorizedFor > 0)
	    {
		// An already approved account remains approved until withdrawal
		return true;
	    }
	    else
	    {
		// Randomly approves or disapproves
		return Math.random() > 0.5;
	    }
	}
	// This should not happen: account not open
	System.err.println("ERROR: account not open");
	return false;
    }
    
    public class AccountInfo
    {
	public int m_authorizedFor = 0;
	public int m_accountNo = 0;
	
	public AccountInfo(int ano)
	{
	    super();
	    m_accountNo = ano;
	}
	
	public AccountInfo(int ano, int af)
	{
	    this(ano);
	    m_authorizedFor = af;
	}
	
	public boolean equals(Object ai)
	{
	    return ((AccountInfo)ai).m_accountNo == m_accountNo;
	}
	
	public int hashCode()
	{
	    return m_accountNo;
	}
    }
}
