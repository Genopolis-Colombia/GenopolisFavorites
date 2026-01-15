package org.gpc.template.usecase;

import org.gpc.template.kernel.Favorite;
import org.gpc.template.port.RepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class CreateFavoriteUseCaseImpl implements UseCase<Favorite, UUID>{

    private final RepositoryPort repositoryPort;
    private static final Logger logger = LoggerFactory.getLogger(CreateFavoriteUseCaseImpl.class);

    public CreateFavoriteUseCaseImpl(RepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public UUID execute(Favorite command) {
        logger.debug("Executing command: " + command);
        return repositoryPort.saveFavorite(command);
    }
}
