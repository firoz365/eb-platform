## 🚀 Service API – Manual Test (Execute Step-by-Step)

Start the app first:

```bash
./gradlew bootRun
```

#### Application runs on:
```
http://localhost:8080
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
