
public class No extends Arvore{
	
	public final Arvore esq, dir; // sub-árvores
	 
    public No(Arvore esq, Arvore dir) {
        super(esq.freq + dir.freq);
        this.esq = esq;
        this.dir = dir;
    }
}
