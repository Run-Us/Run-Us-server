package com.run_us.server.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Converter
public class JsonConverter<T> implements AttributeConverter<T, String> {

  protected final ObjectMapper objectMapper;

  public JsonConverter() {
    objectMapper = new ObjectMapper();
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (ObjectUtils.isEmpty(attribute)) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(attribute); // 3
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T convertToEntityAttribute(String data) {
    if (StringUtils.hasText(data)) {
      Class<?> aClass =
          GenericTypeResolver.resolveTypeArgument(getClass(), JsonConverter.class); // 4
      try {
        return (T) objectMapper.readValue(data, aClass); // 5
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }
}
