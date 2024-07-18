package br.com.alura.tabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.List;

public class ConverteDados implements IConverteDados {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterListaDados(String json, Class<T> classe) {
        try {
            TypeReference<List<T>> lista = new TypeReference<>() {
                @Override
                public Type getType() {
                    return objectMapper.getTypeFactory().constructCollectionType(List.class, classe);
                }
            };
            return objectMapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
