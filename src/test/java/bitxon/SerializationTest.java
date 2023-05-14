package bitxon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class SerializationTest {

    static final UserV1 USER_V1 = new UserV1("Mike", "internalValue", "externalValue");
    static final UserV2 USER_V2 = new UserV2("Mike", "internalValue", "externalValue");

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testInternalV1() throws JsonProcessingException {
        final String serializedValue = objectMapper
            .writerWithView(Views.Internal.class)
            .writeValueAsString(USER_V1);

        assertEquals("{\"name\":\"Mike\",\"internalUuid\":\"internalValue\"}", serializedValue);
    }

    @Test
    void testInternalV2() throws JsonProcessingException {
        final String serializedValue = objectMapper
            .writerWithView(Views.Internal.class)
            .writeValueAsString(USER_V2);

        assertEquals("{\"name\":\"Mike\",\"internalUuid\":\"internalValue\"}", serializedValue);
    }

    @Test
    void testExternalV1() throws JsonProcessingException {
        final String serializedValue = objectMapper
            .writerWithView(Views.External.class)
            .writeValueAsString(USER_V1);

        assertEquals("{\"name\":\"Mike\",\"externalId\":\"externalValue\"}", serializedValue);
    }

    @Test
    void testExternalV2() throws JsonProcessingException {
        final String serializedValue = objectMapper
            .writerWithView(Views.External.class)
            .writeValueAsString(USER_V2);

        assertEquals("{\"name\":\"Mike\",\"externalId\":\"externalValue\"}", serializedValue);
    }
}
