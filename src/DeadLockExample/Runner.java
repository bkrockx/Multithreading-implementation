package DeadLockExample;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

	Account account1 = new Account();
	Account account2 = new Account();

	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();

	private void aquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
		while (true) {
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;
			try {
				gotFirstLock = firstLock.tryLock();
				gotSecondLock = secondLock.tryLock();

			} finally {
				if (gotFirstLock && gotSecondLock)
					return;
				if (gotFirstLock)
					firstLock.unlock();
				if (gotSecondLock)
					secondLock.unlock();
			}
			Thread.sleep(1);
		}
	}

	private void unlockLocks(Lock firstLock, Lock secondLock) {
		firstLock.unlock();
		secondLock.unlock();
	}

	public void firstThread() throws InterruptedException {

		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			aquireLocks(lock1, lock2);
			try {
				Account.transfer(account1, account2, random.nextInt(100));
			} finally {
				unlockLocks(lock1, lock2);
			}
		}

	}

	public void secondThread() throws InterruptedException {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			aquireLocks(lock2, lock1);
			try {
				Account.transfer(account2, account1, random.nextInt(100));
			} finally {
				unlockLocks(lock1, lock2);
			}
		}

	}

	public void finished() {
		System.out.println("Account 1 balance: " + account1.getBalance());
		System.out.println("Account 2 balance: " + account2.getBalance());
		System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));

	}

}
