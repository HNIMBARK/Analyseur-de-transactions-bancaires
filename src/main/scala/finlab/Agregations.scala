package finlab

// ============================================================================
// ÉTAPE 5 — Agrégations avancées
// ============================================================================

import scala.collection.mutable
import Main.Transaction

object Agregations {

  /**
   * Calcule, pour chaque compte, la somme des DEBITS.
   *
   * Exemple de résultat :
   *   Map("COMPTE-A" -> 1230.50, "COMPTE-B" -> 980.20, ...)
   */
  def debitsParCompte(txs: List[Transaction]): mutable.Map[String, Double] = {
    // TODO 26 : créer une mutable.Map[String, Double] vide
    val sommes = mutable.Map[String, Double]()

    // TODO 27 : pour chaque transaction de type "DEBIT", ajouter son montant
    for (t <- txs if t._4 == "DEBIT") {
      val compte = t._3
      val montant = t._5
      sommes(compte) = sommes.getOrElse(compte, 0.0) + montant
    }

    sommes
  }


  /**
   * Croise compte × catégorie et retourne, pour chaque couple,
   * la somme des montants dépensés (DEBIT uniquement).
   */
  def debitsParCompteEtCategorie(txs: List[Transaction]): mutable.Map[(String, String), Double] = {
    val resultat = mutable.Map[(String, String), Double]()

    // TODO 28 : compréhension imbriquée
    for (t <- txs if t._4 == "DEBIT") {
      val cle = (t._3, t._6)   // (compte, categorie)
      resultat(cle) = resultat.getOrElse(cle, 0.0) + t._5
    }

    resultat
  }


  /**
   * Retourne le TOP N des catégories les plus dépensées (DEBITS uniquement),
   * triées par montant décroissant.
   */
  def topCategoriesDebits(txs: List[Transaction], n: Int): List[(String, Double)] = {
    // TODO 29 : créer une mutable.Map[String, Double] et la remplir
    val sommes = mutable.Map[String, Double]()
    for (t <- txs if t._4 == "DEBIT") {
      val cat = t._6
      sommes(cat) = sommes.getOrElse(cat, 0.0) + t._5
    }

    // TODO 30 : convertir en List, trier décroissant (-_._2) et prendre n éléments
    sommes.toList.sortBy(-_._2).take(n)
  }


  /**
   * Compte le nombre de transactions par MOIS.
   */
  def comptageParMois(txs: List[Transaction]): mutable.Map[String, Int] = {
    // TODO 31 : compréhension for + Map[String, Int]
    val compteur = mutable.Map[String, Int]()
    for (t <- txs) {
      val mois = t._2.substring(0, 7) // Format "AAAA-MM"
      compteur(mois) = compteur.getOrElse(mois, 0) + 1
    }
    compteur
  }
}
