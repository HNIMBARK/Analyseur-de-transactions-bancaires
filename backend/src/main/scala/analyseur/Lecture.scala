package analyseur

import scala.io.Source
import java.io.File
import Main.Transaction

object Lecture {

  def parserLigne(ligne: String): Transaction = {
    val champs = ligne.split(",")
    (champs(0).toInt, champs(1), champs(2), champs(3), champs(4).toDouble, champs(5), champs(6))
  }

  def charger(chemin: String): List[Transaction] = {
    val source = Source.fromFile(chemin)
    val toutesLignes = source.getLines().toList
    source.close()
    for (ligne <- toutesLignes.drop(1) if ligne.trim.nonEmpty) yield parserLigne(ligne)
  }

  /**
   * Charge et fusionne tous les fichiers CSV présents dans un dossier (ex: ../data/raw/)
   */
  def chargerDossier(dossierChemin: String): List[Transaction] = {
    val dir = new File(dossierChemin)
    if (dir.exists() && dir.isDirectory) {
      val fichiersCsv = dir.listFiles((_, name) => name.toLowerCase.endsWith(".csv"))
      if (fichiersCsv != null) {
        fichiersCsv.flatMap(f => charger(f.getAbsolutePath)).toList
      } else {
        List.empty
      }
    } else {
      List.empty
    }
  }
}