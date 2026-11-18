package analyseur

import java.io.PrintWriter
import com.lowagie.text.{Document, Paragraph, Font}
import com.lowagie.text.pdf.PdfWriter
import java.io.FileOutputStream
import Main.Transaction

object Rapport {

  /**
   * Exporte un rapport texte synthétique.
   */
  def exporter(txs: List[Transaction], chemin: String): Unit = {
    val writer = new PrintWriter(chemin)
    writer.println("=== Rapport FinLab ===")
    writer.println()
    writer.println(s"Nombre total de transactions: ${Stats.nombreTotal(txs)}")
    writer.println(s"Solde net: ${Stats.solde(txs)} €")
    writer.println(s"Montant moyen: ${Stats.montantMoyen(txs)} €")
    writer.close()
  }

  /**
   * Exporte un rapport au format PDF.
   */
  def exporterPdf(txs: List[Transaction], chemin: String): Unit = {
    val document = new Document()
    PdfWriter.getInstance(document, new FileOutputStream(chemin))
    document.open()

    val titreFont = new Font(Font.HELVETICA, 18, Font.BOLD)
    val texteFont = new Font(Font.HELVETICA, 12, Font.NORMAL)

    document.add(new Paragraph("Rapport Financier - FinLab Pulse", titreFont))
    document.add(new Paragraph("------------------------------------------------------------------------", texteFont))
    document.add(new Paragraph(s"Nombre total de transactions : ${Stats.nombreTotal(txs)}", texteFont))
    document.add(new Paragraph(s"Solde net : ${Stats.solde(txs)} EUR", texteFont))
    document.add(new Paragraph(s"Montant moyen : ${Stats.montantMoyen(txs)} EUR", texteFont))
    document.add(new Paragraph(" ", texteFont))
    
    document.add(new Paragraph("Top Catégories de Dépenses :", titreFont))
    for ((cat, montant) <- Agregations.topCategoriesDebits(txs, 3)) {
      document.add(new Paragraph(s"- $cat : $montant EUR", texteFont))
    }

    document.close()
  }
}