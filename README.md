# Analyseur de transactions bancaires 🏦📊

> Un système modulaire haute performance développé en **Scala 2.13** pour l'ingestion, le nettoyage, l'analyse statistique et l'exportation de rapports de transactions bancaires, couplé à un tableau de bord web interactif en temps réel.

---

## 🏗️ Architecture du Projet

Le projet suit une organisation modulaire stricte pour séparer clairement les responsabilités (Backend, ETL, Frontend, Données et Scripts) :

```text
Analyseur-de-transactions-bancaires/
├── .gitignore
├── LICENSE
├── README.md
├── backend/                  # Logique métier Scala, agrégations et API REST
│   ├── build.sbt
│   ├── project/
│   └── src/main/scala/analyseur/
│       ├── Agregations.scala # Analyses avancées, top catégories, tendances mensuelles
│       ├── ApiServer.scala   # Serveur HTTP natif (com.sun.net.httpserver) & routes JSON/PDF
│       ├── Filtres.scala     # Filtrage par compte, catégorie, seuils de montant
│       ├── Lecture.scala     # Ingestion des fichiers CSV (simple ou multi-fichiers)
│       ├── Main.scala        # Point d'entrée principal et maintien du serveur
│       ├── Menu.scala        # Interface console interactive (CLI)
│       └── Rapport.scala     # Génération de rapports textuels et PDF
├── etl/                      # Pipeline de traitement et nettoyage des données
│   ├── build.sbt
│   ├── project/
│   └── src/main/scala/
├── frontend/                 # Interface web interactive (Dashboard)
│   ├── build.sbt
│   └── index.html            # Dashboard Tailwind CSS & Chart.js avec boutons d'import/export
├── data/                     # Stockage des données
│   ├── raw/                  # Fichiers CSV bruts des transactions
│   ├── processed/            # Données nettoyées et transformées
│   └── sqlite/               # Base de données locale SQLite (optionnelle)
└── scripts/                  # Outils d'automatisation
    └── run_all.sh            # Script global de lancement
```

---

## 🚀 Fonctionnalités Clés

1. **Ingestion Multi-sources (`Lecture.scala`)** :
   - Chargement de fichiers CSV unitaires ou fusion automatique de tous les fichiers présents dans le dossier `data/raw/`.
   - Fonctionnalité d'import dynamique de fichiers via l'interface web.
2. **Moteur d'Agrégation & Statistiques (`Agregations.scala`, `Stats.scala`)** :
   - Calcul des soldes nets, totaux crédits/débits et montants moyens.
   - Top N des catégories de dépenses et analyse croisée par compte.
   - Évolution temporelle mensuelle et détection des plus grosses dépenses (*outliers*).
3. **API REST & Serveur Web Intégré (`ApiServer.scala`)** :
   - Serveur HTTP léger en Scala pur sur le port `8080`.
   - Endpoints JSON pour le dashboard (`/api/stats`, `/api/transactions`, `/api/analyse-avancee`).
   - Routes dédiées pour le téléchargement instantané du rapport financier au format **PDF** (`/api/rapport-pdf`) et l'importation de fichiers (`/api/import`).
4. **Tableau de Bord Interactif (`frontend/index.html`)** :
   - Design moderne et mode sombre (*Dark Mode*) propulsé par **Tailwind CSS**.
   - Visualisations graphiques dynamiques via **Chart.js** (Graphique en barres et diagramme circulaire).
   - Boutons d'import de fichiers CSV et d'export direct en TXT et PDF.
5. **Double Interface (Console & Web)** :
   - Possibilité d'utiliser le menu interactif textuel en console (`Menu.scala`) ou l'interface graphique web.

---

## 🛠️ Stack Technique

- **Langage** : Scala 2.13
- **Outil de Build** : SBT (Simple Build Tool)
- **Serveur HTTP & API** : `com.sun.net.httpserver` (natif Java/Scala)
- **Génération PDF** : OpenPDF (`com.github.librepdf:openpdf`)
- **Frontend** : HTML5, Tailwind CSS (CDN), Chart.js (CDN)

---

## ⚙️ Guide d'Installation & Utilisation

### 1. Ajouter vos données sources
Déposez vos fichiers de transactions au format CSV dans le dossier :
```text
data/raw/transactions.csv
```
*(Format CSV attendu : `id,date,compte,type,montant,categorie,description`)*

### 2. Lancer le Backend et le Serveur API
Rendez-vous dans le module backend et lancez l'application avec SBT :
```bash
cd backend
sbt run
```
*Le serveur démarrera et affichera :*
```text
=== Backend & Dashboard ===
183 transactions chargées.
Serveur API démarré sur http://localhost:8080
```

### 3. Accéder au Tableau de Bord Web
Ouvrez votre navigateur web et accédez à l'adresse :
👉 **`http://localhost:8080`**

Vous y trouverez :
- Les indicateurs clés de performance (KPIs).
- Les graphiques interactifs.
- Le bouton **📁 Importer CSV** pour ajouter de nouvelles données à la volée.
- Les boutons d'exportation directe des rapports en **TXT** et **PDF**.

---

## 👥 Auteur & Licence
Projet développé sous licence **MIT**. Libre d'utilisation et d'extension.