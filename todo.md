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
- Test : cree class EmpController et au dessus apina urlMapping 
- utils : - on prend tout les classes avec les annotation controller (methode 1) [ok]
          - on prend tout les methodes qui se trouve dans ce classe (si annoter par url alors methode ) 
          - on regarde leur urlMapping si il y en a qui correspond a celle taper si oui on prend ce methode et on fait : url : controller -> nom de la methode sinon on prend tout les url et tout les controller de ces url -> les methode de ces controller 