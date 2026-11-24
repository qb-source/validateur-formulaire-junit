public class ValidateurFormulaire {

    boolean nomValide(String nom) {
        if (nom.isEmpty() || nom.length() > 15) {
            return false;
        }
        return true;
    }

    boolean courrielValide(String courriel) {
        return courriel.contains("@") && courriel.contains(".");
    }

    boolean motDePasseValide(String motDePasse) {
        if (motDePasse.length() < 8) {
            return false;
        }
        return contientUnChiffre(motDePasse);
    }

    boolean contientUnChiffre(String texte) {
        for (int i = 0; i <= 9; i++) {
            if (texte.contains("" + i)) {
                return true;
            }
        }
        return false;
    }

    boolean ageValide(int age) {
        return age >= 16;
    }

    void inscrire(String nom, String courriel, String motDePasse, int age) {
        if (nomValide(nom) == false) {
            throw new IllegalArgumentException("Nom d'utilisateur invalide");
        } else if (courrielValide(courriel) == false) {
            throw new IllegalArgumentException("Courriel invalide");
        } else if (motDePasseValide(motDePasse) == false) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères dont un chiffre");
        } else if (ageValide(age) == false) {
            throw new IllegalArgumentException("Il faut avoir au moins 16 ans");
        }
    }
}
