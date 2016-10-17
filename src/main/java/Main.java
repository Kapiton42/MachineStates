import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  private final static Map<String, String> typeOfVariables = new HashMap<>();
  public static void main(String[] args) {
    List<String> strings = new ArrayList<>();
    StringGenerator stringGenerator = new StringGenerator();
    Turnstile turnstile = new Turnstile();
    long startTime = 0;
    long right = 0;

    for (int i = 0; i < 50000; i++) {
      String string = stringGenerator.nextString();
      //System.out.println(string);
      strings.add(string);
    }

    for (String string : strings) {
      if (checkStringRegexp(string) != checkSMC(string, turnstile)) {
        System.out.println(string + checkStringRegexp(string) + checkSMC(string, turnstile));
      }

    }

    for (int i = 0; i < 10; i++) {
      for (String string : strings) {
        checkStringRegexp(string);
      }
    }

    for (int i = 0; i < 10; i++) {
      startTime = System.currentTimeMillis();
      for (String string : strings) {
        if (checkStringRegexp(string)) {
          right++;
        }
      }
      System.out.println("Regexp time: " + (System.currentTimeMillis() - startTime) + " Right: " + right);
      right = 0;
    }

    for (int i = 0; i < 10; i++) {
      for (String string : strings) {
        checkSMC(string, turnstile);
      }
    }

    for (int i = 0; i < 10; i++) {
      startTime = System.currentTimeMillis();
      for (String string : strings) {
        if (checkSMC(string, turnstile)) {
          right++;
        }
      }
      System.out.println("SMC time: " + (System.currentTimeMillis() - startTime)  + " Right: " + right);
      right = 0;
    }
  }

  public static boolean checkStringRegexp(String string) {
    Pattern p = Pattern.compile("^(?<type>int|long|short)" +
        " (?<name>[a-z][a-zA-Z0-9]{0,15})" +
        "=(?<first>([0-9]{1,16})|([a-z][a-zA-Z0-9]{0,15}))" +
        "( (\\*|%|\\/) (?<second>([0-9]{1,16})|([a-z][a-zA-Z0-9]{0,15})))?;$");
    Matcher m = p.matcher(string);
    if(m.matches()) {
      //System.out.println("regex right: " + string);
      checkVariable(m.group("type"), m.group("name"), m.group("first"), m.groupCount() == 6 ? m.group("second") : null);
      return true;
    }

    return false;
  }

  public static boolean checkSMC(String string, Turnstile turnstile) {
    List<String> list = turnstile.getGroups(string);
    if(list != null) {
      //System.out.println("smc right: " + string);
      checkVariable(list.get(0), list.get(1), list.get(2), list.size() == 5 ? list.get(4) : null);
      return true;
    }

    return false;
  }

  public static void checkVariable(String type, String variableName, String firstLiteral, String secondLiteral) {
      if(typeOfVariables.get(variableName) != null && !Objects.equals(typeOfVariables.get(variableName), type)) {
        if(secondLiteral != null) {
          if (checkVariableOnExist(secondLiteral)) return;
        }

        if (checkVariableOnExist(firstLiteral)) return;

        //System.out.println(variableName + " was " + typeOfVariables.get(variableName) + " but get " + type);
      } else {
        typeOfVariables.put(variableName, type);
      }
  }

  private static boolean checkVariableOnExist(String variable) {
    if (!(variable.charAt(0) >= '0' && variable.charAt(0) <= '9')) {
      if (!typeOfVariables.containsKey(variable)) {
        return true;
      }
    }
    return false;
  }
}
