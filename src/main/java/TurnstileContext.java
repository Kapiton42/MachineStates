public class TurnstileContext
    extends statemap.FSMContext
{
//---------------------------------------------------------------
// Member methods.
//

  public TurnstileContext(Turnstile owner)
  {
    this (owner, MainMap.Type);
  }

  public TurnstileContext(Turnstile owner, TurnstileState initState)
  {
    super (initState);

    _owner = owner;
  }

  @Override
  public void enterStartState()
  {
    getState().entry(this);
    return;
  }

  public void nextState()
  {
    _transition = "nextState";
    getState().nextState(this);
    _transition = "";
    return;
  }

  public void nextSymbol(char symbol)
  {
    _transition = "nextSymbol";
    getState().nextSymbol(this, symbol);
    _transition = "";
    return;
  }

  public TurnstileState getState()
      throws statemap.StateUndefinedException
  {
    if (_state == null)
    {
      throw(
          new statemap.StateUndefinedException());
    }

    return ((TurnstileState) _state);
  }

  protected Turnstile getOwner()
  {
    return (_owner);
  }

  public void setOwner(Turnstile owner)
  {
    if (owner == null)
    {
      throw (
          new NullPointerException(
              "null owner"));
    }
    else
    {
      _owner = owner;
    }

    return;
  }

//---------------------------------------------------------------
// Member data.
//

  transient private Turnstile _owner;

  //-----------------------------------------------------------
  // Constants.
  //

  private static final long serialVersionUID = 1L;

//---------------------------------------------------------------
// Inner classes.
//

  public static abstract class TurnstileState
      extends statemap.State
  {
    //-----------------------------------------------------------
    // Member methods.
    //

    protected TurnstileState(String name, int id)
    {
      super (name, id);
    }

    protected void entry(TurnstileContext context) {}
    protected void exit(TurnstileContext context) {}

    protected void nextState(TurnstileContext context)
    {
      Default(context);
    }

    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Default(context);
    }

    protected void Default(TurnstileContext context)
    {
      throw (
          new statemap.TransitionUndefinedException(
              "State: " +
                  context.getState().getName() +
                  ", Transition: " +
                  context.getTransition()));
    }

    //-----------------------------------------------------------
    // Member data.
    //

    //-------------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  /* package */ static abstract class MainMap
  {
    //-----------------------------------------------------------
    // Member methods.
    //

    //-----------------------------------------------------------
    // Member data.
    //

    //-------------------------------------------------------
    // Constants.
    //

    public static final MainMap_Type Type =
        new MainMap_Type("MainMap.Type", 0);
    public static final MainMap_Name Name =
        new MainMap_Name("MainMap.Name", 1);
    public static final MainMap_FirstLiteralBegin FirstLiteralBegin =
        new MainMap_FirstLiteralBegin("MainMap.FirstLiteralBegin", 2);
    public static final MainMap_FirstLiteralVariable FirstLiteralVariable =
        new MainMap_FirstLiteralVariable("MainMap.FirstLiteralVariable", 3);
    public static final MainMap_FirstLiteralNumber FirstLiteralNumber =
        new MainMap_FirstLiteralNumber("MainMap.FirstLiteralNumber", 4);
    public static final MainMap_SecondLiteralBegin SecondLiteralBegin =
        new MainMap_SecondLiteralBegin("MainMap.SecondLiteralBegin", 5);
    public static final MainMap_SecondLiteralVariable SecondLiteralVariable =
        new MainMap_SecondLiteralVariable("MainMap.SecondLiteralVariable", 6);
    public static final MainMap_SecondLiteralNumber SecondLiteralNumber =
        new MainMap_SecondLiteralNumber("MainMap.SecondLiteralNumber", 7);
    public static final MainMap_Operator Operator =
        new MainMap_Operator("MainMap.Operator", 8);
    public static final MainMap_Failed Failed =
        new MainMap_Failed("MainMap.Failed", 9);
    public static final MainMap_Final Final =
        new MainMap_Final("MainMap.Final", 10);
  }

  protected static class MainMap_Default
      extends TurnstileState
  {
    //-----------------------------------------------------------
    // Member methods.
    //

    protected MainMap_Default(String name, int id)
    {
      super (name, id);
    }

    //-----------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_Type
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_Type(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == ' ' && ctxt.rightType())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Name);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextTypeSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.Type);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_Name
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_Name(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == '=' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.FirstLiteralBegin);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextNameSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.Name);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_FirstLiteralBegin
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_FirstLiteralBegin(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol >= 'a' && symbol <= 'z')
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.FirstLiteralVariable);
          (context.getState()).entry(context);
        }

      }
      else if (symbol >= '0' && symbol <= '9')
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.FirstLiteralNumber);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_FirstLiteralVariable
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_FirstLiteralVariable(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == ';' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Final);
          (context.getState()).entry(context);
        }

      }
      else if (symbol == ' ' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Operator);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextLiteralSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.FirstLiteralVariable);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_FirstLiteralNumber
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_FirstLiteralNumber(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == ';' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Final);
          (context.getState()).entry(context);
        }

      }
      else if (symbol == ' ' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Operator);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextLiteralNumberSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.FirstLiteralNumber);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_SecondLiteralBegin
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_SecondLiteralBegin(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol >= 'a' && symbol <= 'z')
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.SecondLiteralVariable);
          (context.getState()).entry(context);
        }

      }
      else if (symbol >= '0' && symbol <= '9')
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.SecondLiteralNumber);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_SecondLiteralVariable
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_SecondLiteralVariable(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == ';' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Final);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextSecondLiteralSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.SecondLiteralVariable);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_SecondLiteralNumber
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_SecondLiteralNumber(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == ';' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.Final);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextSecondLiteralNumberSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.SecondLiteralNumber);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_Operator
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_Operator(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      if (symbol == ' ' && ctxt.notEmpty())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveGroup();
        }
        finally
        {
          context.setState(MainMap.SecondLiteralBegin);
          (context.getState()).entry(context);
        }

      }
      else if (ctxt.isValidNextOperatorSymbol(symbol))
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.appendSymbol(symbol);
        }
        finally
        {
          context.setState(MainMap.Operator);
          (context.getState()).entry(context);
        }

      }
      else
      {
        (context.getState()).exit(context);
        context.setState(MainMap.Failed);
        (context.getState()).entry(context);
      }

      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_Failed
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_Failed(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextState(TurnstileContext context)
    {

      (context.getState()).exit(context);
      context.setState(MainMap.Failed);
      (context.getState()).entry(context);
      return;
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {

      (context.getState()).exit(context);
      context.setState(MainMap.Failed);
      (context.getState()).entry(context);
      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }

  private static final class MainMap_Final
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_Final(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextState(TurnstileContext context)
    {

      (context.getState()).exit(context);
      context.setState(MainMap.Failed);
      (context.getState()).entry(context);
      return;
    }

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {

      (context.getState()).exit(context);
      context.setState(MainMap.Failed);
      (context.getState()).entry(context);
      return;
    }

    //-------------------------------------------------------
    // Member data.
    //

    //---------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;
  }
}

/*
 * Local variables:
 *  buffer-read-only: t
 * End:
 */
