# Rapport S3.02

Ce rapport a pour but de décrire l'implémentation de l'algorithme **k-NN** au sein de la SAE 3.02.


# Algorithme

* **k-NN**: implémenté dans la classe **MethodeKnn** et dépendant de l'interface **Distance**, au sein du package **fr.univlille.utils.knn** 
    * Calculs de distance dans les classes implémentant l'interface **Distance**
    * Normalisation des valeurs par les classes implémentant **Distance**, via l'attribut **amplitudeMap** de **MethodeKnn**
    * Obtention de la liste des **k** plus proches voisins en tant que valeur de retour de la méthode **knn()**, issue de la classe **MethodeKnn**, après instanciation de celle-ci
* **Classification**: implémentée dans la classe **Classeur**, au sein du package **fr.univlille.knn.model**
    * **Classification** d'une donnée avec la méthode **classifierDonnee()**, à l'aide des résultats de **l'exécution du k-NN**
    * Calcul de la **robustesse** à l'aide de la méthode **robustness()**

* **Validation Croisée**: se calcule tout d'abord en mélangeant le jeu de données, puis on sépare le jeu en 4 paquets de même taille. Ensuite, on sélectionne un paquet comme paquet test. On classe chaque donnée de ce paquet avec les données des 3 autres paquets et on compte le nombre de bonnes catégories. On répète cette action pour chaque paquet. À la fin, on divise le nombre de bons choix par le nombre de données. Ce qui nous donne un double compris entre 0 et 1.

* **Choix du K optimal**: On effectue la validation croisée pour tous les K compris entre 1 et un quart du nombre de données et on choisit le K avec le meilleur résultat.