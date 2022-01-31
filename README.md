# Application: RecipeBook
- Spring boot application


## Entity
- Recipe: Store the details of a recipe
    - Name : name of recipe
    - vegetarian : indicator to specify if vegetarian or not
    - noOfPersons : indicator displaying the number of people the dish is suitable for
    - creationDate : Date and time  of creation
    - Ingredients : List of ingredients used in recipe
    - Instructions : Instructions to prepare recipe
- Ingredient: Store the ingredients
    - Name : name of the ingredient
    - Amount : amount required for recipe
    

## REST API's
- create recipe data: [http://localhost:8080/api/createRecipe](http://localhost:8080/api/createRecipe)
- retrieve all recipes: [http://localhost:8080/api/allRecipes](http://localhost:8080/api/allRecipes)
- Update a given recipe: [http://localhost:8080/api/allRecipes](http://localhost:8080/api/updateRecipe)
- Retrieve recipe based on name, vegetarian or noOfPersons: [http://localhost:8080/api/retrieveRecipes?name=&vegetarian=&noOfPersons=](http://localhost:8080/api/retrieveRecipes?name=&vegetarian=&noOfPersons=)
  All are optional fields
- delete recipe given by name: [http://localhost:8080/api/deleteRecipe/{name}](http://localhost:8080/api/deleteRecipe/{name})

## Test & Integration

- With MockMvc


## Spring security enabled
- All api's require authentication with following credentials
 username: user
 password: wordPass

## Database
- Increement1: H2 

## API documentation
- swagger.yaml [src/main/resources/swagger.yaml](swagger)

## Build and run application
Pre-requisite - install docker daemon
- Run command to mysql docker container:
  docker-compose up

This run a mysql container and by startup loads data present in mysql/setup/data.sql

- Then build and  start application using command:
./mvnw install && ./mvnw spring-boot:run

## CURL commands to test api's in command line

- POST
  curl --user "user:wordPass" -d '{"name":"fries", "vegetarian" : "yes"}' -H 'Content-Type: application/json' http://localhost:8080/api/createRecipe  

- GET : Unauthorized
  curl --user "user:pass" http://localhost:8080/api/allRecipes

- GET : successful
  curl --user "user:wordPass" http://localhost:8080/api/allRecipes
  
- PUT - successful
  curl --user "user:wordPass" -X  PUT  -d '{"name":"fries", "vegetarian" : "no"}' -H 'Content-Type: application/json' http://localhost:8080/api/updateRecipe

- DELETE - successful
  curl --user "user:wordPass" -X DELETE http://localhost:8080/api/deleteRecipe/fries