package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ConsistenciaFisica {
	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String prefixo;
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
				this.prefixo = JOptionPane.showInputDialog(null,
						"NOME DO BOOK:","GERADOR DE CONSISTENCIA:", 3);
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
			this.gravador.println("           IF ("+ this.prefixo.toUpperCase() + "-E-" + this.variavel[0].replace('_', '-'));
			if (a.contains("DECIMAL")) {
				this.gravador
						.println("                                       NOT NUMERIC)");
			} else {
				this.gravador
						.println("                                       EQUAL LOW-VALUES)");
			}
			if (a.contains("NOT NULL")) {
	   			this.gravador.println("           OR ("+ this.prefixo.toUpperCase() + "-E-" + this.variavel[0].replace('_', '-'));
				if (a.contains("PIC  9")) {
					this.gravador
							.println("                                       EQUAL ZEROS)");
				} else {
					this.gravador
							.println("                                       EQUAL SPACES)");
				}
			}
			this.gravador
					.println("               MOVE 08                 TO FEMPW000-R-COD-RETORNO");
			this.gravador
					.println("               MOVE '0000'             TO FEMPW000-R-COD-ERRO");
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
