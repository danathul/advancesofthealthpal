package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Text translation request")
public class TranslationDTO {
    @Schema(description = "Text to translate", example = "مرحبا")
    private String text;

    @Schema(description = "Source language", example = "ar", defaultValue = "ar")
    private String sourceLang = "ar";

    @Schema(description = "Target language", example = "en", defaultValue = "en")
    private String targetLang = "en";

    public TranslationDTO() {}
    public TranslationDTO(String text) { this.text = text; }
}