package org.gpc.template.configuration;

import org.gpc.template.adapters.out.mysql.MysqlProteinRepositoryImpl;
import org.gpc.template.adapters.out.mysql.ProteinRepository;
import org.gpc.template.handlers.CreateProteinHandler;
import org.gpc.template.handlers.DeleteProteinHandler;
import org.gpc.template.handlers.GetProteinHandler;
import org.gpc.template.port.RepositoryPort;
import org.gpc.template.usecase.CreateFavoriteUseCaseImpl;
import org.gpc.template.usecase.DeleteFavoriteUseCaseImpl;
import org.gpc.template.usecase.GetFavoriteUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    MysqlProteinRepositoryImpl getMysqlProteinRepositoryImpl(ProteinRepository proteinRepository){
        return new MysqlProteinRepositoryImpl(proteinRepository);
    }
    @Bean
    CreateFavoriteUseCaseImpl getCreateProteinUseCaseImpl(RepositoryPort repositoryPort){
        return new CreateFavoriteUseCaseImpl(repositoryPort);
    }

    @Bean
    GetFavoriteUseCaseImpl getProteinUseCase(RepositoryPort repositoryPort){
        return new GetFavoriteUseCaseImpl(repositoryPort);
    }

    @Bean
    DeleteFavoriteUseCaseImpl getDeleteProteinUseCase(RepositoryPort repositoryPort){
        return new DeleteFavoriteUseCaseImpl(repositoryPort);
    }
    @Bean
    PutProteinUseCaseImpl getPutProteinUseCase(RepositoryPort repositoryPort){
        return new PutProteinUseCaseImpl(repositoryPort);
    }

    @Bean
    UpdateProteinHandler getUpdateProteinHandler(GetFavoriteUseCaseImpl getProteinUseCase, PutProteinUseCaseImpl putProteinUseCase){
        return new UpdateProteinHandler(putProteinUseCase, getProteinUseCase);
    }

    @Bean
    CreateProteinHandler getCreateProteinHandler(CreateFavoriteUseCaseImpl createProteinUseCase){
        return new CreateProteinHandler(createProteinUseCase);
    }

    @Bean
    GetProteinHandler getGetProteinHandler(GetFavoriteUseCaseImpl getProteinUseCase){
        return new GetProteinHandler(getProteinUseCase);
    }

    @Bean
    DeleteProteinHandler getDeleteProteinHandler(DeleteFavoriteUseCaseImpl deleteProteinUseCase){
        return new DeleteProteinHandler(deleteProteinUseCase);
    }

}
