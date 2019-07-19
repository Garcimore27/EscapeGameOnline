package Jeu;

import java.util.List;

public class Joueur {
	
	Combinaison creeCombi;
	boolean gagne = false;
	
	
	public Joueur(int nbChiffres){
		creeCombi = new Combinaison(nbChiffres);
	}
	
	/**
	 * M�thode qui compare la proposition du joueur � la combinaison g�n�r�e par l'ordinateur qu'il doit trouver 
	 * @param proposition Proposition du Joueur
	 * @return retourne une suite de +, - ou = en fonctions des chiffres � trouver et de la proposition du joueur
	 */
	
	public String Compare(String proposition) {
		String reponse = "";
		int chiffresOK = 0;
		gagne = false;
		if (proposition.length() != this.creeCombi.getGenereCombi().size()) {
			System.out.println("D�sol�, la longueur propos�e n'est pas la bonne, ceci est compt� comme un essai quand m�me ");
		}
		// Si la proposition est trop longue, on ne prend que les X premiers car. correspondant � la taille de la combinaison � trouver
		if (proposition.length() > this.creeCombi.getGenereCombi().size()) {
			proposition = proposition.substring(0,this.creeCombi.getGenereCombi().size());
			System.out.println("Seuls les " + this.creeCombi.getGenereCombi().size() + " premiers chiffres de votre proposition sont pris en compte ! (" + proposition + ")");
		}
		
		// Si la proposition n'est pas assez longue, on la comble avec des z�ros devant !
		if (proposition.length() < this.creeCombi.getGenereCombi().size()) {
			for (int i = 0; i < this.creeCombi.getGenereCombi().size() - proposition.length() + 1; i++) {
				proposition = "0" + proposition;
			}
			System.out.println("Votre proposition a �t� modifi�e en ceci : " + proposition + " pour �tre �valu�e correctement !");
		}
		for (int i =0; i < this.creeCombi.getGenereCombi().size(); i++) {
			if(this.creeCombi.getGenereCombi().get(i) > Character.getNumericValue(proposition.charAt(i))) {
				reponse+= "+";
			}else
				if(this.creeCombi.getGenereCombi().get(i) < Character.getNumericValue(proposition.charAt(i))) {
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
