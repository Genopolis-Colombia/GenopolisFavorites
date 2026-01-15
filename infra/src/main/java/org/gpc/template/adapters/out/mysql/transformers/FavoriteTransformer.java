package org.gpc.template.adapters.out.mysql.transformers;

import org.gpc.template.adapters.out.mysql.model.FavoriteEntity;
import org.gpc.template.kernel.Favorite;

public class FavoriteTransformer {

    public static FavoriteEntity favoriteToEntity(Favorite favorite) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUserId(favorite.userId());
        favoriteEntity.setProteinId(favorite.proteinId());
        favoriteEntity.setFastaName(favorite.fastaName());
        return favoriteEntity;
    }

    public static Favorite entityToFavorite(FavoriteEntity favoriteEntity) {
        return new Favorite(
                favoriteEntity.getId(),
                favoriteEntity.getUserId(),
                favoriteEntity.getProteinId(),
                favoriteEntity.getFastaName()
        );
    }

}
