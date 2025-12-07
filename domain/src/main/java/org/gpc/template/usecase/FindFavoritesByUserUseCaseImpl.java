package org.gpc.template.usecase;

import org.gpc.template.kernel.Favorite;
import org.gpc.template.port.RepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class FindFavoritesByUserUseCaseImpl implements UseCase<UUID, List<Favorite>> {
    private final RepositoryPort repositoryPort;
    private static final Logger logger = LoggerFactory.getLogger(FindFavoritesByUserUseCaseImpl.class);

    public FindFavoritesByUserUseCaseImpl(RepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Favorite>  execute(UUID userId) {
        logger.debug("Executing command: " + userId);
        return repositoryPort.findByUserId(userId);
    }
}

