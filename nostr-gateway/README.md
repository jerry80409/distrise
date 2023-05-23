# Nostr-Gateway
The nostr-gateway work as Event Aggregator.

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
export SPRING_PROFILES_ACTIVE=cockroach,rabbitmq,relay

./gradlew :nostr-gateway:bootRun 
```

## Http Endpoint
```shell
curl 'http://localhost:8080/event?size=10&page=0'
```

## Demo
![demo](../doc/demo-1.png)
![cockroachdb](../doc/cockraochdb.png)
![rabbitmq](../doc/rabbitmq.png)
![selectdb](../doc/selectdb.png)

## Q&A
Please provide a short writeup of why you chose a particular database for Phase 3, answering the following questions:
* Why did you choose this database? Is it the same or different database as the one you used in Phase 2? Why is it 
the same or a different one?

我還是會選擇 Cockroach database, 跟 Phase2 的考量一樣, Nostr Gateway 在責任上還是算是 Relay 的一種, 但是需要特別去提供依照某些條件讀取 Event 數據, 可能來自於 Web 畫面上的操作 (這個情境就有別於 Relay 回覆訊息), 
因此在資料的查詢與索引上會需要特別注意.

我並沒有找到更容易理解 Cockroach 的學習資源, 我粗略了解了 Cockroach 的 [Range](https://www.cockroachlabs.com/docs/v23.1/architecture/glossary#range) 概念.

Range 看起來像是 Cockroach 的 Sharding 功能, 會將 table 與 index 依據 Range Size 的設定產生 Shard. 而這些 Shard 也能設定在對應的 Region 或 Zone 上,
去做到更好的 Load balance.

在[優化](https://www.cockroachlabs.com/docs/v23.1/performance-best-practices-overview.html) 章節裡面, 也介紹了很多有別於單點式 DB 的一些設定, 比如 ID 更推薦使用 UUID 的方式, 或是避免 [Hot Spot](https://www.cockroachlabs.com/docs/v23.1/performance-best-practices-overview.html#hot-spots) 的一些建議.

而這一篇 2018 年的文章 (https://blog.wolfogre.com/posts/test-of-cockroachdb/) 總結了許多 Cockroach 的容災, 可用的概念.

* If the number of events to be stored will be huge, what would you do to scale the database?

Gateway 本身職責在寫與讀, 不太會有更新資料, 資料鎖的問題, 在讀取資料上, 比較不需要即時性, 但可能依據 client 想要的資訊做出查詢. 故在查詢資料上應盡可能地避免全表掃描.
  * 若針對 Nostr Events 的資訊去處理, 應該會是一張大表,
  * 基於 Cockroach 很好的支援了分布式的 database, 可以簡單透過水平擴展的方式增加 Node 來提乘載量.
  * 在 Cockroach 的建議下採用 UUID 的 ID 設計會是需要的, 避免 Hot spot 的發生.
  * Index 的設計, 我還沒想好, 但我想 user 可能會想針對某些 tags, filters 或是 subscribeId 做出資料的查詢, 這些可能是 index 設計的重點.