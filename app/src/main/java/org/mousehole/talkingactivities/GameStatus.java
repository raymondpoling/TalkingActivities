package org.mousehole.talkingactivities;

import java.io.Serializable;
import java.util.LinkedList;

public class GameStatus implements Serializable {
    private String guesses = "";
    private String hints = "";

    public void addGuess(String guess) {
        guesses = guess + "\n" + guesses;
    }

    public String getGuesses() {
        return guesses;
    }

    public void addHint(String hint) {
        hints = hint + "\n" + hints;
    }

    public String getHints() {
        return hints;
    }

}
