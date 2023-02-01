package edu.ntnu.idatt2001.alekol.Wargames.Model.Enum;

/**
 * simple enum to give the armies a banner
 */
public enum Banner {
    FORESTGREEN,
    ROYALBLUE,
    CRIMSON,
    ORANGE;

    public String getName() {
        switch (this) {
            case CRIMSON -> {
                return "Red";
            }
            case ORANGE -> {
                return "Orange";
            }
            case ROYALBLUE -> {
                return "Blue";
            }
            case FORESTGREEN -> {
                return "Green";
            }
        }
        return null;
    }
}
