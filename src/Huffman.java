import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {

	/*
	 * Implementação do Algoritmo de Huffman Compactação um texto
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
			System.out.println("1 – Comprimir texto\r\n" + "0 – Sair");
			System.out.print("Digite: ");
			n = in.nextInt();
			limparBuffer(in);
			if (n == 1) {
				// Texto para Compactar  Algoritmo de Huffman
				String test; 
				System.out.print("Digite a String: ");
				test = in.nextLine();
				

				// Neste exemplo será considerado que teremos no máximo 256 caracteres
				// diferentes
				// Passo 1 - Percorre o texto contando os símbolos e montando um vetor de
				// frequências.
				int[] charFreqs = new int[256];
				for (char c : test.toCharArray())
					charFreqs[c]++;

				// Criar a Árvore dos códigos para a Compactação
				Arvore tree = construirArvore(charFreqs);

				// Resultados das quantidade e o código da Compactação
				System.out.println("\n======================TABELA==========================");
				System.out.println("CARACTERE\tQUANTIDEDE\tCÓDIGO\t\tBITS");
				imprimir(tree, new StringBuffer());

				// Compactar o texto
				System.out.println();
				String codigo = codificar(tree, test);

				// Mostrar o texto Compactado
				System.out.print("\nTexto Compactado: ");
				System.out.println(codigo);
				
				//Mostrar quantidade de bits do texto original
				System.out.println("\nQuantidade de bits do texto: " + test.getBytes().length * 8);
				
				//Mostrar quantidade de bits usados na decodificação
				System.out.println("Quantidade de Bits usados para codificar: " + codigo.length());
				Double taxa = ((double) codigo.length() / (double) (test.getBytes().length * 8)) * 100;
				
				//Mostrar a taxa de compressão
				System.out.println("Taxa de compressão: " + taxa + "%");
				System.out.println();

				System.out.println("========================================================");
				System.out.println();
			} else if (n == 0) {
				System.out.println("Programa Encerrado");

			} else {
				System.out.println("Digite um valor válido!");
			}
		} while (n != 0 || n == 1);

	}

	/*
	 * Criar a árvore de Codificação - A partir da quantidade de frequências de cada
	 * letra cria-se uma árvore binária para a compactação do texto Parâmetro de
	 */
	public static Arvore construirArvore(int[] charFreqs) {
		// Cria uma Fila de Prioridade
		// A Fila será criado pela ordem de frequência da letra no texto
		PriorityQueue<Arvore> trees = new PriorityQueue<Arvore>();
		
		for (int i = 0; i < charFreqs.length; i++) {
			if (charFreqs[i] > 0)
				trees.offer(new Folha(charFreqs[i], (char) i)); // Inser os elementos, nó folha, na fila de
																// prioridade
		}
		// Percorre todos os elementos da fila
		// Criando a árvore binária de baixo para cima
		while (trees.size() > 1) {
			// Pega os dois nós com menor frequência
			Arvore a = trees.poll(); //Retorna o próximo nó da Fila ou NULL se não tem mais
			Arvore b = trees.poll(); 

			// Criar os nós da árvore binária
			trees.offer(new No(a, b));
		}
		// Retorna o primeiro nó da árvore
		return trees.poll();
	}

	/*
	 * COMPACTAR a string Parâmetros de Entrada: tree - Raiz da Árvore de
	 * compactação encode - Texto original Parâmetros de Saída: encodeText- Texto
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
	 * Método para percorrer a Árvore e mostra a tabela de compactação 
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
	 * Método para retornar o código compactado de uma letra
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
