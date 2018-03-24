package io.rollout;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class SimpleRestClient {

    boolean holidaySeason = true;

    private String REST_URI = "https://xkcd.com/386/info.0.json";

    private Client client = ClientBuilder.newClient();

    public Response getState() {

        if (holidaySeason) {
            REST_URI = "https://xkcd.com/521/info.0.json";

        }

        return client
            .target(REST_URI)
            .request(MediaType.APPLICATION_JSON).get(Response.class);

    }
}