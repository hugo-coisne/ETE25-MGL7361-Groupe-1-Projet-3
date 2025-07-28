package ca.uqam.mgl7361.lel.gp1.shop.exception;

import java.util.List;
import java.util.Map;

public class InvalidArgumentException extends IllegalArgumentException {
    Map<String, List<String>> problems;
    public InvalidArgumentException(Map<String, List<String>> problems){
        super();
        this.problems = problems;
    }

    public Map<String, List<String>> getProblems(){
        return this.problems;
    }
}
