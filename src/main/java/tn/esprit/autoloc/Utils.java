package tn.esprit.autoloc;

public final class Utils {

    private Utils() {
    }

    // Une chaine est valide si elle n'est ni null, ni vide, ni composee uniquement d'espaces
    public static boolean isNotEmpty(String value) {
        return value != null && !value.isBlank();
    }
}
