package com.run_us.server.config;

import com.run_us.server.global.exception.GlobalExceptionHandler;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

public class MockMvcFactory {
  public static MockMvc getMockMvc(Object... controllers) {
    return getMockMvcBuilder(controllers).build();
  }

  public static MockMvc getRestDocsMockMvc(RestDocumentationContextProvider restDocumentationContextProvider, String uri, Object... controllers) {
    var documentationConfigurer = MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider);
    documentationConfigurer.uris().withHost(uri).withPort(443);
    return getMockMvcBuilder(controllers).apply(documentationConfigurer).build();
  }

  private static StandaloneMockMvcBuilder getMockMvcBuilder(Object... controllers) {
    FormattingConversionService conversionService = new DefaultFormattingConversionService();
    return MockMvcBuilders.standaloneSetup(controllers)
        .setControllerAdvice(
            new GlobalExceptionHandler())
        .setConversionService(conversionService)
        .setMessageConverters(new MappingJackson2HttpMessageConverter())
        .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true));
  }
}
