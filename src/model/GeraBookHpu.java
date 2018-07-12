package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import util.Calendario;


public class GeraBookHpu {

	private Calendario objCalendario = new Calendario();
	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo;
	private String aux;
	private String descricao;
	private String vetor[] = new String[3];
	private boolean envio = false;
	private String dataAtual;


	public void processar() {
		try {
			this.leitorArquivo = new Scanner(new FileReader("entrada.txt"));

			while (this.leitorArquivo.hasNext()) {
				this.montarLista();
			}

			if (this.listaArquivos.size() > 0) {
				this.gravador = new PrintStream("saida.txt");
				this.envio = true;
				this.montarJCL();
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

	private void montarJCL() {
		this.dataAtual = this.objCalendario.getMesAtual() + "/"
				+ this.objCalendario.getAnoAtual();

		this.gravador.println("      *****************************************************************");
		this.gravador.println("      *    PARTICIPANTE INFORMA ATUALIZACAO DE DADOS CADASTRAIS       *");
		this.gravador.println("      *    NOME DA INC =  XXXXXXXX                                    *");
		this.gravador.println("      *    ANALISTA    =  BSI TECNOLOGIA                              *");
		this.gravador.println("      *    DATA         = " + this.dataAtual	+ "                                     *");
		this.gravador.println("      *    RESPONSAVEL =  PAULO  - BSI TECNOLOGIA                     *");
		this.gravador.println("      *****************************************************************");
		this.gravador.println("");
		this.gravador.println("       01  REG-XXXX.");

		for (String a : this.listaArquivos) {
			this.aux = a.trim();
			this.vetor = aux.split(" ");
			System.out.println(this.aux);
			System.out.println(this.vetor[0]);
			System.out.println(this.vetor[1]);
			System.out.println(this.vetor[2]);
			System.out.println(this.vetor[3]);
			System.out.println(this.vetor[4]);
			System.out.println(this.vetor[5]);
			System.out.println(this.vetor[6]);
			System.out.println(this.vetor[7]);
			System.out.println(this.vetor[8]);
			System.out.println(this.vetor[9]);
			System.out.println(this.vetor[10]);
			System.out.println(this.vetor[11]);
			System.out.println(this.vetor[12]);
			System.out.println(this.vetor[13]);
			System.out.println(this.vetor[14]);
			System.out.println(this.vetor[15]);
			this.descricao = this.vetor[0];
			
			this.gravador.println("           03  XXXX-" + descricao + "");		
		}
	}

	private void montarLista() {
		try {
			this.campo = (String) this.leitorArquivo.nextLine();
			this.listaArquivos.add(this.campo);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista.");
		}
	}
	
} 