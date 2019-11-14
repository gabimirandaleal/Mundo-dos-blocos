package Mundo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class MundoDosBlocos {
	HashMap<String, Integer> mapeamento = new HashMap();
	HashMap<String, Integer> mapeamentoAux = new HashMap();
	HashMap<Integer, String> mapearAcoes = new HashMap();
	ArrayList<String> estInicial = new ArrayList();
	ArrayList<String> estFinal = new ArrayList();
	ArrayList<String> acoes = new ArrayList();
	ArrayList<String> PreCond = new ArrayList();
	ArrayList<String> PosCond = new ArrayList();
	ArrayList<String> auxPreCond = new ArrayList();
	ArrayList<String> auxPosCond = new ArrayList();
	ArrayList<String> tudo = new ArrayList();
	ArrayList<String> copia = new ArrayList();
	ArrayList<Integer> posCondInt = new ArrayList();
	ArrayList<String> escreverAux = new ArrayList();
	boolean flag;
	boolean hello;
	int contMap = 1;
	int level = 1;
	int maior;
	String saida;

	public void abrirLer() {
		int cont = 0;
		String linha = null;
		BufferedReader br;
		try {
			br = new BufferedReader(
					new FileReader("/home/windows/Downloads/atividade-1/Mundo dos blocos/blocks-8-0.strips"));
			while (br.ready()) {
				linha = br.readLine();
				if (cont != 2 && cont != 1 && cont != 0) {
					cont = 0;
				}
				if (!linha.isEmpty()) {
					if (cont == 0) {
						acoes.add(linha);
					} else if (cont == 1) {
						PreCond.add(linha);
					} else if (cont == 2) {
						PosCond.add(linha);
					}
					cont++;
				}
				if (linha.isEmpty()) {
					linha = br.readLine();
					cont = 0;
					if (cont == 0) {
						estInicial.add(linha);
						linha = br.readLine();
						cont++;
					}
					if (cont == 1)
						estFinal.add(linha);
					cont++;

				}
			}
			br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapear();

	}

	public void mapear() {
		String[] auxPre = null;
		String[] auxPos = null;
		String[] auxAcoes = null;
		for (int i = 0; i < PreCond.size(); i++) {
			auxPre = PreCond.get(i).split(";");
			for (int j = 0; j < auxPre.length; j++) {
				if (!(mapeamento.containsKey(auxPre[j])) && !(mapeamento.containsKey(auxPre[j].substring(1)))) {
					if (auxPre[j].charAt(0) == '~') {
						mapeamento.put(auxPre[j].substring(1), contMap);
					} else {
						mapeamento.put(auxPre[j], contMap);
					}
					contMap++;
				}
			}
			auxPos = PosCond.get(i).split(";");
			for (int j = 0; j < auxPos.length; j++) {
				if (!(mapeamento.containsKey(auxPos[j])) && !(mapeamento.containsKey(auxPos[j].substring(1)))) {
					if (auxPos[j].charAt(0) == '~') {
						mapeamento.put(auxPos[j].substring(1), contMap);
						posCondInt.add(contMap);
					} else {
						mapeamento.put(auxPos[j], contMap);
						posCondInt.add(contMap);
					}
					contMap++;
				}
			}

		}
		for (int i = 0; i < acoes.size(); i++) {
			auxAcoes = acoes.get(i).split(";");
			for (int j = 0; j < auxAcoes.length; j++) {
				if (!(mapeamento.containsKey(auxAcoes[j])) && !(mapeamento.containsKey(auxAcoes[j].substring(1)))) {
					mapeamento.put(auxAcoes[j], contMap);
					mapearAcoes.put(contMap, auxAcoes[j]);
					contMap++;
				}
			}
		}
		String auxEstIni[] = estInicial.get(0).split(";");
		for (int i = 0; i < auxEstIni.length; i++) {
			if (!(mapeamento.containsKey(auxEstIni[i])) && !(mapeamento.containsKey(auxEstIni[i].substring(1)))) {
				mapeamento.put(auxEstIni[i], contMap);
				contMap++;
			}
		}
		String auxEstFinal[] = estFinal.get(0).split(";");
		for (int i = 0; i < auxEstFinal.length; i++) {
			if (!(mapeamento.containsKey(auxEstFinal[i])) && !(mapeamento.containsKey(auxEstFinal[i].substring(1)))) {
				mapeamento.put(auxEstFinal[i], contMap);
				contMap++;
			}
		}

		escreverArquivo();
	}

	public void escreverArquivo() {
		flag = true;
		boolean verifica = false;
		String podeEscrever = null;
		for (int i = 0; i < estInicial.size(); i++) {
			String[] aux = estInicial.get(i).split(";");
			for (int j = 0; j < aux.length; j++)
				tudo.add(mapeamento.get(aux[j]) + " 0 \n");
		}
		for (int i = 0; i < PreCond.size(); i++) {
			String[] aux = PreCond.get(i).split(";");
			String[] aux1 = estInicial.get(0).split(";");
			for (int k = 0; k < aux.length; k++) {
				verifica = false;
				for (int j = 0; j < aux1.length; j++) {
					if (aux[k].charAt(0) == '~') {
						podeEscrever = aux[k].substring(1);
						if (podeEscrever.equals(aux1[j])) {
							verifica = true;
							break;
						}
					} else {
						podeEscrever = aux[k];
						if (podeEscrever.equals(aux1[j])) {
							verifica = true;
							break;
						}
					}
				}
				if (!verifica) {
					if (escreverAux.isEmpty()) {
						tudo.add(("-" + mapeamento.get(podeEscrever) + " 0 \n"));
						escreverAux.add(podeEscrever);
					}
					for (String g : escreverAux) {

						if (g.equals(podeEscrever)) {
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
					if (flag) {
						tudo.add(("-" + mapeamento.get(podeEscrever) + " 0 \n"));
						escreverAux.add(podeEscrever);
					}
				}
			}
		}
		for (int i = 0; i < PosCond.size(); i++) {
			String[] aux = PosCond.get(i).split(";");
			String[] aux1 = estInicial.get(0).split(";");
			for (int k = 0; k < aux.length; k++) {
				verifica = false;
				for (int j = 0; j < aux1.length; j++) {
					if (aux[k].charAt(0) == '~') {
						podeEscrever = aux[k].substring(1);
						if (podeEscrever.equals(aux1[j])) {
							verifica = true;
							break;
						}
					} else {
						podeEscrever = aux[k];
						if (podeEscrever.equals(aux1[j])) {
							verifica = true;
							break;
						}
					}
				}
				if (!verifica) {
					if (escreverAux.isEmpty()) {
						tudo.add(("-" + mapeamento.get(podeEscrever) + " 0 \n"));
						escreverAux.add(podeEscrever);
					}
					for (String g : escreverAux) {
						if (g.equals(podeEscrever)) {
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
					if (flag) {
						tudo.add(("-" + mapeamento.get(podeEscrever) + " 0 \n"));
						escreverAux.add(podeEscrever);
					}
				}
			}
		}
		String aux = "";
		for (int i = 0; i < acoes.size(); i++) {
			aux += ((mapeamento.get(acoes.get(i)) + " "));

		}
		copia.add((aux + "0 \n"));
		tudo.add(aux + "0 \n");
		for (int i = 0; i < acoes.size(); i++) {
			for (int j = i + 1; j < acoes.size(); j++) {
				tudo.add(((mapeamento.get(acoes.get(i)) * -1) + " " + (mapeamento.get(acoes.get(j)) * -1) + " 0 \n"));
				copia.add(((mapeamento.get(acoes.get(i)) * -1) + " " + (mapeamento.get(acoes.get(j)) * -1) + " 0 \n"));
			}
			String auxPre[] = PreCond.get(i).split(";");
			String auxPos[] = PosCond.get(i).split(";");
			for (int k = 0; k < PreCond.size(); k++) {
				String auxPre1[] = PreCond.get(k).split(";");
				for (int j = 0; j < auxPre1.length; j++) {
					if (!auxPreCond.contains(auxPre1[j]) && !auxPreCond.contains(auxPre1[j].substring(1))) {
						auxPreCond.add(auxPre1[j]);
					}
				}
			}
			for (int k = 0; k < PosCond.size(); k++) {
				String auxPos1[] = PosCond.get(k).split(";");
				for (int j = 0; j < auxPos1.length; j++) {
					if (!auxPreCond.contains(auxPos1[j]) && !auxPreCond.contains(auxPos1[j].substring(1))) {
						auxPreCond.add(auxPos1[j]);
					}
				}
			}

			for (int k = 0; k < auxPre.length; k++) {
				if (auxPre[k].charAt(0) == '~') {
					tudo.add(((mapeamento.get(acoes.get(i)) * -1) + " "
							+ ((mapeamento.get(auxPre[k].substring(1)) * -1) + " 0 \n")));
					for (int h = 0; h < auxPreCond.size(); h++) {
						if (auxPreCond.contains(auxPre[k].substring(1))) {
							auxPreCond.remove(auxPre[k].substring(1));
						}
					}
					copia.add(((mapeamento.get(acoes.get(i)) * -1) + " "
							+ ((mapeamento.get(auxPre[k].substring(1)) * -1) + " 0 \n")));
				} else {
					tudo.add(((mapeamento.get(acoes.get(i)) * -1) + " " + (mapeamento.get(auxPre[k])) + " 0 \n"));
					for (int h = 0; h < auxPreCond.size(); h++) {
						if (auxPreCond.contains(auxPre[k])) {
							auxPreCond.remove(auxPre[k]);
						}
					}
					copia.add(((mapeamento.get(acoes.get(i)) * -1) + " " + (mapeamento.get(auxPre[k])) + " 0 \n"));
				}

			}
			for (int k = 0; k < auxPos.length; k++) {
				if (auxPos[k].charAt(0) == '~') {
					tudo.add(((mapeamento.get(acoes.get(i)) * -1) + " "
							+ (((mapeamento.get(auxPos[k].substring(1)) + (contMap - 1)) * -1) + " 0 \n")));
					for (int h = 0; h < auxPreCond.size(); h++) {
						if (auxPreCond.contains(auxPos[k].substring(1))) {
							auxPreCond.remove(auxPos[k].substring(1));
						}
					}
					copia.add(((mapeamento.get(acoes.get(i)) * -1) + " "
							+ (((mapeamento.get(auxPos[k].substring(1)) + (contMap - 1)) * -1) + " 0 \n")));
				} else {
					tudo.add(((mapeamento.get(acoes.get(i)) * -1) + " " + ((mapeamento.get(auxPos[k])) + (contMap - 1))
							+ " 0 \n"));
					for (int h = 0; h < auxPreCond.size(); h++) {
						if (auxPreCond.contains(auxPos[k])) {
							auxPreCond.remove(auxPos[k]);
						}
					}
					copia.add(((mapeamento.get(acoes.get(i)) * -1) + " " + ((mapeamento.get(auxPos[k])) + (contMap - 1))
							+ " 0 \n"));
				}
			}
			for (int k = 0; k < auxPreCond.size(); k++) {
				for (int l = 0; l < auxPre.length && k < auxPreCond.size(); l++) {
					if (auxPreCond.get(k).charAt(0) == '~') {
						if (auxPreCond.get(k).substring(1).equals(auxPre[l]) || auxPreCond.get(k).equals(auxPre[l])) {
							auxPreCond.remove(auxPre[l]);
						}
					} else {
						if (auxPreCond.get(k).equals(auxPre[l])) {
							auxPreCond.remove(auxPre[l]);

						}
					}
				}
				for (int l = 0; l < auxPos.length && k < auxPreCond.size(); l++) {
					if (auxPreCond.get(k).charAt(0) == '~') {
						if (auxPreCond.get(k).substring(1).equals(auxPos[l]) || auxPreCond.get(k).equals(auxPos[l])) {
							auxPreCond.remove(auxPos[l]);
						}
					} else {
						if (auxPreCond.get(k).equals(auxPos[l])) {
							auxPreCond.remove(auxPos[l]);
						}
					}
				}
			}
			for (int j = 0; j < auxPreCond.size(); j++) {
				if (auxPreCond.get(j).charAt(0) == '~') {
					tudo.add(((mapeamento.get(acoes.get(i)) * -1) + " " + mapeamento.get(auxPreCond.get(j).substring(1))
							+ " -" + (mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1)) + " 0 \n"));
					copia.add(((mapeamento.get(acoes.get(i)) * -1) + " "
							+ mapeamento.get(auxPreCond.get(j).substring(1)) + " -"
							+ (mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1)) + " 0 \n"));
					tudo.add((mapeamento.get(acoes.get(i)) * -1) + " -" + mapeamento.get(auxPreCond.get(j).substring(1))
							+ " " + (mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1)) + " 0 \n");
					copia.add(
							(mapeamento.get(acoes.get(i)) * -1) + " -" + mapeamento.get(auxPreCond.get(j).substring(1))
									+ " " + (mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1)) + " 0 \n");
					if (maior < mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1)) {
						maior = mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1);
					} else if (maior < (mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1) * -1)) {
						maior = mapeamento.get(auxPreCond.get(j).substring(1)) + (contMap - 1);
					}
				} else {

					tudo.add((mapeamento.get(acoes.get(i)) * -1) + " " + mapeamento.get(auxPreCond.get(j)) + " -"
							+ (mapeamento.get(auxPreCond.get(j)) + (contMap - 1)) + " 0 \n");
					copia.add((mapeamento.get(acoes.get(i)) * -1) + " " + mapeamento.get(auxPreCond.get(j)) + " -"
							+ (mapeamento.get(auxPreCond.get(j)) + (contMap - 1)) + " 0 \n");
					tudo.add((mapeamento.get(acoes.get(i)) * -1) + " -" + mapeamento.get(auxPreCond.get(j)) + " "
							+ (mapeamento.get(auxPreCond.get(j)) + (contMap - 1)) + " 0 \n");
					copia.add((mapeamento.get(acoes.get(i)) * -1) + " -" + mapeamento.get(auxPreCond.get(j)) + " "
							+ (mapeamento.get(auxPreCond.get(j)) + (contMap - 1)) + " 0 \n");
					if (maior < mapeamento.get(auxPreCond.get(j)) + (contMap - 1)) {
						maior = mapeamento.get(auxPreCond.get(j)) + (contMap - 1);
					} else if (maior < (mapeamento.get(auxPreCond.get(j)) + (contMap - 1) * -1)) {
						maior = mapeamento.get(auxPreCond.get(j)) + (contMap - 1);
					}

				}
			}

		}
		escrever();
	}

	public void escrever() {
		FileWriter file;
		try {
			file = new FileWriter("/home/windows/Downloads/glucose-syrup-4.1/simp/teste.cnf");
			PrintWriter gravarArq = new PrintWriter(file);
			String aux[] = estFinal.get(0).split(";");
			if (level > 1) {
				gravarArq.write("p cnf " + (maior + (contMap - 1) * level) + " " + (tudo.size() + aux.length) + "\n");
			} else {
				gravarArq.write("p cnf " + (maior * level) + " " + (tudo.size() + aux.length) + "\n");
			}
			for (int i = 0; i < tudo.size(); i++) {
				gravarArq.write(tudo.get(i));
			}
			String auxFinal[] = estFinal.get(0).split(";");

			for (int i = 0; i < auxFinal.length; i++) {
				if (level > 1)
					gravarArq.write(((mapeamento.get(auxFinal[i]) + (contMap - 1) * level) + (contMap - 1)) + " 0 \n");
				else if (level == 1)
					gravarArq.write(((mapeamento.get(auxFinal[i]) + (contMap - 1) * level)) + " 0 \n");
			}
			file.close();
			System.out.println("terminou level = " + level);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void incrementoLevel() {
		String aux1[] = copia.get(0).split(" ");
		String aux2 = "";
		for (int i = 0; i < aux1.length - 1; i++) {
			if ((Integer.parseInt(aux1[i]) != 0)) {
				aux2 += ((Integer.parseInt(aux1[i]) + (contMap - 1) * level) + " ");
			}
		}
		tudo.add(aux2 + "0 \n");
		aux2 = "";
		for (int i = 1; i < copia.size(); i++) {
			String aux[] = copia.get(i).split(" ");
			for (int j = 0; j < aux.length - 1; j++) {
				if ((Integer.parseInt(aux[j]) >= 0)) {
					if ((Integer.parseInt(aux[j]) > (contMap - 1)) && Integer.parseInt(aux[j]) != 0) {
						aux2 += (Integer.parseInt(aux[j]) + (contMap - 1) * (level) + " ");
						continue;
					} else if (Integer.parseInt(aux[j]) == 0) {
						aux2 += ("0 \n");
						continue;
					}
					aux2 += ((Integer.parseInt(aux[j]) + (contMap - 1) * level) + " ");
				} else {
					if (((Integer.parseInt(aux[j]) * -1) > (contMap - 1))) {
						aux2 += ((((Integer.parseInt(aux[j]) * -1) + (contMap - 1) * (level)) * -1) + " ");
						continue;
					}
					aux2 += ((((Integer.parseInt(aux[j]) * -1) + (contMap - 1) * level)) * -1 + " ");
				}
			}
			tudo.add(aux2);
			// System.out.println(aux2);
			aux2 = "";
		}
		escrever();
	}

	public boolean CMD() {
		try {
			Runtime r = Runtime.getRuntime();
			String comando = "/glucose_static -model /home/windows/Downloads/glucose-syrup-4.1/simp/teste.cnf";
			Process process = Runtime.getRuntime().exec("/home/windows/Downloads/glucose-syrup-4.1/simp" + comando);
			System.out.println(tudo.size());
//			while (process.isAlive()) {
//				//System.out.println();
//				Thread.sleep(100);
//			}
			if(level<=29) {
				while (process.isAlive());
			}
			if(level>29 && level<=34) {
				Thread.sleep(90000);
			}
			if(level>34) {
				Thread.sleep(100000);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while (br.ready()) {
				saida = br.readLine();
				if (!saida.isEmpty() && saida.charAt(0) == 'v') {
					System.out.println(saida);
					return false;
				} else if (saida.equals("s UNSATISFIABLE")) {
					System.out.println(saida);
					return true;
				}
			}
			br.close();

		} catch (IOException iOException) {
			iOException.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void verificar() {
		saida = saida.substring(2);
		String aux[] = saida.split(" ");
		for (int i = 0; i < aux.length - 1; i++) {
			for (int j = 0; j < level; j++) {
				if (aux[i].charAt(0) != '-') {
					if (mapearAcoes.containsKey(Integer.parseInt(aux[i]))) {
						System.out.println(mapearAcoes.get(Integer.parseInt(aux[i])));
						break;
					}
					if (mapearAcoes.containsKey((Integer.parseInt(aux[i])) - ((contMap - 1) * j))) {
						System.out.println(mapearAcoes.get(Integer.parseInt(aux[i]) - ((contMap - 1) * j)));
						break;
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		MundoDosBlocos mundo = new MundoDosBlocos();
		mundo.abrirLer();
		boolean oi = mundo.CMD();
		while (oi) {
			mundo.incrementoLevel();
			oi = mundo.CMD();
			System.out.println(oi);
			System.out.println("Nível: " + mundo.level);
			mundo.level++;
		}
		mundo.verificar();
		double seg = (System.currentTimeMillis() - start) / 1000.0;
		System.out.println("Tempo de execução em segundos " + seg + "s");
	}
}
