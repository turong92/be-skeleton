package com.skeleton.common.config.swagger;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.skeleton.common.config.header.Headers;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	// private final List<String> FORCE_REQUIRED_WHITE_LIST = List.of("request", "input", "query", "create", "update");

	@Bean
	public OpenAPI openAPI(
		@Value("${springdoc.version}") String springdocVersion,
		@Value("${springdoc.title}") String springdocTitle,
		@Value("${springdoc.description}") String springdocDescription) {

		List<String> securitySchemeNameList = List.of(Headers.AUTHORIZATION, Headers.DEV_AUTHORIZATION);

		return new OpenAPI()
			.security(Collections.singletonList(securityRequirement(securitySchemeNameList)))
			//.addSecurityItem(securityRequirement(securitySchemeNameList))
			.components(components(securitySchemeNameList))
			.info(info(springdocVersion, springdocTitle, springdocDescription));
	}

	// // for web codegen
	// // codegen 사용할 때 response field에 required 안달려있으면 일일히 null handling 해야 하는 수고가 있다고 해서 아래 코드 추가함
	// @Bean
	// public OpenApiCustomizer responseSchemaCustomizer() {
	// 	return openAPI -> {
	// 		openAPI.getComponents().getSchemas().forEach((name, schema) -> {
	// 			if (schema != null) {
	// 				setSchemaRequired((Schema<?>) schema, name);
	// 			}
	// 		});
	// 	};
	// }

	// private void setSchemaRequired(Schema<?> schema, String name) {
	//
	// 	boolean forceRequired = true;
	//
	// 	for (String whiteStr : FORCE_REQUIRED_WHITE_LIST) {
	// 		if (StringUtils.containsIgnoreCase(name, whiteStr)) {
	// 			forceRequired = false;
	// 			break;
	// 		}
	// 	}
	//
	// 	if (forceRequired) {
	// 		schema.setRequired(schema.getProperties().keySet().stream().toList());
	// 	}
	// }

	private SecurityRequirement securityRequirement(List<String> securitySchemeNameList) {

		SecurityRequirement securityRequirement = new SecurityRequirement();
		for (String securitySchemeName : securitySchemeNameList) {
			securityRequirement.addList(securitySchemeName);
		}

		return securityRequirement;
	}

	private Components components(List<String> securitySchemeNameList) {

		Components components = new Components();
		for (String securitySchemeName : securitySchemeNameList) {
			components.addSecuritySchemes(securitySchemeName, securityScheme(securitySchemeName));
		}

		return components;
	}

	private SecurityScheme securityScheme(String securitySchemeName) {

		SecurityScheme securityScheme = new SecurityScheme();
		securityScheme.setName(securitySchemeName);
		securityScheme.setType(SecurityScheme.Type.APIKEY);
		securityScheme.setIn(SecurityScheme.In.HEADER);
		securityScheme.setScheme("bearer");
		securityScheme.setBearerFormat("JWT");
		// securityScheme.setDescription("In : " + SecurityScheme.In.HEADER);

		return securityScheme;
	}

	private Info info(String springdocVersion, String springdocTitle, String springdocDescription) {

		Info info = new Info();
		info.setVersion(springdocVersion);
		info.setTitle(springdocTitle);
		info.setDescription(springdocDescription);

		return info;
	}
}
