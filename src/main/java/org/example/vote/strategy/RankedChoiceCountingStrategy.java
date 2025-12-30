package org.example.vote.strategy;

import org.example.vote.model.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankedChoiceCountingStrategy implements CountingStrategy {

    @Override
    public Map<String, Integer> count(List<Vote> votes) {
        Map<String, Integer> result = new HashMap<>();
        for (Vote v : votes) {
            result.merge(v.candidateId(), 1, Integer::sum);
        }
        return result;
    }
}
