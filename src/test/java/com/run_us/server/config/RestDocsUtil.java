package com.run_us.server.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import javax.management.Attribute;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@TestConfiguration
public class RestDocsUtil {

  public static OperationRequestPreprocessor getDocumentRequest() {
    return preprocessRequest(
        prettyPrint());
  }

  public static OperationResponsePreprocessor getDocumentResponse() {
    return preprocessResponse(prettyPrint());
  }

  public static final Attribute field(final String name, final String value) {
    return new Attribute(name, value);
  }
}
