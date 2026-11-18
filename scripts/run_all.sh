#!/bin/bash
echo "=== Démarrage de l'Analyseur Bancaire ==="

# 1. Lancement du Backend Scala en arrière-plan
echo "Lancement du backend..."
cd backend
sbt run &
BACKEND_PID=$!
cd ..

# Attente du démarrage du serveur
sleep 3

# 2. Ouverture de l'interface web (ou instructions)
echo "Serveur prêt ! Ouvrez votre navigateur sur http://localhost:8080"

# Maintenir le script actif jusqu'à l'arrêt par l'utilisateur
trap "kill $BACKEND_PID; exit" INT
wait $BACKEND_PID