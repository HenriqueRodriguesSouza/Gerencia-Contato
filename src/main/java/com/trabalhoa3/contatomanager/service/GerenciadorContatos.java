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

    // Estruturas de dados para armazenamento
    private ArrayList<Contato> listaContatos;
    private TreeMap<String, Contato> contatosOrdenados;

    // Construtor
    public GerenciadorContatos() {
        this.listaContatos = new ArrayList<>();
        this.contatosOrdenados = new TreeMap<>();
    }

    public static void salvarContatoNoArquivo(String caminhoArquivo, Contato contato) {
        GerenciadorContatos gerenciador;

        // Carrega os contatos existentes do arquivo atualmente
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            gerenciador = (GerenciadorContatos) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Cria uma nova instância se o arquivo não existir ou estiver vazio
            gerenciador = new GerenciadorContatos();
        }

        // Verifica se o contato já existe pelo nome e atualiza suas informações
        Contato contatoExistente = gerenciador.buscarContatoNome(contato.getNome());
        if (contatoExistente != null) {
            contatoExistente.setEmail(contato.getEmail());
            contatoExistente.setTelefone(contato.getTelefone());
            System.out.println("Contato '" + contato.getNome() + "' atualizado com sucesso.");
        } else {
            // Adiciona o contato se ele não existir
            gerenciador.adicionarContato(contato);
            System.out.println("Contato '" + contato.getNome() + "' salvo com sucesso no arquivo.");
        }

        // Salva todos os contatos
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(gerenciador);
        } catch (IOException e) {
            System.err.println("Erro ao salvar contato no arquivo: " + e.getMessage());
        }
    }

    // Metodo estático para carregar os contatos de um arquivo
    public static GerenciadorContatos carregarContatos(String caminhoArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (GerenciadorContatos) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar contatos: " + e.getMessage());
            return new GerenciadorContatos(); // Retorna um gerenciador vazio em caso de erro
        }
    }

    public static void removerContatoDoArquivo(String caminhoArquivo, String telefone) {
        GerenciadorContatos gerenciador = carregarContatos(caminhoArquivo); // Carrega contatos do arquivo

        // Remove o contato ou o telefone específico
        gerenciador.removerContatoPorTelefone(telefone);

        // Salva a instância atualizada de volta ao arquivo
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(gerenciador);
            System.out.println("Contatos atualizados salvos no arquivo após a remoção.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos atualizados: " + e.getMessage());
        }
    }


    // Metodo para limpar todos os contatos no arquivo salvo
    public static void limparArquivo(String caminhoArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(new GerenciadorContatos()); // Escreve uma nova instância vazia
            System.out.println("Arquivo de contatos limpo com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao limpar o arquivo de contatos: " + e.getMessage());
        }
    }

    // Metodo para adicionar um novo contato verificando se o telefone já está cadastrado
    public void adicionarContato(Contato contato) {
        // Verifica se algum telefone do contato já está cadastrado
        for (Contato c : listaContatos) {
            for (String telefoneNovo : contato.getTelefone()) {
                if (c.getTelefone().contains(telefoneNovo)) {
                    throw new IllegalArgumentException("O telefone " + telefoneNovo + " já está cadastrado para o contato: " + c.getNome());
                }
            }
        }

        // Adiciona o contato às coleções, pois nenhum telefone está duplicado
        listaContatos.add(contato);
        contatosOrdenados.put(contato.getNome(), contato);
        System.out.println("Contato " + contato.getNome() + " adicionado com sucesso!");
    }

    public void removerContatoPorTelefone(String telefone) {
        Contato contatoEncontrado = null;

        // Procura pelo contato que contém o telefone especificado
        for (Contato contato : listaContatos) {
            if (contato.getTelefone().contains(telefone)) {
                contato.getTelefone().remove(telefone); // Remove o telefone específico
                contatoEncontrado = contato;
                break;
            }
        }

        if (contatoEncontrado != null) {
            if (contatoEncontrado.getTelefone().isEmpty()) {
                // Se o contato não tiver mais telefones, remova-o da lista e do mapa
                listaContatos.remove(contatoEncontrado);
                contatosOrdenados.remove(contatoEncontrado.getNome());
                System.out.println("Contato " + contatoEncontrado.getNome() + " removido, pois não possui mais números de telefone.");
            } else {
                System.out.println("Telefone " + telefone + " removido do contato " + contatoEncontrado.getNome());
            }
        } else {
            System.out.println("Nenhum contato encontrado com o telefone: " + telefone);
        }
    }
    // Metodo para buscar contato pelo nome
    public Contato buscarContatoNome(String nome) {
        return contatosOrdenados.get(nome);
    }

    // Metodo para listar contatos em ordem alfabética
    public void listarContatosOrdenados() {
        for (Map.Entry<String, Contato> entry : contatosOrdenados.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    // Metodo para retornar a lista de contatos
    public List<Contato> getListaContatos() {
        return listaContatos;
    }

}
