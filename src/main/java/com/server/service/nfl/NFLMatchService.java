package com.server.service.nfl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.server.document.nfl.NFLMatch;
import com.server.exception.NoRecordException;
import com.server.lib.data.Pair;
import com.server.repository.nfl.NFLMatchRepositoryImpl;

@Service
public class NFLMatchService {
    public static List<Pair<Integer, String>> ROUNDS = Arrays.asList(
        new Pair<>(1, "Week 1"),
        new Pair<>(2, "Week 2"),
        new Pair<>(3, "Week 3"),
        new Pair<>(4, "Week 4"),
        new Pair<>(5, "Week 5"),
        new Pair<>(6, "Week 6"),
        new Pair<>(7, "Week 7"),
        new Pair<>(8, "Week 8"),
        new Pair<>(9, "Week 9"),
        new Pair<>(10, "Week 10"),
        new Pair<>(11, "Week 11"),
        new Pair<>(12, "Week 12"),
        new Pair<>(13, "Week 13"),
        new Pair<>(14, "Week 14"),
        new Pair<>(15, "Week 15"),
        new Pair<>(16, "Week 16"),
        new Pair<>(17, "Week 17"),
        new Pair<>(18, "Week 18"),
        new Pair<>(70, "Wildcard Round"),
        new Pair<>(80, "Divisional Round"),
        new Pair<>(90, "Conference Championship"),
        new Pair<>(99, "Super Bowl")
    );

    @Value("${sport.record.count}")
    private int recordCount;

    @Autowired
    private NFLMatchRepositoryImpl matchRepository;

    public boolean save(NFLMatch match) {
        List<NFLMatchError> validateErrors = validate(match);

        if(validateErrors.isEmpty()) {
            matchRepository.save(match);
            return true;
        }

        return false;
    }

    public int getMatchStatus(NFLMatch match) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(match.getScoreOne()== 0 && match.getScoreTwo() == 0 && match.getSeason() == currentYear) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public List<NFLMatchError> validate(NFLMatch match) {
        List<NFLMatchError> errors = new ArrayList<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(match.getSeason() > currentYear) {
            errors.add(NFLMatchError.SeasonError);
        }

        if(match.getScoreOne() == 0 && match.getScoreTwo() == 0 && match.getSeason() < currentYear) {
            errors.add(NFLMatchError.ScoreError);
        }

        if(match.getTeamOne().equals(match.getTeamTwo())) {
            errors.add(NFLMatchError.SameTeamError);
        }

        return errors;
    }

    public NFLMatch findById(String id) throws NoRecordException {
        NFLMatch match =  matchRepository.getById(id);
        if(match == null) {
            throw new NoRecordException("No match with id :" + id);
        }
        return match;
    }

    public List<NFLMatch> findBySeason(int season) {
        return matchRepository.getBySeason(season);
    }

    public void clear() {
        matchRepository.clear();
    }
}
