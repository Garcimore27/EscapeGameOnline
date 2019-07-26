package Jeu;


//Definit les modes de jeu possibles
public interface Jouable {

	public void ModeChallenger(Config listeParametres);
	
	public void ModeDefenseur(Config listeParametres);
	
	public void ModeDuel(Config listeParametres);
	
}
