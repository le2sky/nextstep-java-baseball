package baseballgamev2.controller;

import static java.util.Arrays.stream;

import baseballgamev2.domain.Balls;
import java.util.List;
import java.util.stream.Collectors;

public class BallsMapper {

    public static Balls mapToBalls(String input) {
        List<Integer> target = stream(input.split(""))
            .map(Integer::parseInt)
            .collect(Collectors.toUnmodifiableList());

        return new Balls(target);
    }
}