import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Turnstile {
  TurnstileContext turnstileContext = new TurnstileContext(this);
  Map<String, String> map = new HashMap<>();

  private StringBuilder temp = new StringBuilder();

  public static void main(String[] args) {
    Turnstile turnstile = new Turnstile();
    turnstile.getMap("int a=123");
  }

  public Map<String, String> getMap(String string) {
    char[] chars = string.toCharArray();
    turnstileContext.getState();
    for (char aChar : chars) {
      turnstileContext.nextSymbol(aChar);
    }
    return turnstileContext.getState().getName().equals("Failed") ? null : map;
  }

  public void nextFirstLiteralSymbol(char symbol) {
    if(symbol == ';') {
      turnstileContext.nextState();
    } else {
      temp.append(symbol);
    }
  }

  public void saveFirstLiteral() {
    map.put("first_literal", temp.toString());
    temp = new StringBuilder();
  }

  public boolean isValidFirstLiteral() {
    return temp.toString().length() < 16;
  }

  public void nextNameSymbol(char symbol) {
    if(symbol == '=') {
      turnstileContext.nextState();
    } else {
      temp.append(symbol);
    }
  }

  public void saveName() {
    map.put("name", temp.toString());
    temp = new StringBuilder();
  }

  public boolean isValidName() {
    return true;
  }

  public void nextTypeSymbol(char symbol) {
    if(symbol == ' ') {
      turnstileContext.nextState();
    } else {
      temp.append(symbol);
    }
  }

  public boolean isValidType() {
    String type = temp.toString();
    return Objects.equals(type, "int") || Objects.equals(type, "float") || Objects.equals(type, "long");
  }

  public void saveType() {
    map.put("type", temp.toString());
    temp = new StringBuilder();
  }
}
