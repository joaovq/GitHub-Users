package com.githubusers.presentation.events;

/**
 * Message model that represent events arguments
 */
public class ArgumentEvent {
    public Object getArgument1() {
        return argument1;
    }

    public void setArgument1(Object argument1) {
        this.argument1 = argument1;
    }

    private Object argument1;

    public Object getArgument2() {
        return argument2;
    }

    public void setArgument2(Object argument2) {
        this.argument2 = argument2;
    }

    private Object argument2;

  public ArgumentEvent(Object argument1){
      this.argument1 = argument1;
  }

    public ArgumentEvent(Object argument1, Object argument2){
        this.argument1 = argument1;
        this.argument2 = argument2;
    }
}
