package com.ljselection.exercise.exceptions;

public class ParamNotValidException extends RuntimeException {

  private static final long serialVersionUID = -7273125690321783966L;

  public ParamNotValidException(String msg) {
    super(msg);
  }

  public ParamNotValidException(String msg, Throwable t) {
    super(msg, t);
  }
}
