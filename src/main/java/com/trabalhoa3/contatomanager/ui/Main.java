package main.java.com.trabalhoa3.contatomanager.ui;

import main.java.com.trabalhoa3.contatomanager.model.Contato;
import main.java.com.trabalhoa3.contatomanager.service.GerenciadorContatos;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Classe da tela (swing ui) inicial do programa e que podemos criar o contato
 */

public class Main extends JFrame {
    //caminho arquivo serializado
    String caminhoArquivo = "src/main/resources/contatos.ser";

    // Instancia o gerenciador de contatos
    GerenciadorContatos gerenciador = new GerenciadorContatos();

    private JPanel Name;
    private JTextField FieldName;
    private JPanel Email;
    private JTextField FieldEmail;
    private JPanel Telefone;
    private JTextField FieldTelefone;
    private JButton listaButton;
    private JButton salvarButton;
    private JPanel MainPanel;

    //construtor
    public Main() {
        super("Agenda de Contatos");
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);
        setVisible(true);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pega os dados do campo
                String nome = FieldName.getText();
                String email = FieldEmail.getText();
                String telefone = FieldTelefone.getText();

                // carrega o Gerenciador de Contatos a partir do arquivo
                GerenciadorContatos gerenciadorCarregado = GerenciadorContatos.carregarContatos(caminhoArquivo);

                // verifica se o telefone já está em uso por outro contato
                for (Contato c : gerenciadorCarregado.getListaContatos()) {
                    if (c.getTelefone().contains(telefone)) {
                        JOptionPane.showMessageDialog(
                                Main.this,
                                "O telefone " + telefone + " já pertence ao contato: " + c.getNome() +
                                        "\nA criação do novo contato não foi realizada.",
                                "Erro de Validação",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return; // interrompe o processo de salvamento se o telefone estiver duplicado
                    }
                }

                // cria um novo contato com os dados informados
                Contato contato = new Contato(nome, email);

                // valida o telefone antes de salvar
                if (contato.addTelefone(telefone)) {
                    // salva o contato no arquivo
                    GerenciadorContatos.salvarContatoNoArquivo(caminhoArquivo, contato);

                    // limpa os campos após salvar
                    FieldName.setText("");
                    FieldEmail.setText("");
                    FieldTelefone.setText("");

                    // Mostra o diálogo de confirmação com os dados do contato criado
                    JOptionPane.showMessageDialog(
                            null,
                            "Contato criado com sucesso:\n\n" +
                                    "Nome: " + contato.getNome() + "\n" +
                                    "Email: " + contato.getEmail() + "\n" +
                                    "Telefone: " + contato.getTelefone(),
                            "Confirmação de Criação",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    // Mostra uma mensagem de erro se o telefone for inválido
                    JOptionPane.showMessageDialog(
                            Main.this,
                            "Número de telefone inválido! A criação do contato não foi realizada.",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });


// abre a tela de lista de contatos
        listaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Carrega o Gerenciador de Contatos a partir do arquivo
                GerenciadorContatos gerenciadorCarregado = GerenciadorContatos.carregarContatos(caminhoArquivo);

                // Obtém os contatos em ordem alfabética usando o TreeMap
                List<Contato> contatosOrdenados = new ArrayList<>(gerenciadorCarregado.getContatosOrdenados().values());

                // Cria e exibe a janela de lista de contatos com os dados carregados
                ContactsList contactsList = new ContactsList(contatosOrdenados);
                contactsList.setVisible(true);
            }
        });

    }

    public static void main(String[] args) {
        new Main();
    }
}