package Jeu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerificationErreurs {
	static final Logger logger = LogManager.getLogger();
	String regex = "\\d+";
	
	
	public VerificationErreurs() {
		
	}
	
	public String VerifChiffres(String proposition, Combinaison creeCombi) {
		// proposition vide
		if (proposition.length() == 0) {
			for (int i = 0; i < creeCombi.getGenereCombi().size() ; i++) {
				proposition = "0" + proposition;
			}
			System.out.println("Votre proposition a �t� modifi�e en ceci : " + proposition + " pour �tre �valu�e correctement !");
		}
		
		
		if (proposition.length() != creeCombi.getGenereCombi().size()) {
			System.out.println("D�sol�, la longueur propos�e n'est pas la bonne, ceci est compt� comme un essai quand m�me ");
		}
		// Si la proposition est trop longue, on ne prend que les X premiers car. correspondant � la taille de la combinaison � trouver
		if (proposition.length() > creeCombi.getGenereCombi().size()) {
			proposition = proposition.substring(0,creeCombi.getGenereCombi().size());
			System.out.println("Seuls les " + creeCombi.getGenereCombi().size() + " premiers chiffres de votre proposition sont pris en compte ! (" + proposition + ")");
		}
		
		// Si la proposition n'est pas assez longue, on la comble avec des z�ros devant !
		if (proposition.length() < creeCombi.getGenereCombi().size()) {
			for (int i = 0; i < creeCombi.getGenereCombi().size() - proposition.length() + 1; i++) {
				proposition = "0" + proposition;
			}
			System.out.println("Votre proposition a �t� modifi�e en ceci : " + proposition + " pour �tre �valu�e correctement !");
		}
		
		
		
		// Si la proposition ne contient pas que des chiffres !
		if ((!proposition.matches(regex))&& proposition.length() > 0) {
				int i = 0;
				for (char c : proposition.toCharArray()) {
		            if (!Character.isDigit(c)) {
		                if(i == 0) {
		                	proposition = "0" + proposition.substring(1, proposition.length());
		                } else if(i == proposition.length() - 1) {
		                	proposition = proposition.substring(0, proposition.length() - 1) + "0";
		                } else {
		                	proposition = proposition.substring(0, i) + "0" + proposition.substring(i+1, creeCombi.getGenereCombi().size());
		                }
 		            }
		            i += 1;
		        }
				
			System.out.println("Chaque caract�re qui n'est pas un chiffre a �t� remplac� par un 0 pour �tre �valu� correctement ! \n "
					+ "Voici votre proposition modifi�e : " + proposition);
		}
		
		return proposition;
	}
	
	//M�thode permettant de v�rifier que la saisie ne contient que des signes
    public boolean estCeQueSignesOK(String saisie, String prop) {
        boolean signesOK = true;
        char[] signesSaisie = saisie.toCharArray();

        if (saisie.length() != prop.length()) {
            signesOK = false;
        }

        for (char signe : signesSaisie) {
            if ((!String.valueOf(signe).equals("+")
                    && !String.valueOf(signe).equals("-")
                    && !String.valueOf(signe).equals("="))) {
                signesOK = false;
            }
        }
        return signesOK;
    }

}
