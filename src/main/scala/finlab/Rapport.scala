package finlab

// ============================================================================
// ÉTAPE 7 — Export du rapport (écriture de fichier)
// ============================================================================

import java.io.PrintWriter
import Main.Transaction

object Rapport {

  /**
   * Génère un rapport synthétique et l'écrit dans le fichier `chemin`.
   * Le rapport doit contenir :
   *   - le nombre total de transactions
   *   - le solde net
   *   - le montant moyen
   *   - le top 3 des catégories de dépenses
   *   - les dépenses par compte
   */
  def exporter(txs: List[Transaction], chemin: String): Unit = {

    // TODO 34 : ouvrir un PrintWriter sur le chemin
    val writer = new PrintWriter(chemin)

    // TODO 35 : écrire un en-tête
    writer.println("=== Rapport FinLab ===")
    writer.println()

    // TODO 36 : écrire le nombre total, le solde, le montant moyen
    writer.println(s"Nombre total de transactions : ${Stats.nombreTotal(txs)}")
    writer.println(s"Solde net : ${Stats.solde(txs)}")
    writer.println(s"Montant moyen : ${Stats.montantMoyen(txs)}")
    writer.println()

    // TODO 37 : écrire le top 3 des catégories
    writer.println("Top 3 des catégories de dépenses :")
    for ((categorie, montant) <- Agregations.topCategoriesDebits(txs, 3)) {
      writer.println(s"- $categorie : $montant")
    }
    writer.println()

    // TODO 38 : écrire les dépenses par compte
    writer.println("Dépenses par compte :")
    for ((compte, montant) <- Agregations.debitsParCompte(txs)) {
      writer.println(s"- Compte $compte : $montant")
    }

    // TODO 39 : NE PAS OUBLIER de fermer le writer
    writer.close()
  }
  
}
