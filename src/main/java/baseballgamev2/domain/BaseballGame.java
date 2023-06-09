package baseballgamev2.domain;

public class BaseballGame {

    private final Balls answer;
    private GameStatus gameStatus;

    public BaseballGame(BallsGenerator ballsGenerator) {
        this.answer = ballsGenerator.generate();
        this.gameStatus = GameStatus.PLAY;
    }

    public PlayResult play(Balls userBalls) {
        if (isEnd()) {
            throw new IllegalStateException();
        }

        return getPlayResult(userBalls);
    }

    public boolean isEnd() {
        return gameStatus.isEnd();
    }

    private PlayResult getPlayResult(Balls userBalls) {
        PlayResult play = answer.play(userBalls);
        if (play.isGameOver()) {
            changeGameStatus();
        }

        return play;
    }

    private void changeGameStatus() {
        this.gameStatus = GameStatus.END;
    }
}