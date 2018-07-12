package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class GeraJclQuebraLote {

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
				this.montarListaCampo();
			}

			while (this.leitorTamanho.hasNext()) {
				this.montarListaTamanho();
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
				this.gravador = new PrintStream("SCC3" + a + ".JCL");
				this.envio = true;

				this.gravador.println("//SCC3" + a
						+ " JOB 'SCC3,4011,BN,SCC30000',CLASS=E,MSGCLASS=Z,");
				this.gravador.println("//         NOTIFY=&SYSUID");
				this.gravador.println("//*");
				this.gravador.println("//JOBLIB   DD DSN=AV.BIBGERTT,DISP=SHR");
				this.gravador.println("//         DD DSN=AV.BIBGERAL,DISP=SHR");
				this.gravador
						.println("//         DD DSN=SYS1.CEE.SCEERUN,DISP=SHR");
				this.gravador
						.println("//         DD DSN=DB2A2.R2.DSNLOAD,DISP=SHR");
				this.gravador.println("//*");
				this.gravador.println("//STEP00   EXEC PGM=IDCAMS");
				this.gravador.println("//*");
				this.gravador
						.println("//* ***    ************************************************************");
				this.gravador
						.println("//* ***    STEP AUXILIAR PARA DELETAR OS ARQUIVOS DE SAIDA");
				this.gravador
						.println("//* ***    ************************************************************");
				this.gravador.println("//*");
				this.gravador.println(" DELETE 'AD.C87.SCC3.J" + a
						+ "S01.SARQRXML'");
				this.gravador.println(" DELETE 'AD.C87.SCC3.J" + a
						+ "S01.SARQREST'");
				this.gravador.println(" DELETE 'AD.C87.SCC3.J" + a
						+ "S01.SCTRLOTE'");
				this.gravador.println(" DELETE 'AD.C87.SCC3.J" + a
						+ "S01.SCTRLREM'");
				this.gravador.println("//*");
				this.gravador.println("//SYSOUT   DD SYSOUT=*");
				this.gravador.println("//SYSPRINT DD SYSOUT=*");
				this.gravador.println("//*");
				this.gravador.println("//STEP01   EXEC PGM=SCC3" + a + ",");
				this.gravador.println("//         PARM='00010'");
				this.gravador.println("//*");
				this.gravador
						.println("//* ***    ************************************************************");
				this.gravador
						.println("//* ***    GERAR O ARQUIVO SARQRXML COM CONTROLE DE QUEBRA DE LOTE");
				this.gravador
						.println("//* ***    ************************************************************");
				this.gravador.println("//*");
				this.gravador.println("//EARQREST DD DSN=AD.C87.SCC3.SCC3" + a
						+ ".EARQREST,");
				this.gravador.println("//       DISP=SHR");
				this.gravador.println("//ECTRLOTE DD DUMMY,");
				this.gravador.println("//       DISP=SHR");
				this.gravador.println("//SARQRXML DD DSN=AD.C87.SCC3.J" + a
						+ "S01.SARQRXML,");
				this.gravador.println("//       DISP=(,CATLG,DELETE),");
				this.gravador.println("//       UNIT=DISCO,");
				this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
				this.gravador.println("//       DCB=(AD.A,LRECL="
						+ this.listaTamanho.get(this.b) + ",RECFM=FB)");
				this.gravador.println("//SARQREST DD DSN=AD.C87.SCC3.J" + a
						+ "S01.SARQREST,");
				this.gravador.println("//       DISP=(,CATLG,DELETE),");
				this.gravador.println("//       UNIT=DISCO,");
				this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
				this.gravador.println("//       DCB=(AD.A,LRECL="
						+ this.listaTamanho.get(this.b) + ",RECFM=FB)");
				this.gravador.println("//SCTRLOTE DD DSN=AD.C87.SCC3.J" + a
						+ "S01.SCTRLOTE,");
				this.gravador.println("//       DISP=(,CATLG,DELETE),");
				this.gravador.println("//       UNIT=DISCO,");
				this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
				this.gravador.println("//       DCB=(AD.A,LRECL=080,RECFM=FB)");
				this.gravador.println("//SCTRLREM DD DSN=AD.C87.SCC3.J" + a
						+ "S01.SCTRLREM,");
				this.gravador.println("//       DISP=(,CATLG,DELETE),");
				this.gravador.println("//       UNIT=DISCO,");
				this.gravador.println("//       SPACE=(TRK,(0200,0020),RLSE),");
				this.gravador.println("//       DCB=(AD.A,LRECL=240,RECFM=FB)");
				this.gravador.println("//*");
				this.gravador.println("//SRELCTRL DD SYSOUT=*");
				this.gravador.println("//SYSOUT   DD SYSOUT=*");
				this.gravador.println("//SYSUDUMP DD SYSOUT=*");
				this.gravador.println("//*");

				this.gravador.close();
				this.b++;

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

	private void montarListaTamanho() {
		try {
			this.campo = (String) this.leitorTamanho.nextLine();
			this.listaTamanho.add(this.campo);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista de Tamanho.");
		}
	}

}