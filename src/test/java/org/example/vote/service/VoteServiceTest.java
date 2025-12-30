package org.example.vote.service;

import org.example.vote.model.Vote;

import java.util.List;

public interface VoteServiceTest {
    public void save(Vote vote) ;
    public List<Vote> findAll() ;
    public void clear() ;
}
