package Jeu;

import java.util.ArrayList;
import java.util.List;

public class Combinaison {

	private List<Integer> propositionCombi = new ArrayList(); //proposition en cours
	private List<Integer> combiMin = new ArrayList(); //combinaison contenant à chaque place le chiffre mini
	private List<Integer> combiMax = new ArrayList(); // combinaison contenant à chaque place le chiffre maxi
	private List<Integer> genereCombi = new ArrayList(); // combinaison aléatoire que le joueur doit trouver
	
	/**
	 * Méthode qui crée 4 listes de nbChiffres chiffres
	 * @param nbChiffres - nombre de chiffres dans les combinaisons (nb contenu ds le fichier de propriétés)
	 */
	public Combinaison(int nbChiffres) {
		for (int i = 0; i < nbChiffres; i++) {
			this. propositionCombi.add(5);
			this. combiMax.add(10);
			this. combiMin.add(0);
			int k = (int) (Math.random() * 10);
			this.genereCombi.add(k);
			}
	}

	/**
	 * @return the propositionCombi
	 */
	public List<Integer> getPropositionCombi() {
		return propositionCombi;
	}

	/**
	 * @param propositionCombi the propositionCombi to set
	 */
	/*
	 * public void setPropositionCombi(List<Integer> propositionCombi) {
	 * this.propositionCombi = propositionCombi; }
	 */

	/**
	 * @return the combiMin
	 */
	public List<Integer> getCombiMin() {
		return combiMin;
	}

	/**
	 * @param combiMin the combiMin to set
	 */
	/*
	 * public void setCombiMin(List<Integer> combiMin) { this.combiMin = combiMin; }
	 */

	/**
	 * @return the combiMax
	 */
	public List<Integer> getCombiMax() {
		return combiMax;
	}

	/**
	 * @param combiMax the combiMax to set
	 */
	/*
	 * public void setCombiMax(List<Integer> combiMax) { this.combiMax = combiMax; }
	 */

	/**
	 * @return the genereCombi
	 */
	public List<Integer> getGenereCombi() {
		return genereCombi;
	}

	/**
	 * @param genereCombi the genereCombi to set
	 */
	/*
	 * public void setGenereCombi(List<Integer> genereCombi) { this.genereCombi =
	 * genereCombi; }
	 */

}