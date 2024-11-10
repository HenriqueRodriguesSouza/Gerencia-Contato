package main.java.com.trabalhoa3.contatomanager.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.regex.Pattern;

// Classe de modelo para contato

public class Contato implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Atributos
    private String nome;
    private String email;
    private HashSet<String> telefone;

    // Expressão regular para validar o formato de telefone (exemplo: "(XX) XXXXX-XXXX" ou "XXXXXXXXXX")
    private static final Pattern TELEFONE_REGEX = Pattern.compile("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$");

    public Contato(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.telefone = new HashSet<>();
    }

    // Construtor para criar contato com telefone inicial
    public Contato(String nome, String email, String telefoneInicial) {
        this.nome = nome;
        this.email = email;
        this.telefone = new HashSet<>();

        // Valida o telefone inicial e adiciona se for válido
        if (isTelefoneValido(telefoneInicial)) {
            this.telefone.add(telefoneInicial);
        } else {
            throw new IllegalArgumentException("Número de telefone inicial inválido: " + telefoneInicial);
        }
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public HashSet<String> getTelefone() {
        return telefone;
    }
    public void setTelefone(HashSet<String> telefone) {
        this.telefone = telefone;
    }

    // Metodo para adicionar telefone com validação
    public boolean addTelefone(String telefone) {
        if (isTelefoneValido(telefone)) {
            return this.telefone.add(telefone);
        } else {
            System.out.println("Número de telefone inválido: " + telefone);
            return false;
        }
    }

    // Metodo para remover telefone
    public boolean removeTelefone(String telefone) {
        return this.telefone.remove(telefone);
    }

    // Metodo de validação do telefone usando expressão regular
    private boolean isTelefoneValido(String telefone) {
        return TELEFONE_REGEX.matcher(telefone).matches();
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Email: " + email + ", Telefones: " + telefone;
    }
}