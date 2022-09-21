package Goalsetter.login;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;


public class ABMRestTest {

    @Before
    public void before(){
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    public void getTest(){
        given()
                .param("page", 2)
                .get("/users")
        .then()
                .statusCode(200)      // (*1)
                .body("total_pages", equalTo(2))    //(*2)
                .and()
                .body("data.id",hasItems(7,8,9,10,11,12) ) //(*3)
                ;
    }
    //-------------------------------------------------------
    // T E S T S
    //-------------------------------------------------------
    //Request method: GET
    //Request URI:    https://reqres.in/api/users?page=2
    //Request params: page=2
    //Headers:        Accept=*/*
    //Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 8.838 sec
    //
    //Results :
    //
    //Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

    @Test
    public void postTest(){
        given()
                .accept(ContentType.JSON)
                . body("{\"name\":\"juan\", \"job\":\"developer\"}")
                .post("/users")
                .then().log().ifValidationFails()
                .statusCode(201)             //(*1)
                .body("$", hasKey("id"))     //(*2)
                .and()
                .body("$", hasKey("createdAt"))//(*2)
        ;
    }

    @Test
    public void putTest(){
        // Se crea el dato de prueba
        JsonPath jsonPath  = given()
                .accept(ContentType.JSON)
                . body("{\"name\":\"juan2\", \"job\":\"leader\"}")
                .post("/users")
                .then()
                .extract().jsonPath();
        ;

        // Se extrae el dato de la llamada
        String idCreated =  jsonPath.get("id").toString() ;


        given()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                . body("{ \"job\" : \"TESTER\" }")
                .put("/users/"+idCreated)       //  <-- se utiliza el dato creado en el POST
                .then().log().all()
                .statusCode(200)                //  (*1)
                .and()
                .body("$", hasKey("updatedAt")) //  (*2)
                .body("job", equalTo("TESTER")) //  (*3)
        ;
    }

    @Test
    public void deleteTest(){

        //se crea el recurso
        JsonPath jsonPath  = given().log().all()
                .accept(ContentType.JSON)
                . body("{\"name\":\"juan2\", \"job\":\"leader\"}")
                .post("/users")
                .then()
                .extract().jsonPath();
        ;

        String idCreated = ( jsonPath.get("id").toString() );

        //se elimina el recurso
        given().log().all()
                .accept(ContentType.JSON)
                .param("id", idCreated)
                .delete("/users")
                .then().log().ifValidationFails()
                .statusCode(204);             // (*1)
    }
}
