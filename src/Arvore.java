
public class Arvore implements Comparable<Arvore>{
	public final int freq; // Frequência da árvore
    //
    public Arvore(int freq) { 
    	this.freq = freq; 
    }
    
    // Compara as frequências
    public int compareTo(Arvore arv) {
        return freq - arv.freq;
    }
}
