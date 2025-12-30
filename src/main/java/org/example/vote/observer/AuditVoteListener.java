package org.example.vote.observer;

import org.example.vote.model.Vote;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AuditVoteListener implements VoteListener {

    private final String filename;

    public AuditVoteListener(String filename) {
        this.filename = filename;
    }

    @Override
    public void onVote(Vote vote) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println("VOTE | Voter: " + vote.voterId() + " | Candidate: " + vote.candidateId() + " | Timestamp: " + vote.timestamp());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
