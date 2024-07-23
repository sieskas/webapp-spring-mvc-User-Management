package com.example.usermanagementapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PowerBIService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public String createDataset(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String datasetPayload = "{\n" +
                "  \"name\": \"SalesDataset\",\n" +
                "  \"defaultMode\": \"Push\",\n" +
                "  \"tables\": [\n" +
                "    {\n" +
                "      \"name\": \"SalesTable\",\n" +
                "      \"columns\": [\n" +
                "        {\n" +
                "          \"name\": \"Product\",\n" +
                "          \"dataType\": \"string\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Amount\",\n" +
                "          \"dataType\": \"int\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Date\",\n" +
                "          \"dataType\": \"DateTime\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(datasetPayload, headers);

        String powerBICreateDatasetUrl = "https://api.powerbi.com/v1.0/myorg/datasets";

        ResponseEntity<String> response = restTemplate.postForEntity(powerBICreateDatasetUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create dataset");
        }
    }

    public String addRowsToDataset(String datasetId, String tableName, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String rowsPayload = "{\n" +
                "  \"rows\": [\n" +
                "    {\n" +
                "      \"Product\": \"Product A\",\n" +
                "      \"Amount\": 100,\n" +
                "      \"Date\": \"2023-01-01T00:00:00Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"Product\": \"Product B\",\n" +
                "      \"Amount\": 200,\n" +
                "      \"Date\": \"2023-01-02T00:00:00Z\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(rowsPayload, headers);

        String powerBIAddRowsUrl = String.format("https://api.powerbi.com/v1.0/myorg/datasets/%s/tables/%s/rows", datasetId, tableName);

        ResponseEntity<String> response = restTemplate.postForEntity(powerBIAddRowsUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to add rows to dataset");
        }
    }
}
