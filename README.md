# spring-boot-with-graphql

Projecto de teste de implementcacao de graphql com spring boot
onde o objectivo e a persistencia dos dados e a sua busca nao depender das definicoes dos modelos feitos no backedn mas sim das necessidades do cliente e do negocio
neste projecto implemento usando uma classe departamento, em que tenho um metodo que busca atributos especificos na classe departamento e um outro que persiste dados a escolha do cliente na tabela departamento

o peojecto possui documentacao, mas dem maneira a auxiliar, colocarei alguns exemplos de como as querys sao feitas
Query para busca de designacao na tabela departamento:
{
    getAllDepartamento{
        designacao
      
    }
}

resultado da query:
{
  "mensagem": "Todos departamentos",
  "data": {
    "errors": [],
    "data": {
      "getAllDepartamento": [
       {
          "designacao": "Departamento de Exemplo"
        },
        {
          "designacao": "Informatica"
        }
      ]
    },
    "extensions": null,
    "dataPresent": true
  }
}


Query para perisitir dados na tabela departamento onde nao envio o departamento que e um dos atributos da classe:
mutation {
    createDepartamento(input: {
    
        designacao: "Departamento ",
        objectivo: "Formar",
        endereco: "Alto Mae"
    }) {
        designacao
        objectivo
        endereco
    }
}
