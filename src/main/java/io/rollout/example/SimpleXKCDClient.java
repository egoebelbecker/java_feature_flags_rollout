package io.rollout.example;


import io.rollout.client.ConfigurationFetchedHandler;
import io.rollout.client.FetcherResults;
import io.rollout.rox.server.Rox;
import io.rollout.rox.server.RoxOptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleXKCDClient {

    // Flags container
    private Flags flags;

    // XKCD URI
    private String REST_URI = "https://xkcd.com/386/info.0.json";


    // REST client
    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * Retrieve XKCD info and exit
     * @param args properties file (if re-enabled)
     */
    public static void main(String args[]) {
        SimpleXKCDClient simpleXKCDClient = new SimpleXKCDClient();
        XKCDComic comic = simpleXKCDClient.getComic();
        System.err.println("Got " + comic.getTitle());
    }

    /**
     * Class constructor
     */
    SimpleXKCDClient() {

        // Uncomment to use properties file, as shown in tutorial
        //InputStream props = new FileInputStream("src/test/resources/application.properties");
        //System.getProperties().load(props);
        //holidaySeason = Boolean.parseBoolean(Optional.of(System.getProperty("holidaySeason")).orElse("false"));

        // Initialize rollout
        initializeRox();
    }

    /**
     * Initialize ROX API, block until initial config is retrieved
     */
    private void initializeRox() {

        // Latched below when rollout configuration is retrieved
        CountDownLatch roxFirstFetch = new CountDownLatch(1);

        try {

            // Create Rollout container
            flags = new Flags();

            // Register container with Rollout
            Rox.register("Flags", flags);

            // Create Rox options with a fetched handler
            RoxOptions options = new RoxOptions.Builder()
                .withConfigurationFetchedHandler(new ConfigurationFetchedHandler() {
                    @Override
                    public void onConfigurationFetched(FetcherResults arg0) {
                        if (roxFirstFetch.getCount() > 0) {
                            roxFirstFetch.countDown();
                            System.err.println("Got Rollout configuration");
                        }
                    }
                }).build();

            // Initialize Rollout
            Rox.setup("application key", options);
            roxFirstFetch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            System.err.println("Interrupted waiting for rollout data.");
        }
    }

    /**
     * Grab the XKCD information
     * @return
     */
    public XKCDComic getComic() {

        // Should we get the holiday strip?
        if (flags.getHolidaySeason().isEnabled()) {
            REST_URI = "https://xkcd.com/521/info.0.json";
        }

        Request request = new Request.Builder().url(REST_URI).build();
        try (Response response = httpClient.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body().bytes(), XKCDComic.class);
        } catch(IOException ioe) {
            return null;
        }
    }

}








