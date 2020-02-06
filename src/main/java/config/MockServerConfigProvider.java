package config;

import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.concurrent.TimeUnit;

public class MockServerConfigProvider {

    public static void emulateNbp() {
        MockServerClient client = new MockServerClient("localhost", 1080).reset();

        client.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/api/exchangerates/tables/A/"))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json"))
                        .withBody("[\n" +
                                "\t{\n" +
                                "\t\t\"table\": \"A\",\n" +
                                "\t\t\"no\": \"024/A/NBP/2020\",\n" +
                                "\t\t\"effectiveDate\": \"2020-02-05\",\n" +
                                "\t\t\"rates\": [\n" +
                                "\t\t\t{\n" +
                                "\t\t\t\t\"currency\": \"bat (Tajlandia)\",\n" +
                                "\t\t\t\t\"code\": \"THB\",\n" +
                                "\t\t\t\t\"mid\": 0.1220\n" +
                                "\t\t\t},\n" +
                                "\t\t\t{\n" +
                                "\t\t\t\t\"currency\": \"dolar amerykański\",\n" +
                                "\t\t\t\t\"code\": \"USD\",\n" +
                                "\t\t\t\t\"mid\": 3.8654\n" +
                                "\t\t\t}\n" +
                                "\t\t]\n" +
                                "\t}\n" +
                                "]"));
    }

}
