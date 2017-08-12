package DeadLockExample;

public class Account {
	
	private int balance = 20000;
	
	public void deposit(int amount){
		balance += amount;
	}
	
	public void withdraw(int amount){
		balance -= amount;
	}
	
	public int getBalance(){
		return balance;
	}

	public static void transfer(Account account1,Account account2, int amount){
		account1.withdraw(amount);
		account2.deposit(amount);
	}
}
