package org.gpc.template.adapters.out.mysql;

import org.gpc.template.adapters.out.mysql.model.ProteinEntity;
import org.gpc.template.adapters.out.mysql.transformers.ProteinTransformer;
import org.gpc.template.kernel.Favorite;
import org.gpc.template.port.RepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;


public class MysqlProteinRepositoryImpl implements RepositoryPort {

    private final ProteinRepository proteinRepository;
    private static final Logger logger = LoggerFactory.getLogger(MysqlProteinRepositoryImpl.class);

    public MysqlProteinRepositoryImpl(ProteinRepository proteinRepository) {
        this.proteinRepository = proteinRepository;
    }

    @Override
    public UUID saveProtein(Favorite favorite) {
        logger.debug("Starting saving protein");
        return proteinRepository.save(ProteinTransformer.proteinToEntity(favorite)).getId();
    }

    @Override
    public Optional<Favorite> getProtein(UUID id) {
        return proteinRepository.findById(id).map(ProteinTransformer::entityToProtein);
    }

    @Override
    public void deleteProtein(UUID id) {
        proteinRepository.deleteById(id);
    }

    @Override
    public Optional<Favorite> putProtein(UpdateProtein updateprotein) {

        ProteinEntity proteinEntity = ProteinTransformer.updateProteinToEntity(updateprotein);
        proteinRepository.save(proteinEntity);
        return Optional.of(ProteinTransformer.entityToProtein(proteinEntity));
    }

    // Only for testing purposes
    @Override
    public void deleteAll(){
        proteinRepository.deleteAll();
    }
}
