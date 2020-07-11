package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
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
	
	private static int tour;
	private static final int COLUMN = 7;
	private static final int ROW = 6;

	public Puissance4() {
		super("Puissance 4");
		setSize(590, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JMenuBar barreMenu = new JMenuBar();
		setJMenuBar(barreMenu);
		JMenu modeJeu = new JMenu("Mode de jeu");
		JRadioButtonMenuItem homVsMach = new JRadioButtonMenuItem("Homme vs Machine");
		JRadioButtonMenuItem machVsHom = new JRadioButtonMenuItem("Machine vs Homme");
		JRadioButtonMenuItem homVsHom = new JRadioButtonMenuItem("Homme vs Homme");
		JRadioButtonMenuItem machVsMach = new JRadioButtonMenuItem("Machine vs Machine");
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

		JLabel tourJoueur = new JLabel("Joueur " + ", à vous de jouer !");
		barreMenu.add(tourJoueur);
		JPanel jeu = new JPanel();
		jeu.setSize(400, 300);
		jeu.setLayout(new GridLayout(6, 7));
		jeu.setBackground(Color.GRAY);

		Jouer jouer = new Jouer(tourJoueur);
		
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				jeu.add(new Panneau(jouer));
			}
		}

		this.getContentPane().add(jeu, BorderLayout.CENTER);

	}

	public class Panneau extends JPanel {
		private static final long serialVersionUID = 1L;

		public Panneau(MouseAdapter adapter) {
			this.addMouseListener(adapter);
		}

		public void colorer(Color color) {	
			Graphics g = super.getGraphics();			
			this.paintComponent(g);	
			g.setColor(color);
			g.fillOval(15, 10, 60, 60);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			super.setBackground(Color.BLUE);
			g.setColor(Color.white);
			g.fillOval(15, 10, 60, 60);
		};
	}

	class Jouer extends MouseAdapter {
		
		private JLabel tourJoueur;
		
		public Jouer(JLabel tourJoueur) {
			this.tourJoueur = tourJoueur;
		}

		@Override
		public void mouseClicked(MouseEvent e) {			
			tour++;
			tourJoueur.setText(Integer.toString(tour));
			Panneau pan = (Panneau) e.getSource();			
			pan.colorer(Color.YELLOW);

			if (tour % 2 == 1) {
				pan.colorer(Color.RED);
			} 
		}
	}
}
