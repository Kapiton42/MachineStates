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

    strings.add("bla");
    strings.add("int a=103;");
    strings.add("int b=a * 103;");
    strings.add("long b=d;");
    strings.add("int b=d % a123;");

    for (String string : strings) {
      checkStringRegexp(string);
    }
  }

  public static void checkStringRegexp(String string) {
    Pattern p = Pattern.compile("^(int|long|short)" +
        " ([a-zA-Z][a-zA-Z0-9]{0,15})" +
        "=([1-9][0-9]++|([a-zA-Z][a-zA-Z0-9]{0,15}))" +
        "( (\\*|%|\\/) ([1-9][0-9]{0,15}|([a-zA-Z][a-zA-Z0-9]{0,15})))?;$");
    Matcher m = p.matcher(string);
    if(m.matches()) {
      checkVariable(m.group(1), m.group(2), m.group(4), m.groupCount() == 6 ? m.group(1) : null);
    }
  }

  public static void checkVariable(String type, String variableName, String firstLiteral, String secondLiteral) {
      if(typeOfVariables.get(variableName) != null && !Objects.equals(typeOfVariables.get(variableName), type)) {
        if(secondLiteral != null) {
          if (checkVariableOnExist(secondLiteral)) return;
        }

        if (checkVariableOnExist(firstLiteral)) return;

        System.out.println(variableName + " was " + typeOfVariables.get(variableName) + " but get " + type);
      } else {
        typeOfVariables.put(variableName, type);
      }
  }

  private static boolean checkVariableOnExist(String variable) {
    if(variable.charAt(0) > '0' && variable.charAt(0) < '9') {
      if (!typeOfVariables.containsKey(variable)) {
        return true;
      }
    }
    return false;
  }
}
