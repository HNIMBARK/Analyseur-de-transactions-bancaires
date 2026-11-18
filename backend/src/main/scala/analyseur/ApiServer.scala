package analyseur

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import Main.Transaction

object ApiServer {

  def demarrer(txs: List[Transaction], port: Int = 8080): Unit = {
    val server = HttpServer.create(new InetSocketAddress(port), 0)

    // Route Frontend : sert ../frontend/index.html
    server.createContext("/", new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        val path = Paths.get("../frontend/index.html")
        if (Files.exists(path)) {
          val bytes = Files.readAllBytes(path)
          exchange.getResponseHeaders.set("Content-Type", "text/html; charset=UTF-8")
          exchange.sendResponseHeaders(200, bytes.length)
          val os = exchange.getResponseBody
          os.write(bytes)
          os.close()
        } else {
          repondreTexte(exchange, 404, "Fichier frontend/index.html introuvable")
        }
      }
    })

    // API : Transactions avec filtres optionnels
    server.createContext("/api/transactions", new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        val params = parseQuery(exchange.getRequestURI.getQuery)
        var resultat = txs

        params.get("compte").filter(_.nonEmpty).foreach { c =>
          resultat = Filtres.parCompte(resultat, c)
        }
        params.get("categorie").filter(_.nonEmpty).foreach { cat =>
          resultat = Filtres.parCategorie(resultat, cat)
        }

        val json = "[" + resultat.map(txToJson).mkString(",") + "]"
        repondreJson(exchange, 200, json)
      }
    })
    server.createContext("/api/analyse-avancee", new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        val parMois = Agregations.debitsParMois(txs)
          .map { case (mois, m) => s"""{"mois":"$mois","montant":$m}""" }
          .mkString(",")

        val grossesDepenses = Agregations.plusGrossesDepenses(txs, 5)
          .map(t => s"""{"id":${t._1},"date":"${t._2}","montant":${t._5},"categorie":"${t._6}","description":"${t._7}"}""")
          .mkString(",")

        val json =
          s"""{
            |  "debitsParMois": [$parMois],
            |  "plusGrossesDepenses": [$grossesDepenses]
            |}""".stripMargin

        repondreJson(exchange, 200, json)
      }
    })
    // API : Statistiques & Agrégations
    server.createContext("/api/stats", new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        val total = Stats.nombreTotal(txs)
        val credits = Stats.sommeParType(txs, "CREDIT")
        val debits = Stats.sommeParType(txs, "DEBIT")
        val solde = Stats.solde(txs)
        val moyenne = Stats.montantMoyen(txs)

        val topCat = Agregations.topCategoriesDebits(txs, 6)
          .map { case (cat, m) => s"""{"categorie":"$cat","montant":$m}""" }
          .mkString(",")

        val parCompte = Agregations.debitsParCompte(txs)
          .map { case (cpt, m) => s"""{"compte":"$cpt","montant":$m}""" }
          .mkString(",")

        val json =
          s"""{
             |  "nombreTotal": $total,
             |  "totalCredits": $credits,
             |  "totalDebits": $debits,
             |  "soldeNet": $solde,
             |  "montantMoyen": $moyenne,
             |  "topCategories": [$topCat],
             |  "debitsParCompte": [$parCompte]
             |}""".stripMargin

        repondreJson(exchange, 200, json)
      }
    })

    // API : Export du rapport.txt
    server.createContext("/api/rapport-pdf", new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        val cheminPdf = "rapport.pdf"
        Rapport.exporterPdf(txs, cheminPdf)
        val bytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(cheminPdf))
        
        exchange.getResponseHeaders.set("Content-Type", "application/pdf")
        exchange.getResponseHeaders.set("Content-Disposition", "attachment; filename=rapport.pdf")
        exchange.getResponseHeaders.set("Access-Control-Allow-Origin", "*")
        exchange.sendResponseHeaders(200, bytes.length)
        val os = exchange.getResponseBody
        os.write(bytes)
        os.close()
      }
    })
    server.setExecutor(null)
    server.start()
    println(s"Serveur Analyseur Bancaire démarré sur http://localhost:$port")
  }

  private def txToJson(t: Transaction): String = {
    val descClean = t._7.replace("\"", "\\\"")
    s"""{"id":${t._1},"date":"${t._2}","compte":"${t._3}","type":"${t._4}","montant":${t._5},"categorie":"${t._6}","description":"$descClean"}"""
  }

  private def parseQuery(query: String): Map[String, String] = {
    if (query == null || query.isEmpty) Map.empty
    else query.split("&").flatMap { pair =>
      val idx = pair.indexOf("=")
      if (idx > 0) Some(pair.substring(0, idx) -> java.net.URLDecoder.decode(pair.substring(idx + 1), "UTF-8"))
      else None
    }.toMap
  }

  private def repondreJson(exchange: HttpExchange, code: Int, json: String): Unit = {
    exchange.getResponseHeaders.set("Content-Type", "application/json; charset=UTF-8")
    exchange.getResponseHeaders.set("Access-Control-Allow-Origin", "*")
    val bytes = json.getBytes(StandardCharsets.UTF_8)
    exchange.sendResponseHeaders(code, bytes.length)
    val os = exchange.getResponseBody
    os.write(bytes)
    os.close()
  }

  private def repondreTexte(exchange: HttpExchange, code: Int, texte: String): Unit = {
    exchange.getResponseHeaders.set("Content-Type", "text/plain; charset=UTF-8")
    exchange.getResponseHeaders.set("Access-Control-Allow-Origin", "*")
    val bytes = texte.getBytes(StandardCharsets.UTF_8)
    exchange.sendResponseHeaders(code, bytes.length)
    val os = exchange.getResponseBody
    os.write(bytes)
    os.close()
  }
}