package projet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.text.AttributedCharacterIterator;
import java.awt.event.MouseAdapter;
import javax.swing.*;

public class Puissance4 extends JFrame{
	private static final long serialVersionUID = 3660342240433840428L;
	private final static int vide=0;
	private final static int rouge=1;
	private final static int jaune=2;
	private static int couleur,tour;
	private static JLabel tourJoueur;
	private static Graphics graph;
	
	public Puissance4() {
	    super("Puissance 4");
	    setSize(590,600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    JMenuBar barreMenu=new JMenuBar();
	    setJMenuBar(barreMenu);
	    JMenu modeJeu=new JMenu("Mode de jeu");
	    JRadioButtonMenuItem homVsMach =new JRadioButtonMenuItem("Homme vs Machine");
	    JRadioButtonMenuItem machVsHom=new JRadioButtonMenuItem("Machine vs Homme");
	    JRadioButtonMenuItem homVsHom=new JRadioButtonMenuItem("Homme vs Homme");
	    JRadioButtonMenuItem machVsMach=new JRadioButtonMenuItem("Machine vs Machine");
	    ButtonGroup gr=new ButtonGroup();
	    gr.add(homVsHom);
	    gr.add(homVsMach);
	    gr.add(machVsHom);
	    gr.add(machVsMach);
	    modeJeu.add(homVsHom);
	    modeJeu.add( homVsMach);
	    modeJeu.add( machVsHom);
	    modeJeu.add(machVsMach);
	    barreMenu.add(modeJeu);
	    
	    tourJoueur=new JLabel("Joueur "+", à vous de jouer !");
	    barreMenu.add(tourJoueur);
	    JPanel jeu=new JPanel();
	    jeu.setSize(400, 300);
	    jeu.setLayout(new GridLayout(6,7));
	    jeu.setBackground(Color.gray);
	    Panneau grille [][]=new Panneau[6][7];
	    
	    Jouer jouer = new Jouer();
	    for(int i=0;i<grille.length;i++) {
	    	for(int j=0;j<grille.length;j++) {
	    		grille[i][j]=new Panneau(jouer);
	    	jeu.add(grille[i][j]);
	    	}
	    }
	    
	    this.getContentPane().add(jeu,BorderLayout.CENTER);
	   
	}
	
	public class Panneau extends JPanel {
		private static final long serialVersionUID = 1L;

		public Panneau(MouseAdapter adapter) {
			this.addMouseListener(adapter);
		}

		public void colorer(Graphics g,int color) {	
			if(color==rouge) {
				g.setColor(Color.RED);
			}else if (color==jaune) {
				g.setColor(Color.YELLOW);
			}else {
				g.setColor(Color.WHITE);
			}
			super.paintComponent(g);
			super.setBackground(Color.BLUE);
			g.fillOval(15, 10, 60, 60);
			
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			super.setBackground(Color.BLUE);
			g.setColor(Color.white);
			g.fillOval(15, 10, 60, 60);
		};
	}
	
	class Jouer extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			Graphics g;
			tour++;
			String tr=Integer.toString(tour);
			tourJoueur.setText(tr);
			Panneau pan= (Panneau) e.getSource();
			g=pan.getGraphics();
			
			if(tour%2==1) {
				couleur=rouge;
				pan.colorer(g,rouge);
			}else if (tour%2==0) {
				couleur=jaune;
				pan.colorer(g,jaune);
			}

					
					
		}
	}
}
