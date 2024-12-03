package ru.matveyakulov.servcomeback.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Request {

    private String sourceText;
    private String translatedText;
    private String sourceLanguageCode;
    private String targetLanguageCode;
}
