# Distrise
TBD

## Modules
* [Nostr-Core](./nostr-core/README.md): Nostre client and Relay message serialize and deserialize.
* [Nostr-Relay](./nostr-relay/README.md): Work as Relay, can handler client events and subscribe.
* [Nostr-Gateway](./nostr-gateway/README.md): Work as Relay event aggregator, subscribed Relays and handle event by RabbitMQ 
  and store data to Cockroach database.

## Required
* JDK17