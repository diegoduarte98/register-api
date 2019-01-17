#### POST https://api-register.herokuapp.com/users
{
  "email": "diego@diegoduarte.com.br",
  "name": "Diego",
  "password": "123456",
  "phones": [
    {
      "ddd": 11,
      "number": 957995397
    }
  ]
}

#### POST https://api-register.herokuapp.com/users/auth
{
    "email":"diego@diegoduarte.com.br",
    "password":"123456"
}

#### GET https://api-register.herokuapp.com/users/{id}
Authorization: Bearer {TOKEN}


#### https://api-register.herokuapp.com/swagger-ui.html
Para facilitar o uso da API, foi adicionado ao projeto o Swagger. é nescessário adicionar o token para o recurso /user/{id}.
Bearer {TOKEN}
