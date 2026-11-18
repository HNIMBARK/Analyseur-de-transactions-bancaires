# FinLab — Squelette du projet

## Structure

```
finlab/
├── build.sbt
├── data/
│   └── transactions.csv          (183 transactions à analyser)
└── src/main/scala/finlab/
    ├── Main.scala                (étape 1 — orchestration)
    ├── Lecture.scala             (étape 2 — parsing CSV)
    ├── Stats.scala               (étape 3 — stats de base)
    ├── Filtres.scala             (étape 4 — fonctions d'ordre supérieur)
    ├── Agregations.scala         (étape 5 — agrégations avancées)
    ├── Menu.scala                (étape 6 — menu interactif)
    └── Rapport.scala             (étape 7 — export fichier)
```

## Lancer le projet

```bash
sbt run
```

ou dans IntelliJ : clic droit sur `Main.scala` → Run.

## Total : 39 TODO à compléter

Suivez l'ordre des TODO — ils sont conçus pour que le projet compile
progressivement et que vous puissiez tester à chaque étape.

## Conseils

1. Après chaque étape, lancez `sbt compile` pour vérifier que tout passe.
2. Pour tester rapidement une fonction, ouvrez `sbt console` et faites :
   ```scala
   import finlab._
   val txs = Lecture.charger("data/transactions.csv")
   Stats.nombreTotal(txs)
   ```
3. Si vous bloquez sur une étape, passez à la suivante en remplaçant
   le `???` par une valeur factice — ça vous débloquera le reste.
# Finlab_Squelettes
# Analyseur-de-transactions-bancaires
# Analyseur-de-transactions-bancaires
# Analyseur-de-transactions-bancaires
# Analyseur-de-transactions-bancaires
