package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class GeraVarredura {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo, aux;
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
				this.aux = JOptionPane.showInputDialog(null,
						"NOME DO ARQUIVO:","GERADOR DE MOVIEMENTAÇÕES", 3);
				this.montarMove(this.aux.toUpperCase());
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

	private void montarMove(String arquivo) {

		for (String a : this.listaArquivos) {
			this.gravador.println("                    WHEN W"+ arquivo +"-" + a + "-R");
			this.gravador.println("                         MOVE XML-TEXT TO Y"+ arquivo + "-" + a);
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