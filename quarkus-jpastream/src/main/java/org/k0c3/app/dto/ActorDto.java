package org.k0c3.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorDto {
    private short actorId;
    private String firstName;
    private String lastName;
    private Timestamp lastUpdate;
    private List<FilmDto> films;
}
