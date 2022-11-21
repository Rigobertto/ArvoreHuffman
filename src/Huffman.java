import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {

	/*
	 * Implementa��o do Algoritmo de Huffman Compacta��o um texto
	 */
	static Scanner in = new Scanner(System.in);

	private static void limparBuffer(Scanner scanner) {
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}

	public static void main(String[] args) {
		int n = 0;
		do {
			System.out.println("1 � Comprimir texto\r\n" + "0 � Sair");
			System.out.print("Digite: ");
			n = in.nextInt();
			limparBuffer(in);
			if (n == 1) {
				// Texto para Compactar  Algoritmo de Huffman
				String test; 
				System.out.print("Digite a String: ");
				test = in.nextLine();
				

				// Neste exemplo ser� considerado que teremos no m�ximo 256 caracteres
				// diferentes
				// Passo 1 - Percorre o texto contando os s�mbolos e montando um vetor de
				// frequ�ncias.
				int[] charFreqs = new int[256];
				for (char c : test.toCharArray())
					charFreqs[c]++;

				// Criar a �rvore dos c�digos para a Compacta��o
				Arvore tree = construirArvore(charFreqs);

				// Resultados das quantidade e o c�digo da Compacta��o
				System.out.println("\n======================TABELA==========================");
				System.out.println("CARACTERE\tQUANTIDEDE\tC�DIGO\t\tBITS");
				imprimir(tree, new StringBuffer());

				// Compactar o texto
				System.out.println();
				String codigo = codificar(tree, test);

				// Mostrar o texto Compactado
				System.out.print("\nTexto Compactado: ");
				System.out.println(codigo);
				
				//Mostrar quantidade de bits do texto original
				System.out.println("\nQuantidade de bits do texto: " + test.getBytes().length * 8);
				
				//Mostrar quantidade de bits usados na decodifica��o
				System.out.println("Quantidade de Bits usados para codificar: " + codigo.length());
				Double taxa = ((double) codigo.length() / (double) (test.getBytes().length * 8)) * 100;
				
				//Mostrar a taxa de compress�o
				System.out.println("Taxa de compress�o: " + taxa + "%");
				System.out.println();

				System.out.println("========================================================");
				System.out.println();
			} else if (n == 0) {
				System.out.println("Programa Encerrado");

			} else {
				System.out.println("Digite um valor v�lido!");
			}
		} while (n != 0 || n == 1);

	}

	/*
	 * Criar a �rvore de Codifica��o - A partir da quantidade de frequ�ncias de cada
	 * letra cria-se uma �rvore bin�ria para a compacta��o do texto Par�metro de
	 */
	public static Arvore construirArvore(int[] charFreqs) {
		// Cria uma Fila de Prioridade
		// A Fila ser� criado pela ordem de frequ�ncia da letra no texto
		PriorityQueue<Arvore> trees = new PriorityQueue<Arvore>();
		
		for (int i = 0; i < charFreqs.length; i++) {
			if (charFreqs[i] > 0)
				trees.offer(new Folha(charFreqs[i], (char) i)); // Inser os elementos, n� folha, na fila de
																// prioridade
		}
		// Percorre todos os elementos da fila
		// Criando a �rvore bin�ria de baixo para cima
		while (trees.size() > 1) {
			// Pega os dois n�s com menor frequ�ncia
			Arvore a = trees.poll(); //Retorna o pr�ximo n� da Fila ou NULL se n�o tem mais
			Arvore b = trees.poll(); 

			// Criar os n�s da �rvore bin�ria
			trees.offer(new No(a, b));
		}
		// Retorna o primeiro n� da �rvore
		return trees.poll();
	}

	/*
	 * COMPACTAR a string Par�metros de Entrada: tree - Raiz da �rvore de
	 * compacta��o encode - Texto original Par�metros de Sa�da: encodeText- Texto
	 * Compactado
	 */
	public static String codificar(Arvore tree, String encode) {
		String encodeText = "";
		if (tree != null) {
			for (char c : encode.toCharArray()) {
				encodeText += (codCompactado(tree, new StringBuffer(), c));

			}
		}

		return encodeText; // Retorna o texto Compactado
	}

	/*
	 * M�todo para percorrer a �rvore e mostra a tabela de compacta��o 
	 */
	
	public static void imprimir(Arvore tree, StringBuffer prefixo) {
		if (tree != null) {

			if (tree instanceof Folha) {
				Folha folha = (Folha) tree;

				// Imprime na tela a Lista
				System.out.println(folha.valor + "\t\t" + folha.freq + "\t\t" + prefixo + "\t\t"
						+ Integer.toBinaryString(folha.valor));

			} else if (tree instanceof No) {
				No node = (No) tree;

				
				prefixo.append('0');
				imprimir(node.esq, prefixo);
				prefixo.deleteCharAt(prefixo.length() - 1);

				
				prefixo.append('1');
				imprimir(node.dir, prefixo);
				prefixo.deleteCharAt(prefixo.length() - 1);
			}
		}
	}

	/*
	 * M�todo para retornar o c�digo compactado de uma letra
	 */
	public static String codCompactado(Arvore tree, StringBuffer prefixo, char valor) {
		if (tree != null) {

			if (tree instanceof Folha) {
				Folha folha = (Folha) tree;

				// Retorna o texto compactado da letra
				if (folha.valor == valor) {
					return prefixo.toString();
				}

			} else if (tree instanceof No) {
				No node = (No) tree;

				// Percorre a esquerda
				prefixo.append('0');
				String esq = codCompactado(node.esq, prefixo, valor);
				prefixo.deleteCharAt(prefixo.length() - 1);

				// Percorre a direita
				prefixo.append('1');
				String dir = codCompactado(node.dir, prefixo, valor);
				prefixo.deleteCharAt(prefixo.length() - 1);

				if (esq == null)
					return dir;
				else
					return esq;
			}
		}
		return null;
	}

}
