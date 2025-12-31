package org.example.vote.service;

import org.example.vote.model.Vote;
import org.example.vote.observer.VoteListener;
import org.example.vote.repo.VoteRepository;
import org.example.vote.strategy.CountingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Active Mockito
class VoteServiceTest {

    @Mock
    private VoteRepository repository; // Crée un faux repository

    @Mock
    private CountingStrategy strategy; // creer une fausse strategy

    @Mock
    private VoteListener listener; // Crée un faux listener

    @InjectMocks
    private VoteService service; // Injecte les mocks dans le service

    @Test
    void testCastVote_Nominal() {
        // Arrange
        String candidate = "CandidatA";
        Vote existingVote = new Vote("autre_votant", candidate, 1L);
        Vote newVote = new Vote("moi", candidate, 2L);

        // Simulation : On dit que le candidat existe déjà (via un vote précédent)
        // et que "moi" n'ai pas encore voté.
        when(repository.findAll()).thenReturn(List.of(existingVote));

        service.addListener(listener);

        // Act
        service.castVote(newVote);

        // Assert
        verify(repository).save(newVote); // Vérifie que la méthode save a été appelée
        verify(listener).onVote(newVote); // Vérifie que le listener a été notifié
    }

    @Test
    void testCastVote_DejaVote() {
        // Arrange
        Vote vote = new Vote("moi", "CandidatA", 1L);
        // Simulation : "moi" est déjà dans la liste des votes retournée par le repo
        when(repository.findAll()).thenReturn(List.of(vote));

        // Act
        service.castVote(vote);

        // Assert
        verify(repository, never()).save(any()); // save ne doit JAMAIS être appelé
    }

    @Test
    void testCountVotes() {
        // Arrange
        List<Vote> fakeVotes = List.of(new Vote("a", "b", 1L));

        when(repository.findAll()).thenReturn(fakeVotes);
        when(strategy.count(fakeVotes)).thenReturn(Map.of("b", 1));

        // Act
        Map<String, Integer> result = service.countVotes();

        // Assert
        assertEquals(1, result.get("b"));
    }
}