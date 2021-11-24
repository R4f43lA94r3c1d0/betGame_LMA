package exception;

/***********************************
 * Classe de Exceção personalizada *
 ***********************************
 *
 * Responsável por tratar possíveis exceções
 * no jogo do dado, lançando uma exceção personalizada
 * através da RuntimeException
 * **/

public class DiceException extends RuntimeException
{
    /**
     * Controle de versionamento: Ele identifica se a versão do objeto é compatível com
     * a versão da classe que a serializou. Aqui estamos trabalhando com uma classe que
     * acabou de ser criada, e temos certeza de que não há nenhum objeto serializado
     * para ela, então a versão é 1
     * **/
    private static final long serialVersionUID = 1L;

    public DiceException(String msg)
    {
        /** Passando a mensagem do erro para a classe mãe **/
        super(msg);
    }
}
