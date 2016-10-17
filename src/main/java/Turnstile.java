import java.util.ArrayList;
import java.util.List;

public class Turnstile {
  TurnstileContext turnstileContext = new TurnstileContext(this);
  private List<String> groups;
  private final List<Character> operators = new ArrayList<>();
  private final List<String> types = new ArrayList<>();

  private StringBuilder temp;

  public Turnstile() {
    operators.add('%');
    operators.add('*');
    operators.add('/');

    types.add("float");
    types.add("int");
    types.add("long");
  }

  public static void main(String[] args) {
    Turnstile turnstile = new Turnstile();
    System.out.println(turnstile.getGroups("long mE=2;"));
  }

  public List<String> getGroups(String string) {
    char[] chars = string.toCharArray();
    groups = new ArrayList<>();
    temp = new StringBuilder();
    turnstileContext.setState(TurnstileContext.MainMap.Type);

    for (char aChar : chars) {
      turnstileContext.nextSymbol(aChar);
    }
    return turnstileContext.getState().getName().equals("MainMap.Final") ? groups : null;
  }

  public boolean isValidNextTypeSymbol(char symbol) {
    boolean returnBoolean = false;

    if (temp.length() < 5 && symbol > 'a' && symbol < 'z') {
      for (String type : types) {
        if(temp.length() + 1 == type.length() && (temp.toString() + symbol).equals(type)) {
          returnBoolean = true;
        } else {
          if(temp.length() + 1 < type.length()) {
            if (type.substring(0, temp.length() + 1).equals(temp.toString() + symbol)) {
              returnBoolean = true;
            }
          }
        }
      }
    }

    return returnBoolean;
  }

  public boolean isValidNextNameSymbol(char symbol) {
    return isValidNextLiteralSymbol(symbol);
  }

  public boolean isValidNextLiteralSymbol(char symbol) {
    return temp.length() < 16 && ((symbol >= 'A' && symbol <= 'z') || (symbol >= '0' && symbol <= '9'));
  }

  public void appendSymbol(char symbol) {
    temp.append(symbol);
  }

  public boolean isValidNextOperatorSymbol(char symbol) {
    return operators.contains(symbol) && temp.length() == 0;
  }

  public void saveGroup() {
    groups.add(temp.toString());
    temp = new StringBuilder();  }

  public boolean isValidNextSecondLiteralSymbol(char symbol) {
    return temp.length() < 16 && ((symbol >= 'A' && symbol <= 'z') || (symbol >= '0' && symbol <= '9'));
  }

  public boolean isValidNextLiteralNumberSymbol(char symbol) {
    return temp.length() < 16 && symbol >= '0' && symbol <= '9';
  }

  public boolean isValidNextSecondLiteralNumberSymbol(char symbol) {
    return temp.length() < 16 && symbol >= '0' && symbol <= '9';
  }

  public boolean notEmpty() {
    return temp.length() != 0;
  }

  public boolean rightType() {
    return types.contains(temp.toString());
  }
}
