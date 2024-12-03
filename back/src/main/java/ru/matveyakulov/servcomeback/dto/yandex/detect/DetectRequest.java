package ru.matveyakulov.servcomeback.dto.yandex.detect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DetectRequest {

    private String text;
    private String folderId;
}
