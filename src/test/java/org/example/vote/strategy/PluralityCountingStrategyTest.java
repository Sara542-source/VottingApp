package org.example.vote.strategy;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PluralityCountingStrategyTest {

    @Test
    void testCountVotes() {
        // Arrange
        CountingStrategy strategy = new PluralityCountingStrategy();
        List<Vote> votes = List.of(
                new Vote("v1", "CandidatA", 1L),
                new Vote("v2", "CandidatA", 2L),
                new Vote("v3", "CandidatB", 3L)
        );

        // Act
        Map<String, Integer> results = strategy.count(votes);

        // Assert
        assertEquals(2, results.get("CandidatA"));
        assertEquals(1, results.get("CandidatB"));
    }

    @Test
    void testCountEmptyVotes() {
        CountingStrategy strategy = new PluralityCountingStrategy();
        Map<String, Integer> results = strategy.count(List.of());
        assertTrue(results.isEmpty());
    }
}