package org.gpc.template.usecase;

import lombok.AllArgsConstructor;
import org.gpc.template.kernel.Favorite;
import org.gpc.template.port.RepositoryPort;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class GetFavoriteUseCaseImpl implements UseCase<UUID, Optional<Favorite>> {

    private final RepositoryPort repositoryPort;
    @Override
    public Optional<Favorite> execute(UUID id) {
        return repositoryPort.getFavorite(id);
    }
}
