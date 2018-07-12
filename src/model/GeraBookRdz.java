package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class GeraBookRdz {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo;
	private boolean envio = false;

	public void processar() {
		try {
			this.leitorArquivo = new Scanner(new FileReader("entrada.txt"));

			while (this.leitorArquivo.hasNext()) {
				this.montarListaCampo();
			}

			if (this.listaArquivos.size() > 0) {
				this.montarJCL();
			} else {
				this.envio = false;
			}

			if (this.envio == true) {
				JOptionPane.showMessageDialog(null, "Processamento Concluído.");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private void montarJCL() {

		for (String a : this.listaArquivos) {
			try {
				this.gravador = new PrintStream("FEMPW" + a + ".CPY");
				this.envio = true;
				this.gravador
						.println("      ******************************************************************");
				this.gravador.println("      * NOME BOOK : FEMPW" + a
						+ " - INTERFACE DO MODULO BASICO FEMP4" + a + "     *");
				this.gravador
						.println("      * DATA......: 04/05/2017                                         *");
				this.gravador
						.println("      * AUTOR.....: DIEGO MUNHOZ                                       *");
				this.gravador
						.println("      * EMPRESA...: BSI TECNOLOGIA                                     *");
				this.gravador
						.println("      * GRUPO.....: FEMP                                               *");
				this.gravador
						.println("      * TAMANHO...: 0000 BYTES                                         *");
				this.gravador
						.println("      ******************************************************************");
				this.gravador.println("       05  FEMPW" + a + "-HEADER.");
				this.gravador
						.println("           10  FEMPW" + a
								+ "-COD-LAYOUT     PIC  X(008) VALUE 'FEMPW"
								+ a + "'.");
				this.gravador.println("           10  FEMPW" + a
						+ "-TAM-LAYOUT     PIC  9(005) VALUE  00000.");
				this.gravador.println("       05  FEMPW" + a + "-REGISTRO.");
				this.gravador.println("           10  FEMPW" + a
						+ "-BLOCO-ENTRADA.");
				this.gravador.println("               15  FEMPW" + a
						+ "-E-MAX-OCCURS          PIC  9(005).");
				this.gravador.println("");
				this.gravador.println("");
				this.gravador.println("           10  FEMPW" + a
						+ "-BLOCO-PAGINACAO.");
				this.gravador.println("               15  FEMPW" + a
						+ "-INDICADOR-PAGINACAO   PIC  X(001).");
				this.gravador.println("                   88  FEMPW" + a
						+ "-P-INICIAL         VALUE 'I'. ");
				this.gravador.println("                   88  FEMPW" + a
						+ "-P-PRIMEIRA        VALUE 'P'.");
				this.gravador.println("                   88  FEMPW" + a
						+ "-P-SEGUINTE        VALUE 'S'.");
				this.gravador.println("                   88  FEMPW" + a
						+ "-P-ANTERIOR        VALUE 'A'.");
				this.gravador.println("                   88  FEMPW" + a
						+ "-P-ULTIMA          VALUE 'U'.");
				this.gravador.println("               15  FEMPW" + a
						+ "-CHAVE-INI.");
				this.gravador.println("");
				this.gravador.println("");
				this.gravador.println("               15  FEMPW" + a
						+ "-CHAVE-FIM.");
				this.gravador.println("");
				this.gravador.println("");
				this.gravador.println("           10  FEMPW" + a
						+ "-BLOCO-SAIDA.");
				this.gravador.println("               15  FEMPW" + a
						+ "-S-NUM-CONSULTAS       PIC  9(005). ");
				this.gravador.println("               15  FEMPW" + a
						+ "-REG-SAIDA             OCCURS 0 TO 50 TIMES");
				this.gravador
						.println("                                  DEPENDING ON FEMPW"
								+ a + "-S-NUM-CONSULTAS.");
				this.gravador.println("                   20  FEMPW" + a
						+ "-S-");
				this.gravador.println("");
				this.gravador.println("");

				this.gravador.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void montarListaCampo() {
		try {
			this.campo = (String) this.leitorArquivo.nextLine();
			this.listaArquivos.add(this.campo.trim());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista de Campo.");
		}
	}

}
