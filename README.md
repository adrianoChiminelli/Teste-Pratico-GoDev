# TestePratico-GoDev-Senior

Minha implementação do teste prático do programa GoDev da Senior.
Jdk: 17 e Banco de dados PostgreSQL.

Na atividade utilizei além das tecnologias solicitadas o Lombok e DevTools.

Setup para funcionamento:

O servidor tomcat vai utilizar a porta 8080.

A aplicação está configurada para conectar em um banco de dados com nome: dbgodev
na porta 5432, url da conexão: jdbc:postgresql://localhost:5432/dbgodev
nome de usuário e senha do banco: postgres

# Endpoints da API:

Para manipulação de itens ---------------------------------------------

http://localhost:8080/api/items

    GET -> retorna uma lista todos os itens cadastrador.
  
    POST -> efetua a criação de um item e retorna o mesmo, 
    no corpo da requisição deve conter: descrição, valor e tipo (P ou S)


http://localhost:8080/api/items/{itemId}

    GET -> retorna o item do id presente na url.
  
    DELETE -> deleta o item da base de dados.
  
    PUT -> altera o item na base de dados, o corpo da requisição deve conter:
    id do item, descrição, valor e tipo (P ou S)
  

Para manipulação de pedidos ---------------------------------------------

http://localhost:8080/api/orders

    GET -> retorna uma lista todos os pedidos cadastrador.
  
    POST -> efetua a criação de um pedido e retorna o mesmo, 
    no corpo da requisição deve conter: numero do pedido, data (em formato dd/mm/yyyy) e percentual de desconto.


http://localhost:8080/api/orders/{ordersId}

    GET -> retorna o pedido do id presente na url.
  
    DELETE -> deleta o pedido da base de dados.
  
    PUT -> altera o pedido na base de dados, o corpo da requisição deve conter:
    id do pedido, numero, data (em formato dd/mm/yyyy) e percentual de desconto.
  

Para manipulação de itens de pedidos ---------------------------------------------


http://localhost:8080/api/orders/{ordersId}/items

    GET -> retorna uma lista com todos itens cadastrados para o pedido (id presente na url).
  
    POST -> efetua a inclusão de um item para o pedido (id presente na url) e retorna o mesmo,
    no corpo da requisição deve conter: id do item a ser adicionado e quantidade.
    
 
http://localhost:8080/api/orders/{ordersId}/items/{IditemdoPedido}

    GET -> retorna o item de pedido que está vinculado a esse pedido.
  
    DELETE -> remove o item de pedido do pedido.
  
    PUT -> altera o item de pedido que está vinculado a esse pedido, o corpo da requisição deve conter:
    id do item a ser adicionado e quantidade.

    
Fechamento do pedido ---------------------------------------------
    
http://localhost:8080/api/orders/{ordersId}/close

    GET -> retorna os dados do pedido com a lista de itens e desconto aplicado sobre produtos, 
    caso o desconto tenha sido informado na criação do pedido.
    
    POST -> aplica um novo desconto sobre os produtos e retorna os dados do pedido, 
    o corpo da requisição deve conter: id do pedido e novo percentual de desconto.
    
