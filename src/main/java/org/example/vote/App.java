package org.example.vote;

import org.example.vote.factory.RepositoryFactory;
import org.example.vote.model.Vote;
import org.example.vote.observer.AuditVoteListener;
import org.example.vote.observer.LoggingVoteListener;
import org.example.vote.repo.VoteRepository;
import org.example.vote.service.VoteService;
import org.example.vote.strategy.CountingStrategy;
import org.example.vote.strategy.PluralityCountingStrategy;

import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Choix du type de repository
        System.out.println("Choisissez le type de repository : memory / file");
        String repoType = sc.nextLine().trim();

        VoteRepository repository = RepositoryFactory.createRepo(repoType);
        CountingStrategy strategy = new PluralityCountingStrategy();
        VoteService service = new VoteService(repository,strategy);
        service.addListener(new LoggingVoteListener());
        service.addListener(new AuditVoteListener("data/audit.txt"));

        System.out.println("Bienvenue dans VotingApp !");
        System.out.println("Commandes : vote / count / reset / exit");

        while (true) {
            String cmd = sc.nextLine();
            switch (cmd) {
                case "vote" -> {
                    System.out.print("Voter ID : ");
                    String voterId = sc.nextLine();
                    System.out.print("Candidate : ");
                    String candidateId = sc.nextLine();
                    Vote vote = new Vote(voterId, candidateId, System.currentTimeMillis());
                    service.castVote(vote);
                }
                case "count" -> {
                    Map<String, Integer> results = service.countVotes();
                    System.out.println("Résultats : " + results);
                }
                case "reset" -> {
                    service.reset();
                    System.out.println("Votes réinitialisés !");
                }
                case "exit" -> {
                    System.out.println("Au revoir !");
                    sc.close();
                    return;
                }
                default -> System.out.println("Commande inconnue !");
            }
        }
    }
}
