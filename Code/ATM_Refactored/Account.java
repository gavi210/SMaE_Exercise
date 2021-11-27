import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class describing a bank account. Each bank account is identified via customerNumber 
 * and the security code is pinNumber. Two types of balances are managed: checking balance 
 * and saving balance.
 */
public class Account {
	private static final double POSITIVE_VALUE = 0;
	private double checkingBalance;
	private double savingBalance;

	/**
	 * unique identifier for a customer
	 */
	private final int customerNumber;
	/**
	 * security number to access the account
	 */
	private final int pinNumber;
	
	private final Scanner input; 
	private final DecimalFormat moneyFormat;

	/**
	 * instantiate a new account object
	 * @param customerNumber
	 * @param pinNumber
	 * @param checkingBalance
	 * @param savingBalance
	 */
	public Account(int customerNumber, int pinNumber, double checkingBalance, double savingBalance) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.checkingBalance = checkingBalance;
		this.savingBalance = savingBalance;

		this.input = new Scanner(System.in);
		this. moneyFormat = new DecimalFormat("'$'###,##0.00");
	}

	// getter 
	public int getCustomerNumber() {
		return this.customerNumber;
	}

	public int getPinNumber() {
		return this.pinNumber;
	}

	public double getCheckingBalance() {
		return this.checkingBalance;
	}

	public double getSavingBalance() {
		return this.savingBalance;
	}

	// no setters are implemented, so to increase safety of data. 
	// Balance's values could only be modified through provided methods 
	
	/**
	 * tranfer an amount of money from chekingBalance to savingBalance
	 * @param amount money to be tranfered
	 * @throws InvalidOperationException if checking balance < amount
	 */
	private void transferMoneyFromCheckingToSaving(double amount) throws NotEnoughMoneyException {
		if (this.checkingBalance >= amount) { // if enough money to perform the action
			this.checkingBalance = this.checkingBalance - amount;
			this.savingBalance = this.savingBalance + amount;
		}
		else
			throw new NotEnoughMoneyException();
	}

	/**
	 * tranfer an amount of money from savingBalance to checkingBalance
	 * @param amount money to be tranfered
	 * @throws InvalidOperationException if saving balance < amount
	 */
	private void transferMoneyFromSavingToChecking(double amount) throws NotEnoughMoneyException {
		if (this.savingBalance >= amount) { // if enough money to perform the action
			this.savingBalance = this.savingBalance - amount;
			this.checkingBalance = this.checkingBalance + amount;
		}
		else
			throw new NotEnoughMoneyException();
	}

	/**
	 * perform withdraw of money from checkingBalance. It ensures data consistency, since performed
	 * only if enough data are available
	 * @param amount 
	 * @throws NotEnoughMoneyException if checking balance < amount
	 */
	private void withdrawFromChecking(double amount) throws NotEnoughMoneyException {
		if (this.checkingBalance >= amount) // if enough money to perform the action
			this.checkingBalance -= amount;
		else
			throw new NotEnoughMoneyException();
	}

	/**
	 * perform withdraw of money from savingBalance. It ensures data consistency, since performed
	 * only if enough data are available
	 * @param amount
	 * @throws NotEnoughMoneyException if saving balance < amount
	 */
	private void withdrawFromSaving(double amount) throws NotEnoughMoneyException {
		if (this.savingBalance >= amount)
			this.checkingBalance -= amount;
		else
			throw new NotEnoughMoneyException();
	}

	/**
	 * retrieve value form the user through command line. The input provided is checked against stopping sequence
	 * @param stopSequence reserved sequence of characters identifying the user intentio to exit the current balance operation
	 * @param defaultOnExit 
	 * @param defaultOnError
	 * @return if the input is not the stop sequence, the value provided by the user is returned
	 */
	private double getUserInput(String stopSequence, double defaultOnExit, double defaultOnError) {
		String str = input.nextLine();

		if(stopSequence.equals(str)) 
			return defaultOnExit;
		else {
			try {
				return Double.parseDouble(str);
			}
			catch(InputMismatchException e) {
				return defaultOnError;
			}
		}
	}

	/**
	 * 
	 * @param promptedMessage message prompted the user specifying the amount to be provided
	 * @param lowerBound threshold, minimum value for the amount provided to be. It must be a value >= 0
	 * @return amount provided by the user; -1 if user wants to stop operation
	 */
	private double getAmount(String promptedMessage, double lowerBound) {
		while(true) {
			System.out.print(promptedMessage);
			final double amount = getUserInput("S", -1, -2);

			if (amount < 0) { // singaling an error
				switch((int) amount) {
					case -1: 
					return -1; // propagate user wants to exit the sequence
				case -2:
					System.out.println("Invalid Input, retry");
					break; // repeate the loop
				default: 
					break;
				}
			}
			else if(amount >= lowerBound)
				return amount;
		}
	}
	
	/**
	 * cmd interaction with the user to withdraw money from checkingBalance
	 */
	public void startWithdrawSequenceForChecking() {
		System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(this.checkingBalance));

		double amount;
		boolean end = false;
		while(!end) {
			amount = getAmount("\nAmount you want to withdraw from Checkings Account (type 'S' to exit): ", POSITIVE_VALUE);
			if(amount == -1) {
				System.out.println("Exit from withdraw sequence");
				end = true;
			}
			else {
				try {
					withdrawFromChecking(amount);
					end = true; // if reaches here, withdraw was completed correctly
				}
				catch(NotEnoughMoneyException e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * cmd interaction with the user to withdraw money from savingBalance
	 */
	public void startWithdrawSequenceForSaving() {
		System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(this.savingBalance));

		double amount;
		boolean end = false;
		while(!end) {
			amount = getAmount("\nAmount you want to withdraw from Savings Account (type 'S' to exit): ", POSITIVE_VALUE);
			if(amount == -1) {
				System.out.println("Exit from withdraw sequence");
				end = true;
			}
			else {
				try {
					withdrawFromSaving(amount);
					end = true; // if reaches here, withdraw was completed correctly
				}
				catch(NotEnoughMoneyException e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * cmd interaction with the user to deposit money into checkingBalance
	 */
	public void startDepositSequenceForChecking() {
		System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(this.checkingBalance));

		double amount = getAmount("\nAmount you want to deposit into Checkings Account (type 'S' to exit): ", POSITIVE_VALUE);
		if(amount == -1) 
			System.out.println("Exit from deposit sequence");
		else 
			this.checkingBalance += amount;
	}

	/**
	 * cmd interaction with the user to deposit money into savingBalance
	 */
	public void startDepositSequenceForSaving() {
		System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(this.savingBalance));

		double amount = getAmount("\nAmount you want to deposit into Savings Account (type 'S' to exit): ", POSITIVE_VALUE);
		if(amount == -1) 
			System.out.println("Exit from deposit sequence");
		else 
			this.savingBalance += amount;
	}

	/**
	 * cmd interaction with the user to tranfer money from checkingBalance to savingBalance
	 */
	public void startTransferSequenceFromCheckingToSaving() {
		double amount;
		boolean end = false;
		while(!end) {
			amount  = getAmount("\nCurrent Checkings Account Balance: " + moneyFormat.format(this.checkingBalance), POSITIVE_VALUE);
			if (amount == -1) {
				System.out.println("Exit from tranfer sequence");
				end = true;
			}
			else {
				try {
					transferMoneyFromCheckingToSaving(amount);
					end = true; // if reaches here, transfer was complete correctly
				}
				catch(NotEnoughMoneyException e) {
					System.out.println("Not enough money, retry!");
				}
			}
		}
	}

	/**
	 * cmd interaction with the user to tranfer money from savingsBalance to checkingBalance
	 */
	public void startTransferSequenceFromSavingToChecking() {
		double amount;
		boolean end = false;
		while(!end) {
			amount  = getAmount("\nCurrent Savings Account Balance: " + moneyFormat.format(this.savingBalance), POSITIVE_VALUE);
			if (amount == -1) {
				System.out.println("Exit from tranfer sequence");
				end = true;
			}
			else {
				try {
					transferMoneyFromSavingToChecking(amount);
					end = true; // if reaches here, transfer was complete correctly
				}
				catch(NotEnoughMoneyException e) {
					System.out.println("Not enough money, retry!");
				}
			}
		}
	}
}
