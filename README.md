# FIPE API CommandLineRunner

Este projeto foi desenvolvido como parte de um desafio de uma aula da Alura. O objetivo do projeto é utilizar a API pública da FIPE (FIPE API HTTP REST) para criar um programa SpringBoot em Java que atua como um CommandLineRunner.

## Descrição do Projeto

O programa faz uso da biblioteca Jackson para leitura dos JSONs solicitados da API. Ele funciona da seguinte maneira:

1. O usuário escolhe um tipo de veículo: carros, motos ou caminhões.
2. É impressa no console/terminal uma lista de marcas de veículos do tipo escolhido, obtida da API.
3. O usuário seleciona uma marca.
4. O usuário escolhe um modelo de veículo.
5. Finalmente, é impressa uma lista de todos os preços dos anos disponíveis para o veículo selecionado, conforme fornecido pela API.

## Tecnologias Utilizadas

- Java
- SpringBoot
- Jackson
- FIPE API HTTP REST
