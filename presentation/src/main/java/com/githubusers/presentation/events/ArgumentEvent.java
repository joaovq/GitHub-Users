package com.githubusers.presentation.events;

import lombok.Getter;
import lombok.Setter;

/**
 * Message model that represent events arguments
 */
@Setter @Getter
public class ArgumentEvent {
  Object argument;

  public ArgumentEvent(Object argument){
      this.argument = argument;
  }
}
