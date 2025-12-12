package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
//rahaf's part
@Data
@Schema(description = "Webinar response data")
public class WebinarDTO {

    @Schema(description = "Webinar ID")
    private Integer id;

    @Schema(description = "Title")
    private String title;

    @Schema(description = "Webinar description")
    private String description;

    @Schema(description = "Presenter name")
    private String presenter;

    @Schema(description = "Date", example = "2025-03-10")
    private String date;

    @Schema(description = "Webinar link", example = "https://zoom.com/abc")
    private String link;
}
