/**
 * custom exception to assert that not enough money are available to 
 * perform the desidered operation
 */
public class NotEnoughMoneyException extends Exception { 
  public NotEnoughMoneyException() {
      super("Not enough money");
  }
}