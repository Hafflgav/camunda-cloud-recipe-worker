import DataModel.Recipe;
import HttpRestClient.HttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Test_HttpClient {
    private static HttpClient recipeClient;
    private static URL url;

    @BeforeAll
    static void setup() throws MalformedURLException {
        recipeClient = new HttpClient("https://api.spoonacular.com/recipes/random?number=1&apiKey=", "d416b5a893204a06bf73bb8a46d70e2e");
    }

    @Test
    void test_HttpGet() {
        //given
        try{
            url = new URL("https://api.spoonacular.com/recipes/random?number=1&apiKey=d416b5a893204a06bf73bb8a46d70e2e");
        }catch (Exception ex){
            System.out.println(ex);
        }

        //when
        String response= recipeClient.httpGET(url);

        //then
        assertFalse(response.isEmpty());
        assertNotEquals("", response);

        System.out.println(response);
    }

    @Test
    void test_recipeReqiest() {
        //when
        Recipe recipe = recipeClient.recipeRequest();

        //then
        assertNotEquals("", recipe.getTitle());
        assertNotEquals("", recipe.getInstructions());

        System.out.println(recipe.getTitle());
        System.out.println(recipe.getInstructions());
    }
}