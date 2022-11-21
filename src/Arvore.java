
public class Arvore implements Comparable<Arvore>{
	public final int freq; // Frequ�ncia da �rvore
    //
    public Arvore(int freq) { 
    	this.freq = freq; 
    }
    
    // Compara as frequ�ncias
    public int compareTo(Arvore arv) {
        return freq - arv.freq;
    }
}
