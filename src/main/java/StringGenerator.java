import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringGenerator {
  private final Random random = new Random();
  private final String firstSymbol = "qwertyuiopasdfghjklzxcvbnm";
  private final String characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

  private final List<String> types = new ArrayList<>();
  private final List<String> operators = new ArrayList<>();
  private final List<String> variables = new ArrayList<>();

  public StringGenerator() {
    types.add("float");
    types.add("int");
    types.add("long");

    operators.add("%");
    operators.add("/");
    operators.add("*");
  }

  public String nextString() {
    if(random.nextBoolean()) {
      if (random.nextBoolean()) {
        return String.format("%s %s=%s;", getType(true), getVariableName(true, true), getLiteral(true));
      } else {
        return String.format("%s %s=%s;",
            getType(random.nextBoolean()), getVariableName(random.nextBoolean(), false), getLiteral(random.nextBoolean()));
      }
    } else {
      if (random.nextBoolean()) {
        return String.format("%s %s=%s %s %s;",
            getType(true), getVariableName(true, true), getLiteral(true), getOperator(true), getLiteral(true));
      } else {
        return String.format("%s %s=%s %s %s;",
            getType(random.nextBoolean()), getVariableName(random.nextBoolean(), false),
            getLiteral(random.nextBoolean()), getOperator(random.nextBoolean()), getLiteral(random.nextBoolean()));
      }
    }
  }

  private String getType(Boolean right) {
    if (right) {
      return types.get(random.nextInt(types.size()));
    } else {
      return generateString(random.nextInt(20));
    }
  }

  private String getVariableName(Boolean right, Boolean createVariable) {
    if (random.nextBoolean() && variables.size() > 1) {
      return variables.get(random.nextInt(variables.size()));
    }
    if (right) {
      String variable = firstSymbol.charAt(random.nextInt(firstSymbol.length()))
          + generateString(random.nextInt(15));
      if(createVariable) {
        if (variables.size() > 1000000) {
          variables.remove(random.nextInt(1000000));
        }
        variables.add(variable);
      }
      return variable;
    } else {
      return generateString(17 + random.nextInt(16));
    }
  }

  private String getLiteral(Boolean right) {
    if (random.nextBoolean()) {
      return getVariableName(right, false);
    } else {
      if (right) {
        return String.valueOf(Math.abs(random.nextLong()) % 999999999999L);
      } else {
        return generateString(random.nextInt(16));
      }
    }
  }

  private String getOperator(Boolean right) {
    if (right) {
      return operators.get(random.nextInt(operators.size()));
    } else {
      return generateString(20);
    }
  }

  private String generateString(int length)
  {
    char[] text = new char[length];
    for (int i = 0; i < length; i++)
    {
      text[i] = characters.charAt(random.nextInt(characters.length()));
    }
    return new String(text);
  }
}
