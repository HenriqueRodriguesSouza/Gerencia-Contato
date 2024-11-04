import java.util.*;

public class GerenciadorContatos {
    // estrutura de dados para armazenamento
    private ArrayList<Contato> listaContatos;
    private TreeMap<String, Contato> contatosOrdenados;

    // construtor
    public GerenciadorContatos() {
        this.listaContatos = new ArrayList<>();
        this.contatosOrdenados = new TreeMap<>();
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
        System.out.println("Contato " + contato.getNome() + " adicionado com sucesso!");
    }


    //metodo para remover contato pelo email
    public void removerContato(String nome) {
        Contato contatoRemovido = contatosOrdenados.remove(nome);
        if (contatoRemovido != null) {
            listaContatos.remove(contatoRemovido);
            System.out.println("Contato removido: " + nome);
        } else {
            System.out.println("Contato com o nome '" + nome + "' não encontrado.");
        }
    }

    //metodo buscar contato pelo nome
    public Contato buscarContato(String nome) {
        return contatosOrdenados.get(nome);
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
