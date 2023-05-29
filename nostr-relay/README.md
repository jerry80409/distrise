# Nostr-Relay

## Features
* [x] Accept events from nostr clients ((NIP-42 authentication is not supported))
* [x] Provide a websockets (ws://localhost:8080/relay)
* [x] Support the following types of messages:
  - [x] ["EVENT", <event JSON as defined above>]
  - [x] ["REQ", <subscription_id>, <filters JSON>...]
  - [x] ["CLOSE", <subscription_id>]
* [x] Verify that the event’s id and signature (sig) are valid

## Required
* JDK 17
* docker-compose

## QuickStart
Run up the Cockroach database and the RabbitMQ.
```shell
cd docker && docker-compose up
```

Run up the Spring boot
```shell
export SPRING_PROFILES_ACTIVE=cockroach,relay

./gradlew :nostr-gateway:bootRun 
```

## Q&A
### Why did I choose this database?
I would choose the Cockroach database, because is a open source distribute database, supported data sharding 
capabilities, and I think I'm not really sure about the partitioning and sharding, so the first I will consider 
sharding the data using time slicing, such as creating a table for events in the same month/years?! 

### If the number of events to be stored will be huge, what would you do to scale the database?
The Cockroach database has [Multi-Region Capabilities](https://www.cockroachlabs.com/docs/dev/multiregion-overview.html), I consider that because the Relay Events stored in a regional 
have several benefit,
1. Horizontal expansion.
2. Reduced the network latency.
3. Enhanced High available and Disaster Recovery.