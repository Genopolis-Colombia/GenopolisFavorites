package org.gpc.template.adapters.out.mysql;

import org.gpc.template.adapters.out.mysql.model.FavoriteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;


public interface FavoriteRepository extends CrudRepository<FavoriteEntity, UUID> {

    List<FavoriteEntity> findByUserId(UUID userId);

}
