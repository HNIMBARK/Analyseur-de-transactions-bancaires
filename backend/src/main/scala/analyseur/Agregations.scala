package analyseur

import scala.collection.mutable
import Main.Transaction

object Agregations {

  def debitsParCompte(txs: List[Transaction]): mutable.Map[String, Double] = {
    val sommes = mutable.Map[String, Double]()
    for (t <- txs if t._4 == "DEBIT") {
      sommes(t._3) = sommes.getOrElse(t._3, 0.0) + t._5
    }
    sommes
  }

  def depensesParCompteEtCategorie(txs: List[Transaction]): mutable.Map[(String, String), Double] = {
    val resultat = mutable.Map[(String, String), Double]()
    for (t <- txs if t._4 == "DEBIT") {
      val cle = (t._3, t._6)
      resultat(cle) = resultat.getOrElse(cle, 0.0) + t._5
    }
    resultat
  }

  def topCategoriesDebits(txs: List[Transaction], n: Int): List[(String, Double)] = {
    val sommes = mutable.Map[String, Double]()
    for (t <- txs if t._4 == "DEBIT") {
      sommes(t._6) = sommes.getOrElse(t._6, 0.0) + t._5
    }
    sommes.toList.sortBy(-_._2).take(n)
  }

  /**
   * Analyse avancée : Évolution des dépenses par mois (DEBIT uniquement).
   */
  def debitsParMois(txs: List[Transaction]): List[(String, Double)] = {
    val sommes = mutable.Map[String, Double]()
    for (t <- txs if t._4 == "DEBIT") {
      val mois = t._2.substring(0, 7) // Format "YYYY-MM"
      sommes(mois) = sommes.getOrElse(mois, 0.0) + t._5
    }
    sommes.toList.sortBy(_._1)
  }

  /**
   * Analyse avancée : Les 5 transactions les plus élevées (Outliers / Grosses dépenses).
   */
  def plusGrossesDepenses(txs: List[Transaction], n: Int = 5): List[Transaction] = {
    txs.filter(_._4 == "DEBIT").sortBy(-_._5).take(n)
  }
}