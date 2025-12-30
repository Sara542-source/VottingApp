package org.example.vote.observer;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditVoteListenerTest {

    private static final String AUDIT_FILE = "test_audit.txt";
    private AuditVoteListener listener;

    @BeforeEach
    void setUp() {
        new File(AUDIT_FILE).delete();
        listener = new AuditVoteListener(AUDIT_FILE);
    }

    @AfterEach
    void tearDown() {
        new File(AUDIT_FILE).delete();
    }

    @Test
    void testOnVoteWritesToFile() throws IOException {
        // Arrange
        Vote vote = new Vote("user1", "candidateA", 123456L);

        // Act
        listener.onVote(vote);

        // Assert : On relit le fichier pour voir si la ligne est bien là
        File file = new File(AUDIT_FILE);
        assertTrue(file.exists(), "Le fichier d'audit doit être créé");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertNotNull(line, "Le fichier ne doit pas être vide");
            assertTrue(line.contains("user1"));
            assertTrue(line.contains("candidateA"));
        }
    }
}