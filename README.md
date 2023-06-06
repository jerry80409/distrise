# Distrise
TBD

## Modules
* [Nostr-Core](./nostr-core/README.md): Nostr protocol implements Client and Relay event message.
* [Nostr-Relay](./nostr-relay/README.md): Work as Relay, can handler client events and subscribe.
* [Nostr-Gateway](./nostr-gateway/README.md): Work as Relay event aggregator, subscribed Relays and handle event by RabbitMQ 
  and store data to Cockroach database.

## Required
* JDK17

## Questions
### Phase1
```java
final RelayClient client = RelayClient.builder().url(URL).build();
final TextContent content = TextContent.builder().content("hello world").build();
final SecKey key = new SecKey(ByteString.decodeHex("PRIVATE KEY"));
final Event event = content.sign(key); 
client.send(event);
```
## Examples
Follow the [example folder](nostr-core/src/main/java/com/distrise/nostr/example).

### Phase2
> Why did I choose this database?

I would consider using Cockroach DB as a database for Nostr, as Cockroach supports a cluster approach (3 quorums), which has a higher availability than traditional single-point databases.

When a quorum fails, one of the remaining replicas will get a range lease (with write capability) to ensure data consistency, and the CP feature is more suitable for Nostr's community platform.

> If the number of events to be stored will be huge, what would you do to scale the database?

The Event data(Table) is the most likely to grow in a short time, and may need to be filtered for queries (where by 
conditionA and...).

I'm not sure about the partitioning and sharding capabilities of Cockroach, but I would first consider sharding the data using time slicing, such as creating a table for events in the same month, or another table for data in the same year, but I'm not sure, it might have to consider with the limitations of Relay.

### Phase3
Please provide a short writeup of why you chose a particular database for Phase 3, answering the following questions:
> Why did you choose this database? Is it the same or different database as the one you used in Phase 2? Why is it the same or a different one?

I would still choose Cockroach database, as in Phase2, Nostr Gateway is still a kind of Relay in terms of responsibility, but it needs to provide a special way to read Event data according to certain conditions, which may come from web screen operations (this situation is different from Relay reply messages).
Therefore, special attention needs to be paid to the querying and indexing of data.

[Range](https://www.cockroachlabs.com/docs/v23.1/architecture/glossary#range) looks like Cockroach's sharding function, which generates shards for tables and indexes according to the 
Range Size setting. These shards can also be set on the corresponding Region or Zone.
The shards can be set on the corresponding Region or Zone to achieve better Load balance.

> If the number of events to be stored will be huge, what would you do to scale the database? 

The Gateway itself is responsible for writing and reading, not so much for updating data and data locking, but for reading data, it does not need to be real-time, but may make queries based on the information the client wants. Therefore, we should avoid full table scan as much as possible when querying data.

* If we handle the information of Event, it will be a big table.
* Since Cockroach supports distributed database very well, it is possible to multiply the load by simply adding Nodes by horizontal scaling.
* With Cockroach's suggestion of using [UUID ID](https://www.cockroachlabs.com/docs/stable/performance-best-practices-overview.html#unique-id-best-practices) design, it is necessary to avoid hot spots.
* Index design, I haven't thought about it clear, but I think users may want to query data against some tags, filters or subscribeId, which may be the focus of index design.
 
### Phase4
Please provide a short writeup of why you chose a particular Queue or Event Stream system for Phase 4, answering the following questions:

> Why did you choose this solution?

I chose RabbitMQ, also implemented Direct Exchange and Consumer, in the code I designed a RESTful API (
[RelayController](nostr-gateway/src/main/java/com/distrise/nostr/nostrgateway/http/RelayController.java)) to allow the Event aggregator to 
dynamically 
subscribe 
to Relays.

```
1 Realy -> 1 Direct Exchange -> 1 Consumer
```

When the API is called, the Exchange and Consumer will be bound dynamically. This design is simpler, easier to maintain, and can ensure message sequencing like Kafka. But the problem is that when Relay sends a large number of Event events, it may cause RabbitMQ to fail, which may require some monitoring mechanism to deal with.

> If the number of events to be stored will be huge, what would you do to scale the database?

I don't think there is much of a bottleneck in writing data to Cockroach DB, as there are no complicated update and delete transactions.

I think reading data is the biggest problem, I have tried several NOSTR sites myself and almost all of them are very slow in reading information.

It depends on where the actual bottleneck is, it may be the table shard, or index problem, I will consider these ways
1. used the primary key of UUID to avoid hot spot.
2. index should set the size, not more than 1Mb [schema-design-indexes](https://www.cockroachlabs.com/docs/v23.1/schema-design-indexes#index-contents)
3. use [as-of-system-time](https://www.cockroachlabs.com/docs/stable/as-of-system-time.html) to reduce transaction conflicts by time interval query

### Phase5
My goal was to package to GKE via [Jib](https://github.com/GoogleContainerTools/jib), however some problems occurred that I haven't had time to deal with.

> Why did you choose these 5 metrics?
1. GKE Pods System metrics(CPU, Memory, etc.), need to know when to scale up.
2. Rabbit Management can watch Queue metrics(message counter, consumer rate, delivery rate, etc.), I haven't handle 
   the [Dead Letter](https://www.rabbitmq.com/dlx.html), it will lead to a dead cycle.
3. Cockroach console can watch (TTL, SQL, Runtime, Statement, etc.) need to know the Relay event read bottleneck.

> What kind of errors or issues do you expect them to help you monitor?

Initially, I would like to be able to see the number of messages per Relay, when, how many messages on average, the average message size, or median statistics.
I am using 1 Ralay 1 Exchange 1 Consumer design in RabbitMQ, might this be possible?
For Relay with a particularly large number of Events I think we need to do some processing to avoid overloading the Single RabbitMQ, so we need some Alter and logging.

> If you had more time, what other tools would you use or metrics would you instrument and why?

These metrics are scattered on different architectures, and I need a platform like Grafana that can consolidate this 
information to provide more insight.


## Demo
### CockroachDB
![](doc/cockraochdb.png)
![](doc/selectdb.png)

### RabbitMq Management
![](doc/rabbitmq.png)