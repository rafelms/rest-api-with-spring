package br.com.rafelms.rest_with_spring.integrationtests.swagger;

import br.com.rafelms.rest_with_spring.config.TestConfig;
import br.com.rafelms.rest_with_spring.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class SwaggerIntegrationTest extends AbstractIntegrationTest {


	@Test
	void shouldDisplaySwaggerUiPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
				.port(TestConfig.SERVER_PORT)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
