import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests du validateur de formulaire d'inscription")
class ValidateurFormulaireTest {

    ValidateurFormulaire validateur;

    @BeforeEach
    void preparer() {
        validateur = new ValidateurFormulaire();
    }

    // ===== Nom d'utilisateur =====

    @Test
    @DisplayName("Accepte un nom d'utilisateur avec un tiret (\"qb-source\")")
    void accepteNomAvecTiret() {
        assertTrue(validateur.nomValide("qb-source"));
    }

    @Test
    @DisplayName("Refuse un nom d'utilisateur vide")
    void refuseNomVide() {
        assertFalse(validateur.nomValide(""));
    }

    // ===== Courriel =====

    @Test
    @DisplayName("Accepte un courriel valide (\"quentin@gmail.com\")")
    void accepteCourrielValide() {
        assertTrue(validateur.courrielValide("quentin@gmail.com"));
    }

    @Test
    @DisplayName("Refuse un courriel sans @ (\"quentin.gmail.com\")")
    void refuseCourrielSansArobase() {
        assertFalse(validateur.courrielValide("quentin.gmail.com"));
    }

    @Test
    @Disabled("Bug connu : le validateur accepte un point juste après le @")
    @DisplayName("Refuse un courriel avec un point juste après le @ (\"quentin@.gmail.com\")")
    void refuseCourrielAvecPointApresArobase() {
        assertFalse(validateur.courrielValide("quentin@.gmail.com"));
    }

    // ===== Mot de passe =====

    @Test
    @DisplayName("Accepte un mot de passe de 8 caractères ou plus avec un chiffre (\"motdepasse1\")")
    void accepteMotDePasseValide() {
        assertTrue(validateur.motDePasseValide("motdepasse1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc1", "motdepasse", "1234567"})
    @DisplayName("Refuse un mot de passe trop court ou sans chiffre")
    void refuseMotsDePasseInvalides(String motDePasse) {
        assertFalse(validateur.motDePasseValide(motDePasse));
    }

    // ===== Âge =====

    @Test
    @DisplayName("Accepte 16 ans, l'âge minimum")
    void accepteAgeDe16Ans() {
        assertTrue(validateur.ageValide(16));
    }

    @Test
    @DisplayName("Refuse 15 ans, juste sous l'âge minimum")
    void refuseAgeDe15Ans() {
        assertFalse(validateur.ageValide(15));
    }

    // ===== Inscription complète =====

    @Test
    @DisplayName("Accepte l'inscription quand les 4 informations sont valides")
    void accepteInscriptionValide() {
        assertDoesNotThrow(
            () -> validateur.inscrire("qb-source", "quentin@gmail.com", "motdepasse1", 20));
    }

    @Test
    @DisplayName("Refuse l'inscription avec un nom vide")
    void refuseInscriptionNomVide() {
        IllegalArgumentException erreur = assertThrows(
            IllegalArgumentException.class,
            () -> validateur.inscrire("", "quentin@gmail.com", "motdepasse1", 20));
        assertEquals("Nom d'utilisateur invalide", erreur.getMessage());
    }

    @Test
    @DisplayName("Refuse l'inscription avec un courriel invalide")
    void refuseInscriptionCourrielInvalide() {
        IllegalArgumentException erreur = assertThrows(
            IllegalArgumentException.class,
            () -> validateur.inscrire("qb-source", "quentin.gmail.com", "motdepasse1", 20));
        assertEquals("Courriel invalide", erreur.getMessage());
    }

    @Test
    @DisplayName("Refuse l'inscription avec un mot de passe trop faible")
    void refuseInscriptionMotDePasseInvalide() {
        IllegalArgumentException erreur = assertThrows(
            IllegalArgumentException.class,
            () -> validateur.inscrire("qb-source", "quentin@gmail.com", "abc1", 20));
        assertEquals("Le mot de passe doit contenir au moins 8 caractères dont un chiffre", erreur.getMessage());
    }

    @Test
    @DisplayName("Refuse l'inscription à 15 ans")
    void refuseInscriptionAgeTropBas() {
        IllegalArgumentException erreur = assertThrows(
            IllegalArgumentException.class,
            () -> validateur.inscrire("qb-source", "quentin@gmail.com", "motdepasse1", 15));
        assertEquals("Il faut avoir au moins 16 ans", erreur.getMessage());
    }

    @Test
    @DisplayName("Avec 4 informations invalides, signale d'abord le nom")
    void signaleLeNomEnPremier() {
        IllegalArgumentException erreur = assertThrows(
            IllegalArgumentException.class,
            () -> validateur.inscrire("", "quentin.gmail.com", "abc1", 15));
        assertEquals("Nom d'utilisateur invalide", erreur.getMessage());
    }
}
