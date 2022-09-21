package Goalsetter.login;
import org.testng.annotations.Test;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class RestAssuredClass {

    static URI url = URI.create("https://reqres.in/api/users");

    @Test
    public static void getResponseBody(){
        given().queryParam("page","2")
                .when().get(url).then().log()
                .body();
    }

    @Test
    public static void getResponseStatus(){
        int statusCode= given().queryParam("page","6").when().get(url).getStatusCode();
        System.out.println("The response status is "+statusCode);
        given().when().get(url).then().assertThat().statusCode(200);
    }

    @Test
    public static void getResponseTime(){
        System.out.println("The time taken to fetch the response "+get(url)
                .timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
    }

    @Test
    public static void getResponseHeaders(){
        System.out.println("The headers in the response "+
                get(url).then().extract().headers());
    }

    @Test
    public static void getResponseContentType(){
        System.out.println("The content type of response "+
                get(url).then().extract()
                        .contentType());
    }
}
