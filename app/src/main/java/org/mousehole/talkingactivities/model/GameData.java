package org.mousehole.talkingactivities.model;

import java.io.Serializable;
import java.util.LinkedList;

public class GameData implements Serializable {
    private String guesses = "";
    private String hints = "";

    public GameData(String guesses, String hints) {
        this.guesses = guesses;
        this.hints = hints;
    }

    public void addGuess(String guess) {
        guesses = guess + "\n" + guesses;
    }

    public void setGuesses(String gueses) {this.guesses = guesses;}

    public String getGuesses() {
        return guesses;
    }

    public void addHint(String hint) {
        hints = hint + "\n" + hints;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) { this.hints = hints; }

}
