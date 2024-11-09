package main.java.com.trabalhoa3.contatomanager.ui;

import main.java.com.trabalhoa3.contatomanager.model.Contato;
import main.java.com.trabalhoa3.contatomanager.service.GerenciadorContatos;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    String caminhoArquivo = "src/main/resources/contatos.ser";

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

                // cria o objeto contato
                Contato contato = new Contato(nome, email);

                // Valida o telefone antes de salvar
                if (contato.addTelefone(telefone)) {
                    // Salva o contato no arquivo
                    GerenciadorContatos.salvarContatoNoArquivo(caminhoArquivo, contato);

                    // Limpa os campos após salvar
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

                // Obtém a lista de contatos
                List<Contato> contatos = gerenciadorCarregado.getListaContatos();

                // Cria e exibe a janela de lista de contatos com os dados carregados
                ContactsList contactsList = new ContactsList(contatos);
                contactsList.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}