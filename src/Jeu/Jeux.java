package Jeu;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La classe Jeux d�finit les 3 modes de jeux (Challenger, D�fenseur et Duel) et une m�thode pour demander � rejouer 
 * @author agarnij
 *
 */

public class Jeux {
	static final Logger logger = LogManager.getLogger();
	private String jeuChoisi = "non"; // contient "non" si pas encore de choix ou Challenger ou D�fenseur ou Duel
	private boolean choix = false; //si on a choisi un jeu, cette variable est vraie sinon elle est fausse
	protected Scanner sc = new Scanner(System.in); // lecture des entr�es au clavier
	//private int nbCoups = 0; // Contient le nombre de propositions effectu�es ... d�plac� dans la classe JouerMode
	static int choixMode = -1; // Contient le n� du jeu choisi et conserve ce choix pour la prochaine instance si le joueur souhaite rejouer au m�me jeu
	
	/**
	 * Affiche les menus et intercepte les choix de l'utilisateur
	 * @param parametres
	 */
	public Jeux(Config parametres, int numMemeJeu) {
		
		if (!choix && (numMemeJeu == 0 || numMemeJeu == 2)) {
            System.out.println("A quel jeu souhaitez-vous jouer ?");
            int i = 0;
            //Liste des �num�rations de ChoixJeux
            for (ChoixJeux detail: ChoixJeux.values()) {
            	i++;
            	System.out.println(i + " - " + detail.toString());
            }
            /*
            System.out.println("1 - Challenger : A vous de trouver le code d�tenu par la Machine");
            System.out.println("2 - D�fenseur : La Machine cherche votre code");
            System.out.println("3 - Duel : le premier qui trouve le code de l'autre a gagn� !");
            */
            while (!choix) {
            	boolean typeReponseOK;
                do {
                    try {
                    	choixMode = sc.nextInt();
                    	if (choixMode > 0 && choixMode <= ChoixJeux.values().length) {
                    		typeReponseOK = true;
                    	} else {
                            System.out.println("Le nombre saisi doit �tre compris entre 1 et "+ ChoixJeux.values().length + ". Recommencez !");
                            logger.error("R�ponse inattendue dans le choix des jeux ... l'entier saisi ne correspond � aucun jeu !");
                    		typeReponseOK = false;
                    	}
                   	
                    } catch (InputMismatchException e) {
                        sc.next();
                        System.out.println("Vous devez saisir un nombre entre 1 et "+ ChoixJeux.values().length + " pour d�clencher le jeu correspondant !");
                        logger.error("R�ponse inattendue dans le choix des jeux ... ce n'est pas un entier !");
                        typeReponseOK = false;
                    }
                } while (!typeReponseOK);

                sc.nextLine();

                switch (choixMode) {
                    case 1:
                        this.jeuChoisi = ChoixJeux.Challenger.getNom();
                        choix = true;
                        break;

                    case 2:
                        this.jeuChoisi = ChoixJeux.Defenseur.getNom();
                        choix = true;
                        break;

                    case 3:
                        this.jeuChoisi = ChoixJeux.Duel.getNom();
                        choix = true;
                        break;

                    default:
                        System.out.println("Vous avez fait une erreur, ce choix n'existe pas !");

                }
                System.out.println("Mode " + this.jeuChoisi + " s�lectionn� !");
                logger.info("Mode "+ this.jeuChoisi + " s�lectionn� !");	
            }
            
          
        }
		JouerMode quelMode = new JouerMode();
		lancementJeu(parametres, choixMode, quelMode);
         	
 	}
	
	public void lancementJeu(Config para, int jeuChoix, JouerMode quelMode) {
		switch(jeuChoix) {
		case 1:
			quelMode.ModeChallenger(para);
			break;
		case 2:
			quelMode.ModeDefenseur(para);
			break;
		case 3:
			quelMode.ModeDuel(para);
			break;
		}
		
	}
	
	/*
	public void ModeChallenger(Config listeParametres) {
		String indices = ""; // contient les +, - ou = ... indices donn�s par la machine
		String prop = ""; // Contient la proposition d'une combinaison 
		boolean perdu = false;
		String aTrouver = "";
		
		Joueur machine = new Joueur(listeParametres.getNbChiffres());
		
		
		for (int i = 0; i < machine.creeCombi.getGenereCombi().size(); i++) {
			aTrouver += machine.creeCombi.getGenereCombi().get(i).toString();
		}
				
		if(listeParametres.getModeDeveloppeur()) {
			
			System.out.println("Mode d�veloppeur activ� - La combinaison � trouver est : " + aTrouver);
			}
		System.out.println("Essayez de deviner ma combinaison, veuillez entrer une premi�re proposition de " + machine.creeCombi.getGenereCombi().size() + " chiffres");
		prop = sc.nextLine();
		
		while (!machine.gagne && !perdu) {
			nbCoups ++;
			indices = machine.Compare(prop);
			
			if (nbCoups == 1) {
				System.out.print(nbCoups + "�re Proposition : ");
			}else {
				System.out.print(nbCoups + "�me Proposition : ");
			}
		
			System.out.println(prop + " : " + indices);
			
			if (nbCoups == listeParametres.getNbEssais() && !machine.gagne) {
				perdu = true;
			} else if(!machine.gagne){
				System.out.println("Quelle est votre nouvelle proposition ?");
				prop = sc.nextLine();
			}
		} 
		
		if(perdu) {
			System.out.println("Vous avez perdu, il fallait trouver la combinaison : " + aTrouver);
		}else {
			System.out.println("Bravo, vous avez trouv� la bonne combinaison en " + nbCoups + " essai(s) !");
		}
		
	}

	public void ModeDefenseur(Config listeParametres) {
		String indices = ""; // contient les +, - ou = ... indices donn�s par le joueur
		String prop = ""; // Contient la proposition d'une combinaison calcul�e par le programme 
				
		Joueur homme = new Joueur(listeParametres.getNbChiffres());
		
		do {
			nbCoups ++;
			prop = "";
			for (int i=0; i < homme.creeCombi.getPropositionCombi().size(); i++) {
				prop += homme.creeCombi.getPropositionCombi().get(i).toString();
			}
			if (nbCoups ==1) {
				System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres � deviner est plus grand, moins grand ou �gal � ceux qui sont propos�s !");
				System.out.print(nbCoups + "�re Proposition : ");
			}else {
				System.out.print(nbCoups + "�me Proposition : ");
			}
		
			System.out.println(homme.creeCombi.getPropositionCombi());
			
			indices = sc.nextLine();
			
		} while (!(homme.nouvelleCombinaison(indices, homme.creeCombi.getPropositionCombi())) && (nbCoups < listeParametres.getNbEssais()));
		
		if(homme.nouvelleCombinaison(indices, homme.creeCombi.getPropositionCombi())) {
			System.out.println("Le programme a trouv� la combinaison en " + nbCoups + " essai(s) !");
		}else {
			System.out.println("Bravo, le programme n'a pas trouv� votre combinaison et a atteint le nb de coups max : " + nbCoups + " essai(s) !");
		}
	}
	
public void ModeDuel(Config listeParametres) {
	
	// Le mode duel consiste � alterner le mode challenger et le mode Defenseur
	String indices = ""; // contient les +, - ou = ... indices donn�s par la machine
	String prop = ""; // Contient la proposition d'une combinaison calcul�e par le programme 
	boolean perdu = false;
	String aTrouver = "";
	int nbSize = 0;
	
	String indicesHomme = ""; // contient les +, - ou = ... indices donn�s par le joueur
	String propMachine = ""; // Contient la proposition d'une combinaison calcul�e par le programme 
			
	System.out.println("Mode Duel activ� - Chacun d'entre nous, alternativement, doit tenter de trouver la combinaison de l'autre !");
	System.out.println("Celui qui la trouve le plus vite a gagn� !");
	
	Joueur homme = new Joueur(listeParametres.getNbChiffres()); //Idem mode Defenseur
	Joueur machine = new Joueur(listeParametres.getNbChiffres()); // Idem mode Challenger
	
	for (int i = 0; i < machine.creeCombi.getGenereCombi().size(); i++) {
		aTrouver += machine.creeCombi.getGenereCombi().get(i).toString(); // contient la fameuse combinaison d�finie par le programme que le joueur doit trouver
	}
	
	// Idem mode Challenger
	if(listeParametres.getModeDeveloppeur()) {
		System.out.println("Mode d�veloppeur activ� - La combinaison � trouver est : " + aTrouver);
		}
	
	nbSize = machine.creeCombi.getGenereCombi().size();
	
	
	// Idem mode Challenger
	while (!machine.gagne && !perdu && !homme.gagne) {
		//propMachine = "";
		// Idem mode Challenger
		System.out.println("Proposition de " + nbSize + " chiffres : ");
		prop = sc.nextLine();
		
		nbCoups ++;
		indices = machine.Compare(prop);
		
		if (nbCoups == 1) {
			System.out.print(nbCoups + "�re Proposition : ");
		}else {
			System.out.print(nbCoups + "�me Proposition : ");
		}
	
		System.out.println(prop + " : " + indices);
		
		if (nbCoups == listeParametres.getNbEssais() && !machine.gagne) {
			perdu = true;
			System.out.println("Vous avez perdu, le nombre maximum d'essais est atteint, il fallait trouver la combinaison : " + aTrouver);
			System.out.println("Voyons si la machine fait mieux !");
		}
		if(machine.gagne){
			System.out.println("Bravo, vous avez trouv� la bonne combinaison en " + nbCoups + " essai(s) !");
			System.out.println("Voyons si la machine fait aussi bien pour son dernier essai !");
		}
		
		//Partie mode Defenseur
		propMachine = "";
		for (int i=0; i < homme.creeCombi.getPropositionCombi().size(); i++) {
			propMachine += homme.creeCombi.getPropositionCombi().get(i).toString();
		}
		if (nbCoups ==1) {
			System.out.println("Merci d'indiquer des +, des - ou des = lorsque chacun des chiffres � deviner est plus grand, moins grand ou �gal � ceux qui sont propos�s !");
			System.out.print("Mode D�fenseur - " + nbCoups + "�re Proposition : ");
		}else {
			System.out.print("Mode D�fenseur - " + nbCoups + "�me Proposition : ");
		}
		System.out.println(propMachine);//homme.creeCombi.propositionCombi
		
		indicesHomme = sc.nextLine();
		
		if (nbCoups == listeParametres.getNbEssais() && !homme.gagne) {
			perdu = true;
			System.out.println("La machine a perdu, le nombre maximum d'essais est atteint !");
		}
		
		if(homme.nouvelleCombinaison(indicesHomme, homme.creeCombi.getPropositionCombi())) {
			System.out.println("La machine a trouv� la bonne combinaison en " + nbCoups + " essai(s) !");
		}
	} 
	
	// Si l'homme gagne et que la machine a perdu :
	if (nbCoups != listeParametres.getNbEssais() && !homme.gagne) {
		perdu = true;
		System.out.println("La machine a perdu !");
	}
}

// Ancienne m�thode pour rejouer mais ne prend pas en compte le fait de rejouer le m�me jeu.
public boolean rejouer(){
    while (true){
        System.out.println("Souhaitez-vous rejouer ? - O / N ");
        String choixRejouer = sc.nextLine();
        choixRejouer = choixRejouer.toUpperCase();
        switch(choixRejouer){
            case "O" :
                return true;
            case "N" :
                return false;
            default :
                System.out.println("Je n'ai pas compris votre r�ponse !");
                continue;
        	}
    	}
	}*/

public Integer choixFinPartie() {
	boolean choixOK = false;
	int retourChoix = 0;
	do {
        try {
        	System.out.println("Que souhaitez-vous faire maintenant ? \n" +
    	            "1 - Relancer le m�me jeu ? \n" +
    	            "2 - Revoir le choix de tous les jeux ? \n" +
    	            "3 - Quitter l'application ? \n");
    	    System.out.print("Votre choix ? 1 - 2 ou 3 : ");
        	retourChoix = sc.nextInt();
        	if (retourChoix > 0 && retourChoix < 4) {
        		choixOK = true;
        	} else {
                System.out.println("Le nombre saisi doit �tre compris entre 1 et 3. Recommencez !");
                logger.error("R�ponse inattendue dans le choix des menus ... l'entier saisi ne correspond � aucun jeu !");
        		choixOK = false;
        		retourChoix = 0;
        	}
       	
        } catch (InputMismatchException e) {
            sc.next();
            System.out.println("Vous devez saisir un nombre entre 1 et 3 pour d�clencher l'action correspondante !");
            logger.error("R�ponse inattendue dans le choix des menus (1 � 3) ... ce n'�tait pas un entier !");
            choixOK = false;
            retourChoix = 0;
        }
    } while (!choixOK);

	return retourChoix;
	
	}
}

