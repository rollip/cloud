package ru.matveyakulov.servcomeback.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.matveyakulov.servcomeback.dto.yandex.detect.DetectRequest;
import ru.matveyakulov.servcomeback.dto.yandex.detect.DetectResponse;
import ru.matveyakulov.servcomeback.dto.yandex.languages.LanguageRequest;
import ru.matveyakulov.servcomeback.dto.yandex.languages.LanguageResponse;
import ru.matveyakulov.servcomeback.dto.yandex.translate.TranslateRequest;
import ru.matveyakulov.servcomeback.dto.yandex.translate.TranslateResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TranslateService {

    @Value("${application.yandex.url}")
    private String yandexUrl;

    @Value("${application.yandex.api-key}")
    private String token;

    @Value("${application.yandex.folder-id}")
    private String folderId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String translate(String text, String targetLanguageCode, String sourceLanguageCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(TranslateRequest.builder()
                .sourceLanguageCode(sourceLanguageCode)
                .targetLanguageCode(targetLanguageCode)
                .texts(List.of(text))
                .folderId(folderId)
                .build()), getHeaders());
        return restTemplate.postForEntity(
                yandexUrl + "/translate", entity, TranslateResponse.class)
                .getBody()
                .getTranslations().get(0).getText();

    }

    @SneakyThrows
    public String getLanguageCode(String text) {
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(DetectRequest.builder()
                .text(text)
                .folderId(folderId)
                .build()), getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(yandexUrl + "/detect", entity, DetectResponse.class)
                .getBody()
                .getLanguageCode();
    }

    @SneakyThrows
    public List<LanguageResponse.LanguageDto> getLanguages() {
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(
                new LanguageRequest(folderId)), getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(yandexUrl + "/languages", entity, LanguageResponse.class)
                .getBody()
                .getLanguages();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Api-Key " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
