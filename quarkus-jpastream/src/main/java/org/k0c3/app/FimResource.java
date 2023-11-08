package org.k0c3.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.k0c3.app.dto.FilmDto;
import org.k0c3.app.model.Film;
import org.k0c3.app.repository.FilmRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("api/film")
@AllArgsConstructor
public class FimResource {

    private final FilmRepository filmRepository;

    @GET
    @Path("{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Film> getFilmById(short id) {
        return filmRepository.getFilmById(id);
    }

    @GET
    @Path("/pagedFilms/{page}/{minLength: \\d+}")
    @Produces(MediaType.TEXT_PLAIN)// return to client
    public String getPagedFilms(long page, short minLength) {
        return filmRepository.paged(page, minLength)
                .map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))
                .collect(Collectors.joining("\n"));
    }
    @GET
    @Path("/{startsWith}/{minLength: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilmDto> getActors2(String startsWith, short minLength) {
        return filmRepository.filmsWithActors(startsWith, minLength);
    }
}
