package bitxon;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class DeserializationTest {

    static final String BODY_WITH_ALL_FIELDS = """
        {
            "name":"Mike",
            "externalId":"externalValue",
            "internalUuid":"internalValue"
        }
        """;

    ObjectMapper objectMapper = new ObjectMapper();

    // NOT WORKING
    @Test
    void testInternalV1() throws JsonProcessingException {
        final UserV1 deserializedValue = objectMapper
            .readerWithView(Views.Internal.class)
            .forType(UserV1.class)
            .readValue(BODY_WITH_ALL_FIELDS);

        assertAll(
            () -> assertEquals("Mike", deserializedValue.name()),
            () -> assertEquals("internalValue", deserializedValue.internalUuid()),
            () -> assertEquals(null, deserializedValue.externalId()) // NOT WORKING
        );
    }

    @Test
    void testInternalV2() throws JsonProcessingException {
        final UserV2 deserializedValue = objectMapper
            .readerWithView(Views.Internal.class)
            .forType(UserV2.class)
            .readValue(BODY_WITH_ALL_FIELDS);

        assertAll(
            () -> assertEquals("Mike", deserializedValue.name()),
            () -> assertEquals("internalValue", deserializedValue.internalUuid()),
            () -> assertEquals(null, deserializedValue.externalId())
        );
    }

    // NOT WORKING
    @Test
    void testExternalV1() throws JsonProcessingException {
        final UserV1 deserializedValue = objectMapper
            .readerWithView(Views.External.class)
            .forType(UserV1.class)
            .readValue(BODY_WITH_ALL_FIELDS);

        assertAll(
            () -> assertEquals("Mike", deserializedValue.name()),
            () -> assertEquals(null, deserializedValue.internalUuid()), // NOT WORKING
            () -> assertEquals("externalValue", deserializedValue.externalId())
        );
    }

    @Test
    void testExternalV2() throws JsonProcessingException {
        final UserV2 deserializedValue = objectMapper
            .readerWithView(Views.External.class)
            .forType(UserV2.class)
            .readValue(BODY_WITH_ALL_FIELDS);

        assertAll(
            () -> assertEquals("Mike", deserializedValue.name()),
            () -> assertEquals(null, deserializedValue.internalUuid()),
            () -> assertEquals("externalValue", deserializedValue.externalId())
        );
    }
}
