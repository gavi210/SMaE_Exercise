import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
	private static final double POSITIVE_VALUE = 0;
	private double checkingBalance = 0;
	private double savingBalance = 0;

	// variables
	private int customerNumber;
	private int pinNumber;
	
	private Scanner input = new Scanner(System.in);
	private DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");

	/*
	provide description when used one constructor and when the other
	*/
	public Account(int customerNumber, int pinNumber) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
	}

	public Account(int customerNumber, int pinNumber, double checkingBalance, double savingBalance) {
		this.customerNumber = customerNumber;
		this.pinNumber = pinNumber;
		this.checkingBalance = checkingBalance;
		this.savingBalance = savingBalance;
	}

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

	/**
	 * 
	 * @param amount money to be tranfered
	 * @throws InvalidOperationException if checking balance < amount
	 */
	public void transferMoneyFromCheckingToSaving(double amount) {
		if (this.checkingBalance >= amount) { // if enough money to perform the action
			this.checkingBalance = this.checkingBalance - amount;
			this.savingBalance = this.savingBalance + amount;
		}
		else
			throw new InvalidOperationException("Not enough money");
	}

	private double getUserInput(String stopSequence, double defaultOnExit, double defaultOnError) {
		String str = input.nextLine();

		if(str.equals(stopSequence)) 
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

	private double getAmount(String promptedMessage, double lowerBound) {
		while(true) {
			System.out.print(promptedMessage);
			double amount = getUserInput("S", -1, -2);

			switch(amount) {
				case -1: 
					return -1; // propagate user wants to exit the sequence
				case -2:
					System.out.println("Invalid Input, retry");
					break; // repeate the loop
				default: 
					if(amount >= lowerBound) // else continue the loop
						return amount;
			}
		}
	}

	public void startWithdrawSequenceForCheckingBalance() {
		System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(this.checkingBalance));

		double amount;
		boolean end = false;
		while(!end) {
			amount = getAmount("\nAmount you want to withdraw from Checkings Account (type 'S' to exit): ", POSITIVE_VALUE);
			if(amount == -1) {
				System.out.println("Exit from withdraw sequence");
				end = true;
			}
			else if (this.checkingBalance > amount) { // already checked amount is positive value
				this.checkingBalance -= amount;
				end = true;
			}
			else 
				System.out.println("Not enough money");
		}
	}

	public void startWithdrawSequenceForSavingBalance() {
		System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(this.savingBalance));

		double amount;
		boolean end = false;
		while(!end) {
			amount = getAmount("\nAmount you want to withdraw from Savings Account (type 'S' to exit): ", POSITIVE_VALUE);
			if(amount == -1) {
				System.out.println("Exit from withdraw sequence");
				end = true;
			}
			else if (this.checkingBalance > amount) { // already checked amount is positive value
				this.savingBalance -= amount;
				end = true;
			}
			else
				System.out.println("Not enough money!");
		}
	}

	public void startDepositSequenceForChecking() {
		System.out.println("\nCurrent Checkings Account Balance: " + moneyFormat.format(this.checkingBalance));

		double amount = getAmount("\nAmount you want to deposit into Checkings Account (type 'S' to exit): ", POSITIVE_VALUE);
		if(amount == -1) 
			System.out.println("Exit from deposit sequence");
		else 
			this.checkingBalance += amount;
	}

	public void startDepositSequenceForSaving() {
		System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(this.savingBalance));

		double amount = getAmount("\nAmount you want to deposit into Savings Account (type 'S' to exit): ", POSITIVE_VALUE);
		if(amount == -1) 
			System.out.println("Exit from deposit sequence");
		else 
			this.savingBalance += amount;
	}

	public void startTransferSequence() {
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
					end = true;
				}
				catch(InvalidOperationException e) {
					System.out.println("Not enough money, retry!");
				}
			}
		}
	}
}
