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

		JLabel tourJoueur = new JLabel("Joueur " + ", à  vous de jouer !");
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
			tourJoueur.setText(Integer.toString(tour));
			Panneau pan = (Panneau) e.getSource();
			Color color = tour % 2 == 1 ? Color.RED : Color.YELLOW;

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
				tourJoueur.setText("Ce coup n'est pas valide !");
			}

			boolean gagne = estCeGagne(panneaux, color);
			boolean plein = plein(panneaux);

			if (gagne) {
				if (color == Color.YELLOW) {
					tourJoueur.setText("Le joueur O a gagnï¿½ !");
				} else {
					tourJoueur.setText("Le joueur X a gagnï¿½ !");
				}
				tour = 0;
			} else if (plein) {
				tourJoueur.setText("Il y a match nul !");
				tour = 0;
			}
		}

		public boolean plein(Panneau[][] panneaux) {
			// si on trouve une case vide sur la 1ï¿½re ligne, la grille n'est pas pleine :
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
			// on parcourt la colonne du bas jusqu'ï¿½ la premiï¿½re case vide :
			int ligne = panneaux.length - 1;
			while (panneaux[ligne][colonne].getForeground() != Color.WHITE) {
				--ligne;
			}
			// on remplit la case vide trouvée :
			panneaux[ligne][colonne].setForeground(couleur);
			return true;
		}

		public boolean estCeGagne(Panneau[][] panneaux, Color couleurJoueur) {
			boolean gagnant=false;
			for(int ligne=panneaux.length-1;ligne>=0;ligne--) {
				for(int col=panneaux[ligne].length-1;col>=0;col--) {
					couleurJoueur=panneaux[ligne][col].getForeground();
					if(ligne==5) {
						if(col<=3) {
							if((panneaux[ligne][col+1].getForeground()==couleurJoueur && 
								panneaux[ligne][col+2].getForeground()==couleurJoueur &&
								panneaux[ligne][col+3].getForeground()==couleurJoueur) //||
							   /*(panneaux[ligne+1][col].getForeground()==couleurJoueur && 
								panneaux[ligne+2][col].getForeground()==couleurJoueur &&
								panneaux[ligne+3][col].getForeground()==couleurJoueur) ||
							   (panneaux[ligne+1][col+1].getForeground()==couleurJoueur && 
								panneaux[ligne+2][col+2].getForeground()==couleurJoueur &&
								panneaux[ligne+3][col+3].getForeground()==couleurJoueur)*/) {
								gagnant=true;
							}
						}
					}else {
						gagnant=false;
					}
				}
			}
			/*couleurJoueur=panneaux[5][0].getForeground();
			if((panneaux[5][1].getForeground()==couleurJoueur && panneaux[5][2].getForeground()==couleurJoueur) ||
					(panneaux[4][0].getForeground()==couleurJoueur && panneaux[3][0].getForeground()==couleurJoueur)){
				gagnant= true;
			}else {
				gagnant=false;
			}*/
			return gagnant;
		}

		/*public int compter(Panneau[][] panneaux, int ligDepart, int colDepart, int dirLigne, int dirColonne) {
			int compteur = 0;
			int ligne = ligDepart;
			int colonne = colDepart;
			// on part de la case (ligDepart,colDepart) et on parcourt la grille
			// dans la direction donnée par (dirLigne,dirColonne)
			// tant qu'on trouve des pions de la mï¿½me couleur que le pion de départ :
			while (panneaux[ligne][colonne] == panneaux[ligDepart][colDepart] && ligne >= 0 && ligne < panneaux.length
					&& colonne >= 0 && colonne < panneaux[ligne].length) {
				++compteur;
				ligne = ligne + dirLigne;
				colonne = colonne + dirColonne;
			}
			return compteur;
		}*/
	}
}