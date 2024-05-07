package com.server.service.nfl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.document.nfl.NFLMatch;
import com.server.exception.NoRecordException;
import com.server.repository.nfl.NFLMatchRepositoryImpl;

@Service
public class NFLMatchService {
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
}
