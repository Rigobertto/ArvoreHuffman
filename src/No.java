
public class No extends Arvore{
	
	public final Arvore esq, dir; // sub-�rvores
	 
    public No(Arvore esq, Arvore dir) {
        super(esq.freq + dir.freq);
        this.esq = esq;
        this.dir = dir;
    }
}
