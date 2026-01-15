package org.gpc.template.configuration;

import org.gpc.template.adapters.out.mysql.MysqlFavoriteRepositoryImpl;
import org.gpc.template.adapters.out.mysql.FavoriteRepository;
import org.gpc.template.handlers.CreateFavoriteHandler;
import org.gpc.template.handlers.DeleteFavoriteHandler;
import org.gpc.template.handlers.FindFavoritesByUserHandler;
import org.gpc.template.handlers.GetFavoriteHandler;
import org.gpc.template.port.RepositoryPort;
import org.gpc.template.usecase.CreateFavoriteUseCaseImpl;
import org.gpc.template.usecase.DeleteFavoriteUseCaseImpl;
import org.gpc.template.usecase.FindFavoritesByUserUseCaseImpl;
import org.gpc.template.usecase.GetFavoriteUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    MysqlFavoriteRepositoryImpl getMysqlFavoriteRepositoryImpl(FavoriteRepository favoriteRepository){
        return new MysqlFavoriteRepositoryImpl(favoriteRepository);
    }
    @Bean
    CreateFavoriteUseCaseImpl getCreateFavoriteUseCaseImpl(RepositoryPort repositoryPort){
        return new CreateFavoriteUseCaseImpl(repositoryPort);
    }

    @Bean
    GetFavoriteUseCaseImpl getFavoriteUseCase(RepositoryPort repositoryPort){
        return new GetFavoriteUseCaseImpl(repositoryPort);
    }

    @Bean
    DeleteFavoriteUseCaseImpl getDeleteProteinUseCase(RepositoryPort repositoryPort){
        return new DeleteFavoriteUseCaseImpl(repositoryPort);
    }

    @Bean
    public FindFavoritesByUserUseCaseImpl findFavoritesByUserUseCase(RepositoryPort repositoryPort) {
        return new FindFavoritesByUserUseCaseImpl(repositoryPort);
    }

    @Bean
    public FindFavoritesByUserHandler findFavoritesByUserHandler(FindFavoritesByUserUseCaseImpl useCase) {
        return new FindFavoritesByUserHandler(useCase);
    }

    @Bean
    CreateFavoriteHandler getCreateFavoriteHandler(CreateFavoriteUseCaseImpl createFavoriteUseCase){
        return new CreateFavoriteHandler(createFavoriteUseCase);
    }

    @Bean
    GetFavoriteHandler getGetFavoriteHandler(GetFavoriteUseCaseImpl getFavoriteUseCase){
        return new GetFavoriteHandler(getFavoriteUseCase);
    }

    @Bean
    DeleteFavoriteHandler getDeleteProteinHandler(DeleteFavoriteUseCaseImpl deleteFavoriteUseCase){
        return new DeleteFavoriteHandler(deleteFavoriteUseCase);
    }

}
