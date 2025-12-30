package org.example.vote.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VoteTest {

    @Test
    void testVoteCreation() {
        // Arrange
        String voterId = "user1";
        String candidateId = "candidateA";
        long timestamp = System.currentTimeMillis();

        // Act
        Vote vote = new Vote(voterId, candidateId, timestamp);

        // Assert
        assertEquals(voterId, vote.voterId());
        assertEquals(candidateId, vote.candidateId());
        assertEquals(timestamp, vote.timestamp());
    }

    @Test
    void testToString() {
        Vote vote = new Vote("u1", "c1", 1000L);
        assertNotNull(vote.toString());
        assertTrue(vote.toString().contains("u1"));
    }
}