package org.example.spacecats;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    properties = {
        "external.service.url=http://localhost:8081"
    }
)
@AutoConfigureWireMock(port = 0)
class ExternalApiClientTest {

    @Autowired
    private ExternalApiClient externalApiClient;

    @Test
    void testGetDataFromExternal() {
        stubFor(get(urlEqualTo("/external"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Hello from WireMock!")));

        String response = externalApiClient.getDataFromExternal();
        assertEquals("Hello from WireMock!", response);

        verify(getRequestedFor(urlEqualTo("/external")));
    }
}
