package com.distrise.nostr.message.relay.json;

import com.distrise.nostr.event.Event;
import com.distrise.nostr.message.relay.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import okio.ByteString;

// todo
public class RelayMessageAdaptor {

//  public RelayMessage relayMessageFromJson(
//    JsonReader reader,
//    TypeAdapter<EndOfStoredEvent> eoseDelegate,
//    TypeAdapter<CommandResult> commandDelegate,
//    TypeAdapter<Notice> noticeDelegate,
//    TypeAdapter<EventMessage> eventDelegate
//  ) throws IOException {
//    reader.beginArray();
//    String messageType = reader.nextString();
//    RelayMessage relayMessage;
//    switch (messageType) {
//      case "EOSE":
//        relayMessage = eoseDelegate.fromJson(reader);
//        break;
//      case "OK":
//        relayMessage = commandDelegate.fromJson(reader);
//        break;
//      case "EVENT":
//        relayMessage = eventDelegate.fromJson(reader);
//        break;
//      case "NOTICE":
//        relayMessage = noticeDelegate.fromJson(reader);
//        break;
//      default:
//        throw new IllegalArgumentException("Unsupported message type: " + messageType);
//    }
//    return relayMessage;
//  }
//
//  public void relayMessageToJson(JsonWriter writer, RelayMessage message) throws IOException {
//    if (message instanceof CommandResult) {
//      commandResultToJson(writer, (CommandResult) message);
//    } else if (message instanceof EventMessage) {
//      eventMessageToJson(writer, (EventMessage) message);
//    } else if (message instanceof Notice) {
//      noticeToJson(writer, (Notice) message);
//    } else if (message instanceof EndOfStoredEvent) {
//      eoseToJson(writer, (EndOfStoredEvent) message);
//    } else {
//      throw new IllegalArgumentException("Unsupported message type: " + message.getClass().getName());
//    }
//  }
//
//  public EndOfStoredEvent eoseFromJson(JsonReader reader) throws IOException {
//    reader.beginArray();
//    reader.nextString(); // "EOSE"
//    String subscriptionId = reader.nextString();
//    reader.endArray();
//    return new EndOfStoredEvent(subscriptionId);
//  }
//
//  public void eoseToJson(JsonWriter writer, EndOfStoredEvent eose) throws IOException {
//    writer.beginArray();
//    writer.value("EOSE");
//    writer.value(eose.subscriptionId());
//    writer.endArray();
//  }
//
//  public CommandResult commandResultFromJson(JsonReader reader) throws IOException {
//    reader.beginArray();
//    reader.nextString(); // "OK"
//    ByteString eventId = ByteString.decodeHex(reader.nextString());
//    boolean success = reader.nextBoolean();
//    String message = reader.hasNext() ? reader.nextString() : null;
//    reader.endArray();
//    return new CommandResult(eventId, success, message);
//  }
//
//  public void commandResultToJson(JsonWriter writer, CommandResult cr) throws IOException {
//    writer.beginArray();
//    writer.value("OK");
//    writer.value(cr.eventId().hex());
//    writer.value(cr.success());
//    if (cr.message() != null) {
//      writer.value(cr.message());
//    }
//    writer.endArray();
//  }
//
//  public Notice noticeFromJson(JsonReader reader) throws IOException {
//    reader.beginArray();
//    reader.nextString(); // "NOTICE"
//    String message = reader.nextString();
//    reader.endArray();
//    return new Notice(message);
//  }
//
//  public void noticeToJson(JsonWriter writer, Notice notice) throws IOException {
//    writer.beginArray();
//    writer.value("NOTICE");
//    writer.value(notice.message());
//    writer.endArray();
//  }
//
//  public EventMessage eventMessageFromJson(JsonReader reader, TypeAdapter<Event> delegate) throws IOException {
//    reader.beginArray();
//    reader.nextString(); // "EVENT"
//    String subscriptionId = reader.nextString();
//    Event event = delegate.fromJson(reader.nextString());
//    reader.endArray();
//    return new EventMessage(subscriptionId, event);
//  }
//
//  public void eventMessageToJson(JsonWriter writer, EventMessage eventMessage) throws IOException {
//    writer.beginArray();
//    writer.value("EVENT");
//    writer.value(eventMessage.subscriptionId());
//    writer.value(eventMessage.event());
//    writer.endArray();
//  }
//
////  public TextNote textNoteFromJson(String text) {
////    return new TextNote(text);
////  }
////
////  public String textNoteToJson(TextNote note) {
////    return note.getText();
////  }
//
////  public Tag tagFromJson(List<String> strings) {
////    return Tag.parseRaw(strings);
////  }
////
////  public List<String> tagToJson(Tag tag) {
////    return tag.toJsonList();
////  }
//
//  // === primitives
//
//  public ByteString byteStringFromJson(String s) {
//    return ByteString.decodeHex(s);
//  }
//
//  public String byteStringToJson(ByteString b) {
//    return b.hex();
//  }
//
//  public Instant instantFromJson(long seconds) {
//    return Instant.ofEpochSecond(seconds);
//  }
//
//  public long instantToJson(Instant i) {
//    return i.getEpochSecond();
//  }

}