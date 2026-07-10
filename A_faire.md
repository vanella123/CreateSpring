## =================== 09/06/26 ========================================
- architecture dossier [ok]
- creation de repository [ok]
- mettre le projet sur github [ok]
## But : terena mandalo amnio servlet io daoly ny url rehetra 
- apprendre le pull request : elle permet a ce que les autres dev puissent donner leur avis sur la modification faites par les autres users [ok]
- apprendre c'est quoi process request , print out (url) [ok]
- 
#### Comment : - on met dans xml : mapping le lien du framework , ce que le deploy.sh doit sortir c'est un fichier .jar , urlParam 
- creer une application web pour tester que le framework marche 

## ================= 15/06/26 ================================
Class : controller 
@controller 
lister (RequestMapping ) : annoter getMapping {
    model vue 
} 
le class zany fatany le spring oe controller 
ainy manao banding automatique : parametre avy any anaty requete 
annotation : - methode , classe , attribut , 
aiza no mipetraka , cree classe ray aiza no asina anio annotation io 
Ao anaty application de test afaka micree controller de anotena am controller atsika 
Mianatra manao anle annotation , aona raha icree anotation 
tenenina le annotation oe aiza avy no azo anapina anazy , inona daoly variable azo apetraka ao anatinle anotation , atao ao anaty application de test 
Micreer classe ray anotena amle classe avy creena , 
## ================= 16/06/26 ================================= SPRINT 1 
- cree classe anotation : cree classe controller framework [ok]
- test : creer classe controller [ok] 
- but : fatatrle framewrok oe reto avy le controller [ok]
- bout de code : mandro anle code rehetra anaaty class excuter lors du demmarage ny anle app executer soit premier appel anle frontservlet ( implementation listenr : au demmarage de executer le code ) [init] 
- Code : - mila fantatra daoly le controller rehetra 
      - FrontServlet : List<String> listeController : init anle classe rehetra ao anaty path , de jerena pour chaque classe ao anaty classe path jerena oe misy anle annotation controller ve ito de ajoutena anaty liste 
      - proceess request : boucler lister de affichena 
      - tsara indrindra : omena le pakcgae anle controller (par configuration ) mamaky anle classe web.xml , de jerena oe iza avy no manao annotation anty test , web.xml : manisy variable , valeur le package misy anle controller anle app test
      - class utilitaire : methode : misy ve ty annotation ty sa tsisy , omeo le package , mitady anle classe retraretra 
      - micree classe listener declarena ao anaty .xml anle client , mimplimente listener 
## ==================== 19/06/26 ==============================   SPRINT 2 
tanjona anana url : iza controller sy methode associer amniny controller iny 
annotation azo apetraka am methode @urlMapping : mila variable io annotation io 
- Partie test  : Class EmpController , [
          - @UrlMapping("/emp/list")
          - Liste 
          - rehefa hita oe ty annoter controller de aveo jerena daoly ny methode ny de jerena raha misy anotation anle am url ve leizy 
          - /emp/new : le controller -> ty le methode 
          - aiko le url sa tsy aiko rehefa tsy ay de mi trhows , de affichena oe tsy fantatro io fa reto iany ny fantatro 
- ] 
## ==================== 23/06/26 =========================== APPROCHE : SPRINT 2 
- apres revu de code : -> 
- manaz 
## sprint 3  : 
- TestController : url /test 
- micree classe ray vaova o de :  
  ## sprint 3 bis : 
## ==================== 30/06/26 ========================== sprint 3 
- ajouter : fonction nom du methode (get na post ) dans urlMapping 
- creer une classe UrlMappingMethod : methode (le fonction ) , class misy anazy 
- UrlMethod : attribut url et method (predefinir la fonction equals )
- Controller: Map<UrlMethod, UrlMappingMethod>



## sprint 4 : 
- tsy tokony initialiser mintsy le code 
- listener : on deplace les codes dans init dans un listener , on cree un listener , si le listener leve une exception alors l'application n'est meme pas demarrer , et on aura une erreur dans log 
- init minitisaliser anle map 
- stuck et hip 

## ============================= model and view =================
- manao class model and view 
- 