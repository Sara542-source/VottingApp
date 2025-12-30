package org.example.vote.factory;

import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.repo.VoteRepository;
import org.example.vote.repo.FileVoteRepository;

public class RepositoryFactory {

    public static VoteRepository createRepo(String type) {
        if ("memory".equalsIgnoreCase(type)) {
            return new InMemoryVoteRepository();

     } else if ("file".equalsIgnoreCase(type)) {
        return new FileVoteRepository("data/votes.txt");
    }
        throw new IllegalArgumentException("Unknown repository type: " + type);
    }
}
