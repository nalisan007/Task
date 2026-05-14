package io.example.Wellness360.configuration;

import java.util.function.Predicate;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.RequestPath;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	    public void configureApiVersioning(ApiVersionConfigurer configurer) {
			Predicate<RequestPath> isVersioned = path -> path.value().startsWith("/api/v");
			configurer.usePathSegment(1, isVersioned).setDefaultVersion("1.0").setVersionRequired(false);
	    }

}
