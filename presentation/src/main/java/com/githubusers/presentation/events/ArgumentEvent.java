package com.githubusers.presentation.events;

import lombok.Getter;
import lombok.Setter;

/**
 * Message model that represent events arguments
 */
public class ArgumentEvent {
    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    Object argument;

  public ArgumentEvent(Object argument){
      this.argument = argument;
  }
}
