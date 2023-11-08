package org.k0c3.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.k0c3.app.dto.ActorDto;
import org.k0c3.app.dto.FilmDto;
import org.k0c3.app.model.Film;
import org.k0c3.app.model.Film$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
@AllArgsConstructor
public class FilmRepository {
    private static final int PAGE_SIZE = 20;

    private final JPAStreamer jpaStreamer;

    public Optional<Film> getFilmById(short id) {
        return jpaStreamer.stream(Film.class).filter(Film$.filmId.equal(id)).findFirst();
    }

    public Stream<Film> paged(long page, short minLength) {
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                .filter(Film$.length.greaterThan(minLength))
                .sorted(Film$.length)
                .skip(page * PAGE_SIZE).limit(PAGE_SIZE);
    }

    public List<FilmDto> filmsWithActors(String startsWith, short minLength) {
        final StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class)
                .joining(Film$.actors);

        return jpaStreamer.stream(sc)
                .sorted(Film$.length.reversed())
                .filter(Film$.title.startsWith(startsWith).
                        and(Film$.length.greaterThan(minLength)))
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
                        .description(film.getDescription())
                        .actors(film.getActors().stream()
                                .map(actor -> ActorDto.builder()
                                        .actorId(actor.getActorId())
                                        .firstName(actor.getFirstName())
                                        .lastName(actor.getLastName())
                                        .lastUpdate(actor.getLastUpdate())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }
}
