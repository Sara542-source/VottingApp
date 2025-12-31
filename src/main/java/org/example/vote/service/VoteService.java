package org.example.vote.service;

import org.example.vote.model.Vote;
import org.example.vote.observer.VoteListener;
import org.example.vote.repo.VoteRepository;
import org.example.vote.strategy.CountingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//enregistrer, notifier, compter, reset , C’est ici que Factory Method + Strategy + Observer se combinent.
public class VoteService {

    private final VoteRepository repository;
    private final List<VoteListener> listeners = new ArrayList<>();
    private CountingStrategy countingStrategy;

    public VoteService(VoteRepository repository,CountingStrategy countingStrategy) {
        this.repository = repository;
        this.countingStrategy=countingStrategy;
    }

    public void setCountingStrategy(CountingStrategy countingStrategy) {
        this.countingStrategy = countingStrategy;
    }
    public CountingStrategy getCountingStrategy() {
        return countingStrategy;
    }

    // Ajouter un observateur
    public void addListener(VoteListener listener) {
        listeners.add(listener);
    }

    // Enregistrer un vote et notifier les listeners
    // Remplace l'ancienne méthode castVote
    public void castVote(Vote vote) {
        // Vérifier si le votant a déjà voté
        boolean alreadyVoted = repository.findAll().stream()
                .anyMatch(v -> v.voterId().equals(vote.voterId()));
        if (alreadyVoted) {
            System.out.println("Erreur : le votant " + vote.voterId() + " a déjà voté !");
            return; // vote non enregistré
        }

        //  Vérifier que le candidat existe
        boolean candidateExists = repository.findAll().stream()
                .anyMatch(v -> v.candidateId().equals(vote.candidateId()));
        if (!candidateExists) {
            System.out.println("Erreur : le candidat " + vote.candidateId() + " n'existe pas !");
            return; // vote non enregistré
        }

        // Enregistrer le vote et notifier les observers
        repository.save(vote);
        for (VoteListener listener : listeners) {
            listener.onVote(vote);
        }
    }


    // Compter les votes selon la stratégie choisie
    public Map<String, Integer> countVotes() {
        return countingStrategy.count(repository.findAll()); // Strategy pattern
    }

    // Réinitialiser tous les votes
    public void reset() {
        repository.clear();
    }
}
