package main.java.com.trabalhoa3.contatomanager.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

public class Contato implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private HashSet<String> telefone;

    public Contato(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.telefone = new HashSet<>();
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

    public boolean addTelefone(String telefone) {
        return this.telefone.add(telefone);
    }
    public boolean removeTelefone(String telefone) {
        return this.telefone.remove(telefone);
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Email: " + email + ", Telefones: " + telefone;
    }
}
