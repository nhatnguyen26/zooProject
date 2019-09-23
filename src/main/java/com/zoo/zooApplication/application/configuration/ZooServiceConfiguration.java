package com.zoo.zooApplication.application.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.moesif.servlet.MoesifFilter;
import com.zoo.zooApplication.application.filter.FirebaseAuthFilter;
import com.zoo.zooApplication.application.filter.ZooMasterAuthFilter;
import com.zoo.zooApplication.firebaseadaptor.FirebaseAuthBinder;
import com.zoo.zooApplication.resource.BookingResource;
import com.zoo.zooApplication.resource.CourtManagementResource;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.ws.rs.ApplicationPath;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Configuration
@EnableJpaRepositories(basePackages = {"com.zoo.zooApplication.dao.repository"})
@EntityScan(basePackages = {"com.zoo.zooApplication.dao.model"})
@ComponentScan(basePackages = {"com.zoo.zooApplication"})
@ApplicationPath("/zooApplication")
public class ZooServiceConfiguration extends ResourceConfig {

	public ZooServiceConfiguration() {
		register(BookingResource.class);
		register(CourtManagementResource.class);
		register(FirebaseAuthFilter.class);
		register(new FirebaseAuthBinder());
		register(ZooMasterAuthFilter.class);
	}

	@PostConstruct
	public void init() {
		this.configureSwagger();
	}

	private void configureSwagger() {
		this.register(ApiListingResource.class);
		this.register(SwaggerSerializers.class);

		BeanConfig config = new BeanConfig();
		config.setTitle("Backend service for zoo application");
		config.setVersion("v1");
		config.setSchemes(new String[]{"http", "https"});
		config.setBasePath("/zooApplication/");
		config.setResourcePackage("com.zoo.zooApplication.resource");
		config.setPrettyPrint(true);
		config.setScan(true);
	}

	@Bean
	@ConditionalOnProperty(value = "FIRE_BASE_APP_CREDENTIAL_JSON")
	public FirebaseApp getFirebaseApp(@Value("${FIRE_BASE_APP_CREDENTIAL_JSON:false}") String firebaseCredentialJSON) throws IOException {
		InputStream credentialStream = new ByteArrayInputStream(firebaseCredentialJSON.getBytes());
		FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredentials(GoogleCredentials.fromStream(credentialStream))
			.setDatabaseUrl("https://zoo-application.firebaseio.com/")
			.build();

		return FirebaseApp.initializeApp(options);
	}

	// fall back if using default
	@Bean
	@ConditionalOnProperty(value = "GOOGLE_APPLICATION_CREDENTIALS")
	@ConditionalOnMissingBean(FirebaseApp.class)
	public FirebaseApp getFirebaseAppFallBack() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredentials(GoogleCredentials.getApplicationDefault())
			.setDatabaseUrl("https://zoo-application.firebaseio.com/")
			.build();

		return FirebaseApp.initializeApp(options);
	}

	@Bean
	@ConditionalOnProperty(value = "MOESIF_APPLICATION_ID")
	public Filter moesifFilter(@Value("${MOESIF_APPLICATION_ID:false}") String moesifAppId) {
		return new MoesifFilter(moesifAppId);
	}

	@Bean
	@ConditionalOnBean(value = FirebaseApp.class)
	public FirebaseAuth getFirebaseAuth(@Autowired FirebaseApp firebaseApp) throws IOException {
		return FirebaseAuth.getInstance(firebaseApp);
	}

}
