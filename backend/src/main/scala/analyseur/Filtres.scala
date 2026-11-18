package analyseur

import Main.Transaction

object Filtres {
  def filtrer(txs: List[Transaction], predicat: Transaction => Boolean): List[Transaction] = txs.filter(predicat)
  def parCategorie(txs: List[Transaction], cat: String): List[Transaction] = filtrer(txs, t => t._6 == cat)
  def parCompte(txs: List[Transaction], compte: String): List[Transaction] = filtrer(txs, t => t._3 == compte)
  def montantSuperieurA(txs: List[Transaction], seuil: Double): List[Transaction] = filtrer(txs, t => t._5 >= seuil)
}