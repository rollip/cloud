package ru.matveyakulov.servcomeback.dto.yandex.translate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TranslateRequest {

    private String sourceLanguageCode;

    private String targetLanguageCode;

    @Builder.Default
    private String format = "PLAIN_TEXT";
    private List<String> texts;
    private String folderId;
}
