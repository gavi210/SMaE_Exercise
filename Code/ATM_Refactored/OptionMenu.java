import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for the option menu of a simulated ATM. It is possible
 * to create and login into an account. Further if one is logged in,
 * it is possible to view balance, withdraw, deposit or transfer funds
 * using checking or savings account.
 */
public final class OptionMenu {

	/**
	 * Single instance
	 */
	private static OptionMenu instance = new OptionMenu();

	/**
	 * Scanner to read input from user
	 */
	private final Scanner menuInput;

	/**
	 * Format to be used to display amounts of money
	 */
	private final DecimalFormat moneyFormat;

	/**
	 * Map Interface to store costumer data
	 */
	private final Map<Integer, Account> accountsData;

	/**
	 * Inital amount to have on your checking when opening an account
	 */
	private final double initialCheckingAmount = 0;

	/**
	 * Inital amount to have on your saving when opening an account
	 */
	private final double initialSavingAmount = 0;

	/**
	 * Stop sequence to be typed by user to exit process
	 */
	private final String stopSequence = "S";

	/**
	 * String for invalid choice
	 */
	private final String invalidChoice = "\nInvalid Choice.";
	/**
	 * String waiting for user choice input
	 */
	private final String choice = "\nChoice: ";

	/**
	 * private constructor
	 */
	private OptionMenu() {
		this.menuInput = new Scanner(System.in);
		this.moneyFormat = new DecimalFormat("'$'###,##0.00");
		this.accountsData = new HashMap<>();
	}

	/**
	 * Instance getter to implement singleton pattern
	 * @return single instance
	 */
	public static OptionMenu getInstance(){
		return instance;
	}

	/**
	 * Generates a string that instructs the user to type i to do choice
	 * @param i number the choice should have
	 * @param choice string of the choice
	 * @return formatted string to be output to user
	 */
	private String typeChoice(final int i, final String choice){
		return " Type " + i + " - " + choice;
	}

	/**
	 * Generates a string that instructs the user to type s to do choice
	 * @param s sequence the choice should have
	 * @param choice string of the choice
	 * @return formatted string to be output to user
	 */
	private String typeChoice(final String s, final String choice){
		return " Type " + s + " - " + choice;
	}

	/**
	 * Implements the login functionality. If login is successful continue
	 * with choosing a type of account. If not successful try again or exit
	 * login.
	 * @throws IOException
	 */
	public void login() throws IOException {
		// Prepare loop variables
		boolean end = false;
		String customerNumberInput = "";
		int customerNumber = 0;
		String pinNumberInput = "";
		int pinNumber = 0;

		while (!end) {
			try {
				System.out.println();
				System.out.print(typeChoice(stopSequence, "Exit"));
				System.out.print("\nEnter your customer number: ");

				// Check if stop sequence or not. If not try to read customer number.
				customerNumberInput = menuInput.nextLine();
				if(stopSequence.equals(customerNumberInput))
					return;
				customerNumber = Integer.parseInt(customerNumberInput);

				System.out.print("\nEnter your PIN number: ");

				// Check if stop sequence or not. If not try to read pin number.
				pinNumberInput = menuInput.nextLine();
				if(stopSequence.equals(pinNumberInput))
					return;
				pinNumber = Integer.parseInt(pinNumberInput);;

				// Prepare loop variables
				Iterator iterator = accountsData.entrySet().iterator();
				Map.Entry pair;
				Account acc;
				while (iterator.hasNext()) {
					pair = (Map.Entry) iterator.next();
					acc = (Account) pair.getValue();
					// See if input data is correct
					if (accountsData.containsKey(customerNumber) && pinNumber == acc.getPinNumber()) {
						// If yes continue with choosing account type
						chooseAccountType(acc);
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Character(s). Only Numbers or exit sequence.");
			}
		}
	}

	/**
	 * lets the user choose whether to operate on savings or checkings
	 * @param acc user account
	 */
	public void chooseAccountType(final Account acc) {
		boolean end = false;
		int selection;

		// Choices
		final int checkingsAccountNo = 1;
		final int savingsAccountNo = 2;
		final int exitNo = 3;

		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(typeChoice(1,"Checkings Account"));
				System.out.println(typeChoice(2,"Savings Account"));
				System.out.println(typeChoice(3,"Exit"));
				System.out.print(choice);

				// Get user Input
				selection = menuInput.nextInt();

				switch (selection) {
				case checkingsAccountNo:
					// Continue with checking account functionalities
					operateChecking(acc);
					break;
				case savingsAccountNo:
					// Continue with saving account functionalities
					operateSavings(acc);
					break;
				case exitNo:
					end = true;
					break;
				default:
					System.out.println(invalidChoice);
				}
			} catch (InputMismatchException e) {
				System.out.println(invalidChoice);
				menuInput.next();
			}
		}
	}

	/**
	 * Lets the user oparate on checking. User is able to view balance,
	 * withdraw funds, deposit funds, transfer funds or exit the function.
	 * @param acc user account
	 */
	public void operateChecking(final Account acc) {
		boolean end = false;

		// Choices
		final int viewBalanceNo = 1;
		final int withdrawFundsNo = 2;
		final int depositFundsNo = 3;
		final int transferFundsNo = 4;
		final int exitNo = 5;

		while (!end) {
			try {
				System.out.println("\nCheckings Account: ");
				System.out.println(typeChoice(1,"View Balance"));
				System.out.println(typeChoice(2,"Withdraw Funds"));
				System.out.println(typeChoice(3,"Deposit Funds"));
				System.out.println(typeChoice(4,"Transfer Funds"));
				System.out.println(typeChoice(5,"Exit"));
				System.out.print(choice);

				int selection = menuInput.nextInt();

				switch (selection) {
				case viewBalanceNo:
					System.out.println("\nCheckings Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
					break;
				case withdrawFundsNo:
					acc.startWithdrawSequenceForChecking();
					break;
				case depositFundsNo:
					acc.startDepositSequenceForChecking();
					break;
				case transferFundsNo:
					acc.startTransferSequenceFromCheckingToSaving();
					break;
				case exitNo:
					end = true;
					break;
				default:
					System.out.println(invalidChoice);
				}
			} catch (InputMismatchException e) {
				System.out.println(invalidChoice);
				menuInput.next();
			}
		}
	}

	/**
	 * Lets the user oparate on savings. User is able to view balance,
	 * withdraw funds, deposit funds, transfer funds or exit the function.
	 * @param acc user account
	 */
	public void operateSavings(final Account acc) {
		boolean end = false;

		// Choices
		final int viewBalanceNo = 1;
		final int withdrawFundsNo = 2;
		final int depositFundsNo = 3;
		final int transferFundsNo = 4;
		final int exitNo = 5;

		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(typeChoice(1,"View Balance"));
				System.out.println(typeChoice(2,"Withdraw Funds"));
				System.out.println(typeChoice(3,"Deposit Funds"));
				System.out.println(typeChoice(4,"Transfer Funds"));
				System.out.println(typeChoice(5,"Exit"));
				System.out.print(choice);
				int selection = menuInput.nextInt();
				switch (selection) {
				case viewBalanceNo:
					System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
					break;
				case withdrawFundsNo:
					acc.startWithdrawSequenceForSaving();
					break;
				case depositFundsNo:
					acc.startDepositSequenceForSaving();
					break;
				case transferFundsNo:
					acc.startTransferSequenceFromSavingToChecking();
					break;
				case exitNo:
					end = true;
					break;
				default:
					System.out.println(invalidChoice);
				}
			} catch (InputMismatchException e) {
				System.out.println(invalidChoice);
				menuInput.next();
			}
		}
	}

	/**
	 * Lets user create an account for further use.
	 * @throws IOException
	 */
	public void createAccount() throws IOException {
		// Prepare loop variables
		boolean end = false;
		String customerNumberInput = "";
		int customerNumber = 0;
		String pinNumberInput = "";
		int pinNumber = 0;

		while (!end) {
			try {
				System.out.println();
				System.out.print(typeChoice(stopSequence, "Exit"));
				System.out.println("\nEnter your customer number ");

				// Read new customer number
				customerNumberInput = menuInput.nextLine();
				if(stopSequence.equals(customerNumberInput))
					return;
				customerNumber = Integer.parseInt(customerNumberInput);

				Iterator iterator = accountsData.entrySet().iterator();
				while (iterator.hasNext()) {
					iterator.next();
					// To create an account the new customer number can't already be taken and must be positive
					if (!accountsData.containsKey(customerNumber) && customerNumber > 0) {
						end = true;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number cannot be chosen. Try a different one.");
				}
			} catch (NumberFormatException e) {
				System.out.println(invalidChoice);
				menuInput.next();
			}
		}

		end = false;
		while (!end) {
			try {
				System.out.println("\nEnter PIN to be registered");

				// Read new customer pin
				pinNumberInput = menuInput.nextLine();
				if(stopSequence.equals(pinNumberInput))
					return;
				pinNumber = Integer.parseInt(pinNumberInput);

				if (pinNumber > 0){
					Iterator iterator = accountsData.entrySet().iterator();
					while (iterator.hasNext()) {
					iterator.next();

						if (!accountsData.containsKey(customerNumber)) {
							end = true;
						}
					}
				}
				if (!end) {
					System.out.println("\nThis pin cannot be taken. Try a different pin");
				}
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		accountsData.put(customerNumber, new Account(customerNumber, pinNumber, initialCheckingAmount, initialSavingAmount));
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");

		// Make sure last newline character is used before going further
		login();
	}

	/**
	 * Starts the manu to let the user operate on the user accounts
	 * @throws IOException
	 */
	public void mainMenu() throws IOException {
		// dummy accounts
		accountsData.put(952_141, new Account(952_141, 191_904, 1_000, 5_000));
		accountsData.put(123, new Account(123, 123, 20_000, 50_000));

		// Choices
		final int loginNo = 1;
		final int createAccountNo = 2;
		final int exitNo = 3;

		boolean end = false;
		while (!end) {
			try {
				System.out.println();
				System.out.println(typeChoice(loginNo, "Login"));
				System.out.println(typeChoice(createAccountNo, "Create Account"));
				System.out.println(typeChoice(exitNo, "Exit"));
				System.out.print(choice);
				int choice = menuInput.nextInt();
				menuInput.nextLine();
				switch (choice) {
				case loginNo:
					login();
					break;
				case createAccountNo:
					createAccount();
					break;
				case exitNo:
					end = true;
					break;
				default:
					System.out.println(invalidChoice);
				}
			} catch (InputMismatchException e) {
				System.out.println(invalidChoice);
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.\n");
	}
}
