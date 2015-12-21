package dao.specs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artifact;
import domain.Connection;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.Assert.assertThat;

/**
 * Created by mmwaikar on 09-12-2015.
 */
public class ConnectionSpecs {
    private Artifact source;
    private Artifact target;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        source = new Artifact("source");
        target = new Artifact("target");

        connection = new Connection("conn");
        connection.setSource(source);
        connection.setTarget(target);

        source.setConnection(connection);
        target.setConnection(connection);
    }

    @Test(expected = StackOverflowError.class)
    public void should_not_serialize() {
        Gson gson = new GsonBuilder().create();
        gson.toJson(connection);
    }

    @Test
    public void should_serialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        assertThat(json, is(notNullValue()));
    }

    @Test
    public void should_deserialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("json: " + json);
        assertThat(json, is(notNullValue()));

        Connection conn = null;
        try {
            conn = objectMapper.readValue(json, Connection.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("conn: " + conn.toString());

        assertThat("conn is null", conn, is(notNullValue()));
        assertThat("name does not match", conn.getName(), is(connection.getName()));
        assertThat("source name does not match", conn.getSource().getName(), is(connection.getSource().getName()));
        assertThat("target name does not match", conn.getTarget().getName(), is(connection.getTarget().getName()));
    }
}
