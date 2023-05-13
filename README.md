# Distrise
It's a simple project to learn about Distributed Systems from [RISE Program](https://rise.alphacamp.co/), and will
build a Peer-to-Pear system via [Nost](https://nostr.com/) protocol.

> Nostr is a simple, open protocol that enables global, decentralized, and censorship-resistant social media.

Actually, I'm not really understand the [NIPs](https://nostr.com/the-protocol/nips), I'm just walking and staggering.

## Required
* JDK17
* Gradle

## Modules
* [nostr-core](./nostr-core/) - Nostr protocol core.
* [nostr-example](./nostr-example) - A simple Nostr Client connect to Relay, only support NIP-01.
* [nostr-spring](./nostr-spring) - Integrated Nostr Relay to Spring boot.
  * Support H2 database .
  * Support [Cokroach database](https://www.cockroachlabs.com/docs/v22.2/build-a-spring-app-with-cockroachdb-jpa).
* [docker](./docker) - You can host the single cockroachDB on local.