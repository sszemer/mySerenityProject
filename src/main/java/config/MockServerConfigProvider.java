package config;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.Scanner;

public class MockServerConfigProvider {

    private static MockServerClient client = new MockServerClient(SystemConfigProvider.getProperty("mockserver.host"), Integer.valueOf(SystemConfigProvider.getProperty("mockserver.port"))).reset();

    private static String readFileToString(String fileDir){
        return new Scanner(MockServerConfigProvider.class.getResourceAsStream(fileDir), "UTF-8").useDelimiter("\\A").next();
    }

    public static void emulateNbp() {
        client.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/api/exchangerates/tables/A/"))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json"))
                        .withBody(readFileToString("/REST/responses/nbp.json")));
    }

}
