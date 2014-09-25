
Apresentacao
============

	Logistics e uma aplicacao desenvolvida para calcular a rota de menor custo entre dois pontos em uma malha logistica.
	Expoe dois endpoints para a persistencia da malha logistica e para o calculo da rota de menor custo.
	A aplicacao foi desenvolvida em java utilizando servidor Jetty 9, framework Spring MVC para exposicao dos endpoints,
	alem do banco de dados de grafos Neo4J, utilizado para persistencia e consulta da malha logistica.

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
        			"distance": 10
        		},
        		{
        			"origin": { "name": "B" },
        			"destiny": { "name": "D" },
        			"distance": 15
        		},
        		{
        			"origin": { "name": "A" },
        			"destiny": { "name": "C" },
        			"distance": 20
        		},
        		{
        			"origin": { "name": "C" },
        			"destiny": { "name": "D" },
        			"distance": 30
        		},
        		{
        			"origin": { "name": "B" },
        			"destiny": { "name": "E" },
        			"distance": 50
        		},
        		{
        			"origin": { "name": "D" },
        			"destiny": { "name": "E" },
        			"distance": 30
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
            "totalCost": 6.25,
            "routes": [
                {
                    "origin": { "name": "A" },
                    "destiny": { "name": "B" },
                    "distance": 10.0
                },
                {
                    "origin": { "name": "B" },
                    "destiny": { "name": "D" },
                    "distance": 15.0
                },
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

    - Jetty 9
		O servidor de aplicacao jetty foi escolhido por sua natureza leve e performatica. Alem da simplicidade na execucao e integracao com o maven.
		Nao requer muitas configuracoes para sua execução.

    - Spring MVC
		O framework Spring MVC foi escolhido pela agilidade e simplicidade de configuracao. Deixa o codigo mais limpo ja que os comportamentos serao
		configurados utilizando anotacoes. E muitas ferramentas e utilitarios ja acompanham, tornando o codigo mais focado no negocio.
		Por ser um padrao de mercado a documentacao e vasta e a implementacao pode ser feita mais rapidamente, de uma forma confiavel e performatica.

    - Neo4J
		Esse e um banco de dados especializado em persistencia e busca de caminhos em grafos. Apos algumas horas de pesquisa decidi utiliza-lo por tratar-se
		de um padrao no mercado para resolver problemas relacionados a busca em grafos. Como e o caso desta aplicacao. Oferece alguns tipos de algoritmos
		de busca como Dijkstra e A*. Por tratar-se de um banco de dados especializado em grafos, contem as otimizacoes necessarias para persistencia e 
		busca de grafos, garantindo os requisitos nao funcionais do sistema. Ao utiliza-lo a aplicacao ganha todo esse expertise para resolucao do problema
		de busca em grafos. Alem de agilizar o desenvolvimento e entrega da aplicacao.


