import java.util.Random;

public class StringGenerator {
  private Random random = new Random();

  public String nextString() {
    if(random.nextBoolean()) {
      if (random.nextBoolean()) {
        return String.format("%s %s=%s", getType(true), getVariableName(true), getFirstLiteral(true));
      } else {
        return String.format("%s %s=%s",
            getType(random.nextBoolean()), getVariableName(random.nextBoolean()), getFirstLiteral(random.nextBoolean()));
      }
    } else {
      if (random.nextBoolean()) {
        return String.format("%s %s=%s %s %s",
            getType(true), getVariableName(true), getFirstLiteral(true), getOperator(true), getSecondLiteral(true));
      } else {
        return String.format("%s %s=%s %s %s",
            getType(random.nextBoolean()), getVariableName(random.nextBoolean()),
            getFirstLiteral(random.nextBoolean()), getOperator(random.nextBoolean()), getSecondLiteral(random.nextBoolean()));
      }
    }
  }

  private String getType(Boolean right) {
    return "";
  }

  private String getVariableName(Boolean right) {
    return "";
  }

  private String getFirstLiteral(Boolean right) {
    return "";
  }

  private String getSecondLiteral(Boolean right) {
    return "";
  }

  private String getOperator(Boolean right) {
    return "";
  }
}
