package org.gpc.template.adapters.in.http;

import org.gpc.template.MySQLTestContainer;
import org.gpc.template.adapters.in.http.dto.CreateFavoriteRequestDTO;
import org.gpc.template.kernel.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FavoriteControllerAdapterTest extends MySQLTestContainer {

    @Value("${local.server.port}")
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";

    @Test
    void shouldSaveAProteinSuccessful() {
        String path = host + port + "/pets";
        CreateFavoriteRequestDTO entity = new CreateFavoriteRequestDTO("P53", "MEEPQSDPSV", "Human", "Homo sapiens", "Transcription Factor", 1234, "Levine et al.");
        HttpEntity<CreateFavoriteRequestDTO> request = new HttpEntity<>(entity);
        ResponseEntity<CreateFavoriteRequestDTO> response = restTemplate.exchange(path, HttpMethod.POST, request, CreateFavoriteRequestDTO.class);
        Optional<Favorite> maybeProteinSaved = mySQLProteinRepository.getProtein(Objects.requireNonNull(response.getBody()).id());

        validateSuccessfulResponse(response);
        assertTrue(maybeProteinSaved.isPresent());
        assertEquals(entity.fastaNombre(), maybeProteinSaved.get().fastaNombre());
        assertEquals(entity.fastaSecuencia(), maybeProteinSaved.get().fastaSecuencia());
        assertEquals(entity.fuente(), maybeProteinSaved.get().fuente());
        assertEquals(entity.organismo(), maybeProteinSaved.get().fastaNombre());
        assertEquals(entity.clasificacion(), maybeProteinSaved.get().clasificacion());
        assertEquals(entity.ecClasificacion(), maybeProteinSaved.get().ecClasificacion());
        assertEquals(entity.autores(), maybeProteinSaved.get().autores());
    }

    @Test
    void shouldGetAProteinSuccessful() {
        Favorite expectedFavorite = new Favorite("PMM2", "MEEPQSDPSV", "Human", "Homo sapiens", "Transcription Factor", 1234, "Levine et al.", 2012-12-03,2025-12-06);
        UUID id = mySQLProteinRepository.saveProtein(expectedFavorite);
        String path = host + port + "/proteins" + "/" + id;
        ResponseEntity<Favorite> response = restTemplate.exchange(path, HttpMethod.GET, null, Favorite.class);

        validateSuccessfulResponse(response);
        assertEquals(expectedFavorite.fastaNombre(), Objects.requireNonNull(response.getBody()).fastaNombre());
        assertEquals(expectedFavorite.fastaSecuencia(), Objects.requireNonNull(response.getBody()).fastaSecuencia());
        assertEquals(expectedFavorite.fuente(), Objects.requireNonNull(response.getBody()).fuente());
        assertEquals(expectedFavorite.organismo(), Objects.requireNonNull(response.getBody()).organismo());
        assertEquals(expectedFavorite.clasificacion(), Objects.requireNonNull(response.getBody()).clasificacion());
        assertEquals(expectedFavorite.ecClasificacion(), Objects.requireNonNull(response.getBody()).ecClasificacion());
        assertEquals(expectedFavorite.autores(), Objects.requireNonNull(response.getBody()).autores());
    }

    @Test
    void shouldUpdateAProteinSuccessful() {
        Favorite updatedFavorite = new Favorite("OldName", "AAAA", "Bacteria", "E.coli", "Enzyme", 111, "Dr. X");
        UUID id = mySQLProteinRepository.saveProtein(updatedFavorite);
        String path = host + port + "/proteins" + "/" + id;

        Favorite expectedFavorite = new Favorite("ProteinaB", "AAAA", "Bacteria", "E.coli", "Enzyme", 111, "Dr. X");
        UpdateProteinRequestDTO entity = new UpdateProteinRequestDTO(
                Optional.of(expectedFavorite.fastaNombre()),
                Optional.of(expectedFavorite.fastaSecuencia()),
                Optional.of(expectedFavorite.fuente()),
                Optional.of(expectedFavorite.organismo()),
                Optional.of(expectedFavorite.clasificacion()),
                Optional.of(expectedFavorite.ecClasificacion()),
                Optional.of(expectedFavorite.autores())
        );

        HttpEntity<UpdateProteinRequestDTO> request = new HttpEntity<>(entity);
        ResponseEntity<Object> response = restTemplate.exchange(path, HttpMethod.PUT, request, Object.class);

        validateSuccessfulResponse(response);
        Optional<Favorite> maybeProtein = mySQLProteinRepository.getProtein(id);
        assertTrue(maybeProtein.isPresent());
        assertEquals(expectedFavorite.name(), maybeProtein.map(Favorite::name).orElse("Invalid"));
        assertEquals(expectedFavorite.age(), maybeProtein.map(Favorite::age).orElse(-1));
        assertEquals(expectedFavorite.specie(), maybeProtein.map(Favorite::specie).orElse(Specie.DOG));
        assertEquals(expectedFavorite.breed(), maybeProtein.map(Favorite::breed).orElse("Invalid"));
    }

    @Test
    void shouldDeleteAPetSuccessful() {
        Favorite petToBeDeleted = new Favorite("Mauricio", 2, Specie.CAT, "Criollito");
        Integer id = mySQLProteinRepository.savePet(petToBeDeleted);
        String path = host + port + "/pets" + "/" + id;

        ResponseEntity<Integer> response = restTemplate.exchange(path, HttpMethod.DELETE, null, Integer.class);
        validateSuccessfulResponse(response);
        Optional<Pet> maybePet = mySQLPetRepository.getPet(id);
        assertTrue(maybePet.isEmpty());
    }

    private <T> void validateSuccessfulResponse(ResponseEntity<T> response) {
        System.out.println("Response: " + response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}