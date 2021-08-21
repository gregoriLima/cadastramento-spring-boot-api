
# cadastramento-spring-boot-api
 Spring boot api create for FSBR

### Implemented with Spring-Boot using:
- Pagination and Sorting
- Caching to improve performance
- Security
- Stateless Authentication using JWT
- Documentation using SpringFox Swagger
- JPA
- MySQL
- REST
- CRUD
- DTO
- Bean Validation
- Exception handler
- Test

## Run the app

    java -jar cadastramento-0.0.1-SNAPSHOT.jar
    
## get JWT (type Bearer)

    curl --location --request POST 'http://localhost:8080/auth' \
	--header 'content-Type: application/json' \
	--data-raw '{
	"email": "gregori@gmail.com",
	"senha": "123456"
	}'


**Documentation**
----
* **endpoint**
  /swagger-ui.html
  
![](https://raw.githubusercontent.com/gregoriLima/cadastramento-spring-boot-api/main/src/main/resources/static/swagger.png)

Grégori Fernandes de Lima