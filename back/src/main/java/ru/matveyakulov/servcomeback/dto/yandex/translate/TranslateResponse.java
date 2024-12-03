package ru.matveyakulov.servcomeback.dto.yandex.translate;

import lombok.Data;

import java.util.List;

@Data
public class TranslateResponse {

    private List<TranslationInner> translations;

    @Data
    public static class TranslationInner {
        private String text;
    }
}
