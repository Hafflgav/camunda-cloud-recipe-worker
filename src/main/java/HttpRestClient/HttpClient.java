package HttpRestClient;

import DataModel.Recipe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private String API_KEY;
    private String URL;

    public HttpClient (String url, String api_key){
        this.API_KEY = api_key;
        this.URL = url;
    }

    public  Recipe recipeRequest() {
        String response="";
        try{
            URL url = new URL(URL + API_KEY);
            response = httpGET(url);
        }catch (Exception ex){
            System.out.println(ex);
        }
        Recipe recipe = parseResponseToRecipe(response);

        return recipe;
    }

    public String httpGET(URL url){
        StringBuilder response = new StringBuilder();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }

        return response.toString();
    }

    public Recipe parseResponseToRecipe(String response) {
        Recipe recipe = new Recipe();
        JSONObject jsonObject = null;
        JSONObject recipeObject = null;

        try {
            jsonObject = new JSONObject(response);
            recipeObject= jsonObject.getJSONArray("recipes").getJSONObject(0);

            recipe.setTitle(recipeObject.getString("title"));
            recipe.setInstructions(recipeObject.getString("instructions"));
            recipe.setSummary(recipeObject.getString("summary"));
            recipe.setReadyInMinutes(recipeObject.getInt("readyInMinutes"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipe;
    }
}
