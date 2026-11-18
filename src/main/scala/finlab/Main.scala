package finlab

// ============================================================================
// FinLab — Analyseur de transactions bancaires
// ============================================================================
//
// Ce projet est divisé en 6 étapes thématiques (étape 1 = ce fichier).
// Chaque étape contient des TODO numérotés que vous devez compléter
// DANS L'ORDRE.
//
// Pour lancer :  sbt run
// ============================================================================

import scala.io.StdIn.readLine

object Main {

  // Nous représentons une transaction comme un Tuple7 :
  //   (id, date, compte, type, montant, categorie, description)
  //   (Int, String, String, String, Double, String, String)
  //
  // Plus tard, en POO, on apprendra à créer une vraie classe Transaction.
  // Pour l'instant, on reste sur les outils de la partie 1 du cours.
  type Transaction = (Int, String, String, String, Double, String, String)


  def main(args: Array[String]): Unit = {

    println("=== FinLab — Analyseur de transactions ===")
    // TODO 1 : déclarer une val `chemin` qui contient le chemin du fichier CSV
    val chemin = "data/transactions.csv"

    // TODO 2 : appeler Lecture.charger(chemin) et stocker le résultat
    // (Note : on suppose que Lecture.charger retourne bien une List[Transaction])
    val transactions: List[Transaction] = Lecture.charger(chemin)

    // TODO 3 : afficher "X transactions chargées." où X est le nombre de transactions
    println(s"${transactions.length} transactions chargées.")

    // TODO 4 : appeler la fonction Menu.boucle(transactions) qui lance le menu interactif
    Menu.boucle(transactions)


    println("À bientôt !")
  }
}
