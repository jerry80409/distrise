package com.distrise.nostr.nostrrelay.relay.exception;

public class RelayException extends RuntimeException {

  public RelayException() {
    super();
  }

  public RelayException(String message) {
    super(message);
  }

  public RelayException(String message, Throwable cause) {
    super(message, cause);
  }

  public RelayException(Throwable cause) {
    super(cause);
  }

  protected RelayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}