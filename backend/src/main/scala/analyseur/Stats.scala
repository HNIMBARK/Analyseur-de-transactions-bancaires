package analyseur


import scala.collection.mutable
import Main.Transaction

object Stats {
  def nombreTotal(txs: List[Transaction]): Int = txs.size

  def sommeParType(txs: List[Transaction], typ: String): Double = {
    var total: Double = 0.0
    for (t <- txs if t._4 == typ) total += t._5
    total
  }

  def solde(txs: List[Transaction]): Double = sommeParType(txs, "CREDIT") - sommeParType(txs, "DEBIT")

  def montantMoyen(txs: List[Transaction]): Double = {
    if (txs.isEmpty) 0.0 else txs.map(_._5).sum / txs.size
  }
}