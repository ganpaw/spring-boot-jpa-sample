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

## ManyToOne Unidirectional (Book 1 -- * Review) - BookController and ReviewController
A Book record can exist without reviews but if a Review record exist, it is always linked to a book.


```
A Book record can exist without reviews but if a Review record exist, it is always linked to a book.

BookController endpoints:

Methods	Urls	Actions				Description
POST			/api/books			Add new book
GET				/api/books			Get all books or books containing title if passed a request parameter (optional)
GET				/api/books/:id   	Get book by id
PUT				/api/books/:id 		Update book by id
DELETE			/api/books/:id 		Delete book (and its reviews) by :id
DELETE			/api/books			Delete all books

ReviewController endpoints:

Methods	Urls	Actions							Description
POST			/api/books/:id/reviews			Add new review by book id - 1. retrieve book by id, 2. review.setBook(book) 3. save review
GET				/api/books/:id/reviews 			Get all reviews of a book - 1. if book doesn't exist throw error 2. get review by book id.
GET				/api/reviews/:id 				Get review by review id
PUT				/api/reviews/:id 				Update review by review id
DELETE			/api/reviews/:id 				Delete review by review id
DELETE			/api/books/:id/reviews			Delete all reviews of a book
````
### BookController endpoints:
Add new book - try adding 3 books with 3 requests
````
POST http://localhost:8080/api/books
{
    "title": "Effective Java"
}
POST http://localhost:8080/api/books
{
    "title": "Head First Python"
}
POST http://localhost:8080/api/books
{
    "title": "Concurrency In Java"
}
````
Get all books or books containing title if passed a request parameter (optional)
````
GET http://localhost:8080/api/books
GET http://localhost:8080/api/books?title=Concurrency In Java
````
Get Book by id
````
GET http://localhost:8080/api/books/2
````
Update book by id
````
PUT http://localhost:8080/api/books/2
{
    "title": "Head First Python 2nd Edition"
}
````
Delete book (and its reviews) by id - Try this request after you add few reviews using ReviewController requests
````
GET http://localhost:8080/api/books/2 - will show book
GET http://localhost:8080/api/books/2/reviews -  will show its reviews
DELETE http://localhost:8080/api/books/2 - delete book whih will also cascade delete review rows
GET http://localhost:8080/api/books/2 - no book
GET http://localhost:8080/api/books/2/reviews - no reviews
````
Delete all books (all their reviews) -  Try this request after you add few reviews using ReviewController requests
````
DELETE http://localhost:8080/api/books
````

### ReviewController endpoints:
Add reviews for books 
````
POST http://localhost:8080/api/books/1/reviews
{
    "comment": "A great java book"
}
POST http://localhost:8080/api/books/1/reviews
{
    "comment": "Best book on Java"
}
POST http://localhost:8080/api/books/2/reviews
{
    "comment": "Good start for Python language"
}
POST http://localhost:8080/api/books/3/reviews
{
    "comment": "Deep understanding on Multithreading"
}
POST http://localhost:8080/api/books/3/reviews
{
    "comment": "Start to Advanced on threading and concurrency"
}
````
Get review by book
````
GET http://localhost:8080/api/books/1/reviews
[
    {
        "id": 1,
        "comment": "A great java book"
    },
    {
        "id": 2,
        "comment": "Best book on Java"
    },
    {
        "id": 3,
        "comment": "Good start for Python language"
    }
]
GET http://localhost:8080/api/books/2/reviews
[
    {
        "id": 4,
        "comment": "Good start for Python language"
    }
]
GET http://localhost:8080/api/books/3/reviews
[
    {
        "id": 5,
        "comment": "Deep understanding on Multithreading"
    },
    {
        "id": 6,
        "comment": "Start to Advanced on threading and concurrency"
    }
]
````
Update a single review by review id
````
PUT http://localhost:8080/api/reviews/3
{
    "comment": "Really an effective java programming book. 5 star"
}
GET http://localhost:8080/api/reviews/3
{
    "id": 3,
    "comment": "Really an effective java programming book. 5 star"
}
GET http://localhost:8080/api/books/1/reviews
[
    {
        "id": 1,
        "comment": "A great java book"
    },
    {
        "id": 2,
        "comment": "Best book on Java"
    },
    {
        "id": 3,
        "comment": "Really an effective java programming book. 5 star"
    }
] 
````
Delete a review by review id
````
DELETE http://localhost:8080/api/reviews/3
Status: 204
````
Delete all reviews for a book
````
DELETE http://localhost:8080/api/books/1/reviews
Status: 204
````






## Exception Handling
* https://stackabuse.com/how-to-return-http-status-codes-in-a-spring-boot-application/
 
## Unit Testing & Integration Testing 
* https://reflectoring.io/unit-testing-spring-boot/
* https://github.com/thombergs/code-examples/tree/master/spring-boot/spring-boot-testing
* https://www.arhohuttunen.com/spring-boot-unit-testing/

## References
https://thorben-janssen.com/entity-mappings-introduction-jpa-fetchtypes/