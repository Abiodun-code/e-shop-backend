package abioduncode.spring_e_shop.exceptions;

public class CustomException extends RuntimeException {
  public CustomException(String message){
    super(message);
  }
}