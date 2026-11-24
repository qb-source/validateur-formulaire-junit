# Validateur de formulaire d'inscription : tests JUnit

Projet d'apprentissage en automatisation des tests : un validateur de formulaire d'inscription, testé avec JUnit.

## Les règles testées

- Nom d'utilisateur : ni vide, ni plus de 15 caractères.
- Courriel : doit contenir un @ et un point.
- Mot de passe : au moins 8 caractères, dont au moins un chiffre.
- Âge : au moins 16 ans.
- L'inscription est refusée dès la première règle enfreinte, avec un message précis.

## Organisation des tests

Les tests sont classés par sujet : nom d'utilisateur, courriel, mot de passe, âge, puis inscription complète.

| Famille | Exemples |
| --- | --- |
| Cas passant | Inscription acceptée avec 4 informations valides |
| Cas d'erreur | Une seule information invalide à la fois, avec vérification du message |
| Cas limites | 16 ans accepté, 15 ans refusé |
| Ordre des règles | Avec 4 informations invalides, c'est le nom qui est signalé en premier |
| Bug connu | Le courriel `quentin@.gmail.com` est accepté alors qu'il ne devrait pas (test désactivé avec @Disabled) |

## Outils

- Java 25
- JUnit 6 (`@Test`, `@BeforeEach`, `@DisplayName`, `@ParameterizedTest`, `@ValueSource`, `@Disabled`, `assertThrows`, `assertDoesNotThrow`)
- Maven

## Lancer les tests

    mvn clean test

## Ce que j'ai appris

- **Examiner un texte en Java** avec `.length()`, `.isEmpty()` et `.contains()`, et combiner des conditions avec `&&` (et) et `||` (ou).
- **Tester une seule règle à la fois** : pour vérifier une erreur, toutes les autres informations doivent rester valides, sinon une autre règle peut refuser à sa place.
- **Tester les frontières** : la règle « au moins 16 ans » se vérifie avec 16 (accepté) et 15 (refusé), car c'est là qu'un `>` à la place d'un `>=` créerait un bug.
- **L'ordre des règles compte** : quand plusieurs informations sont invalides, seule la première règle vérifiée est signalée.
- **Le cas passant** : vérifier qu'une inscription correcte est bien acceptée, avec `assertDoesNotThrow`.
- **Distinguer ce que fait le code et ce qu'il devrait faire** : en relisant la classe, j'ai trouvé que le courriel `quentin@.gmail.com` est accepté. Je l'ai documenté comme bug connu avec un test désactivé (`@Disabled`).
- **Organiser un fichier de tests** par sujet, avec des noms clairs (« Accepte… » / « Refuse… »), pour qu'il se lise comme une liste de règles.
