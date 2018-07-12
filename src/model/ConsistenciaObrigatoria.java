package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ConsistenciaObrigatoria {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo;
	private String[] variavel;
	private boolean envio = false;

	public void processar() {
		try {
			this.leitorArquivo = new Scanner(new FileReader("entrada.txt"));

			while (this.leitorArquivo.hasNext()) {
				this.montarLista();
			}

			if (this.listaArquivos.size() > 0) {
				this.gravador = new PrintStream("saida.txt");
				this.envio = true;
				this.montarCondicional();
			} else {
				this.envio = false;
			}

			if (this.envio == true) {
				this.gravador.close();
				JOptionPane.showMessageDialog(null, "Processamento Concluído.");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private void montarCondicional() {

		for (String a : this.listaArquivos) {
			this.variavel = a.split(" ");

			this.gravador.println("           IF (" + this.variavel[2]);
			if (a.contains("PIC  9")) {
				this.gravador
						.println("                                       NOT NUMERIC)");
			} else {
				this.gravador
						.println("                                       EQUAL LOW-VALUES)");
			}
/*			
   			this.gravador.println("           OR (" + this.variavel[2]);
			if (a.contains("PIC  9")) {
				this.gravador
						.println("                                       EQUAL ZEROS)");
			} else {
				this.gravador
						.println("                                       EQUAL SPACES)");
			}
*/
			this.gravador
					.println("               MOVE 08                 TO FEMPW000-R-COD-RETORNO");
			this.gravador
					.println("               MOVE '0010'             TO FEMPW000-R-COD-ERRO");
			this.gravador
					.println("               MOVE 'FEMP1379'         TO FEMPW000-R-COD-MENSAGEM");
			this.gravador.println("               PERFORM 3000-FINALIZAR");
			this.gravador.println("           END-IF.");
			this.gravador.println("");
		}
	}

	private void montarLista() {
		try {
			this.campo = (String) this.leitorArquivo.nextLine();
			this.listaArquivos.add(this.campo.trim());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao montar lista.");
		}
	}

}
