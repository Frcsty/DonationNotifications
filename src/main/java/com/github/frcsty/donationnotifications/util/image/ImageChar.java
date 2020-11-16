package com.github.frcsty.donationnotifications.util.image;

public enum ImageChar {

    BLOCK('\u2588'),
    DARK_SHADE('\u2593'),
    MEDIUM_SHADE('\u2592'),
    LIGHT_SHADE('\u2591');

    private final char character;

    ImageChar(final char character) {
        this.character = character;
    }

    public char getCharacter() { return this.character; }

}
