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
## ================= 16/06/26 =================================
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
      - 