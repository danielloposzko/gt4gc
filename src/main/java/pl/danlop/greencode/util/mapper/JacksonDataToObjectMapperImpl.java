package pl.danlop.greencode.util.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * An implementation of the {@code DataToObjectMapper} interface, using Jackson's {@code ObjectMapper}
 * for JSON serialization and deserialization.
 *
 * <p>This class maps an input stream to a specific type of object {@code REQ} during deserialization
 * and serializes an object of type {@code RES} into a string representation.
 *
 * @param <REQ> The type of the object into which the input stream data will be deserialized.
 * @param <RES> The type of the object that will be serialized into a string.
 *
 * @author daniel
 */
public class JacksonDataToObjectMapperImpl<REQ, RES> implements DataToObjectMapper<REQ, RES> {

    private final TypeReference<REQ> typeReference;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new {@code JacksonDataToObjectMapperImpl} with the provided type reference.
     *
     * @param typeReference The type reference to use for deserialization.
     */
    public JacksonDataToObjectMapperImpl(TypeReference<REQ> typeReference) {
        this.typeReference = typeReference;
        this.objectMapper = new ObjectMapper() //
                .enable(JsonParser.Feature.AUTO_CLOSE_SOURCE)
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    }

    /**
     * {@inheritDoc}
     *
     * This implementation uses Jackson's {@code ObjectMapper} to deserialize the input stream
     * into an object of type {@code REQ}.
     */
    @Override
    public REQ deserialize(InputStream inputStream) throws IOException {
        if (inputStream.available() == 0) {
            return null;
        }
        return objectMapper.readValue(inputStream, typeReference);
    }

    /**
     * {@inheritDoc}
     *
     * This implementation uses Jackson's {@code ObjectMapper} to serialize the object of type {@code RES}
     * into its JSON string representation.
     */
    @Override
    public String serialize(RES res) throws JsonProcessingException {
        return objectMapper.writeValueAsString(res);
    }

}
