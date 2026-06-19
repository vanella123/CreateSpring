- ## Front controller : 
- Point d'entree de tous les url , elle va intercepter l'url et decide de quel controller il doit utiliser et 
c'est la methode ProcessRequest qui le fait 
## Annotation :metadonne , etiquette pour donner des instructions 
- @Target : ou a ton le droit de coller cette etiquette 
- @Retention : jusqu'a quand elle tourne cette annotation 
- Comment marche le getMapping { String value } : 
        - interception de l'url dans le servlet
        - il va trouver le controller 
        - il va regarder les methodes de ce controller 
        - voit le GetMapping -> value () correspond au url intercepter , value sert de routage 
  