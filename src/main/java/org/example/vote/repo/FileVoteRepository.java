package org.example.vote.repo;

import org.example.vote.model.Vote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileVoteRepository implements VoteRepository {
    private final File file;

    public FileVoteRepository(String filename) {
        this.file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur création fichier votes", e);
        }
    }

    @Override
    public synchronized void save(Vote vote) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(vote.voterId() + "," + vote.candidateId() + "," + vote.timestamp());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erreur écriture vote", e);
        }
    }

    @Override
    public synchronized List<Vote> findAll() {
        List<Vote> votes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    votes.add(new Vote(parts[0], parts[1], Long.parseLong(parts[2])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture votes", e);
        }
        return votes;
    }

    @Override
    public synchronized void clear() {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (IOException e) {
            throw new RuntimeException("Erreur réinitialisation fichier votes", e);
        }
    }
}

