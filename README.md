
to check the code coverage
step 1:- maven clean
step 2:- maven build inside goals enter this "clean org.jacoco:jacoco-maven-plugin:prepare-agent install"
after successfully build . check target/site/jacoco/index.html . run this file in browser.

Below are the api's with request and response , which we have to use .
steps
1).we need to add items one by one using below api with given request
http://localhost:8300/api/item/create-item
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
2).we need to craete user one using below api with given request
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


1).we need to craete order using below api with given request
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