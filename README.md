
# cadastramento-spring-boot-api
 Spring boot api create for FSBR

### Implemented with Spring-Boot using:
- Pagination and Sorting
- Caching to improve performance
- Security
- Stateless Authentication using JWT
- Documentation using SpringFox Swagger
- Monitoring with Actuator
- JPA
- MySQL
- REST
- CRUD
- DTO
- Bean Validation
- Exception handler
- Junit for Tests

## Run the app

    java -jar cadastramento-0.0.1-SNAPSHOT.jar
    
## request JWT (type Bearer)

    curl --location --request POST 'http://desafiofsbr.ddns.net:8080/auth' \
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

## Get API status

### Request

`GET /actuator/health`

    curl --location --request GET 'http://desafiofsbr.ddns.net:8080/actuator/health'

### Response

    {
    	"status": "UP"
    }




- Grégori Fernandes de Lima
