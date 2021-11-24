package entities;

/**
 * *******************************
 * *         BIBLIOTECAS         *
 * *******************************
 */
import entities.enums.PlayerLevel;
import util.Dice;
import java.util.ArrayList;
import java.util.List;
/*********************************/

/*********************
 * Classe do jogador *
 *********************
 *
 * Responsável por administrar qualquer associação que envolve
 * o jogador como seu nome, seu nível como apostador e seu placar.
 * **/

public class Player
{
    /** Membros da classe **/
    private String name;
    private PlayerLevel level;
    private int playerRecord;

    /** COMPOSIÇÃO DE OBJETOS:
     * Aqui começa as associações para o dado,
     * pois um jogador pode escolher vários dados.
     * Detalhe importante: Como aqui temos uma
     * composição "Tem muitos", que no caso é uma lista,
     * não incluímos ela no construtor, apenas instanciamos
     * ela vazia. **/
    private List<Dice> diceList = new ArrayList<>();

    /** Construtor padrão **/
    public Player(){}

    /** Construtor com argumentos **/
    public Player(String name, PlayerLevel level)
    {
        this.name = name;
        this.level = level;
    }

    /** Construtor para escrita no arquivo de placar **/
    public Player(String name, int playerRecord)
    {
        this.name = name;
        this.playerRecord = playerRecord;
    }

    /** Getters e Setters **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerLevel getLevel() {
        return level;
    }

    public void setLevel(PlayerLevel level) {
        this.level = level;
    }

    public int getPlayerRecord() {
        return playerRecord;
    }

    public void setPlayerRecord(int playerRecord) {
        this.playerRecord = playerRecord;
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    /** O método setDiceList é proibido, porque o usuário
     * pode adicionar ou remover um dado da lista, mas JAMAIS
     * substituir ela. O método setDiceList está recebendo
     * OUTRA lista, e essa OUTRA lista está sendo atribuída
     * a lista de dados do jogador, isso NÃO DEVE acontecer! **/
    //public void setDiceList(List<Dice> diceList) {
    //    this.diceList = diceList;
    //}

    /** Métodos por fazer ou desfazer uma associação
     * entre o jogador e o dado **/

    /** Método para adicionar um dado associado ao jogador **/
    public void addDice(Dice dice)
    {
        diceList.add(dice);
    }

    /** Método para remover um dado associado ao jogador **/
    /** Método também PROIBIDO, pois não será possível remover
     * a quantidade de dados durante a execução do jogo, para
     * o nível da aposta não ser afetada. **/
    //public void removeDice(Dice dice)
    //{
    //    diceList.remove(dice);
    //}

    /** Método para calcular a soma de pontos do usuário **/
    public int points()
    {
        int sum = 0;

        /** Armazenando o valor das faces a partir da lista para retornar **/
        /** Para cada dado d na minha lista de dados **/
        for(Dice d : diceList)
        {
            /** Armazeno e somo a face desse dado na variável soma **/
            sum += d.rollDice();
        }

        /** Ao final, retorno essa soma **/
        return sum;
    }
}
