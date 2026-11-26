#!/usr/bin/env bash

set -euo pipefail

RACINE="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

TEMP="$(mktemp -d)"

trap 'rm -rf "$TEMP"' EXIT

CLASSES="$TEMP/classes"
SCENARIOS="$RACINE/verification/scenarios"
REFERENCES="$RACINE/verification/references"
RESULTATS="$TEMP/resultats"

mkdir -p "$CLASSES" "$RESULTATS"

echo "Compilation de TaskFlow..."

javac -encoding UTF-8 -d "$CLASSES" \
    "$RACINE/src/main/java/taskflow/Main.java" \
    "$RACINE/src/main/java/taskflow/ModeleTache.java" \
    "$RACINE/src/main/java/taskflow/GestionnaireTaches.java" \
    "$RACINE/src/main/java/taskflow/SauvegardeTaches.java"

ECHECS=0
REUSSITES=0

rejouer() {
    local scenario="$1"
    local dossier="$2"

    mkdir -p "$dossier"

    echo "Verification : $scenario"

    if ! (
        cd "$dossier"
        java -cp "$CLASSES" taskflow.Main \
            < "$SCENARIOS/$scenario.txt" \
            > "$RESULTATS/$scenario.txt"
    ); then
        echo "ECHEC : erreur d'execution"
        ECHECS=$((ECHECS + 1))
        return
    fi

    if diff --strip-trailing-cr -u \
        "$REFERENCES/$scenario.txt" \
        "$RESULTATS/$scenario.txt"; then

        echo "OK : comportement identique"
        REUSSITES=$((REUSSITES + 1))
    else
        echo "ECHEC : comportement different"
        ECHECS=$((ECHECS + 1))
    fi

    echo
}

rejouer "01-ajout" "$TEMP/ajout"
rejouer "02-liste" "$TEMP/liste"
rejouer "03-priorite" "$TEMP/priorite"
rejouer "04-echeance" "$TEMP/echeance"
rejouer "05-statut" "$TEMP/statut"
rejouer "06-sauvegarde" "$TEMP/persistance"
rejouer "06-rechargement" "$TEMP/persistance"
rejouer "07-erreur" "$TEMP/erreur"

echo "=============================="
echo "BILAN DES VERIFICATIONS"
echo "Scenarios reussis : $REUSSITES"
echo "Scenarios echoues : $ECHECS"
echo "=============================="

if [ "$ECHECS" -ne 0 ]; then
    exit 1
fi

exit 0