## POST http://localhost:8080/users
{
  "email": "diego@diegodusarte.com.br",
  "name": "Diego",
  "password": "123456",
  "phones": [
    {
      "ddd": 11,
      "number": 957995397
    }
  ]
}


## POST http://localhost:8080/login
{"email":"diego@diegodusarte.com.br","password":"123456"}

## GET http://localhost:8080/users
Authorization: {TOKEN}