## Relay Gateway
為了避免 Relay 一瞬間被 Concurrency Requests 打爆, 在 Gateway 這層設計了 RabbitMQ, 以 Queue 的方式處理 Request Event, 再由 Relay Server 
consumer 寫入 DB.