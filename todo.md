- ## ================ 14/06/26 ================================== 
- verifier si le servlet marche : - tomcat [ok]
- bien comprendre la notion de servlet et pull request [ok]
- comprendre le concept de controller [ok]
- comprendre le comment [ok]
- comprendre le comment de l'annotation [ok]
- compiler le framework pour que la creation des annotations soit mis a jour [ok]
- recopier le .jar dans l'application [ok]
- cree une classe dans l'application pour verifier si l'annotation controller marche [ok]
- cree une fonction init dans FrontController, [ok]
-  lire le fichier .xml retourne le nom du package de controller [ok]
- fonction pour mettre , injecter les controllers dans la liste dans FrontController [ok]
- dans le fichier .xml on cree une variable pour mettre le nom du package du controller [ok]
- methode pour verifier si une annotation existe [ok]
- bouclena le liste de on regarde l'annotation controller existe [ok]

## ================= 23/06/26 ============================
- mamorona annotation oe urlMapping [ok]
- urlMapping : pour les methodes [ok]
- Test : cree class EmpController et au dessus apina urlMapping [ok]
- utils : - on prend tout les classes avec les annotation controller (methode 1) [ok]
          - on prend tout les methodes qui se trouve dans ce classe (si annoter par url alors methode ) 
          - on regarde leur urlMapping si il y en a qui correspond a celle taper si oui on prend ce methode et on fait : url : controller -> nom de la methode sinon on prend tout les url et tout les controller de ces url -> les methode de ces controller 
## ================ 30/06/26 ========================== sprint 3 
Goal : specifite de get ou post / executer la methode 
- changer nom de la class GetMapping -> UrlMapping [ok]
- ajouter une mthode de type string method : (c'est soit get , soit post)[ok]
- creer une classe UrlMethodMapping [ok]: - nom de la classe ou se trouve le methode 
                                      - le Method methode c'est la methode 
- creer classe UrlMethod [ok] : - url et methode 
                           - predefinition de equals (comparer url et methode ) : exemple samy /accueil et samy /post -> exception ca devrait pas marcher 
- Tester :[ok] - Get juste taper l'url sur le navigateur 
           - post creer formulaire 
           - any antnle framework apina url roa mitovy de tokony mamoka exception 
           - cree UrlMethodMapping dans front avec deux url 
- FrontController : - creer une Mapping de UrlMethodMapping et UrlMethod -> invoke la methode mais faut pas que samy /accueil et samy /post [ok] 
## ================= 01/07/26 ============================ sprint 3 bis 
Goal : invoquer le methode 
    - ce qu'on a : Mapping qui contient comme cle : - urlMethod (url , method ) - UrlMethodMapping  (class , method )
    - scanner les packages qui ont une annotation controller et urlMapping 
    - et on prend la methode et on fait invoke cette fonction 

- ## ============ 10/07/26 =============================== sprint 3 bis 
        - dans init on scanne toute les classes du projet [ok]
        - on regarde tout ce qui a une annotation controlleur [ok]
        - on cree une instance de cette controller : on regarde lequel des methodes a l'interieur correspond a notre url : on creer une installe de cette methode et on l'invoke 

Oui oui , faut arranger ca 
En fait en ce moment j'ai du mal à me concentrer alors j'éloigne mon téléphone pour pas trop procrastiner, c'est pour ça que ela ela za vo mamaly et que je te réponds entre-temps 
- ## ============== 14/07/26 =============================== sprint 5 
- comprendre la notion de ModelAndView 