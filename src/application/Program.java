package application;

/**
 * *******************************
 * *         BIBLIOTECAS         *
 * *******************************
 */
import entities.Player;
import entities.enums.PlayerLevel;
import exception.DiceException;
import util.Dice;
import util.Game;
import java.text.SimpleDateFormat;
import java.util.*;
/*********************************/

/*********************
 * Programa principal
 *********************
 *
 * Responsável por tratar todas as entradas e saídas,
 * bem como chamada de funções e execução do jogo
 * **/

public class Program
{
    public static void main(String[] args)
    {
        /** Variável para controlar o loop de execução do jogo **/
        char option = 's';

        /** Variável para guardar a quantidade de Vitórias do jogador **/
        int score = 0;

        /** A variável Date representa um tempo, composto por dia, mês e ano.
         * Importante citar aqui que a data não é armazenada por dia, mês e ano.
         * Ela é um número inteiro do tipo long que guarda essa quantidade de tempo. **/
        Date date = new Date();

        /** A classe SimpleDateFormat define formatos para a conversão entre Date e String.
         * Porque quando informamos uma data para um certo sistema, ou quando o sistema vai ler
         * essa data de um banco de dados a partir de um arquivo, ela vai ler um texto. E esse texto,
         * deve ser convertido para um Date. Então para fazer essa conversão, existem várias maneiras.
         * Uma dessas formas é utilizar o SimpleDateFormat.
         * Então nesse exemplo, vou utilizar duas máscaras de formatação: Uma com o padrão dia, mês e ano.
         * Outra com o padrão hora, minuto e segundo. Seria possível utilizar essas duas máscaras de formatação
         * juntas (dd/MM/yyyy HH:mm:ss), porém quero adicionar a palavra "ás" entre as duas máscaras.
         * Para que a letra s não seja confundida com segundos, se faz necessária as duas máscaras. **/

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        /** Abrindo o Scanner do teclado **/
        Scanner sc = new Scanner(System.in);

        /** Apresentação **/
        System.out.println();
        System.out.println("*************************************************************");
        System.out.println("+                    CASA DE APOSTAS LMA                    +");
        System.out.println("*************************************************************");
        System.out.println("+    Bem vindo as apostas do dia " + sdf.format(date) + " ás "+ sdf2.format(date) + "     +"); //Hora atual
        System.out.println("*************************************************************");

        System.out.print("Antes de começar, gostaria de ler as regras do jogo [s/n] ? ");
        char rulesReader = sc.next().toLowerCase(Locale.ROOT).charAt(0);

        /** Loop para verificar opção inválida do usuário **/
        if ((rulesReader != 's')&&(rulesReader != 'n'))
        {
            /** Faça até o usuário digitar uma opção válida **/
            do
            {
                System.out.print("\nEntre apenas com s para sim ou n para não: ");
                rulesReader = sc.next().toLowerCase(Locale.ROOT).charAt(0);
            } while (rulesReader != 's' && rulesReader != 'n');
        }

        if (rulesReader == 's')
        {
            /** Método para ler as regras do jogo **/
            Game.gameRules();
        } else System.out.println("Ok! Vamos começar!");

        System.out.print("Gostaria de saber os números de acertos [s/n] ? ");
        char readScoreboard = sc.next().toLowerCase(Locale.ROOT).charAt(0);

        /** Loop para verificar opção inválida do usuário **/
        if ((readScoreboard != 's')&&(readScoreboard != 'n'))
        {
            /** Faça até o usuário digitar uma opção válida **/
            do
            {
                System.out.print("\nEntre apenas com s para sim ou n para não: ");
                readScoreboard = sc.next().toLowerCase(Locale.ROOT).charAt(0);
            } while (readScoreboard != 's' && readScoreboard != 'n');

        }
        if (readScoreboard == 's')
        {
            /** Método para ler as regras do jogo **/
            Game.ReadScoreboard();
        } else System.out.println("O recorde será seu!");

        sc.nextLine();

        /** Coletar o nome do usuário **/
        System.out.print("\nEntre com o seu nome: ");
        String playerName = sc.nextLine();

        /** Coletar o nível de experiência do usuário **/
        System.out.print("Entre com seu nível (iniciante, intermediario, profissional): ");
        String playerLevel = sc.nextLine().toUpperCase(Locale.ROOT);

        /** Loop para verificar opção inválida do usuário **/
        if ((!playerLevel.equals("INICIANTE"))&&(!playerLevel.equals("INTERMEDIARIO"))&&(!playerLevel.equals("PROFISSIONAL")))
        {
            /** Faça até o usuário digitar uma opção válida **/
            do
            {
                System.out.print("\nEntre apenas com as opções iniciante, intermediario, profissional: ");
                playerLevel = sc.nextLine().toUpperCase(Locale.ROOT);
            } while ((!playerLevel.equals("INICIANTE"))&&(!playerLevel.equals("INTERMEDIARIO"))&&(!playerLevel.equals("PROFISSIONAL")));
        }

        /** Com base em todas as informações, já podemos instanciar esse novo jogador **/
        Player player = new Player(playerName, PlayerLevel.valueOf(playerLevel));
        /** Criando uma instância do tipo enumerado
         * PlayerLevel no valor equivalente ao que foi digitado **/

        /** Loop para coletar a quantidade de dados exigida pelo usuário **/
        System.out.print("Com quantos dados você gostaria de apostar? ");
        int quantityDice = sc.nextInt();

        /** Loop para validar a quantidade de dados mínima para apostar **/
        if (quantityDice < 2)
        {
            /** Faça até o usuário digitar uma opção válida **/
            do
            {
                System.out.println("\nPara apostar é necessário ter no mínimo 2 dados!");
                System.out.print("Com quantos dados você gostaria de apostar? ");
                quantityDice = sc.nextInt();
            } while (quantityDice < 2);
        }

        /** Loop para execução do jogo **/
        while (option == 's')
        {
            /** Bloco de proteção na execução do jogo:
             * Esse bloco é responsável por capturar a excessão
             * que pode ser gerada ao dar uma suspensão na execução
             * para maior imersão no jogo, associando esse delay
             * ao ato de lançar o dado.
             * **/
            try
            {
                System.out.println();
                System.out.println("******************");
                System.out.println("Lançando o dado...");
                System.out.println("******************");
                Thread.sleep(1500);
            }

            /** Captura e lança uma nova exceção personalizada **/
            catch (InterruptedException e){ throw new DiceException("Ops! Erro no lançamento do dado causado por: " + e.getMessage());}

            /** Limpa a lista sempre que o usuário quiser jogar novamente **/
            player.getDiceList().clear();

            /** Loop para percorrer a quantidade de dados solicitado pelo usuário **/
            for (int i = 1; i <= quantityDice; i++)
            {
                /** Instanciando a quantidade de dados desejada pelo usuário **/
                Dice dice = new Dice();
                /** Adicionando o valor das faces na lista **/
                player.addDice(dice);
            }

            /** Bloco para verificação da quantidade de pontos das faces lançadas **/
            System.out.println("Valor das fases: ");
            /** Se o valor das faces forem 7 ou 11 **/
            if (player.points() == 7 || player.points() == 11)
            {
                /** A lista com a quantidade de dados solicitada será percorrida **/
                for(Dice d : player.getDiceList())
                {
                    System.out.print(d.getDiceFace() + " ");
                }
                /** Imprime a vitória do usuário **/
                System.out.println("\nParabéns! Você venceu a aposta!");

                /** Incrementa a variável de recorde **/
                score = score + 1;
                System.out.println("*************************");

                /** Bloco apenas para imprimir a diferença entre "vitória" e "vitórias",
                 * pensando na imersão do jogo.
                 * **/
                if (score == 1)
                {

                    System.out.println("Recorde de " + score + " vitória!");
                }
                else
                {
                    System.out.println("Recorde de " + score + " vitórias!");
                }
                System.out.println("*************************");
            }

            /** Se a pontuação for diferente de 7 ou 11 **/
            else
            {
                /** A lista com a quantidade de dados solicitada será percorrida **/
                for(Dice d : player.getDiceList())
                {
                    System.out.print(d.getDiceFace() + " ");
                }
                /** Imprime a derrota do usuário **/
                System.out.println("\nQue pena! Você perdeu a aposta!");
            }

            /** Perguntar ao usuário se gostaria de jogar novamente **/
            System.out.print("\nDeseja lançar o dado novamente [s/n] ? ");
            option = sc.next().toLowerCase(Locale.ROOT).charAt(0);

            /** Loop para verificar opção inválida do usuário **/
            if ((option != 's')&&(option != 'n'))
            {
                /** Faça até o usuário digitar uma opção válida **/
                do
                {
                    System.out.print("\nEntre apenas com s para sim ou n para não: ");
                    option = sc.next().toLowerCase(Locale.ROOT).charAt(0);
                } while (option != 's' && option != 'n');
            }
            if (option == 's')
            {
                System.out.print("Gostaria de mudar a quantidade de dados [s/n]? ");
                char changeDices = sc.next().toLowerCase(Locale.ROOT).charAt(0);
                /** Loop para verificar opção inválida do usuário **/
                if ((changeDices != 's')&&(changeDices != 'n'))
                {
                    /** Faça até o usuário digitar uma opção válida **/
                    do
                    {
                        System.out.print("\nEntre apenas com s para sim ou n para não: ");
                        option = sc.next().toLowerCase(Locale.ROOT).charAt(0);
                    } while (changeDices != 's' && changeDices != 'n');
                }
                else if(changeDices == 'n')
                {

                }
                else
                {
                    /** Loop para coletar a quantidade de dados exigida pelo usuário **/
                    System.out.print("Com quantos dados você gostaria de apostar? ");
                    quantityDice = sc.nextInt();

                    /** Loop para validar a quantidade de dados mínima para apostar **/
                    if (quantityDice < 2)
                    {
                        /** Faça até o usuário digitar uma opção válida **/
                        do
                        {
                            System.out.println("\nPara apostar é necessário ter no mínimo 2 dados!");
                            System.out.print("Com quantos dados você gostaria de apostar? ");
                            quantityDice = sc.nextInt();
                        } while (quantityDice < 2);
                    }
                    /** Limpa a lista sempre que o usuário quiser jogar novamente **/
                    player.getDiceList().clear();

                    /** Loop para percorrer a quantidade de dados solicitado pelo usuário **/
                    for (int i = 1; i <= quantityDice; i++)
                    {
                        /** Instanciando a quantidade de dados desejada pelo usuário **/
                        Dice dice = new Dice();
                        /** Adicionando o valor das faces na lista **/
                        player.addDice(dice);
                    }
                }
            }

            /** Se o usuário recusar jogar novamente **/
            if (option == 'n')
            {
                /** Definindo o recorde do jogador **/
                player.setPlayerRecord(score);

                /** Chamando a função de escrever o recorde no arquivo placar **/
                Game.writeScoreboard(player);

                /** Imprime o agradecimento juntamente com o seu recorde na partida **/
                System.out.println();
                System.out.println("*************************");
                System.out.println("Muito obrigado por jogar!");

                /** Novamente bloco apenas para imprimir a diferença entre "vitória" e "vitórias",
                 * pensando na imersão do jogo.
                 * **/
                if (score == 1)
                {

                    System.out.println("Recorde de " + score + " vitória!");
                }
                else
                {

                    System.out.println("Recorde de " + score + " vitórias!");
                }
                System.out.println("*************************");
                System.out.println();
                System.out.println("Created by Rafael Aparecido - RA00285591");
            }
        }

        /** Boa prática fechar instâncias abertas **/
        sc.close();
    }
}