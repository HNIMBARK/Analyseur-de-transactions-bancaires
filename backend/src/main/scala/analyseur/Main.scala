package analyseur
object Main {
  type Transaction = (Int, String, String, String, Double, String, String)

  def main(args: Array[String]): Unit = {
    println("=== Backend API & Serveur Web ===")

    val chemin = "../data/raw/transactions.csv"
    val transactions = Lecture.charger(chemin)
    println(s"${transactions.size} transactions chargées en mémoire.")

    // Démarrage du serveur HTTP sur le port 8080
    ApiServer.demarrer(transactions, port = 8080)

    // Bloque le thread principal pour empêcher SBT de fermer le serveur
    println("Serveur en écoute permanente. Appuyez sur Ctrl+C pour quitter.")
    try {
      Thread.currentThread().join()
    } catch {
      case _: InterruptedException => println("Arrêt du serveur...")
    }
  }
}