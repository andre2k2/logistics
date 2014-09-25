
Apresentacao
============



Instalacao
==========

    - Pre-requisitos:
        - Instalar o maven (http://maven.apache.org/download.cgi)

Execucao
========

    - Abrir prompt de comandos na pasta do projeto onde se encontra o pom.xml
    - Executar o comando: mvn jetty:run

Utilizacao
==========

    - Endpoint de persistencia: utilizado para persistir uma malha logistica.

        Ex. de Chamada: POST http://127.0.0.1:8080/logistics/map

        Body Content:
        {
            "name": "teste",
            "routes": [
                {
                "origin": { "name": "A" },
                "destiny": { "name": "B" },
                "distance": 10.0
                }
            ]
        }

        Retorno:

        Codigo 201 (Created) se a malha logistica foi criada com sucesso.
        Codigo 400 (Bad Request) se houver erros de validacao. Um objeto JSON com uma lista de erros será retornanda.
            Ex.: { "errors": [ {"message": "O nome do mapa esta nulo."} ] }
        Codigo 500 (Internal server error) se houve algum problema na criacao.

    - Endpoint de rotas: utilizado para calcular a menor rota e menor custo entre dois pontos de uma malha logistica.

        Ex. de Chamada: GET http://127.0.0.1:8080/logistics/map/route?map=teste&origin=A&destiny=D&autonomy=10.0&cost=2.5
        Ex. de Retorno:
        {
            "name": "teste",
            "autonomy": 10.0,
            "gasCost": 2.5,
            "totalCost": 25.0,
            "routes": [
                {
                "origin": { "name": "A" },
                "destiny": { "name": "B" },
                "distance": 100.0
                }
            ]
        }

        name: nome da malha logistica.
        autonomy: autonomia do caminhao (Km/Litro).
        gasCost: custo do combustivel.
        totalCost: custo total de combustivel que sera gasto na rota definida.
        route: menor rota na malha logistica entre os dois pontos.

        Retorno:

        Codigo 200 (OK) e o conteudo da malha em formato JSON.
        Codigo 400 (Bad Request) se houver erros de validacao. Um objeto JSON com uma lista de erros será retornanda.
            Ex.: { "errors": [ {"message": "O nome do mapa esta nulo."} ] }
        Codigo 500 (Internal server error) se houverem erros na execucao.

Motivacao para Adocao das Tecnologias
=======================================

    - Spring MVC

    - Neo4J

    - Jetty
