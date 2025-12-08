package org.gpc.template.adapters.out.mysql;

import org.gpc.template.adapters.out.mysql.model.FavoriteEntity;
import org.gpc.template.adapters.out.mysql.transformers.FavoriteTransformer;
import org.gpc.template.kernel.Favorite;
import org.gpc.template.port.RepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;


public class MysqlFavoriteRepositoryImpl implements RepositoryPort {

    private final FavoriteRepository favoriteRepository;
    private static final Logger logger = LoggerFactory.getLogger(MysqlFavoriteRepositoryImpl.class);

    public MysqlFavoriteRepositoryImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public UUID saveFavorite(Favorite favorite) {
        logger.debug("Starting saving favorite");
        return favoriteRepository.save(FavoriteTransformer.favoriteToEntity(favorite)).getId();
    }

    @Override
    public Optional<Favorite> getProtein(UUID id) {
        return favoriteRepository.findById(id).map(FavoriteTransformer::entityToProtein);
    }

    @Override
    public void deleteProtein(UUID id) {
        favoriteRepository.deleteById(id);
    }

    @Override
    public Optional<Favorite> putProtein(UpdateProtein updateprotein) {

        FavoriteEntity favoriteEntity = FavoriteTransformer.updateProteinToEntity(updateprotein);
        favoriteRepository.save(favoriteEntity);
        return Optional.of(FavoriteTransformer.entityToProtein(favoriteEntity));
    }

    // Only for testing purposes
    @Override
    public void deleteAll(){
        favoriteRepository.deleteAll();
    }
}
