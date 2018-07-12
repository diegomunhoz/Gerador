package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class GeralJclExpurgo {

	private Scanner leitorArquivo;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private String campo;
	private int n;
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

		this.gravador.println("//SCC3XXXX JOB 'SCC3,4510,BN','M112727',CLASS=E,MSGCLASS=Z");
		this.gravador.println("//*");
		this.gravador.println("//JOBLIB   DD DSN=MX.BIBGERTT,DISP=SHR");
		this.gravador.println("//         DD DSN=MX.BIBGERAL,DISP=SHR");
		this.gravador.println("//         DD DSN=SYS1.CEE.SCEERUN,DISP=SHR");
		this.gravador.println("//*");

		for (String a : this.listaArquivos) {

			this.n++;
			
			this.gravador.println("//STEP"+ this.n +"   EXEC PGM=SCC3EXPU,");
			this.gravador.println("//       PARM=06");
			this.gravador.println("//*");
			this.gravador.println("//* ***    ***********************************************************");
			this.gravador.println("//* ***    EXPURGO DO ARQUIVO PRV DE CONTROLE DE REMESSA");
			this.gravador.println("//* ***    ***********************************************************");
			this.gravador.println("//*");
			this.gravador.println("//ECTRLREM DD DSN=MX.SCC3.PRV.CTRLR"+ a +"(0),");
			this.gravador.println("//       DISP=SHR");
			this.gravador.println("//SCTRLREM DD DSN=MX.SCC3.PRV.CTRLR"+ a +"(+1),");
			this.gravador.println("//       DISP=(,CATLG,DELETE),");
			this.gravador.println("//       UNIT=DISCO,");
			this.gravador.println("//       SPACE=(TRK,(020000,8000),RLSE),");
			this.gravador.println("//       DCB=(MX.A,LRECL=240,RECFM=FB)");
			this.gravador.println("//*");
			this.gravador.println("//SRELCTRL DD SYSOUT=*");
			this.gravador.println("//SYSOUT   DD SYSOUT=*");
			this.gravador.println("//SYSUDUMP DD SYSOUT=*");
			this.gravador.println("//*");		
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