## ProductController CRUD requests

Add Product
````
POST http://localhost:8080/jpa/crud/product
{
    "name": "Google Chromebook",
    "quantity": 50,
    "price": 299.33
}
````
Add Products
````
POST http://localhost:8080/jpa/crud/products
[
    {
        "name": "Thinkpad T520",
        "quantity": 12,
        "price": 299.33
    },
    {
        "name": "Samsung 4k TV",
        "quantity": 5,
        "price": 212.33
    },
    {
        "name": "Blu ray player",
        "quantity": 1,
        "price": 432.33
    }
]
````
Get All Products
````
GET http://localhost:8080/jpa/crud/products
````
Get Product By Id
````
GET http://localhost:8080/jpa/crud/product/<id>
````
Get Product By Name
````
GET http://localhost:8080/jpa/crud/product?name=Thinkpad T520
````
Update single product
````
PUT http://localhost:8080/jpa/crud/product
{
    "id": <id>,
    "name": "Google Chromebook",
    "quantity": 100,
    "price": 99.99
}
````
Verify updated product
````
GET http://localhost:8080/jpa/crud/product/<id>
````
Delete single product
````
DELETE http://localhost:8080/jpa/crud/product/<id>
````

Delete all products
````
DELETE http://localhost:8080/jpa/crud/products
````

## Exception Handling
* https://stackabuse.com/how-to-return-http-status-codes-in-a-spring-boot-application/
 
## Unit Testing & Integration Testing 
* https://reflectoring.io/unit-testing-spring-boot/
* https://github.com/thombergs/code-examples/tree/master/spring-boot/spring-boot-testing
* https://www.arhohuttunen.com/spring-boot-unit-testing/
