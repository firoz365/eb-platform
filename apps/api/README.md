## 🚀 Service API – Manual Test (Execute Step-by-Step)

Start the app first:

```bash
./gradlew bootRun
```

#### Application runs on:
```
http://localhost:8080
```

### GraphQL 
```
http://localhost:8080/graphql
```
### GraphQL

Endpoint:

```
http://localhost:8080/graphql
```

#### List All Services
```declarative
{
  "query": "query { services { id name description owner environment status version createdAt updatedAt } }"
}
```

#### Find By Service Name
```declarative
{
  "query": "query { services(name: \"Payment-Service\") { id name description owner environment status version } }"
}
```

#### Find By Owner
```declarative
{
  "query": "query { services(owner: \"team-payments\") { id name owner environment status } }"
}
```

#### Find By Environment
```declarative
{
  "query": "query { services(environment: DEV) { id name environment status } }"
}
```

#### Find By Status
```declarative
{
  "query": "query { services(status: ACTIVE) { id name status owner environment } }"
}
```

#### Fetch Service With All Events
```declarative
{
  "query": "query { services(name: \"Payment-Service\") { id name status events { id type message createdAt } } }"
}
```

#### Fetch Only ERROR Events for a Service
```declarative
{
  "query": "query { services(name: \"Payment-Service\") { id name status events(type: ERROR) { id type message createdAt } } }"
}
```

#### Fetch ERROR Events for ACTIVE Service
```declarative
{
  "query": "query { services(name: \"Payment-Service\", status: ACTIVE) { id name status events(type: ERROR) { id message createdAt } } }"
}
```

#### Fetch WARN Events in SIT Environment
```declarative
{
  "query": "query { services(environment: SIT, status: ACTIVE) { id name environment status events(type: WARN) { id type message createdAt } } }"
}
```


#### H2 Console
```
http://localhost:8080/h2-console
```
JDBC URL:
```
jdbc:h2:mem:eb
```
![img.png](img.png)


### Things to Be Addressed / Improvements

1. Prevent users from overwriting each other's data (optimistic locking)
2. Use custom exceptions and do not expose stack traces to clients
3. Maintain API versioning (`/api/v1`)
4. Store all date/time values in UTC
5. Use EntityGraph where appropriate to avoid N+1 problems
6. Enable Swagger only in non-production environments
7. Use Envers for auditing (avoid destructive DDL execution in production)
8. Implement structured logging with MDC (correlation ID)
9. Use a base abstract superclass for common auditing fields
10. Create indexes on frequently queried/searchable fields
11. Pagination and sorting
12. Tests
13. Check ACL . Token does not mean we can see other services
14. Logging and Monitoring (ECS Task Count in DataDog / Splunk For long and alert)

####
Queries
```declarative
SELECT * FROM EVENTS;
SELECT * FROM SERVICES;
```
