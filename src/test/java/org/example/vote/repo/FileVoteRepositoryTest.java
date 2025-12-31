package org.example.vote.repo;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileVoteRepositoryTest {

    private static final String TEST_FILE = "test_votes.csv";
    private FileVoteRepository repository;

    @BeforeEach
    void setUp() {
        // On s'assure de partir propre
        new File(TEST_FILE).delete();
        repository = new FileVoteRepository(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        // Nettoyage après le test
        new File(TEST_FILE).delete();
    }

    @Test
    void testSaveAndPersist() {
        Vote vote = new Vote("user1", "candidateA", 1700000000L);
        repository.save(vote);

        // On recrée un repository pour vérifier qu'il lit bien le fichier (persistance)
        FileVoteRepository newRepo = new FileVoteRepository(TEST_FILE);
        List<Vote> votes = newRepo.findAll();

        assertEquals(1, votes.size());
        assertEquals("user1", votes.get(0).voterId());
    }

    @Test
    void testClear() {
        repository.save(new Vote("u1", "c1", 1L));
        repository.clear();

        FileVoteRepository newRepo = new FileVoteRepository(TEST_FILE);
        assertTrue(newRepo.findAll().isEmpty());
    }
}