package finlab

// ============================================================================
// ÉTAPE 2 — Lecture et parsing du CSV
// ============================================================================

import scala.io.Source
import Main.Transaction

object Lecture {

  /**
   * Parse une ligne du CSV en Transaction.
   *
   * Format attendu : id,date,compte,type,montant,categorie,description
   * Exemple        : 1,2026-01-03,COMPTE-A,DEBIT,45.80,COURSES,Carrefour
   */
  def parserLigne(ligne: String): Transaction = {
    // TODO 5 : utiliser ligne.split(",")
    val champs = ligne.split(",")

    // TODO 6 : extraire et CONVERTIR chaque champ
    val id = champs(0).toInt
    val date = champs(1)
    val compte = champs(2)
    val typ = champs(3)
    val montant = champs(4).toDouble
    val categorie = champs(5)
    val description = champs(6)

    // TODO 7 : retourner le Tuple7 correspondant
    (id, date, compte, typ, montant, categorie, description)
  }


  /**
   * Charge un fichier CSV et retourne une List[Transaction].
   */
  def charger(chemin: String): List[Transaction] = {

    // TODO 8 : ouvrir le fichier
    val source = Source.fromFile(chemin)

    // TODO 9 : lire toutes les lignes
    val toutesLignes = source.getLines().toList

    // TODO 10 : fermer la source
    source.close()

    // TODO 11 : SUPPRIMER la première ligne et appliquer parserLigne
    for (ligne <- toutesLignes.drop(1)) yield parserLigne(ligne)
  }
}
