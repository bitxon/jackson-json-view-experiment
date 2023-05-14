
## Problem with JsonView deserialization

I have model with Internal and External fields
```java
public interface Views {
    interface Default {}
    interface Internal extends Default {}
    interface External extends Default {}
}
```
```java
public record UserV1(
    @JsonView(Default.class)
    String name,
    
    @JsonView(Internal.class)
    String internalUuid,
    
    @JsonView(External.class)
    String externalId
) {}
```
```java
UserV1 deserializedValue = objectMapper
    .readerWithView(Internal.class) // expecting to read Default and Internal fields only
    .forType(UserV1.class)
    .readValue(BODY_WITH_ALL_FIELDS);

assertEquals(null, deserializedValue.externalId()); // expecting external fields to be null
```

### Expected
Jackson take into account JsonView `Internal.class` -> `externalId == null`

### Actual
Jackson ignores JsonView `Internal.class` -> `externalId != null`


## Workaround
If field additionally annotated with `@JsonProperty("someName")` -> everything works as expected 
```java
public record UserV2(
    ...
    @JsonView(Views.External.class)
    @JsonProperty("externalId") // solves problem
    String externalId
) {}
```