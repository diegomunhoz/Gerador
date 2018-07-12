package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class GeraCondicional {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo;
	private int i = 1;
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
			this.i++;
			this.gravador.println("                    WHEN W913-GRUPO-ACCC913-" + a.toUpperCase() + "-R");
			this.gravador.println("                         IF  WRK-ACCC913");
			this.gravador.println("                                       EQUAL 'S'");
			this.gravador.println("                             MOVE 'N'  TO WRK-ACCC913");
			this.gravador.println("                             PERFORM 3500-GRAVAR-SARQSEQ");
			this.gravador.println("                         END-IF");
			this.gravador.println("                         MOVE SPACES   TO REG-Y913");
			this.gravador.println("                         MOVE "+ i +"       TO Y913-TIPO-REG");
			this.gravador.println("                         MOVE '" + a.toUpperCase() + "'");
			this.gravador.println("                                       TO WRK-GRUPO");
			this.gravador.println("");
		}
	}

	private void montarLista() {
		try {
			this.campo = (String) this.leitorArquivo.nextLine();
			this.listaArquivos.add(this.campo.trim());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista.");
		}
	}
	
} 