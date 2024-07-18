package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.Dados;
import br.com.alura.tabelafipe.model.DadosModelo;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ConsumoApi;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitor = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibirMenu() {
        var menu = """
        **** OPÇÕES ****
        Carros
        Motos
        Caminhoes
               \s
        Digite uma das opções para consultar valores:
       \s""";

        System.out.println(menu);
        var opcao = leitor.nextLine().toLowerCase();

        while (!opcao.equals("carros") && !opcao.equals("motos") && !opcao.equals("caminhoes")) {
            System.out.println("Opção inválida. Digite novamente uma das opções ofertadas: ");
            opcao = leitor.nextLine();
        }

        var endereco = URL_BASE + opcao + "/marcas/";
        var stringJson = consumoApi.obterDados(endereco);
        var listaObjetosJson = conversor.obterListaDados(stringJson, Dados.class);

        listaObjetosJson.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitor.nextLine();

        boolean estaNaListaObjetosJson = false;
        while (!estaNaListaObjetosJson) {
            var codigo = codigoMarca;
            Optional<Dados> encontrou = listaObjetosJson.stream()
                    .filter(o -> o.codigo().equals(codigo))
                    .findFirst();
            if (encontrou.isPresent()) {
                estaNaListaObjetosJson = true;
            } else {
                System.out.println("Codigo da marca inválido. Digite novamente: ");
                codigoMarca = leitor.nextLine();
            }
        }


        System.out.println("\n---------------------------------------");

        endereco += codigoMarca + "/modelos/";
        stringJson = consumoApi.obterDados(endereco);
        var objetoJson = conversor.obterDados(stringJson, DadosModelo.class);
        objetoJson.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("\nDigite um trecho do nome do veiculo para consulta: ");
        var filtroPorNome = leitor.nextLine().toLowerCase();
        objetoJson.modelos().stream()
                .filter(o -> o.nome().toLowerCase().contains(filtroPorNome))
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("\nDigite o código do modelo para consultar valores:");
        var codigoModeloCarro = leitor.nextLine();

        estaNaListaObjetosJson = false;
        while (!estaNaListaObjetosJson) {
            var codigo = codigoModeloCarro;
            Optional<Dados> encontrou = objetoJson.modelos().stream()
                    .filter(o -> o.codigo().equals(codigo))
                    .findFirst();
            if (encontrou.isPresent()) {
                estaNaListaObjetosJson = true;
            } else {
                System.out.println("Codigo do modelo inválido. Digite novamente: ");
                codigoModeloCarro = leitor.nextLine();
            }
        }

        endereco += codigoModeloCarro + "/anos/";
        stringJson = consumoApi.obterDados(endereco);
        var listaAnosModeloSelecionado = new ArrayList<Dados>(conversor.obterListaDados(stringJson, Dados.class));
        var listaSomenteAnos = listaAnosModeloSelecionado.stream()
                .map(Dados::codigo)
                .collect(Collectors.toList());


        List<Veiculo> listaVeiculos = new ArrayList<>();
        String finalEndereco = endereco;
        listaSomenteAnos.forEach(ano -> {
            var json = consumoApi.obterDados(finalEndereco + ano);
            var jsonConvertidoVeiculo = conversor.obterDados(json, Veiculo.class);
            listaVeiculos.add(jsonConvertidoVeiculo);
        });

        listaVeiculos.forEach(System.out::println);


    }
}
