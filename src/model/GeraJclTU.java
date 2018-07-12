package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class GeraJclTU {

	private Scanner leitorArquivo;
	private Scanner leitorTamanho;
	private PrintStream gravador;
	private List<String> listaArquivos = new ArrayList<String>();
	private List<String> listaTamanho = new ArrayList<String>();
	private String campo;
	private int b;
	private boolean envio = false;

	public void processar() {
		try {
			this.leitorArquivo = new Scanner(new FileReader("entrada.txt"));
			this.leitorTamanho = new Scanner(new FileReader("tamanho.txt"));

			while (this.leitorArquivo.hasNext()) {
				this.montarLista();
			}

			while (this.leitorTamanho.hasNext()) {
				this.montarListaVariaveisDescricao();
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

			this.gravador.println("//SCC3" + a + " JOB 'SCC3,4011,BN,SCC30000',CLASS=E,MSGCLASS=Z,");
			this.gravador.println("//         NOTIFY=&SYSUID");
			this.gravador.println("//*");
			this.gravador.println("//JOBLIB   DD DSN=AV.BIBGERTT,DISP=SHR");
			this.gravador.println("//         DD DSN=AV.BIBGERAL,DISP=SHR");
			this.gravador.println("//         DD DSN=SYS1.CEE.SCEERUN,DISP=SHR");
			this.gravador.println("//         DD DSN=DB2A2.R2.DSNLOAD,DISP=SHR");
			this.gravador.println("//*");
			this.gravador.println("//STEP00   EXEC PGM=IDCAMS");
			this.gravador.println("//*");
			this.gravador.println("//* ****   ************************************************************");
			this.gravador.println("//* ****   STEP AUXILIAR PARA DELETAR OS ARQUIVOS DE SAIDA");
			this.gravador.println("//* ****   ************************************************************");
			this.gravador.println("//*");
			this.gravador.println(" DELETE 'AD.C87.SCC3.J"+ a + "S01.SARQSEQS'");
			this.gravador.println(" DELETE 'AD.C87.SCC3.J"+ a + "S01.SARQSEQF'");
			this.gravador.println(" DELETE 'AD.C87.SCC3.J"+ a + "S01.SARQSEQO'");
			this.gravador.println(" DELETE 'AD.C87.SCC3.J"+ a + "S01.SCHAVXML'");
			this.gravador.println("//*");
			this.gravador.println("//SYSOUT   DD SYSOUT=*");
			this.gravador.println("//SYSPRINT DD SYSOUT=*");
			this.gravador.println("//*");
			this.gravador.println("//STEP01   EXEC PGM=SCC3" + a);
			this.gravador.println("//*");
			this.gravador.println("//* ****   ************************************************************");
			this.gravador.println("//* ****   SEPARAR PARTICIPANTE ADMINISTRADO POR SISTEMA DE ORIGEM");
			this.gravador.println("//* ****   ************************************************************");
			this.gravador.println("//*");
			this.gravador.println("//EARQSEQ  DD DSN=AD.C87.SCC3.SCC3"+ a +".EARQSEQ,");
			this.gravador.println("//       DISP=SHR");
			this.gravador.println("//SARQSEQS DD DSN=AD.C87.SCC3.J"+ a +"S01.SARQSEQS,");
			this.gravador.println("//       DISP=(,CATLG,DELETE),");
			this.gravador.println("//       UNIT=DISCO,");
			this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
			this.gravador.println("//       DCB=(AD.A,LRECL="+ this.listaTamanho.get(this.b) + ",RECFM=FB)");
			this.gravador.println("//SARQSEQF DD DSN=AD.C87.SCC3.J"+ a +"S01.SARQSEQF,");
			this.gravador.println("//       DISP=(,CATLG,DELETE),");
			this.gravador.println("//       UNIT=DISCO,");
			this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
			this.gravador.println("//       DCB=(AD.A,LRECL="+ this.listaTamanho.get(this.b) + ",RECFM=FB)");
			this.gravador.println("//SARQSEQO DD DSN=AD.C87.SCC3.J"+ a +"S01.SARQSEQO,");
			this.gravador.println("//       DISP=(,CATLG,DELETE),");
			this.gravador.println("//       UNIT=DISCO,");
			this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
			this.gravador.println("//       DCB=(AD.A,LRECL="+ this.listaTamanho.get(this.b) + ",RECFM=FB)");
			this.gravador.println("//SCHAVXML DD DSN=AD.C87.SCC3.J"+ a +"S01.SCHAVXML,");
			this.gravador.println("//       DISP=(,CATLG,DELETE),");
			this.gravador.println("//       UNIT=DISCO,");
			this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
			this.gravador.println("//       DCB=(AD.A,LRECL=240,RECFM=FB)");
			this.gravador.println("//*");
			this.gravador.println("//SRELCTRL DD SYSOUT=*");
			this.gravador.println("//SYSOUT   DD SYSOUT=*");
			this.gravador.println("//SYSUDUMP DD SYSOUT=*");
			this.gravador.println("//*");

		
			this.gravador.println("");
			this.gravador.println("");
			this.gravador.println("***************** SEPARADOR *********************");
			this.gravador.println("");
			this.gravador.println("");
		
		    this.b++;
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
	
	private void montarListaVariaveisDescricao() {
		try {
			this.campo = (String) this.leitorTamanho.nextLine();
			this.listaTamanho.add(this.campo);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista de Descricao.");
		}
	}

} 