package main.java.com.trabalhoa3.contatomanager.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.trabalhoa3.contatomanager.model.Contato;
import main.java.com.trabalhoa3.contatomanager.service.GerenciadorContatos;

/**
 * Classe da tela de editar contatos
 */

public class EditContactDialog extends JDialog {
    private JButton salvarButton;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField telefoneField;
    private JPanel Name;
    private JPanel Email;
    private JPanel Telefone;
    private Contato contato;
    private String caminhoArquivo = "src/main/resources/contatos.ser";

    public EditContactDialog(JFrame parent, Contato contato) {
        super(parent, "Editar Contato", true);
        this.contato = contato;

        setContentPane(this.createContentPane());
        setModal(true);

        // Preenche os campos com as informações do contato selecionado
        nameField.setText(contato.getNome());
        emailField.setText(contato.getEmail());
        String telefone = contato.getTelefone().isEmpty() ? "" : contato.getTelefone().iterator().next();
        telefoneField.setText(telefone);

        // Torna o campo de nome não editável
        nameField.setEditable(false);

        // Função do botão Salvar
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contato.setEmail(emailField.getText());

                String novoTelefone = telefoneField.getText();

                // Valida o novo telefone antes de atualizar
                if (contato.addTelefone(novoTelefone)) {
                    contato.getTelefone().clear();
                    contato.addTelefone(novoTelefone);

                    // Atualiza o contato no arquivo
                    GerenciadorContatos.salvarContatoNoArquivo(caminhoArquivo, contato);

                    JOptionPane.showMessageDialog(
                            EditContactDialog.this,
                            "Contato atualizado com sucesso!",
                            "Confirmação",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose(); // Fecha a janela após salvar
                } else {
                    JOptionPane.showMessageDialog(
                            EditContactDialog.this,
                            "Número de telefone inválido! A alteração não foi salva.",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    // Cria o painel principal da janela
    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Name);
        contentPane.add(Email);
        contentPane.add(Telefone);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(salvarButton);
        contentPane.add(buttonPanel);

        return contentPane;
    }
}
