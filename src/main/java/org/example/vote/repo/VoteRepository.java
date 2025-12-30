package org.example.vote.repo;

import org.example.vote.model.Vote;

import java.util.List;

public interface VoteRepository {
    public void save(Vote vote);
    public List<Vote> findAll();
    public void clear();
}
