package tn.esprit.autoloc.io;

public class HybrideRapportWriter implements IRapportWriter {

    @Override
    public String ecrire(String rapport) {
        int finEntete = rapport.indexOf('\n');
        System.out.println(rapport.substring(0, finEntete));
        return rapport.substring(finEntete + 1);
    }
}
