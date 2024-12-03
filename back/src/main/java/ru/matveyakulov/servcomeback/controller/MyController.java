package ru.matveyakulov.servcomeback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matveyakulov.servcomeback.domain.Request;
import ru.matveyakulov.servcomeback.dto.TranslateResponseDto;
import ru.matveyakulov.servcomeback.dto.yandex.languages.LanguageResponse;
import ru.matveyakulov.servcomeback.facade.MyFacade;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MyController {

    private final MyFacade facade;

    @GetMapping("/translate")
    public TranslateResponseDto translate(@RequestParam String text, @RequestParam String targetLanguageCode) {
        return facade.translate(text, targetLanguageCode);
    }

    @GetMapping("/languages")
    public List<LanguageResponse.LanguageDto> getLanguages() {
        return facade.getLanguages();
    }

    @GetMapping("/requests")
    public List<Request> getRequests() {
        return facade.getRequests();
    }

}
