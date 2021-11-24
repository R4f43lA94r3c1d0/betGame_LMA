package util;

/********************
 *   Classe do dado *
 ********************
 *
 * Responsável por representar o dado, a sua face
 * e o ato de lançar o dado
 * **/

public class Dice
{
    int diceFace;

    /** Construtor padrão **/
    public Dice(){}

    /** Construtor com argumentos **/
    public Dice(int diceFace)
    {
        this.diceFace = diceFace;
    }

    /** Getters and Setters **/
    public int getDiceFace()
    {
        return diceFace;
    }

    /** Método de setar privado pois não será permitidas
     * modificações no valor do dado fora desta classe **/
    private void setDiceFace(int diceFace)
    {
        this.diceFace = diceFace;
    }

    /** Método para rolar o dado **/
    public int rollDice ()
    {
        /** Setando o dado com um valor aleatório na
         *  faixa de 0 a 5 acrescentando + 1 **/
        setDiceFace((int) (Math.random() * 6 + 1));

        /** Retornando o valor do dado **/
        return getDiceFace();
    }
}
