package ru.matveyakulov.servcomeback.dto.yandex.languages;

import lombok.Data;

import java.util.List;

@Data
public class LanguageResponse {

    private List<LanguageDto> languages;

    @Data
    public static class LanguageDto {
        private String code;
        private String name;
    }
}
