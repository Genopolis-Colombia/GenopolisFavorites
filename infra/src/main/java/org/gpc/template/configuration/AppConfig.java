package org.gpc.template.configuration;

import org.gpc.template.adapters.out.mysql.MysqlFavoriteRepositoryImpl;
import org.gpc.template.adapters.out.mysql.FavoriteRepository;
import org.gpc.template.handlers.CreateFavoriteHandler;
import org.gpc.template.handlers.DeleteFavoriteHandler;
import org.gpc.template.handlers.GetFavoriteHandler;
import org.gpc.template.port.RepositoryPort;
import org.gpc.template.usecase.CreateFavoriteUseCaseImpl;
import org.gpc.template.usecase.DeleteFavoriteUseCaseImpl;
import org.gpc.template.usecase.GetFavoriteUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    MysqlFavoriteRepositoryImpl getMysqlProteinRepositoryImpl(FavoriteRepository favoriteRepository){
        return new MysqlFavoriteRepositoryImpl(favoriteRepository);
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
    CreateFavoriteHandler getCreateProteinHandler(CreateFavoriteUseCaseImpl createProteinUseCase){
        return new CreateFavoriteHandler(createProteinUseCase);
    }

    @Bean
    GetFavoriteHandler getGetProteinHandler(GetFavoriteUseCaseImpl getProteinUseCase){
        return new GetFavoriteHandler(getProteinUseCase);
    }

    @Bean
    DeleteFavoriteHandler getDeleteProteinHandler(DeleteFavoriteUseCaseImpl deleteProteinUseCase){
        return new DeleteFavoriteHandler(deleteProteinUseCase);
    }

}
