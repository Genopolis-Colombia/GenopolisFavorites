package org.gpc.template.usecase;


import lombok.AllArgsConstructor;
import org.gpc.template.port.RepositoryPort;
import java.util.UUID;

@AllArgsConstructor
public class DeleteFavoriteUseCaseImpl implements UseCase<UUID, UUID>{

    private final RepositoryPort repositoryPort;

    @Override
    public UUID execute (UUID id){
        repositoryPort.deleteFavorite(id);
        return id;
    }
}
