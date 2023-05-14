package pl.danlop.greencode.util.mapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * An interface for mapping data to objects. It provides a contract for
 * deserialization of input streams into objects of type {@code REQ}
 * and serialization of objects of type {@code RES} into strings.
 *
 * @param <REQ> The type of the object into which the input stream data will be deserialized.
 * @param <RES> The type of the object that will be serialized into a string.
 *
 * @author daniel
 */
public interface DataToObjectMapper<REQ, RES> {

    /**
     * Deserializes an input stream into an object of type {@code REQ}.
     *
     * @param inputStream The input stream to deserialize.
     * @return The deserialized object.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    REQ deserialize(InputStream inputStream) throws IOException;

    /**
     * Serializes an object of type {@code RES} into a string.
     *
     * @param res The object to serialize.
     * @return A string representation of the serialized object.
     * @throws IOException If an I/O error occurs during serialization.
     */
    String serialize(RES res) throws IOException;

}
