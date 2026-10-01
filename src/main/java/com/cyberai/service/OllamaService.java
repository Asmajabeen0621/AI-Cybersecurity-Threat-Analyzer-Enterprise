package com.cyberai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    private static final String OLLAMA_URL =
            "http://localhost:11434/api/generate";

    private static final String MODEL =
            "llama3.2:3b";

    private static final int MAX_DATA_LENGTH = 3000;

    public OllamaService(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String generateInvestigationSummary(
            String investigationData) {

        try {

            String limitedData = investigationData;

            if (limitedData == null
                    || limitedData.isBlank()) {

                limitedData =
                        "No investigation data available.";
            }

            if (limitedData.length() > MAX_DATA_LENGTH) {

                limitedData = limitedData.substring(
                        0,
                        MAX_DATA_LENGTH
                );

                limitedData +=
                        "\n\n[Additional data omitted.]";
            }

            String prompt = """
                    You are CyberShield AI, a cybersecurity analyst.

                    Analyze the investigation data and create a short report.

                    Include only:
                    1. Summary
                    2. Main Threats
                    3. Risk Level
                    4. Recommended Actions

                    Rules:
                    - Maximum 150 words.
                    - Use bullet points.
                    - Use only the provided data.
                    - Do not repeat information.
                    - Do not invent facts.

                    Investigation Data:
                    %s
                    """.formatted(limitedData);

            Map<String, Object> requestBody =
                    new HashMap<>();

            requestBody.put("model", MODEL);
            requestBody.put("prompt", prompt);
            requestBody.put("stream", false);

            Map<String, Object> options =
                    new HashMap<>();

            options.put("temperature", 0.2);
            options.put("num_predict", 150);

            requestBody.put("options", options);

            String jsonRequest =
                    objectMapper.writeValueAsString(
                            requestBody
                    );

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(OLLAMA_URL))
                            .timeout(Duration.ofSeconds(180))
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(jsonRequest)
                            )
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {

                return "Unable to generate AI summary. "
                        + "Ollama returned status: "
                        + response.statusCode();
            }

            JsonNode responseJson =
                    objectMapper.readTree(response.body());

            JsonNode generatedResponse =
                    responseJson.get("response");

            if (generatedResponse != null
                    && !generatedResponse.asText().isBlank()) {

                return generatedResponse.asText();
            }

            return "AI summary was not returned by Ollama.";

        } catch (HttpTimeoutException exception) {

            return "AI summary unavailable: "
                    + "Ollama request timed out after "
                    + "180 seconds.";

        } catch (InterruptedException exception) {

            Thread.currentThread().interrupt();

            return "AI summary generation was interrupted.";

        } catch (Exception exception) {

            return "AI summary unavailable: "
                    + exception.getMessage();
        }
    }
}