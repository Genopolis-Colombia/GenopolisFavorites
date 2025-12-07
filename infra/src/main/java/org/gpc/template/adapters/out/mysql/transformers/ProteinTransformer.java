package org.gpc.template.adapters.out.mysql.transformers;

import org.gpc.template.adapters.out.mysql.model.ProteinEntity;
import org.gpc.template.kernel.Favorite;

public class ProteinTransformer {

    public static ProteinEntity proteinToEntity(Favorite favorite) {
        ProteinEntity proteinEntity = new ProteinEntity();
        proteinEntity.setFastaNombre(favorite.fastaNombre());
        proteinEntity.setFastaSecuencia(favorite.fastaSecuencia());
        proteinEntity.setFuente(favorite.fuente());
        proteinEntity.setOrganismo(favorite.organismo());
        proteinEntity.setClasificacion(favorite.clasificacion());
        proteinEntity.setEcClasificacion(favorite.ecClasificacion());
        proteinEntity.setAutores(favorite.autores());
        return proteinEntity;
    }

    public static Favorite entityToProtein(ProteinEntity proteinEntity) {
        return new Favorite(
                proteinEntity.getFastaNombre(),
                proteinEntity.getFastaSecuencia(),
                proteinEntity.getFuente(),
                proteinEntity.getOrganismo(),
                proteinEntity.getClasificacion(),
                proteinEntity.getEcClasificacion(),
                proteinEntity.getAutores()
        );
    }

    public static ProteinEntity updateProteinToEntity(UpdateProtein updateProtein) {
        ProteinEntity proteinEntity = new ProteinEntity();
        proteinEntity.setId(updateProtein.idProteina());
        proteinEntity.setFastaNombre(updateProtein.fastaNombre());
        proteinEntity.setFastaSecuencia(updateProtein.fastaSecuencia());
        proteinEntity.setFuente(updateProtein.fuente());
        proteinEntity.setOrganismo(updateProtein.organismo());
        proteinEntity.setClasificacion(updateProtein.clasificacion());
        proteinEntity.setEcClasificacion(updateProtein.ecClasificacion());
        proteinEntity.setAutores(updateProtein.autores());
        return proteinEntity;
    }
}
