Below are the api's with request and response , which we have to use .
http://localhost:8400/api/item/create-item
Request:-
 {
    "name":"testing",
	"code": "code12356",
    "price":"500",
     "quantity":"1",
     "category":"grocery"
}

Response:-
{
    "id": 6,
    "name": "testing",
    "code": "code12356",
    "price": "500",
    "quantity": "1",
    "category": "GROCERY"
}

http://localhost:8300/api/user/create-user
Request:-
{
    "email":"testing@gmail.com",
	"userName": "testUser",
    "firstName":"test",
     "lastName":"user",
     "category":"customer"
}



Response;-
{
    "id": 3,
    "email": "testing@gmail.com",
    "userName": null,
    "firstName": "test",
    "lastName": "user",
    "category": "AFFILATED"
}



http://localhost:8300/api/order/create-order
Request:-
{
    "userId":3,
	"itemids":[
        1,2,3,4,5,6
    ]
}

Response:-
{
    "id": 5,
    "userResponseDTO": {
        "id": 3,
        "email": "testing@gmail.com",
        "userName": "userName",
        "firstName": "test",
        "lastName": "user",
        "category": "AFFILATED"
    },
    "totalAmount": 519.5
}