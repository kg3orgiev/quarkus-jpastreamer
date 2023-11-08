package org.k0c3.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDto {
    private short filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private short languageId;
    private Short originalLanguageId;
    private short rentalDuration;
    private BigDecimal rentalRate;
    private Short length;
    private BigDecimal replacementCost;
    private String rating;
    private String specialFeatures;
    private Timestamp lastUpdate;
    private List<ActorDto> actors;
}
