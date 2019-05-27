# Spring Boot, JPA, Hibernate, H2

## Rest API

The app defines following APIs.

    GET /v1/loans

    GET /v1/loans/{loanId}

    POST /v1/loans

    PUT /v1/loans

Content-Type = "application/json"

##Get
Headers:
   UserId = "userId"

##Put

```javascript
{
    "userId": 2,
    "amount": 10,
    "startDate": "2019-05-26T01:08",
    "closeDate": "2019-05-27"
}
```
##Post
```javascript
{"id":null,"userId":2,"amount":10,"startDate":null,"termHrs":20}
```
