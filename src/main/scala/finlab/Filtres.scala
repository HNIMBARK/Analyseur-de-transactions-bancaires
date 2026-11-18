package finlab

// ============================================================================
// ÉTAPE 4 — Filtres (fonctions valeurs + fonctions d'ordre supérieur)
// ============================================================================

import Main.Transaction

object Filtres {

  /**
   * Filtre les transactions selon un PRÉDICAT donné.
   *
   * Un prédicat est une fonction qui prend une Transaction et retourne Boolean.
   * Ici, on l'écrit comme une FONCTION VALEUR — donc avec son type complet.
   *
   * Cette fonction est dite "d'ordre supérieur" car elle prend une fonction
   * comme argument.
   */
  def filtrer(txs: List[Transaction], predicat: Transaction => Boolean): List[Transaction] = {
    // TODO 22 : utiliser la méthode .filter de List en lui passant le prédicat
    txs.filter(predicat)
  }


  /**
   * Garde uniquement les transactions de la catégorie demandée.
   */
  def parCategorie(txs: List[Transaction], cat: String): List[Transaction] = {
    // TODO 23 : appeler filtrer(txs, t => t._6 == cat)
    filtrer(txs, t => t._6 == cat)
    }


  /**
   * Garde uniquement les transactions du compte demandé.
   */
  def parCompte(txs: List[Transaction], compte: String): List[Transaction] = {
    // TODO 24 : filtrer sur t._3 (le compte)
    filtrer(txs, t => t._3 == compte)
  }


  /**
   * Garde uniquement les transactions dont le montant est >= seuil.
   */
  def montantSuperieurA(txs: List[Transaction], seuil: Double): List[Transaction] = {
    // TODO 25 : filtrer sur t._5 (le montant)
    filtrer(txs, t => t._5 >= seuil)
  }
}
