package rise.distrise.nostr.core.message;

public enum NmessageType {
  // client side
  EVENT,
  REQ,
  CLOSE,

  // server side
  EOSE,

  // when error occurred
  NOTICE,

  UNKNOWN;

}