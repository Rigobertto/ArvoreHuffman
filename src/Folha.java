
public class Folha extends Arvore{
	public final char valor; // A letra é atribuida a um nó folha 
	 
    public Folha(int freq, char valor) {
        super(freq);
        this.valor = valor;
    }
}
