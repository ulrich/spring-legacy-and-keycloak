# Getting Started

### Reference Documentation

For more information please take a look [there](https://dev.to/ulrich/)

### Try it

#### Start and initialize the Keycloak instance

```shell
❯ DOCKER_BUILDKIT=1 docker run --name keycloak -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin jboss/keycloak
```

#### Add realm and user configurations

```shell
❯ docker exec -ti keycloak /opt/jboss/keycloak/bin/kcadm.sh create realms -s realm=test -s enabled=true --realm master --user admin --password admin --server http://localhost:8080/auth
Created new realm with id 'test'
```

```shell
❯ docker exec -ti keycloak /opt/jboss/keycloak/bin/kcadm.sh create clients -r test -s clientId=client-api -s enabled=true --realm master --user admin --password admin --server http://localhost:8080/auth         
Created new client with id '55e72a23-617f-4bc4-a2a4-7210af3aa460'
```

```shell
❯ docker exec -ti keycloak /opt/jboss/keycloak/bin/kcadm.sh create users -r test -s username=ulrich -s enabled=true --realm master --user admin --password admin --server http://localhost:8080/auth
Created new user with id '71fcfe58-1266-4b7c-99fd-dbe24c90a00f'
```

```shell
❯ docker exec -ti keycloak /opt/jboss/keycloak/bin/kcadm.sh set-password -r test --username ulrich --new-password ulrich --realm master --user admin --password admin --server http://localhost:8080/auth
Logging into http://localhost:8080/auth as user admin of realm master
```

#### Start the Spring Boot application

```shell
❯ mvn clean package -Dmaven.test.skip=true && java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6000 -jar target/spring-legacy-and-keycloak-0.0.1-SNAPSHOT.jar --server.port=9999 --spring.profiles.active=dev -Dspring-boot.run.profiles=dev
```

#### Test all endpoints

Get the access token for the user `test/ulrich`.

```shell
❯ export TOKEN=$(curl -k -X POST \
   -H "Content-Type:application/x-www-form-urlencoded" \
   -d "grant_type=password" \
   -d "client_id=admin-cli" \
   -d "username=ulrich" \
   -d "password=ulrich" \
 'http://localhost:8080/auth/realms/test/protocol/openid-connect/token' | jq -r .access_token)
```

Test the new endpoint `/api/v2/user`

```shell
❯ curl -H "Authorization: Bearer $TOKEN" --verbose http://localhost:9999/api/v2/user
*   Trying 127.0.0.1:9999...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 9999 (#0)
> GET /api/v2/user HTTP/1.1
> Host: localhost:9999
> User-Agent: curl/7.68.0
> Accept: */*
> Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJhT3JXOTQ4SDBkMm9MbEVxa3ZUZU9hZTNJTURvWEVXR0QtVUVmLVM3WXZzIn0.eyJleHAiOjE2NTMzMTUzNTIsImlhdCI6MTY1MzMxNTA1MiwianRpIjoiNzc4YzNlMjItZjMxMi00MTBmLWE4ODktYTIzYTQ4NThlNGEyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3Rlc3QiLCJzdWIiOiI3MWZjZmU1OC0xMjY2LTRiN2MtOTlmZC1kYmUyNGM5MGEwMGYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhZG1pbi1jbGkiLCJzZXNzaW9uX3N0YXRlIjoiYmExYmUxNjgtMGUxMi00OTBiLWFlOTctOTBmNDVjNDI1MzNjIiwiYWNyIjoiMSIsInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6ImJhMWJlMTY4LTBlMTItNDkwYi1hZTk3LTkwZjQ1YzQyNTMzYyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoidWxyaWNoIn0.c9cfMLTU1VQxExsICDw0IWaztu6nFJ3p2HiA_d0f6IGxnTI_bJ3ax31eg9sxxQEclKeS87_jziSRf7Z0N3f6SYpWCVF3gq5e9tbSL41fbbJTnCrtwFDtKzCcxIHKNc6cBQCPUsOr35LQVIYbbaos-V7aYXTdTB6PofbSJtKZaK9aI-quCo7ejaq4bDbc1K_5GU2DbyLqq9gkCyoKfpwGi7hl3FAYtymIbqy4JraQwowi2zi2u3PtFIndIYJE6frlPL8tX20Ze6AxSnrx4_NEWucX8gmOXIdw3BqQHHO4wJMcn8KJ4tTmaafeiJu-D4bKrqeGyQJnhs43l0EFkGzuzQ
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< Set-Cookie: JSESSIONID=FDEFFFBF476ADE1631059897ECBFF328; Path=/; HttpOnly
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Mon, 23 May 2022 14:11:40 GMT
< 
* Connection #0 to host localhost left intact
{"id":"1","name":"Ulrich"}
```

Test the existing endpoint `/api/v1/user`

```shell
❯ curl -H "Authorization: authorization" --verbose http://localhost:9999/api/v1/user
*   Trying 127.0.0.1:9999...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 9999 (#0)
> GET /api/v1/user HTTP/1.1
> Host: localhost:9999
> User-Agent: curl/7.68.0
> Accept: */*
> Authorization: authorization
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Mon, 23 May 2022 14:12:53 GMT
< 
* Connection #0 to host localhost left intact
{"id":"1","name":"Ulrich"}
```
