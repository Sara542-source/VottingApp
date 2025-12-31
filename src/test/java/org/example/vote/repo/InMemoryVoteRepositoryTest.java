package org.example.vote.repo;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryVoteRepositoryTest {

    private InMemoryVoteRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryVoteRepository();
    }

    @Test
    void testSaveAndFindAll() {
        Vote vote = new Vote("user1", "candidateA", System.currentTimeMillis());
        repository.save(vote);

        List<Vote> votes = repository.findAll();
        assertEquals(1, votes.size());
        assertEquals(vote, votes.get(0));
    }

    @Test
    void testClear() {
        repository.save(new Vote("u1", "c1", 123L));
        repository.clear();

        assertTrue(repository.findAll().isEmpty(), "Le repository devrait être vide après un clear");
    }
}