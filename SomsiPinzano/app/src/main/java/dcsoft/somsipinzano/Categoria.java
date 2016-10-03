package dcsoft.somsipinzano;

/**
 * Created by daniele on 03/10/16.
 */

public class Categoria {
    public int idCategoria;
    public String nome;

    @Override
    public String toString() {
        return "\n" +
                "\nidCategoria: " + idCategoria +
                "\nnome: " + nome +
                "\n\n";
    }
}
