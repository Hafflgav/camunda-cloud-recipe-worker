package CamundaWorker;

import DataModel.Recipe;
import HttpRestClient.HttpClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableZeebeClient
@RestController
public class WorkerApplication {
	private final String API_KEY = "d416b5a893204a06bf73bb8a46d70e2e";
	private String URL = "https://api.spoonacular.com/recipes/random?number=1&apiKey=";

	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

	@ZeebeWorker(type = "recipe-worker")
	public void handleJobFoo(final JobClient client, final ActivatedJob job) {

		HttpClient recipeClient = new HttpClient(URL, API_KEY);
		Recipe recipe = recipeClient.recipeRequest();

		client.newCompleteCommand(job.getKey())
				.variables("{\"recipe_title\": "+ "\""+recipe.getTitle()+"\"" + ", \"recipe_content\":"+ "\""+ recipe.getInstructions() +"\""+ "}")
				.send()
				.exceptionally( throwable -> { throw new RuntimeException("Could not complete job " + job, throwable); });
	}

}
