package org.example.vote.strategy;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RankedChoiceCountingStrategyTest {

    @Test
    void testCountVotes() {
        // Pour l'instant, le comportement est identique à Plurality
        CountingStrategy strategy = new RankedChoiceCountingStrategy();
        List<Vote> votes = List.of(
                new Vote("v1", "CandidatA", 1L)
        );

        Map<String, Integer> results = strategy.count(votes);
        assertEquals(1, results.get("CandidatA"));
    }
}