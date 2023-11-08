package org.k0c3.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.k0c3.app.dto.ActorDto;
import org.k0c3.app.model.Actor;
import org.k0c3.app.repository.ActorRepository;

import java.util.List;
import java.util.Optional;

@Path("api/actor")
@AllArgsConstructor
public class ActorResource {

    private final ActorRepository actorRepository;

    @GET
    @Path("{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Actor> getActorById(@PathParam("id") short id) {
        return actorRepository.getActorById(id);
    }

    @GET
    @Path("/{startsWith}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ActorDto> getActorsStartsWith(String startsWith) {
        return actorRepository.getActorsStartsWith(startsWith);
    }
}
