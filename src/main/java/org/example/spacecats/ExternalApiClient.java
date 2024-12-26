package org.example.spacecats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiClient {

    @Value("${external.service.url}")
    private String externalBaseUrl;

    public String getDataFromExternal() {
        RestTemplate restTemplate = new RestTemplate();
        String url = externalBaseUrl + "/external";
        return restTemplate.getForObject(url, String.class);
    }
}
