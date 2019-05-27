# Spring Boot, JPA, Hibernate, H2

## Rest API

The app defines following APIs.

    GET /v1/loans

    GET /v1/loans/{loanId}

    POST /v1/loans

    PUT /v1/loans

Content-Type = "application/json"

## Get

Headers:
   UserId = "userId"

## Put

```javascript
{
    "userId": 2,
    "amount": 10,
    "startDate": "2019-05-26T01:08",
    "closeDate": "2019-05-27"
}
```

__curl__ example:

```
curl -X PUT http://localhost:8080/v1/loans -H "Content-Type: application/json" -d "{\"userId\": 2,\"amount\": 10,\"startDate\": \"2019-05-26T01:08\",\"closeDate\": \"2019-05-27\"}"
```

## Post

```javascript
{"id":null,"userId":2,"amount":10,"startDate":null,"termHrs":20}
```

__curl__ example:

```
curl -X POST http://localhost:8080/v1/loans -H "Content-Type: application/json" -d "{\"userId\": 2,\"amount\": 10,\"startDate\": \"2019-05-26T01:08\",\"closeDate\": \"2019-05-27\"}"
```

## H2 Console

H2_Console: [http://localhost:8080/v1/h2-console/](http://localhost:8080/v1/h2-console/)

| Parameter | Value |
| --------- | --------- |
| Driver Class: | org.h2.Driver |
| JDBC URL: | jdbc:h2:mem:testdb |
| User Name: | sa |