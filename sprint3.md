Sprint3:
Annotation: argumenet optionelle method url,method(get, post)
Class: urlMethod: 
    @Override equals comparer les url
Controller: Map<UrlMethod, UrlMappingMethod>

Sprint3-bis:
invoke la methode 


// On demande directement à l'objet Method ses types de paramètres
Class<?>[] parameterTypes = urlMethodMapping.getMethod().getParameterTypes();


if (mapping.containsKey(new UrlMethod(requestURI, request.getMethod()))) {
    UrlMethodMapping urlMethodMapping = mapping.get(new UrlMethod(requestURI, request.getMethod()));
    Object controllerInstance = urlMethodMapping.getControllerClass().getDeclaredConstructor().newInstance();
    
    // 1. Récupérer les types de paramètres attendus par la méthode du contrôleur
    Class<?>[] parameterTypes = urlMethodMapping.getMethod().getParameterTypes();
    
    // 2. Créer le tableau d'arguments à lui envoyer
    Object[] methodArguments = new Object[parameterTypes.length];
    
    // 3. Remplir dynamiquement selon les besoins de la méthode
    for (int i = 0; i < parameterTypes.length; i++) {
        if (parameterTypes[i] == HttpServletRequest.class) {
            methodArguments[i] = request;
        } else if (parameterTypes[i] == HttpServletResponse.class) {
            methodArguments[i] = response;
        } else {
            methodArguments[i] = null;
        }
    }
    
    // 4. Invoquer la méthode avec le tableau d'arguments
    Object result = urlMethodMapping.getMethod().invoke(controllerInstance, methodArguments);
    
    // 5. Afficher le résultat si c'est un String
    if (result instanceof String) {
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
    }
} 