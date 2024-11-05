package main.java.com.trabalhoa3.contatomanager;

import main.java.com.trabalhoa3.contatomanager.model.Contato;
import main.java.com.trabalhoa3.contatomanager.service.GerenciadorContatos;

public class Main {
    public static void main(String[] args) {
        String caminhoArquivo = "src/main/resources/contatos.ser";

        GerenciadorContatos gerenciador = new GerenciadorContatos();

        // Criando contatos
        Contato contato1 = new Contato("Pedro", "pedro@gmail.com");
        contato1.addTelefone("111111111");

        Contato contato2 = new Contato("Bruno", "bruno@gmail.com");
        contato2.addTelefone("222222222");

        Contato contato3 = new Contato("Carlos", "carlos@gmail.com");
        contato3.addTelefone("333333333");

        // adicionando os contatos ao gerenciador
        gerenciador.adicionarContato(contato1);
        gerenciador.adicionarContato(contato2);
        gerenciador.adicionarContato(contato3);

        // listando contatos em ordem alfabéticas
        System.out.println("Contatos em ordem alfabética:");
        gerenciador.listarContatosOrdenados();

        // listando contatos na ordem de inserção
        System.out.println("\nContatos na ordem de inserção:");
        gerenciador.listarContatosInsercao();

        // buscando um contato por nome
        System.out.println("\nBuscando contato 'Bruno':");
        Contato encontrado = gerenciador.buscarContatoNome("Bruno");
        System.out.println(encontrado != null ? encontrado : "Contacts.Contato não encontrado");

        // buscando contato pelo numero
        System.out.println("\nBuscando contato com numero '222222222':");
        Contato numero = gerenciador.buscarContatoTelefone("222222222");
        System.out.println(numero != null ? numero : "Contacts.Contato não encontrado");


        // removendo um contato por nome
        System.out.println("\nRemovendo contato 'Pedro':");
        gerenciador.removerContato("Pedro");

        // listando contatos após a remoção
        System.out.println("\nContatos após a remoção:");
        gerenciador.listarContatosOrdenados();

        // Salvar todos os contatos em um arquivo
        gerenciador.salvarContatos(caminhoArquivo);

        // Salvando apenas o contato no arquivo
//        GerenciadorContatos.salvarContatoNoArquivo(caminhoArquivo, contato1);
        GerenciadorContatos.salvarContatoNoArquivo(caminhoArquivo, contato3);


        // Removendo apenas o contato no arquivo
        GerenciadorContatos.removerContatoDoArquivo(caminhoArquivo, "Bruno");

//        //limpa os contatos no arquivo
//        GerenciadorContatos.limparArquivo("src/main/resources/contatos.ser");

        // Carregar os contatos de um arquivo
        System.out.println("\nContatos salvos:");
        GerenciadorContatos gerenciadorCarregado = GerenciadorContatos.carregarContatos(caminhoArquivo);
        gerenciadorCarregado.listarContatosOrdenados();
    }
}
