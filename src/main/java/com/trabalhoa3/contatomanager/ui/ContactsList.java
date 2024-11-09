package main.java.com.trabalhoa3.contatomanager.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import main.java.com.trabalhoa3.contatomanager.model.Contato;
import main.java.com.trabalhoa3.contatomanager.service.GerenciadorContatos;

public class ContactsList extends JFrame {
    private JPanel contactsListPanel;
    private JList<Contato> contactsList;
    private JButton editButton;
    private JButton deleteButton;
    private JButton clearButton; // Novo botão de limpar lista
    private JTextField searchField;
    private JButton searchButton;
    private DefaultListModel<Contato> listModel;
    private List<Contato> allContacts;
    String caminhoArquivo = "src/main/resources/contatos.ser";

    public ContactsList(List<Contato> contatos) {
        super("Lista de Contatos");
        this.allContacts = contatos;

        // Inicializa o painel e configura layout
        contactsListPanel = new JPanel(new BorderLayout());

        // Cria modelo para a JList com os contatos
        listModel = new DefaultListModel<>();
        contatos.forEach(listModel::addElement);

        // Configura a JList com o modelo
        contactsList = new JList<>(listModel);
        contactsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(contactsList);
        contactsListPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel para barra de pesquisa
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Pesquisar");

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        contactsListPanel.add(searchPanel, BorderLayout.NORTH);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        editButton = new JButton("Editar");
        deleteButton = new JButton("Excluir");
        clearButton = new JButton("Limpar Lista"); // Novo botão de limpar lista

        // Desativa os botões inicialmente
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Adiciona os botões ao painel de botões
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton); // Adiciona o botão "Limpar Lista" ao painel
        contactsListPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Listener para habilitar/desabilitar botões com base na seleção
        contactsList.addListSelectionListener(e -> {
            boolean isSelected = contactsList.getSelectedIndex() != -1;
            editButton.setEnabled(isSelected);
            deleteButton.setEnabled(isSelected);
        });

        // Configurações da janela
        setContentPane(contactsListPanel);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ActionListener para o botão Pesquisar
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText().trim();
            if (!searchQuery.isEmpty()) {
                filterContacts(searchQuery);
            } else {
                showAllContacts(); // Mostra todos os contatos se a pesquisa estiver vazia
            }
        });

        // ActionListeners para os botões
        editButton.addActionListener(e -> {
            Contato selectedContact = contactsList.getSelectedValue();
            if (selectedContact != null) {
                editarContato(selectedContact);
            }
        });

        deleteButton.addActionListener(e -> {
            Contato selectedContact = contactsList.getSelectedValue();
            if (selectedContact != null) {
                excluirContato(selectedContact);
            }
        });

        // ActionListener para o botão Limpar Lista
        clearButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Deseja realmente limpar toda a lista de contatos?",
                    "Limpar Lista", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                GerenciadorContatos.limparArquivo(caminhoArquivo);
                listModel.clear(); // Limpa a lista exibida
                allContacts.clear(); // Limpa a lista de contatos carregada
                JOptionPane.showMessageDialog(this, "Lista de contatos limpa com sucesso.");
            }
        });
    }

    // Métodos para editar, excluir, filtrar e atualizar a lista (mantidos conforme o código anterior)
    private void editarContato(Contato contato) {
        EditContactDialog editDialog = new EditContactDialog(this, contato);
        editDialog.setVisible(true);
        contactsList.repaint();
    }

    private void excluirContato(Contato contato) {
        int response = JOptionPane.showConfirmDialog(this, "Deseja excluir o contato " + contato.getNome() + "?",
                "Excluir Contato", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            ((DefaultListModel<Contato>) contactsList.getModel()).removeElement(contato);
            GerenciadorContatos.removerContatoDoArquivo(caminhoArquivo, contato.getTelefone().iterator().next());
            JOptionPane.showMessageDialog(this, "Contato excluído: " + contato.getNome());
        }
    }

    private void filterContacts(String query) {
        List<Contato> filteredContacts = allContacts.stream()
                .filter(contato -> contato.getNome().toLowerCase().contains(query.toLowerCase())
                        || contato.getTelefone().stream().anyMatch(tel -> tel.contains(query)))
                .collect(Collectors.toList());

        updateContactList(filteredContacts);
    }

    private void updateContactList(List<Contato> contatos) {
        listModel.clear();
        contatos.forEach(listModel::addElement);
    }

    private void showAllContacts() {
        updateContactList(allContacts);
    }
}