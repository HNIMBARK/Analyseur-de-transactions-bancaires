package finlab

// ============================================================================
// ÉTAPE 3 — Statistiques de base
// ============================================================================

import scala.collection.mutable
import Main.Transaction

object Stats {

  /**
   * Retourne le nombre total de transactions.
   */
  def nombreTotal(txs: List[Transaction]): Int = {
    // TODO 12 : retourner la taille de la liste (.size ou .length)
    txs.length
  }


  /**
   * Calcule la somme des montants pour un type donné ("DEBIT" ou "CREDIT").
   *
   * Utilisez une boucle `for` avec un filtre, et une variable `var` pour
   * accumuler la somme. Vous pouvez aussi utiliser .filter et .map plus tard.
   */
  def sommeParType(txs: List[Transaction], typ: String): Double = {
    // TODO 13 : initialiser une var `total: Double = 0.0`
    var total: Double = 0.0

    // TODO 14 : pour chaque transaction, si son type (4e élément, indice _4)
    //           est égal à `typ`, ajouter son montant (5e élément, _5) à total
    for (t <- txs) {
      if (t._4 == typ) {
        total += t._5
      }
    }

    // TODO 15 : retourner total
    total
  }


  /**
   * Calcule le solde net : somme des CREDIT - somme des DEBIT.
   */
  def solde(txs: List[Transaction]): Double = {
    // TODO 16 : utiliser sommeParType ci-dessus, puis renvoyer la différence
    sommeParType(txs, "CREDIT") - sommeParType(txs, "DEBIT")
  }


  /**
   * Retourne le nombre de transactions par catégorie sous forme de Map.
   *
   * Exemple de résultat :
   *   Map("COURSES" -> 12, "SALAIRE" -> 9, "TRANSPORT" -> 8, ...)
   *
   * Vous devez utiliser une mutable.Map[String, Int].
   */
  def comptageParCategorie(txs: List[Transaction]): mutable.Map[String, Int] = {
    // TODO 17 : créer une mutable.Map[String, Int] vide (val compteur = mutable.Map[String, Int]())
    val compteur = mutable.Map[String, Int]()

    // TODO 18 : pour chaque transaction, récupérer sa catégorie (t._6)
    for (t <- txs) {
      val cat = t._6
      compteur(cat) = compteur.getOrElse(cat, 0) + 1
    }

    // TODO 19 : retourner la Map
    compteur
  }


  /**
   * Calcule le montant moyen des transactions.
   *
   * Attention au cas où la liste est vide ! (division par zéro)
   */
  def montantMoyen(txs: List[Transaction]): Double = {
    // TODO 20 : si la liste est vide, retourner 0.0
    if (txs.isEmpty) {
      0.0
    } else {
      // TODO 21 : sinon, calculer la somme des montants puis diviser par txs.size
      var sommeTotale: Double = 0.0
      for (t <- txs) {
        sommeTotale += t._5
      }
      sommeTotale / txs.length
    }
  }
}
