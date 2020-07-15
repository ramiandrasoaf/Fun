package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class Puissance4 extends JFrame {
	private static final long serialVersionUID = 3660342240433840428L;

	private static int tour;
	private JLabel annonce;
	private String joueur;
	private static final int COLUMN = 7;
	private static final int ROW = 6;
	private ModeJeu modeJeurActuel=ModeJeu.HOMME_VS_HOMME;

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

		joueur="ROUGE";
		annonce= new JLabel("Joueur "+ joueur+", � vous de jouer ! ");
		barreMenu.add(annonce);
		JLabel tourJoueur = new JLabel("  Tour 0");
		barreMenu.add(tourJoueur);
		JPanel jeu = new JPanel();
		jeu.setSize(400, 300);
		jeu.setLayout(new GridLayout(ROW, COLUMN));
		jeu.setBackground(Color.GRAY);

		Panneau[][] panneaux = new Panneau[ROW][COLUMN];

		for (int rowIndex = 0; rowIndex < ROW; rowIndex++) {
			for (int colIndex = 0; colIndex < COLUMN; colIndex++) {
				Panneau panneau = new Panneau(rowIndex, colIndex);
				panneaux[rowIndex][colIndex] = panneau;
				panneau.addMouseListener(new Jouer(panneaux, tourJoueur, modeJeurActuel));
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
			modeJeurActuel = modeJeuEnum;
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

	
	class Jouer extends MouseAdapter {

		private JLabel tourJoueur;
		private final Panneau[][] panneaux;
		private final ModeJeu modeJeu;

		public Jouer(Panneau[][] panneaux, JLabel tourJoueur, ModeJeu modeJeu) {
			this.tourJoueur = tourJoueur;
			this.panneaux = panneaux;
			this.modeJeu = modeJeu;
		}

		public Panneau[][] getPanneaux() {
			return panneaux;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			tour++;
			tourJoueur.setText("  Tour "+Integer.toString(tour));
			Panneau pan = (Panneau) e.getSource();
			Color color = tour % 2 == 1 ? Color.RED : Color.YELLOW;
			if(tour%2==1) {
				joueur="JAUNE";
			}else if (tour%2==0) {
				joueur="ROUGE";
			}
			annonce.setText("Joueur "+ joueur+", � vous de jouer ! ");

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

			boolean valide = jouer(panneaux, pan.colIndex, color);

			if (!valide) {
				annonce.setText("Ce coup n'est pas valide !");
			}

			boolean gagne = estCeGagne(panneaux, color);
			boolean plein = plein(panneaux);

			if (gagne) {
				if (color == Color.YELLOW) {
					annonce.setText("Le joueur JAUNE a gagn� !");
				} else {
					annonce.setText("Le joueur ROUGE a gagn� !");
				}
				tour = 0;
			} else if (plein) {
				annonce.setText("Il y a match nul !");
				tour = 0;
			}
		}

		public boolean plein(Panneau[][] panneaux) {
			// si on trouve une case vide sur la 1�re ligne, la grille n'est pas pleine :
			for (Panneau panneau : panneaux[0]) {
				if (panneau.getForeground() == Color.WHITE) {
					return false;
				}
			}
			// sinon, la grille est pleine :
			return true;
		}

		public boolean jouer(Panneau[][] panneaux, int colonne, Color couleur) {
			// si la colonne est pleine, le coup n'est pas valide :
			if (panneaux[0][colonne].getForeground() != Color.WHITE) {
				return false;
			}
			// on parcourt la colonne du bas jusqu'� la premi�re case vide :
			int ligne = panneaux.length - 1;
			while (panneaux[ligne][colonne].getForeground() != Color.WHITE) {
				--ligne;
			}
			// on remplit la case vide trouv�e :
			panneaux[ligne][colonne].setForeground(couleur);
			return true;
		}

		public boolean estCeGagne(Panneau[][] panneaux, Color couleurJoueur) {
			boolean gagnant=false;
			Scan s=new Scan(panneaux);
			for(int ligne=0;ligne<panneaux.length-4;ligne++) {
				for(int col=0;col<panneaux[ligne].length-4;col++) {
					if(s.trouver4(panneaux,ligne,col)) {
						gagnant=true;
					}
				}
			}
			return gagnant;
		}
	}
	
	
	class Scan {
		//Pour trouver 4 pions c�te � c�te et de la m�me couleur
		private final Panneau [][]panneaux;
		
		public Scan(Panneau[][] panneaux) {
			this.panneaux=panneaux;
		}
		
		private boolean getNext(Panneau[][] pan,int ligne,int colonne) {
			
			return false;
		}
		
		private boolean horizontal(Panneau [][] p,int ligne,int colonne) {
			boolean horizontal=false;
		        //On scanne de gauche � droite 
			if((p[ligne][colonne+1].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne][colonne+2].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne][colonne+3].getForeground()==p[ligne][colonne].getForeground()) ||
					
				//On scanne de droite � gauche	
			   (p[ligne][colonne-1].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne][colonne-2].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne][colonne-3].getForeground()==p[ligne][colonne].getForeground())) {
				horizontal=true;
			}
			return horizontal;
		}
		
		private boolean vertical(Panneau [][] p,int ligne,int colonne) {
			boolean vertical=false;
		        //On scanne de haut en bas 
			if((p[ligne+1][colonne].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne+2][colonne].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne+3][colonne].getForeground()==p[ligne][colonne].getForeground()) ||
					
				//On scanne de bas en haut		
			   (p[ligne-1][colonne].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne-2][colonne].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne-3][colonne].getForeground()==p[ligne][colonne].getForeground())) {
				vertical=true;
			}
			return vertical;
		}
		
		private boolean diagonal(Panneau [][] p,int ligne,int colonne) {
			boolean diagonal=false;
		        //On scanne en haut � droite 
			if((p[ligne-1][colonne+1].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne-2][colonne+2].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne-3][colonne+3].getForeground()==p[ligne][colonne].getForeground()) ||
					
				//On scanne en bas � droite
			   (p[ligne+1][colonne+1].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne+2][colonne+2].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne+3][colonne+3].getForeground()==p[ligne][colonne].getForeground()) ||
			   
			    //On scanne en haut � gauche
			   (p[ligne-1][colonne-1].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne-2][colonne-2].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne-3][colonne-3].getForeground()==p[ligne][colonne].getForeground()) ||
			   
			    //On scanne en bas � gauche
			   (p[ligne+1][colonne-1].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne+2][colonne-2].getForeground()==p[ligne][colonne].getForeground() &&
					p[ligne+3][colonne-3].getForeground()==p[ligne][colonne].getForeground())) {
				diagonal=true;
			}	
			return diagonal;
		}
		
		public boolean trouver4(Panneau [][] p,int ligne,int colonne) {
			boolean trouve=false;
			if (horizontal(p,ligne,colonne) || vertical(p,ligne,colonne) || diagonal(p,ligne,colonne)) {
				trouve=true;
			}
			return trouve;
		}
		
	}
}