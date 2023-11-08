package org.k0c3.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.k0c3.app.dto.ActorDto;
import org.k0c3.app.dto.FilmDto;
import org.k0c3.app.model.Actor;
import org.k0c3.app.model.Actor$;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class ActorRepository {

    private final JPAStreamer jpaStreamer;

    public Optional<Actor> getActorById(short id) {
        return jpaStreamer.stream(Actor.class).filter(Actor$.actorId.equal(id)).findFirst();
    }

    public List<ActorDto> getActorsStartsWith(String startsWith) {

        final StreamConfiguration<Actor> sc = StreamConfiguration.of(Actor.class)
                .joining(Actor$.films);
        return jpaStreamer.stream(sc)
                .filter(Actor$.firstName.startsWithIgnoreCase(startsWith))
                .map(actor -> ActorDto.builder()
                        .actorId(actor.getActorId())
                        .firstName(actor.getFirstName())
                        .lastName(actor.getLastName())
                        .films(actor.getFilms().stream()
                                .map(film -> FilmDto.builder()
                                        .filmId(film.getFilmId())
                                        .title(film.getTitle())
                                        .releaseYear(film.getReleaseYear())
                                        .languageId(film.getLanguageId())
                                        .originalLanguageId(film.getOriginalLanguageId())
                                        .rentalDuration(film.getRentalDuration())
                                        .rentalRate(film.getRentalRate())
                                        .length(film.getLength())
                                        .replacementCost(film.getReplacementCost())
                                        .rating(film.getRating())
                                        .specialFeatures(film.getSpecialFeatures())
                                        .lastUpdate(film.getLastUpdate())
                                        .build())
                                .toList())
                        .build()
                ).toList();
    }
}
