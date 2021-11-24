package util;

/**
 * *******************************
 * *         BIBLIOTECAS         *
 * *******************************
 */
import entities.Player;
import exception.DiceException;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/*********************************/

/******************
 * Classe do jogo *
 ******************
 *
 * Responsável por controlar qualquer associação "do jogo",
 * como por exemplo as regras "do jogo", escrita do placar "do jogo"
 * e leitura do placar "do jogo"
 * **/

public class Game
{
    /** Variável constante definindo um limite de pontuação **/
    private static final int limit = 1000;

    /** Método para imprimir as regras do jogo **/
    public static void gameRules()
    {
        /** A classe FileReader é um stream (sequência) de leitura de caracteres a partir de arquivos **/
        /** A classe BufferedReader é instanciada a partir do FileReader para implementar otimizações através do buffer de memória **/
        try (BufferedReader br = new BufferedReader(new FileReader("rules.txt")))
        {
            System.out.println();
            /** Atribuição de uma nova linha lida na variável line  **/
            String line = br.readLine();

            /** Enquanto a linha for diferente de nula: **/
            while (line != null)
            {
                /** Imprime essa linha **/
                System.out.println(line);
                /** E é atribuída uma nova linha lida **/
                line = br.readLine();
            }
        }

        /** Captura e lança uma nova exceção personalizada **/
        catch (IOException e)
        {
            throw new DiceException("Ops, não foi possível ler as regras! Certifique-se de que o arquivo esteja na pasta do projeto. Erro causado por: " + e.getMessage());
        }

        System.out.println("Agora que você já sabe as regras, vamos começar!");
        System.out.println();
    }

    /** Método para ler os números de acertos no arquivo externo de placar**/
    public static void ReadScoreboard()
    {

        /** Bloco Try With Resources abrindo o arquivo**/
        try(BufferedReader br = new BufferedReader(new FileReader("scoreboard.txt")))
        {
            /** Instanciando uma lista de jogadores **/
            List<Player> playerList = new ArrayList<>();

            /** Lendo uma linha do arquivo **/
            String line = br.readLine();

            /** Ao ler o placar, se ele estiver vazio é impresso na tela que o jogador é o primeiro **/
            if (line == null)
            {
                System.out.println("Placar vazio. Você é o primeiro!");
            }
            /** Caso contrário, segue a leitura **/
            else
            {
                /** Enquanto a linha for diferente de nulo **/
                while (line != null)
                {
                    /** Recorto essa linha (String) em dois pedaços, com nome e pontos **/
                    String[] fields = line.split(",");

                    /** Instanciando o jogador onde fields[0] será o nome e fields[1] os pontos **/
                    playerList.add(new Player(fields[0], Integer.parseInt(fields[1])));
                    line = br.readLine();
                }

                /** Média de acertos **/
                /** Para coletar a média de acertos que está escrita no arquivo scoreboard.txt,
                 ** é iniciado uma stream, que é uma sequência de elementos advinda de uma fonte de
                 * dados (Coleção, Array, função de iteração, recurso de Estrutura de dados) que oferece
                 * suporte a "operações agregadas".
                 * Então para realizar essa stream é feito um pipeline, que são operações em streams
                 * que retornam novas streams, sendo possível criar uma cadeia de operações **/

                /** Iniciando uma novas stream a partir da lista de jogadores **/
                int mediumPoints = playerList.stream()
                        /** INICIANDO PIPELINE **/
                        /** Aqui a função map vai pegar todas as pontuações utilizando expressão lambda, pois
                         * a lista é composta por jogadores, e queremos pegar apenas as pontuações int.
                         * Ou seja, aqui é criada uma nova stream apenas com as pontuações dos jogadores **/
                        .map(p -> p.getPlayerRecord()) //Jogador p levando em p.getPlayerRecord
                        /** Em posse dessa nova stream, é chamada a função reduce que nos permite
                         * realizar o somatório das pontuações e dividindo pelo tamanho da lista. **/
                        .reduce(0, (x, y) -> x+y) / playerList.size();

                /** Imprime a média de acertos **/
                System.out.println("Média de acertos: " + mediumPoints);

                /** Agora, quero imprimir todas as pontuações contidas no arquivo scoreboard.txt
                 * em ordem decrescente. **/

                /** Comparator do tipo inteiro utilizada para comparar duas streams **/
                Comparator<Integer> comp = (s1, s2) -> s1.compareTo(s2);

                /** Para fazer isso, é necessário fazer uma nova lista de inteiros contendo os pontos. **/
                /** Criando uma stream a partir da lista  **/
                List<Integer> points = playerList.stream()
                        /** INICIANDO PIPELINE **/
                        /** Primeira coisa: Filtrar todas as pontuações menores do que uma pontuação limite declarada **/
                        .filter(p -> p.getPlayerRecord() < limit) /** Todo o jogador p tal que p.getPlayerRecord seja menor que o limite  **/
                        /** A partir dessa stream, vou pegar todas essas pontuações menores que o limite com o map**/
                        .map(p -> p.getPlayerRecord())
                        /** De posse dessa nova stream contendo as pontuações, vou ordenar essa stream com base em order
                         * decrescente utilizando o sorted com default method reversed passando um comparator como argumento **/
                        .sorted(comp.reversed()) /** Obs.: Se passarmos apenas o comparator sem o reversed, irá ser ordenado em ordem crescente **/
                        /** Feito isso, o Collectors.toList irá transformar essa stream em uma lista **/
                        .collect(Collectors.toList());

                /** Imprime as pontuações do placar utilizando um forEach **/
                System.out.println();
                System.out.println("*******************");
                System.out.println("+    Pontuações   +");
                System.out.println("*******************");
                /** Para cada pontuação p da minha lista points **/
                for(int p : points)
                {
                    /** Vou imprimir essa pontuação **/
                    System.out.print("* " + p + ", ");
                }

                /** Impressão dos nomes a partir do placar do arquivo externo scoreboard.txt **/
                /** Criando uma stream a partir da lista **/
                List<String> playersName = playerList.stream()
                        /** INICIANDO PIPELINE **/
                        /** Função map para coletar os nomes dos jogadores **/
                        .map(p -> p.getName())
                        /** A partir dessa nova stream de nomes, o Collectors.toList irá transformar essa stream em uma lista **/
                        .collect(Collectors.toList());

                /** Imprime o nome dos jogadores **/
                System.out.println();
                System.out.println("*******************");
                System.out.println("+    Jogadores    +");
                System.out.println("*******************");
                /** Para cada String nome na minha lista de nomes dos jogadores **/
                for(String name : playersName)
                {
                    /** Vou imprimir esse nome **/
                    System.out.print("* " + name + ", ");
                }
                System.out.println();

                /** Após, destruo todas as listas **/
                playerList.clear();
                points.clear();
                playersName.clear();
            }

            /** Captura e lança uma nova exceção personalizada **/
        } catch (IOException e)
        {
            throw new DiceException(e.getMessage());
        }
    }

    /** Método para escrever o recorde do jogador no placar **/
    public static void writeScoreboard(Player player)
    {
        /** Bloco Try With Resources para abrir e escrever no arquivo.
         * Obs.: O append como true informa de que não queremos que seja criado
         * um novo arquivo sempre que essa função de escrita for chamada,
         * porque não queremos um novo placar a cada partida, ele
         * deverá ser único. **/
        /** A classe FileReader é um stream (sequência) de leitura de caracteres a partir de arquivos **/
        /** A classe BufferedReader é instanciada a partir do FileReader para implementar otimizações através da memória temporária **/
        try (BufferedWriter write = new BufferedWriter(new FileWriter("scoreboard.txt", true)))
        {
            /** Escreve o nome do jogador e o seu recorde **/
            write.write(player.getName() + "," + player.getPlayerRecord());
            /** Pula uma nova linha **/
            write.newLine();

            /** Captura e lança uma nova exceção personalizada **/
        } catch (IOException e)
        {
            throw new DiceException(e.getMessage());
        }
    }
}
