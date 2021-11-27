import java.io.IOException;

public class ATM {

	public static void main(String[] args) throws IOException {
		OptionMenu optionMenu = OptionMenu.getInstance();
		introduction();
		optionMenu.mainMenu();
	}

	public static void introduction() {
		System.out.println("Welcome to the ATM Project!");
	}
}
