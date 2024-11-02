package org.mach.screenmatch.service;

public interface ConverterInterface {
    <T> T obterDados(String json, Class<T> tClass);
}
