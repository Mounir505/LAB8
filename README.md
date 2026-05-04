 README - Laboratoire 8 - Multithreading Android

RAPPORT DE LABORATOIRE 8 : PROGRAMMATION ASYNCHRONE
===================================================

* * *

**Cours : Développement d'Applications Mobiles**

**Date : Mai 2026**

  

1\. Introduction Générale
-------------------------

Ce document constitue le rapport technique pour le Laboratoire 8 portant sur la gestion des threads et de la réactivité de l'interface utilisateur dans le système d'exploitation Android. Le but de ce laboratoire est de comprendre comment manipuler les processus de fond.

Dans l'écosystème Android, la gestion du multithreading est cruciale car toutes les opérations de dessin et d'interaction utilisateur se font sur un seul thread appelé _Main Thread_ ou _UI Thread_.

  

2\. Objectifs du Projet
-----------------------

*   Éviter le blocage de l'interface utilisateur lors de tâches longues.
*   Maîtriser l'utilisation de la classe `Thread` classique.
*   Implémenter un `ExecutorService` pour la gestion moderne des tâches.
*   Utiliser les `Handlers` pour la communication inter-thread.
*   Garantir l'accessibilité de l'interface via les ressources externes.

  

3\. Analyse des Composants Utilisés
-----------------------------------

### 3.1. Le Main Thread (Thread Principal)

C'est le thread qui gère l'affichage. Si une tâche dure plus de 5 secondes sur ce thread, Android affiche une boîte de dialogue "ANR" (Application Not Responding). Toutes nos mises à jour de vues (TextView, ProgressBar) doivent y être exécutées.

### 3.2. Worker Threads (Threads de Travail)

Ce sont les threads que nous créons pour effectuer les travaux lourds. Ils s'exécutent en parallèle du thread principal.

### 3.3. Handler et Looper

Comme un Worker Thread ne peut pas toucher directement à l'UI, le `Handler` sert de pont. Il prend un message ou un `Runnable` et le place dans la file d'attente du thread principal pour exécution.

  

4\. Scénarios de Test et Implémentation
---------------------------------------

Le projet implémente trois actions majeures pour tester le multithreading :

Action

Technologie

Comportement

Chargement d'Image

Thread Class

Simule une attente de 2000ms avant d'afficher une ressource graphique.

Calcul Intensif

ExecutorService

Boucle de 50 millions d'opérations avec mise à jour d'une ProgressBar.

Test de Réactivité

UI Thread

Affiche un Toast instantané pour prouver que l'app n'est pas gelée.

  

5\. Analyse de la Structure du Code
-----------------------------------

Le code source suit les recommandations de Google pour 2026 :

**A. Gestion des Ressources :**

Aucun texte n'est écrit directement dans le code Java (Hardcoded). Nous utilisons `getString(R.string.nom_resource)` pour permettre la traduction et faciliter la maintenance.

**B. Cycle de Vie :**

L'utilisation de `executorService.shutdownNow()` dans la méthode `onDestroy()` permet d'arrêter proprement les calculs si l'utilisateur quitte l'application, évitant ainsi les fuites de mémoire (Memory Leaks).

**C. Performance :**

L'utilisation de `Executors.newFixedThreadPool(2)` limite le nombre de threads simultanés, ce qui économise la batterie et le processeur du smartphone.

  

6\. Extrait de Logique de Synchronisation
-----------------------------------------

Voici comment nous garantissons que l'UI est mise à jour depuis un thread de fond :

    // Dans le Worker Thread
    mainHandler.post(new Runnable() {
        @Override
        public void run() {
            // Ici nous sommes de retour sur l'UI Thread
            txtStatus.setText(R.string.status\_complete);
            progressBar.setVisibility(View.INVISIBLE);
        }
    });
    

  

7\. Conclusion du Laboratoire
-----------------------------

Ce laboratoire a permis de mettre en évidence la complexité de la programmation asynchrone. La transition de _AsyncTask_ (méthode historique) vers _Executors_ offre une flexibilité bien plus grande pour les développeurs.

En conclusion, la réactivité d'une application est le premier critère de qualité pour l'utilisateur final. Ce projet valide les compétences nécessaires pour développer des applications robustes et fluides.

  

* * *

**Travail réalisé par :** Mounir MERGHICH  
**Module :** Développement d'applications Android  
**Version du Document :** 1.0 (Définitif)
