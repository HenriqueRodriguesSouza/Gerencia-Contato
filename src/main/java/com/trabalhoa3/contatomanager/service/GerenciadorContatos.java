package main.java.com.trabalhoa3.contatomanager.service;

import java.io.Serial;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import main.java.com.trabalhoa3.contatomanager.model.Contato;
import java.util.*;

public class GerenciadorContatos implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // estrutura de dados para armazenamento
    private ArrayList<Contato> listaContatos;
    private TreeMap<String, Contato> contatosOrdenados;


    // construtor
    public GerenciadorContatos() {
        this.listaContatos = new ArrayList<>();
        this.contatosOrdenados = new TreeMap<>();
    }

    // metodo para salvar apenas um contato em um arquivo
    public static void salvarContatoNoArquivo(String caminhoArquivo, Contato contato) {
        GerenciadorContatos gerenciador = new GerenciadorContatos(); // Cria uma nova instância vazia
        gerenciador.adicionarContato(contato); // Adiciona o contato desejado

        // Salva a nova instância contendo apenas o contato no arquivo
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(gerenciador);
            System.out.println("Contato '" + contato.getNome() + "' salvo com sucesso no arquivo.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar contato no arquivo: " + e.getMessage());
        }
    }

    // metodo para salvar o GerenciadorContatos em um arquivo
    public void salvarContatos(String caminhoArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(this);
            System.out.println("Contatos salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }

    // metodo estático para carregar os contatos de um arquivo
    public static GerenciadorContatos carregarContatos(String caminhoArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (GerenciadorContatos) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar contatos: " + e.getMessage());
            return new GerenciadorContatos(); // Retorna um gerenciador vazio em caso de erro
        }
    }

    // metodo para remover um contato do arquivo
    public static void removerContatoDoArquivo(String caminhoArquivo, String nomeContato) {
        GerenciadorContatos gerenciador = carregarContatos(caminhoArquivo); // Carrega contatos do arquivo

        // Tenta remover o contato pelo nome
        gerenciador.removerContato(nomeContato);

        // Salva a instância atualizada de volta ao arquivo
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(gerenciador);
            System.out.println("Contato '" + nomeContato + "' removido com sucesso do arquivo.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos atualizados: " + e.getMessage());
        }
    }

    // metodo para limpar todos os contatos no arquivo salvo
    public static void limparArquivo(String caminhoArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(new GerenciadorContatos()); // Escreve uma nova instância vazia
            System.out.println("Arquivo de contatos limpo com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao limpar o arquivo de contatos: " + e.getMessage());
        }
    }

    // metodo para adicionar um novo contato verificando se o telefone ja esta cadastrado
    public void adicionarContato(Contato contato) {
        for (Contato c : listaContatos) {
            for (String telefoneNovo : contato.getTelefone()) {
                if (c.getTelefone().contains(telefoneNovo)) {
                    System.out.println("O telefone " + telefoneNovo + " já está cadastrado para o contato: " + c.getNome());
                    return;
                }
            }
        }

        // Adiciona o contato às coleções, pois nenhum telefone está duplicado
        listaContatos.add(contato);
        contatosOrdenados.put(contato.getNome(), contato);
        System.out.println("Contacts.Contato " + contato.getNome() + " adicionado com sucesso!");
    }


    //metodo para remover contato pelo email
    public void removerContato(String nome) {
        Contato contatoRemovido = contatosOrdenados.remove(nome);
        if (contatoRemovido != null) {
            listaContatos.remove(contatoRemovido);
            System.out.println("Contacts.Contato removido: " + nome);
        } else {
            System.out.println("Contacts.Contato com o nome '" + nome + "' não encontrado.");
        }
    }

    //metodo buscar contato pelo nome
    public Contato buscarContatoNome(String nome) {
        return contatosOrdenados.get(nome);
    }

    //metodo buscar contato pelo numero
    public Contato buscarContatoTelefone(String telefone) {
        for (Contato contato : listaContatos) {
            if (contato.getTelefone().contains(telefone)) {
                return contato;
            }
        }
        return null;
    }

    //metodo listar contatos em ordem alfabetica
    public void listarContatosOrdenados(){
        for (Map.Entry<String, Contato> entry : contatosOrdenados.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    //metodo para listar os contatos por ordem de criação
    public void listarContatosInsercao() {
        for (Contato contato : listaContatos) {
            System.out.println(contato);
        }
    }
}
