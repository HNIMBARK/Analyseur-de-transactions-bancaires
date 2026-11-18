package analyseur

import scala.io.StdIn.readLine
import Main.Transaction

object Menu {

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

  def boucle(txs: List[Transaction]): Unit = {

    var continuer: Boolean = true


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
        print("Compte ? ")
        val compte = readLine()
        val resultat = Filtres.parCompte(txs, compte)
        println(s"${resultat.size} transactions trouvées :")
        for (t <- resultat.take(10)) println(s"  $t")
      } else if (choix == "4") {
        val top = Agregations.topCategoriesDebits(txs, 3)
        println("Top 3 des catégories de dépenses :")
        for ((cat, montant) <- top) {
          println(s"  $cat : $montant €")
        }
      } else if (choix == "5") {
        val depenses = Agregations.depensesParCompteEtCategorie(txs)
        println("Dépenses par compte × catégorie :")
        for (((compte, cat), montant) <- depenses) {
          println(s"  [$compte] $cat : $montant €")
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