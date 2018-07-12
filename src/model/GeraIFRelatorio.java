package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class GeraIFRelatorio {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo, aux1, aux2;
	private int index;
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

		for (String a : this.listaArquivos) {
			this.gravador.println("           IF  " + a);
			this.gravador.println("                                       GREATER SPACES");
			this.gravador.println("               INITIALIZE              WCDE-AREA-ENTRADA");
			this.gravador.println("               MOVE '" + a + "'");
			this.gravador.println("                                       TO WRK-REL1-LD5-TAG");
			
			
			this.aux1 = a;
			String[] valorComSplit = this.aux1.split("-ERR");
            
            for(String s : valorComSplit){   
    			this.gravador.println("               MOVE " + s );
            	break;
            }			
			
			this.gravador.println("                                       TO WRK-REL1-LD5-CONTD");
			this.gravador.println("               MOVE 'ECCC'             TO WCDE-SISTEMA");
			this.gravador.println("               MOVE " +  a);
			this.gravador.println("                                       TO WCDE-TAG");
			this.gravador.println("                                          WRK-REL1-LD5-ECCC");
			this.gravador.println("");
			this.gravador.println("               PERFORM 2400-ACESSAR-SCC3CODE");
			this.gravador.println("               WRITE FD-RELCONFC       FROM  WRK-REL1-LINDET5");
			this.gravador.println("               MOVE WRK-GRAVACAO       TO WRK-OPERACAO");
			this.gravador.println("               PERFORM 1120-TESTAR-FS-RELCONFC");
			this.gravador.println("               ADD 1                   TO ACU-GRAVA-RELCONFC");
			this.gravador.println("           END-IF.");
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