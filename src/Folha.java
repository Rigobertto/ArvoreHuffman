
public class Folha extends Arvore{
	public final char valor; // A letra � atribuida a um n� folha 
	 
    public Folha(int freq, char valor) {
        super(freq);
        this.valor = valor;
    }
}
