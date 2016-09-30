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
    public static final MainMap_FirstLiteral FirstLiteral =
        new MainMap_FirstLiteral("MainMap.FirstLiteral", 2);
    public static final MainMap_Failed Failed =
        new MainMap_Failed("MainMap.Failed", 3);
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
    protected void nextState(TurnstileContext context)
    {
      Turnstile ctxt = context.getOwner();

      if (ctxt.isValidType())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveType();
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

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      (context.getState()).exit(context);
      context.clearState();
      try
      {
        ctxt.nextTypeSymbol(symbol);
      }
      finally
      {
        context.setState(MainMap.Type);
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
    protected void nextState(TurnstileContext context)
    {
      Turnstile ctxt = context.getOwner();

      if (ctxt.isValidName())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveName();
        }
        finally
        {
          context.setState(MainMap.FirstLiteral);
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

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      (context.getState()).exit(context);
      context.clearState();
      try
      {
        ctxt.nextNameSymbol(symbol);
      }
      finally
      {
        context.setState(MainMap.Name);
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

  private static final class MainMap_FirstLiteral
      extends MainMap_Default
  {
    //-------------------------------------------------------
    // Member methods.
    //

    private MainMap_FirstLiteral(String name, int id)
    {
      super (name, id);
    }

    @Override
    protected void nextState(TurnstileContext context)
    {
      Turnstile ctxt = context.getOwner();

      if (ctxt.isValidFirstLiteral())
      {
        (context.getState()).exit(context);
        context.clearState();
        try
        {
          ctxt.saveFirstLiteral();
        }
        finally
        {
          context.setState(MainMap.FirstLiteral);
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

    @Override
    protected void nextSymbol(TurnstileContext context, char symbol)
    {
      Turnstile ctxt = context.getOwner();

      (context.getState()).exit(context);
      context.clearState();
      try
      {
        ctxt.nextFirstLiteralSymbol(symbol);
      }
      finally
      {
        context.setState(MainMap.FirstLiteral);
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
}
