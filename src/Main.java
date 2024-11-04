public class Main {
    public static void main(String[] args) {
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
        Contato encontrado = gerenciador.buscarContato("Bruno");
        System.out.println(encontrado != null ? encontrado : "Contato não encontrado");

        // removendo um contato por nome
        System.out.println("\nRemovendo contato 'Alice':");
        gerenciador.removerContato("Alice");

        // listando contatos após a remoção
        System.out.println("\nContatos após a remoção:");
        gerenciador.listarContatosOrdenados();
    }
}
