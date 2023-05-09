# Distrise
It's a simple project to learn about Distributed Systems from [RISE Program](https://rise.alphacamp.co/), and will 
building a Peer-to-Pear system via [Nost](https://nostr.com/) protocol.

> Nostr is a simple, open protocol that enables global, decentralized, and censorship-resistant social media.

Actually, I not really understand the [NIPs](https://nostr.com/the-protocol/nips), I'm just walking and staggering.

## Required
* JDK17
* Maven

## Modules
* [nostr-event](./nostr-event/) - Nostr protocol events.
* [nostr-ws](./nostr-ws) - Simple websocket connect to Relay.

## Run Examples
I prefer to run example on IntelliJ, because it just be a draft. 
```shell
git clone https://github.com/jerry80409/distrise.git
cd distrise

# java clean and compile to the jar 
mvn clean pakcage

# go into the target folder 
cd example/target

# set your Nostr pubkey and privkey
export pubkey=xxxxx
export privkey=xxxxx

# run the example
mvn -jar example-1.0-SNAPSHOT.jar
```