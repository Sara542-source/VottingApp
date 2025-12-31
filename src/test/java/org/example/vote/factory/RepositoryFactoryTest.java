package org.example.vote.factory;

import org.example.vote.repo.FileVoteRepository;
import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.repo.VoteRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryFactoryTest {

    @Test
    void testCreateMemoryRepo() {
        VoteRepository repo = RepositoryFactory.createRepo("memory");
        assertTrue(repo instanceof InMemoryVoteRepository);
    }

    @Test
    void testCreateFileRepo() {
        VoteRepository repo = RepositoryFactory.createRepo("file");
        assertTrue(repo instanceof FileVoteRepository);
    }

    @Test
    void testUnknownRepo() {
        // Vérifie que l'application lance bien une erreur si on demande n'importe quoi
        assertThrows(IllegalArgumentException.class, () -> {
            RepositoryFactory.createRepo("cloud_database_inexistante");
        });
    }
}