package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class GeraValidador {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private PrintStream campos;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo;
	private String aux[] = new String[3];
	private boolean envio = false;

	public void processar() {
		try {
			this.leitorArquivo = new Scanner(new FileReader("_book.txt"));

			while (this.leitorArquivo.hasNext()) {
				this.montarLista();
			}

			if (this.listaArquivos.size() > 0) {
				this.gravador = new PrintStream("_condicional.txt");
				this.campos = new PrintStream("_campos.txt");
				this.envio = true;
				this.montarValidador();
				this.gravador.close();
				this.campos.close();
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

	private void montarValidador() {

		for (String a : this.listaArquivos) {
			this.gravador.println("           IF  "+ a);
			this.gravador.println("		                                  NOT EQUAL SPACES");
			this.gravador.println("               INITIALIZE              WRK-DATA-BRAD1470");
			this.gravador.println("               MOVE " + a + "(1:4)");
			this.gravador.println("                                       TO WRK-ANO-AUX");
			this.gravador.println("               MOVE " + a + "(6:2)");
			this.gravador.println("                                       TO WRK-MES-AUX");
			this.gravador.println("               MOVE " + a + "(9:2)");
			this.gravador.println("                                       TO WRK-DIA-AUX");
			this.gravador.println("               PERFORM XXXX-ACESSAR-BRAD1470");
			this.gravador.println("               IF  WRK-VALIDA          EQUAL 'N'");
			this.gravador.println("                   IF  WRK-GRAVOU-HEADER");
			this.gravador.println("                                       EQUAL 'N'");
			this.gravador.println("                       MOVE 'S'        TO WRK-GRAVOU-HEADER");
			this.gravador.println("                       PERFORM 1300-HEADER-SCONSIST");
			this.gravador.println("                   END-IF");
			this.gravador.println("");
			this.gravador.println("                   MOVE 1              TO W01P-TIPO-REGISTRO");
			this.gravador.println("                   MOVE '" + a + "'");
			this.gravador.println("                                       TO W01P-NOME-CAMPO");
			this.gravador.println("                   STRING");
			this.gravador.println("                       'CAMPO COM DATA INCONSISTENTE - '");
			this.gravador.println("                       " + a);
			this.gravador.println("                   DELIMITED BY SIZE INTO W01P-DESC-INCONSISTENCIA");
			this.gravador.println("                   PERFORM XXXX-GRAVAR-SCONSIST");
			this.gravador.println("               END-IF");
			this.gravador.println("           END-IF.");
			this.gravador.println("");
			
			this.campos.println(a);
		}
	}

	private void montarLista() {
		try {
			this.campo = (String) this.leitorArquivo.nextLine();
			if (this.campo.contains("-DT")) {
				String c = this.campo.trim();
				this.aux = c.split(" ");
				this.listaArquivos.add(this.aux[2]);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista.");
		}
	}
	
} 