package com.ljselection.exercise.exceptions;

public class RecordNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 3877226346067668394L;

  public RecordNotFoundException(String msg) {
    super(msg);
  }

  public RecordNotFoundException(String msg, Throwable t) {
    super(msg, t);
  }
}
