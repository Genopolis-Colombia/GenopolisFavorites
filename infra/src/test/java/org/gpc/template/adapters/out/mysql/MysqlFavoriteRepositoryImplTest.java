package org.gpc.template.adapters.out.mysql;

import org.gpc.template.MySQLTestContainer;
import org.gpc.template.kernel.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
class MysqlFavoriteRepositoryImplTest extends MySQLTestContainer {
    @Test
    void savePet() {
        Integer id = 1;
        String name = "Makarras";
        Integer age = 1;
        Specie specie = Specie.CAT;
        String breed = "Criollo";
        mySQLPetRepository.savePet(new Pet(name, age, specie, breed));
        Optional<Pet> maybePet = mySQLPetRepository.getPet(id);
        assert (maybePet.isPresent());
        assertEquals(name, maybePet.get().name());
        assertEquals(age, maybePet.get().age());
        assertEquals(specie, maybePet.get().specie());
        assertEquals(breed, maybePet.get().breed());
    }

    @Test
    void getGet() {
        Integer id = 2;
        Optional<Pet> maybePet = mySQLPetRepository.getPet(id);
        assertTrue(maybePet.isEmpty());
    }
}