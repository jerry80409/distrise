# Docker Compose

## Required
* 需要事先安裝 docker
* 需要事先安裝 docker-compose

## QuickStart
簡單地按照官方頁面的 Docker Run 改寫了 [docker-compose.yaml](./docker-compose.yaml), 因為只是開發用的, 所以我只跑了 single node, 且沒有任何安全防護 
(--insecure), 所以別用在正式環境上.

```shell
# 可能要先 docker login 才能下載 docker image
docker login
docker-compose up
```

### Cockroach web console
* open browser: http://localhost:8080
![console](../doc/cockroach-console.png)

### docker-compose 更多指令參考
| Command                  | Description                                                      |
|--------------------------|------------------------------------------------------------------|
| `docker-compose`         | Docker-compose main command                                      |
| `docker-compose build`   | Build containers                                                 |
| `docker-compose exec`    | Execute command inside a container                               |
| `docker-compose ps`      | List containers                                                  |
| `docker-compose restart` | Restart container                                                |
| `docker-compose rm`      | Remove container                                                 |
| `docker-compose run`     | Run a command in container                                       |
| `docker-compose stop`    | Stop a container                                                 |
| `docker-compose up`      | Build, (re)create, start, and attach to containers for a service |
| `docker-compose up -d`   | Same as `dcup`, but starts as daemon                             |
| `docker-compose down`    | Stop and remove containers                                       |
| `docker-compose logs`    | Show logs of container                                           |
| `docker-compose logs -f` | Show logs and follow output                                      |
| `docker-compose pull`    | Pull image of a service                                          |
| `docker-compose start`   | Start a container                                                |