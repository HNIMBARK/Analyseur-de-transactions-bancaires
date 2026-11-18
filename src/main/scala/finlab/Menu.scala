package finlab

// ============================================================================
// ÉTAPE 6 — Menu interactif
// ============================================================================

import scala.io.StdIn.readLine
import Main.Transaction

object Menu {

  /**
   * Affiche le menu et lit le choix de l'utilisateur.
   * Retourne l'option choisie sous forme de String.
   */
  def afficherMenu(): String = {
    println()
    println("=========================================")
    println("  Que voulez-vous faire ?")
    println("    1. Statistiques globales")
    println("    2. Filtrer par catégorie")
    println("    3. Filtrer par compte")
    println("    4. Top 3 des catégories de dépenses")
    println("    5. Dépenses par compte × catégorie")
    println("    6. Exporter le rapport")
    println("    0. Quitter")
    println("=========================================")
    print("  Votre choix : ")
    readLine()
  }


  /**
   * Boucle principale du menu. Tant que l'utilisateur n'entre pas "0",
   * on lui propose des actions.
   */
  def boucle(txs: List[Transaction]): Unit = {

    // TODO 32 : créer une var `continuer: Boolean = true`
    var continuer: Boolean = true

    // TODO 33 : tant que continuer est vrai
    while (continuer) {
      val choix = afficherMenu()
      if (choix == "1") {
        println(s"Nombre de transactions : ${Stats.nombreTotal(txs)}")
        println(s"Solde net : ${Stats.solde(txs)} €")
        println(s"Montant moyen : ${Stats.montantMoyen(txs)} €")
      } else if (choix == "2") {
          print("Catégorie ? ")
          val cat = readLine()
          val resultat = Filtres.parCategorie(txs, cat)
          println(s"${resultat.size} transactions trouvées :")
          for (t <- resultat.take(10)) println(s"  $t")
      } else if (choix == "3") {
          print("Nom du compte ? ")
          val compte = readLine()
          val resultat = Filtres.parCompte(txs, compte)
          println(s"${resultat.size} transactions trouvées :")
          for (t <- resultat.take(10)) println(s"  $t")
      } else if (choix == "4") {
          println("Top 3 des catégories de dépenses :")
          for ((cat, montant) <- Agregations.topCategoriesDebits(txs, 3)) {
            println(s"  $cat : $montant €")
          }
      } else if (choix == "5") {
          println("Dépenses par compte et catégorie :")
          // Ici, on itère sur les entrées de la map (clé (String, String) -> valeur Double)
          for (((compte, categorie), montant) <- Agregations.debitsParCompteEtCategorie(txs)) {
            println(s"  Compte $compte, Catégorie $categorie : $montant €")
          }
      } else if (choix == "6") {
          Rapport.exporter(txs, "rapport.txt")
          println("Rapport exporté dans rapport.txt")
      } else if (choix == "0") {
          continuer = false
      } else {
          println("Choix invalide.")
      }
    }
  }
}
