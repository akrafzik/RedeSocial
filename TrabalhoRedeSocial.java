/*
 Trabalho de Pesquisa operacional
 Alec Krafzik Haroutiounian
 */
package trabalhoredesocial;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class TrabalhoRedeSocial {

    public static void main(String[] args) {
        boolean menu = true;
        Scanner entrada = new Scanner(System.in); //Declaração do Scanner
        User[] Usuario = new User[50]; //Definição da lista de usuários
        for (int i = 0; i < Usuario.length; i++) { //inicialização de vetor usuário
            Usuario[i] = new User();
            Usuario[i].setNome(null);
            Usuario[i].setIdade(0);
        }
        int[][] MatrizAdj = new int[50][50]; //Definição da lista de adjacência das conexões
        for (int i = 0; i < MatrizAdj.length; i++) { //Inicialização e zerar a matriz de adjacência
            for (int j = i; j < MatrizAdj[i].length; j++) {
                MatrizAdj[i][j] = 0;
            }
        }
        while (menu == true) { //Menu inicial da rede social
            System.out.println("Bem vindo ao FaceCookie - Menu inicial \n"
                    + "Digite 1 para cadastrar um usuário. \n"
                    + "Digite 2 para conectar dois usuário. \n"
                    + "Digite 3 para apagar um usuário. \n"
                    + "Digite 4 para saber quais são as conexões de um usuario. \n"
                    + "Digite 5 para saber a distância de um usuário à outro. \n"
                    + "Digite 6 para construir uma arvore mínima de conexão entre todos os usuários. \n"
                    + "Digite 7 para saber o status da rede social \n"
                    + "Digite 8 para sair da rede social");
            switch (entrada.nextInt()) {
                case 1: // Cadastro do usuário
                    System.out.println("Digite o nome do usuário ");
                    entrada.nextLine();
                    String nomeUsuarioAdc = entrada.nextLine(); //Entrada do nome do usuário
                    System.out.println("Digite a idade do usuário");
                    int idadeUsuarioAdc = entrada.nextInt(); //Entrada da idade do usuário
                    System.out.println(AdicionarUsuario(Usuario, nomeUsuarioAdc, idadeUsuarioAdc));
                    break;
                case 2: // Conectar Dois usuários
                    System.out.println("Digite o nome do primeiro usuário");
                    String nomeUsuario1 = entrada.next(); //Entrada nome do primeiro usuario
                    System.out.println("Digite o nome do segundo usuário");
                    String nomeUsuario2 = entrada.next(); //Entrada nome do segundo usuário
                    System.out.println("Digite a quantidade de anos de amizade entre os dois usuarios");
                    int idadeAmizade = entrada.nextInt(); //Entrada idade de amizade
                    System.out.println(ConectarUsuarios(MatrizAdj, Usuario, nomeUsuario1, nomeUsuario2, idadeAmizade));
                    break;
                case 3: //Apagar um usuário
                    System.out.println("Digite o nome do usuário a ser apagado");
                    String NomeUsuarioApag = entrada.next(); //Entrada do nome do usuario a ser apagado
                    boolean UsuarioApagEnc = false; //Verificação se o usuario foi encontrado
                    System.out.println(ApagarUsuario(MatrizAdj, Usuario, NomeUsuarioApag));
                    break;
                case 4: //Impressão de amizades de um usuário
                    System.out.println("Digite o nome do usuário que deseja saber as amizades");
                    String NomeUsuarioStalk = entrada.next(); //Entrada nome do usuario que será verificado as amizades
                    System.out.println(AmizadesUmUsuario(MatrizAdj, Usuario, NomeUsuarioStalk));
                    break;
                case 5: //Busca de ditãncia entre dois nós
                    System.out.println("digite o nome do usuário que deseja partir a busca de distância");
                    String NomeUsuarioInic = entrada.next(); //entrada nome inicial
                    System.out.println("Digite o nome do usuario que deseja saber a distância");
                    String NomeUsuarioFin = entrada.next(); // entrada nome final
                    System.out.println(NosDeDistancia(MatrizAdj, Usuario, NomeUsuarioInic, NomeUsuarioFin));
                    break;
                case 6: // Arvore Minima
                    System.out.println(ArvoreKruskal(MatrizAdj));
                    break;
                case 7: /*quantos usuários cadastrados, quantas conexões rede, quais pessoas conectadas e somatório dos pesos das arestas.*/

                    StatusRedeSocial(MatrizAdj, Usuario);
                    break;
                case 8: //Sair do programa
                    menu = SairMenu();
                    break;
                default: //Entrada inválida
                    System.out.println("Entrada Inválida");
            }
        }

    }

    public static int[] BuscaLarguraKruskal(int[][] matriz, int verticeInicial) { //Codigo Buscar Largura Professor

        ArrayList<Integer> fila = new ArrayList<>(); //Novo Array para a fila
        int distancia[] = new int[matriz.length]; //Array para as distãncias
        boolean naFila[] = new boolean[matriz.length]; //Variavel booleana para saber se o vertice analisado já está na fila

        fila.add(verticeInicial); //Vertice inical adicionado a fila
        distancia[verticeInicial] = 0; //Distãncia para o vertice inicia definida como zero por se ele mesmo
        naFila[verticeInicial] = true; //Definição de que o vertifice inicia está na fila

        while (!fila.isEmpty()) { //enquanto a fila não estiver vazia

            int verticeAnalisado = fila.get(0);
            fila.remove(0);
            for (int i = 0; i < matriz[verticeAnalisado].length; i++) { //Enquanto não percorrer a matriz toda
                if (matriz[verticeAnalisado][i] != 0 && !naFila[i]) { //Se a matriz retornar algo maior que zero e se o vetor não estiver na fila
                    fila.add(i); //Fila recebe o vertice de i
                    distancia[i] = distancia[verticeAnalisado] + 1; //Distância do vertice i passa a ser a distância do vertice analisado + 1
                    naFila[i] = true; //Vertice analisado recebe condição booleana de estar na fila
                }
            }
        }
        return distancia; //Retorna vetor com a distãncia de todos os vertices para o vertice inicial

    }

    public static String AdicionarUsuario(User[] Usuario, String nome, int idade) {
        boolean VerificEntrada = false; //verificador se a entrada do usuário foi feita
        String retorno;
        for (int a = 0; a < Usuario.length && VerificEntrada == false; a++) { //Comparação se ja houve entrada e se ja percorrei todo o vetor
            if (Usuario[a].getNome() == null) { //Se econtrar um espaço no Vetor
                Usuario[a].setNome(nome); //Entrada do nome inserido
                Usuario[a].setIdade(idade); //Entrada da idade inserida
                VerificEntrada = true; //Verificador de entrada passa a ser verdadeiro

            }
        }
        if (VerificEntrada == false) { //Caso a rede social esteja cheia, mensagem via impressão
            retorno = "Rede Social Cheia, fazer excluir um usuário da rede social antes de adicionar um novo.";
        } else {
            retorno = "Usuário Adicionado com Sucesso."; //Confirmação de entrada por impressão
        }

        return retorno;
    }

    public static String ConectarUsuarios(int[][] matriz, User[] Usuario, String NomeUsu1, String NomeUsu2, int idadeAmizade) {
        String retorno;
        boolean Usu1enc = false; //Status se encontrou o primeiro usuario
        int loc1enc = 0; //Local do vetor onde encontrou o usuario
        boolean Usu2enc = false; // Status se encontrou o segundo usuario
        int loc2enc = 0; //Local onde encontou o segundo usuario no vetor
        for (int b = 0; b < matriz.length && Usu1enc == false; b++) { //Busca do primeiro usuário no vetor
            if (Usuario[b].getNome().equalsIgnoreCase(NomeUsu1)) {
                loc1enc = b; //Marca o local onde foi encontrado
                Usu1enc = true; //Status de encontro passa a ser verdadeiro
                System.out.println("Usuário 1 encontrado"); //Confirmação via impressão
            }
        }
        for (int b = 0; b < Usuario.length && Usu2enc == false; b++) { //Busca do Segundo usuário no Vetor
            if (Usuario[b].getNome().equalsIgnoreCase(NomeUsu2)) {
                loc2enc = b; //Marco o local onde foi encontrado
                Usu2enc = true; //Status de encontro passa a ser verdadeiro
                System.out.println("Usuário 2 encontrado"); //Confirmação via impressão
            }
        }
        if (Usu1enc == false) {
            retorno = "Usuario 1 não encontrado"; //Resposta em caso negativo para a busca
        } else if (Usu2enc == false) {
            retorno = "Usuário 2 não encontrado"; //Reposta em caso negativo para a busca
        } else {
            matriz[loc1enc][loc2enc] = idadeAmizade; //Peso da idade é vunculada a posição dos vetores na matriz adijacente
            matriz[loc2enc][loc1enc] = idadeAmizade; //Peso da idade é vinculada a posição inversa dos vetores na matriz adjacente
            retorno = "Conexão Efetuada"; //Confirmação de conexão via impressão
        }
        return retorno;
    }

    public static String RemoverUsuario(int[][] MatrizAdj, User[] Usuario, String NomeUsu) {
        String retorno;
        boolean UsuarioApagEnc = false; //Verificação se o usuario foi encontrado
        int locUsuApagEnc = 0; //Local no vetor onde o usuario foi encontrado
        for (int i = 0; i < Usuario.length && UsuarioApagEnc == false; i++) { //Veirifica se o usuario ja foi encotrado ou se rodou o vetor todo
            if (Usuario[i].getNome().equalsIgnoreCase(NomeUsu)) { //Se encontrar o usuario
                locUsuApagEnc = i; //Salva a posição do vetor
                Usuario[i].setNome(null); //Limpa o nome no vetor
                Usuario[i].setIdade(0); //Limpa a idade no Vetor
                UsuarioApagEnc = true; //Verificação de apagado passa a ser verdadeiro
            }
        }
        if (UsuarioApagEnc == false) {
            retorno = "Usuario não encontrado"; //Caso no encontre o usuario mensagem aparece
        } else {
            for (int i = 0; i < Usuario.length; i++) { //i que vai de 0 a 49 para rodar as posições da Matriz
                MatrizAdj[locUsuApagEnc][i] = 0; //Limpeza de Conexões na Coluna do vetor apagado
                MatrizAdj[i][locUsuApagEnc] = 0; //limpeza de Conexões na Linha do vetor apagado
            }
            retorno = "Usuário Apagado"; //Confirmação por mensagem de que o usuario foi apagado
        }
        return retorno;
    }

    public static String ApagarUsuario(int[][] MatrizAdj, User[] Usuario, String NomeUsu) {
        String retorno;
        boolean UsuarioApagEnc = false; //Verificação se o usuario foi encontrado
        int locUsuApagEnc = 0; //Local no vetor onde o usuario foi encontrado
        for (int i = 0; i < Usuario.length && UsuarioApagEnc == false; i++) { //Veirifica se o usuario ja foi encotrado ou se rodou o vetor todo
            if (Usuario[i].getNome().equalsIgnoreCase(NomeUsu)) { //Se encontrar o usuario
                locUsuApagEnc = i; //Salva a posição do vetor
                Usuario[i].setNome(null); //Limpa o nome no vetor
                Usuario[i].setIdade(0); //Limpa a idade no Vetor
                UsuarioApagEnc = true; //Verificação de apagado passa a ser verdadeiro
            }
        }
        if (UsuarioApagEnc == false) {
            retorno = "Usuario não encontrado"; //Caso no encontre o usuario mensagem aparece
        } else {
            for (int i = 0; i < Usuario.length; i++) { //i que vai de 0 a 49 para rodar as posições da Matriz
                MatrizAdj[locUsuApagEnc][i] = 0; //Limpeza de Conexões na Coluna do vetor apagado
                MatrizAdj[i][locUsuApagEnc] = 0; //limpeza de Conexões na Linha do vetor apagado
            }
            retorno = "Usuário Apagado"; //Confirmação por mensagem de que o usuario foi apagado
        }
        return retorno;
    }

    public static String AmizadesUmUsuario(int[][] MatrizAdj, User[] Usuario, String NomeUsu) {
        System.out.println("Amizades de:" + NomeUsu + " são: "); //Texto mostrando qual usuario foi selecionado
        String retorno = "";
        boolean UsuarioEnc = false; //Verificação se o usuário foi encontrado
        int locUsuEnc = 0; //Local no vetor onde o usuario foi encontrado
        for (int i = 0; i < Usuario.length && UsuarioEnc == false; i++) { //Verificação se ele foi encontrado ou se o vetor todo foi percorrido
            if (Usuario[i].getNome().equalsIgnoreCase(NomeUsu)) { //Comparação de nomes
                locUsuEnc = i; //Local é onde o usuario foi encontrado é armazenado
                UsuarioEnc = true; //Sinalizador de busca é dado como verdadeiro
            }
        }
        if (UsuarioEnc == false) {
            retorno = "Usuario não encontrado"; //Caso usuario não encontrado a mensagem é exibida na tela
        } else {
            for (int i = 0; i < Usuario.length; i++) { //i percorrendo o tamanho do vetor
                if (MatrizAdj[locUsuEnc][i] != 0) { //Como ja sabemos em qual linha o nosso usuario está na matriz temos que percorrer todos as posições e encontrar idades de amizade diferentes de zero
                    retorno = retorno + Usuario[i].getNome() + "\n";
                }
            }

        }
        return retorno;
    }

    public static void StatusRedeSocial(int[][] MatrizAdj, User[] Usuario) {
        int qntUsuario = 0;  //Variavel para contar a quantidade de usuarios
        for (int i = 0; i < Usuario.length; i++) { //Percore o vetor de usuarios
            if (Usuario[i].getNome() != null) {
                qntUsuario = qntUsuario + 1; //Soma 1 quando o nome é diferente de nulo
            }
        }
        System.out.println("O numero de usuarios na rede social é: " + qntUsuario); //Impressão da quanidade de usuarios

        int qntConex = 0; //Variavel para quantidade de conexões
        for (int j = 0; j < Usuario.length; j++) { //Percorre a auxiliar j no tamanho do vetor de usuarios
            for (int k = j; k < Usuario.length; k++) { //Percorre k deconsiderando as linhas de J já percorridas
                if (MatrizAdj[j][k] != 0) { //Se o numero na posição da matriz for diferente de 0
                    qntConex = qntConex + 1; //Soma 1 na quanidade de conexões
                }
            }

        }
        System.out.println("A quantidade de Conexões na rede social é: " + qntConex); //Impressão da quantidade de Conexões
        int Usuario1; //Variavel para o primeiro usuário
        int Usuario2; //Variavel para o segundo usuário
        for (int j = 0; j < Usuario.length; j++) { //Percorre a auxiliar j no tamanho do vetor de usuarios
            for (int k = j; k < Usuario.length; k++) { //Percorre k deconsiderando as linhas de J já percorridas
                if (MatrizAdj[j][k] != 0) { //Se o numero na posição da matriz for diferente de 0
                    Usuario1 = j; //Pega posição do primeiro usuário
                    Usuario2 = k; //Pega posição do segundo usuário
                    System.out.println("Existe uma amizade entre: " + Usuario[j].getNome() + " e " + Usuario[k].getNome()); //Impressão da amizade localizada
                }
            }
        }
        int SomaAresta = 0; //Variavel para a soma das arestas
        for (int j = 0; j < Usuario.length; j++) { //Percorre a auxiliar j no tamanho do vetor de usuarios
            for (int k = j; k < Usuario.length; k++) { //Percorre k deconsiderando as linhas de J já percorridas
                if (MatrizAdj[j][k] != 0) { //Se o numero na posição da matriz for diferente de 0
                    SomaAresta = SomaAresta + MatrizAdj[j][k]; //é somado o peso da aresta
                }
            }
        }
        System.out.println("A soma dos pesos das arestas é: " + SomaAresta); //Impressao da soma dos pesos das arestas
    }

    public static boolean SairMenu() {
        System.out.println("Obrigado por usar o FaceCookie");
        return false; //retorno de booleano false para finalizar o programa
    }

    public static String NosDeDistancia(int[][] MatrizAdj, User[] Usuario, String NomeUsu1, String NomeUsu2) {
        String retorno;
        boolean UsuEnc1 = false; //Boolean para encotro do primeiro usuario
        boolean UsuEnc2 = false; //Bolean para encontro do segundo usuario
        int LocUser1 = 0; //Int vertice usuario
        int LocUser2 = 0; // int vertice do usuario
        for (int i = 0; i < Usuario.length && UsuEnc1 == false; i++) { //percorre o vetor de usuarios
            if (Usuario[i].getNome().equalsIgnoreCase(NomeUsu1)) { //procura usuario com o mesmo nome do requisitado
                LocUser1 = i; //localização passa a ser a posição onde foi encontrado
                UsuEnc1 = true; //posição passa a ser verdadeira
            }
        }
        for (int i = 0; i < Usuario.length && UsuEnc2 == false; i++) { //percorre o vetor de usuarios
            if (Usuario[i].getNome().equalsIgnoreCase(NomeUsu2)) { //procura usuario com o mesmo nome do requisitado
                LocUser2 = i; //localização passa a ser a posição onde foi encontrado
                UsuEnc2 = true; //posição passa a ser verdadeira
            }
        }
        if (UsuEnc1 == false) { //se não encontrou o primeiro usuario
            retorno = "Usuario inicial não encontrado"; //mensagem de que o primeiro usuario nao foi encontrado
        } else if (UsuEnc2 == false) { // se não encontrou o segundo usuário
            retorno = "Usuario final não encontrado"; //mensagem de que o segundo usuario nao foi encontrado
        } else {

            int DistInic[] = new int[Usuario.length]; //vetor de distancia usa a distãncia do vetor de usuarios
            DistInic = BuscaLarguraKruskal(MatrizAdj, LocUser1); //Usa a função de busca em largura de Kruskal
            if (DistInic[LocUser2] == 0) { // se o vetor de distãncia na posição do usuario final der 0
                retorno = "Estes usuários não possuem amigos em comum"; //mensagem de que não tem como esses usuarios serem conectados
            } else { //case seja diferente de 0
                retorno = "A distãncia entre " + Usuario[LocUser1].getNome() + " e " // retono com a quantidade de nós entre A e B
                        + Usuario[LocUser2].getNome() + " é de "
                        + DistInic[LocUser2] + " nó(s).";
            }
        }
        return retorno;
    }

    public static boolean VerificaCiclo(int[][] matriz, List<Integer> nos, int i, int j) {

        if (nos.contains(i) && nos.contains(j)) { //se o nós tiver o conteudo de i e j
            int[] dist = BuscaLarguraKruskal(matriz, i); // é feita uma busca em largura usando i
            if (dist[j] == 0) { // se a distancia de j for igual a 0
                return false; //retorna falso
            }
            return true; // se for dirente retorna verdadeiro
        } else {
            return false; //se os nós não tiverem os conteudos de i e j retorna falso
        }
    }

    public static String ArvoreKruskal(int[][] MatrizAdj) {

        String arvore = " "; //inicar String arvore
        List<Integer> nos = new ArrayList<>(); //inicio da lista de nós
        int[][] novaMatriz = new int[MatrizAdj.length][MatrizAdj.length]; // definição de nova matriz para receber a menor arvore

        for (int k = 0; k < MatrizAdj.length - 1; k++) { // percorre a quantidade de espaços
            int menorPeso = 30; //numero maximo para peso de aresta para ser considerado
            int iMenorPeso = 0; // definição de variavel para receber i
            int jMenorPeso = 0; // definição de variavel para rebecer j
            for (int i = 0; i < MatrizAdj.length; i++) { //percorre em i o tamanho da matriz
                for (int j = i; j < MatrizAdj[i].length; j++) { //percorre em j todas as colunas em que i ainda não foi percorrida
                    if (MatrizAdj[i][j] != 0 // se a saida da matriz[i][j] for direrente de 0
                            && MatrizAdj[i][j] < menorPeso // saida da matriz for menor que o peso a ser considerado
                            && !VerificaCiclo(novaMatriz, nos, i, j)) { //verifica o ciclo se retorna verdadeiro ou false
                        menorPeso = MatrizAdj[i][j]; //menor peso recebe o valor de posição ij
                        iMenorPeso = i; // variavel recebe i
                        jMenorPeso = j; // variavel recebe j
                    }
                }
            }
            arvore = arvore + "[" + iMenorPeso + "," + jMenorPeso + "], "; //arvore recebe as posições que tem a ligação de menor peso
            nos.add(iMenorPeso); // adiciona ao no a posição i de menor peso
            nos.add(jMenorPeso); //adiciona ao no a posição j de menor peso
            novaMatriz[iMenorPeso][jMenorPeso] = 1; //recebe a nova matriz na posições i j que já foi verificado
            novaMatriz[jMenorPeso][iMenorPeso] = 1; //recebe a nova matriz na posições i j que já foi verificado
        }
        return arvore;

    }

}
