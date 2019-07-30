package Jeu;

import java.util.List;

public class Joueur {
	
	Combinaison creeCombi;
	boolean gagne = false;
	//String regex = "\\d+";
	
	
	public Joueur(int nbChiffres){
		creeCombi = new Combinaison(nbChiffres);
	}
	
	/**
	 * Méthode qui compare la proposition du joueur à la combinaison générée par l'ordinateur qu'il doit trouver 
	 * @param proposition Proposition du Joueur
	 * @return retourne une suite de +, - ou = en fonctions des chiffres à trouver et de la proposition du joueur
	 */
	
	public String Compare(String proposition) {
		String propModifiee = ""; //proposition corrigée par la gestion des erreurs
		String reponse = "";
		int chiffresOK = 0;
		gagne = false;
		VerificationErreurs vErreurs = new VerificationErreurs();
		propModifiee = vErreurs.VerifChiffres(proposition, this.creeCombi); //Verification des chiffres
		
		for (int i =0; i < this.creeCombi.getGenereCombi().size(); i++) {
			if(this.creeCombi.getGenereCombi().get(i) > Character.getNumericValue(propModifiee.charAt(i))) {
				reponse+= "+";
			}else
				if(this.creeCombi.getGenereCombi().get(i) < Character.getNumericValue(propModifiee.charAt(i))) {
					reponse += "-";
				} else {
					reponse+= "=";
					chiffresOK ++;
				}
		}
		if(this.creeCombi.getGenereCombi().size() == chiffresOK) {
			gagne = true;
		}
		return reponse;
	}
	
	public boolean nouvelleCombinaison(String plusMoins, List<Integer> proposition) {

		int chiffresOK = 0;
		gagne = false;
		
		for (int i = 0; i < plusMoins.length(); i++) {
			int chiffreEnCours = proposition.get(i);
			int maxEnCours = this.creeCombi.getCombiMax().get(i);
			int minEnCours = this.creeCombi.getCombiMin().get(i);
			switch (plusMoins.charAt(i)) {
				case '+':
					this.creeCombi.getPropositionCombi().set(i, (int) ((maxEnCours + chiffreEnCours) / 2));
					this.creeCombi.getCombiMin().set(i, (int) (chiffreEnCours));
					break;
				case '-':
					this.creeCombi.getPropositionCombi().set(i, (int) ((minEnCours + chiffreEnCours) / 2));
					this.creeCombi.getCombiMax().set(i, (int) (chiffreEnCours));
					break;
				case '=':
					this.creeCombi.getCombiMax().set(i, (chiffreEnCours));
					this.creeCombi.getCombiMin().set(i, (chiffreEnCours));
					chiffresOK++;
					break;
			}
		}
		if (chiffresOK == proposition.size()) {
			gagne = true;
		}
		return gagne;
	}
}
