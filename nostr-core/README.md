# Nostr-Core
It's a simple project to learn about Distributed Systems from RISE Program, and will building a Peer-to-Pear system via Nost protocol.

Nostr is a simple, open protocol that enables global, decentralized, and censorship-resistant social media.

Actually, I not really understand the NIPs, I'm just walking and staggering.

## Required
* JDK 17

## Quick Start
```java
final RelayClient client = RelayClient.builder().url(URL).build();
final TextContent content = TextContent.builder().content("hello world").build();
final SecKey key = new SecKey(ByteString.decodeHex("PRIVATE KEY"));
final Event event = content.sign(key); 
client.send(event);
```
## Dependencies
```groovy
// okhttp3
implementation 'com.squareup.okhttp3:okhttp:4.11.0'

// secp256k1
implementation 'fr.acinq.secp256k1:secp256k1-kmp-jvm:0.10.0'
runtimeOnly 'fr.acinq.secp256k1:secp256k1-kmp-jni-jvm:0.10.0'
```

## Examples
Follow the [example folder](./src/main/java/com/distrise/nostr/example).