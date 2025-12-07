package org.gpc.template.port;

import org.gpc.template.kernel.Favorite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryPort {
    UUID saveFavorite(Favorite favorite);
    List<Favorite> findByUserId(UUID userId);
    Optional<Favorite> getFavorite(UUID id);
    void deleteFavorite(UUID id);
    void deleteAll();
}
