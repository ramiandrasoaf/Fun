package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class Puissance4 extends JFrame {
	private static final long serialVersionUID = 3660342240433840428L;

	private static final int NOMBRE_POINT_LIMIT = 4;
	private static int tour;
	private JLabel annonce;
	private String joueur;
	private static final int COLUMN = 7;
	private static final int ROW = 6;
	private ModeJeu modeJeuActuel = ModeJeu.HOMME_VS_HOMME;

	private enum ModeJeu {
		HOMME_VS_HOMME, HOMME_VS_MACHINE, MACHINE_VS_HOMME, MACHINE_VS_MACHINE
	}

	public Puissance4() {
		super("Puissance 4");
		setSize(590, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JMenuBar barreMenu = new JMenuBar();
		setJMenuBar(barreMenu);

		JMenu modeJeu = new JMenu("Mode de jeu");

		JRadioButtonMenuItem homVsMach = new JRadioButtonMenuItem("Homme vs Machine");
		homVsMach.addActionListener(new ModeJeuSelectionListener(ModeJeu.HOMME_VS_MACHINE));

		JRadioButtonMenuItem machVsHom = new JRadioButtonMenuItem("Machine vs Homme");
		machVsHom.addActionListener(new ModeJeuSelectionListener(ModeJeu.MACHINE_VS_HOMME));

		JRadioButtonMenuItem homVsHom = new JRadioButtonMenuItem("Homme vs Homme");
		homVsHom.addActionListener(new ModeJeuSelectionListener(ModeJeu.HOMME_VS_HOMME));

		JRadioButtonMenuItem machVsMach = new JRadioButtonMenuItem("Machine vs Machine");
		machVsMach.addActionListener(new ModeJeuSelectionListener(ModeJeu.MACHINE_VS_MACHINE));

		ButtonGroup gr = new ButtonGroup();
		gr.add(homVsHom);
		gr.add(homVsMach);
		gr.add(machVsHom);
		gr.add(machVsMach);
		modeJeu.add(homVsHom);
		modeJeu.add(homVsMach);
		modeJeu.add(machVsHom);
		modeJeu.add(machVsMach);
		barreMenu.add(modeJeu);

		joueur = "ROUGE";
		annonce = new JLabel("Joueur " + joueur + ", � vous de jouer ! ");
		barreMenu.add(annonce);
		JLabel tourJoueur = new JLabel("  Tour 0");
		barreMenu.add(tourJoueur);
		JPanel jeu = new JPanel();
		jeu.setSize(400, 300);
		jeu.setLayout(new GridLayout(ROW, COLUMN));
		jeu.setBackground(Color.GRAY);

		Panneau[][] panneaux = new Panneau[ROW][COLUMN];

		Jouer mouseListener = new Jouer(panneaux, tourJoueur, modeJeuActuel);
		
		for (int rowIndex = 0; rowIndex < ROW; rowIndex++) {
			for (int colIndex = 0; colIndex < COLUMN; colIndex++) {
				Panneau panneau = new Panneau(rowIndex, colIndex);
				panneaux[rowIndex][colIndex] = panneau;
				panneau.addMouseListener(mouseListener);
				jeu.add(panneau);
			}
		}

		this.getContentPane().add(jeu, BorderLayout.CENTER);
	}

	@SuppressWarnings("unused")
	private final class ModeJeuSelectionListener implements ActionListener {
		private final ModeJeu modeJeuEnum;

		public ModeJeuSelectionListener(ModeJeu modeJeuEnum) {
			this.modeJeuEnum = modeJeuEnum;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			modeJeuActuel = modeJeuEnum;
			if(modeJeuEnum==ModeJeu.HOMME_VS_HOMME) {
				
			}else if (modeJeuEnum==ModeJeu.HOMME_VS_MACHINE) {
				
			}else if(modeJeuEnum==ModeJeu.MACHINE_VS_HOMME) {
				
			}else if(modeJeuEnum==ModeJeu.MACHINE_VS_MACHINE) {
				
			}
		}
	}

	public class Panneau extends JPanel {
		private static final long serialVersionUID = 1L;

		private final int rowIndex;
		private final int colIndex;

		public Panneau(int rowIndex, int colIndex) {
			this.rowIndex = rowIndex;
			this.colIndex = colIndex;
			super.setBackground(Color.BLUE);
			super.setForeground(Color.WHITE);
		}

		public int getRowIndex() {
			return rowIndex;
		}

		public int getColIndex() {
			return colIndex;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.fillOval(15, 10, 60, 60);
		};
	}

	private class Jouer extends MouseAdapter {

		private final JLabel libelle;
		private final Panneau[][] cellules;
		private final ModeJeu modeJeu;		
		private boolean termine = false;
		
		private Scanner scanner;

		public Jouer(Panneau[][] cellules, JLabel libelle, ModeJeu modeJeu) {
			this.libelle = libelle;
			this.cellules = cellules;
			this.modeJeu = modeJeu;
			this.scanner = new Scanner(cellules, NOMBRE_POINT_LIMIT);
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			if(termine) {
				return;
			}
			
			Panneau celluleCliquee = (Panneau) e.getSource();
			tour++;
			libelle.setText("  Tour " + Integer.toString(tour));
			Color color = tour % 2 == 1 ? Color.RED : Color.YELLOW;

			if (tour % 2 == 1) {
				joueur = "JAUNE";
			} else if (tour % 2 == 0) {
				joueur = "ROUGE";
			}

			annonce.setText("Joueur " + joueur + ", � vous de jouer ! ");

			switch (this.modeJeu) {
			case HOMME_VS_HOMME:

				break;
			case MACHINE_VS_HOMME:

				break;
			case HOMME_VS_MACHINE:

				break;
			case MACHINE_VS_MACHINE:

				break;
			default:
				break;
			}

			Panneau celluleJouee = jouer(cellules, celluleCliquee.colIndex, color);

			if (celluleJouee == null) {
				annonce.setText("Ce coup n'est pas valide !");
			}

			boolean gagne = this.scanner.celluleACoteMemeCouleur(celluleJouee);
			boolean plein = this.scanner.premiereLigneCellulesPleine();

			if (gagne) {
				if (color == Color.YELLOW) {
					annonce.setText("Le joueur JAUNE a gagn� !");
				} else {
					annonce.setText("Le joueur ROUGE a gagn� !");
				}
				tour = 0;
				termine = true;
			} else if (plein) {
				annonce.setText("Il y a match nul !");
				tour = 0;
			}
		}

		/**
		 * Si la colonne est pleine et couleur diff�rente de blanc alors la m�thode
		 * retourne null. Sinon elle empile la cellule du bas en haut dans une colonne
		 * quelque soit la couleur de la cellule.
		 * 
		 * @param cellules tableau de cellules
		 * @param colonne la colonne � empiler
		 * @param couleur couleur de la cellule empil�e
		 * @return
		 */
		public Panneau jouer(Panneau[][] cellules, int colonne, Color couleur) {
			
			// si la colonne est pleine, le coup n'est pas valide :
			if (cellules[0][colonne].getForeground() != Color.WHITE) {
				return null;
			}
			
			// on parcourt la colonne du bas jusqu'� la premi�re case vide :
			int ligne = cellules.length - 1;
			while (cellules[ligne][colonne].getForeground() != Color.WHITE) {
				--ligne;
			}
			// on remplit la case vide trouv�e :
			cellules[ligne][colonne].setForeground(couleur);
			return cellules[ligne][colonne];
		}
		
		public void Machine(Panneau [][] panneaux,Color couleurMachine) {
			
		}
	}

	/**
	 * Cette classe contient les informations con�ernant les coordonn�es d'un point.
	 * 
	 * @author fahendrena
	 *
	 */
	private class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	/**
	 * Cette interface permet de cr�er des points appartenant � un segment de
	 * direction bien d�finie.
	 * 
	 * @author fahendrena
	 *
	 */
	private interface Direction {

		/**
		 * Cette m�thode cr�e des points appartenant � un segment par rapport � un point
		 * origine de coordonn�e <code>originX</code> et <code>originY</code>
		 * 
		 * @param originX origine x
		 * @param originY origine y
		 * @param raison  un nombre servant � une incrementation
		 * @return un point du segment
		 */
		Point creePoint(int originX, int originY, int raison);
	}

	/**
	 * Cette classe a pour responsabilit� d'executer un scan des cellules plac�es
	 * autour d'une cellule de reference ou origine de m�me couleur.
	 * 
	 * @author fahendrena
	 *
	 */
	@SuppressWarnings("unused")
	private class Scanner {

		// Pour trouver 4 pions c�te � c�te et de la m�me couleur
		private final Panneau[][] cellules;
		private final int nbPointLimite;

		// 8 repr�sente le nombre de directions
		private final Direction[] directions = new Direction[8];

		/**
		 * Cr�e un objet d'instance de cette classe.
		 * 
		 * @param cellules      liste des cellules
		 * @param nbPointLimite nombre de point limite � parcourir
		 */
		public Scanner(Panneau[][] cellules, int nbPointLimite) {
			this.cellules = cellules;
			this.nbPointLimite = nbPointLimite;
			creeDirections();
		}

		/**
		 * Cr�e les 8 directions
		 */
		private void creeDirections() {
			// horizontal droite
			directions[0] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x + raison</code> , <code>y</code>). par rapport � l'origine originX,
				 * originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX + raison, originY);
				}
			};

			// diagonal bas droite
			directions[1] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x + raison</code> , <code>y + raison</code>). par rapport � l'origine
				 * originX, originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX + raison, originY + raison);
				}
			};

			// vertical bas haut
			directions[2] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x</code> , <code>y - raison</code>). par rapport � l'origine originX,
				 * originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX, originY - raison);
				}
			};

			// vertical haut bas
			directions[3] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x</code> , <code>y + raison</code>). par rapport � l'origine originX,
				 * originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX, originY + raison);
				}
			};

			// diagonal haut droite
			directions[4] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x + raison</code> , <code>y - raison</code>). par rapport � l'origine
				 * originX, originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX + raison, originY - raison);
				}
			};

			// diagonal haut gauche
			directions[5] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x - raison</code> , <code>y - raison</code>). par rapport � l'origine
				 * originX, originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX - raison, originY - raison);
				}
			};

			// horizontal gauche
			directions[6] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x - raison</code> , <code>y</code>). par rapport � l'origine originX,
				 * originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX - raison, originY);
				}
			};

			// diagonal bas gauche
			directions[7] = new Direction() {

				/**
				 * Cette methode cr�e un point appartenant au segment d'�quation
				 * Q(<code>x - raison</code> , <code>y + raison</code>). par rapport � l'origine
				 * originX, originY
				 */
				@Override
				public Point creePoint(int originX, int originY, int raison) {
					return new Point(originX - raison, originY + raison);
				}
			};

		}

		/**
		 * V�rifie si des cellules plac�es c�te � c�te par rapport � la cellule de
		 * reference sont de m�me couleur que la cellule de reference.
		 * 
		 * @param celluleReference cellule de reference
		 * @return Vrai si la condition est satisfaite. Sinon faux.
		 */
		public boolean celluleACoteMemeCouleur(Panneau celluleReference) {

			// Balayage rotationel de direction
			for (Direction direction : directions) {
				boolean celluleMemeCouleur = false;

				// Iteration commence par 1 car on n'a pas besoin de cr�er et cherche la cellule
				// origine car elle est d�j� fournie
				for (int raison = 1; raison < nbPointLimite; raison++) {
					Point point = direction.creePoint(celluleReference.getColIndex(), celluleReference.getRowIndex(),
							raison);
					Panneau cellule = chercheCelluleAu(point);

					if (cellule == null || cellule.getForeground() != celluleReference.getForeground()) {
						celluleMemeCouleur = false;
						break;
					} else {
						celluleMemeCouleur = true;
					}
				}

				if (celluleMemeCouleur) {
					return true;
				}
			}

			return false;

		}

		public boolean premiereLigneCellulesPleine() {
			// si on trouve une case vide sur la 1�re ligne, la grille n'est pas pleine :
			for (Panneau cellulesEnLigne : cellules[0]) {
				if (cellulesEnLigne.getForeground() == Color.WHITE) {
					return false;
				}
			}
			// sinon, la grille est pleine :
			return true;
		}

		/**
		 * Cherche la cellule plac�e au point P(x,y)
		 * 
		 * @param point point � laquelle la cellule correspond
		 * @return Null si la cellule est introuvable. Sinon retourne la cellule
		 *         trouv�e.
		 */
		private Panneau chercheCelluleAu(Point point) {
			for (Panneau[] panneaus : cellules) {
				for (Panneau celluleActuelle : panneaus) {
					if (celluleActuelle.getRowIndex() == point.getY()
							&& celluleActuelle.getColIndex() == point.getX()) {
						return celluleActuelle;
					}
				}
			}

			return null;
		}
	}
}