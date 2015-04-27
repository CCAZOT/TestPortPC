import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.comm.CommPortIdentifier;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AffichageJavaComm extends JFrame implements ListSelectionListener {
	
	  TesteJavaComm JavaComm; 
	  private JPanel container = new JPanel();
	  JButton Valider = new JButton("Valider");
	  JButton Rafraichir = new JButton("Rafraichir");
	  private JLabel ecran = new JLabel();
	  private JList listbox;
	  
	  
	  public AffichageJavaComm(){
		 
		JavaComm = new TesteJavaComm("COM3");
		//D�finition de la fen�tre principale
	    this.setSize(400, 400);
	    this.setTitle("Analyseur de ports s�ries");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    //On initialise le conteneur avec tous les composants
	    //On d�finit la police d'�criture � utiliser
	    Font police = new Font("Arial", Font.BOLD, 20);
	    ecran = new JLabel("0");
	    ecran.setFont(police);
	    ecran.setPreferredSize(new Dimension(100, 20));
	    
	    //Creation du Panel de droite
	    JPanel PanelDroit = new JPanel();      
	    PanelDroit.setPreferredSize(new Dimension(190, 310));
	    PanelDroit.setLayout(new BorderLayout());
	    
	    //Creation du Panel de gauche
	    JPanel PanelGauche = new JPanel();
	    PanelGauche.setPreferredSize(new Dimension(190, 310));
	    PanelGauche.setLayout(new BorderLayout());
	    
	    //Creation du Panel du bas
	    JPanel PanelBas = new JPanel();
	    PanelBas.setPreferredSize(new Dimension(350, 40));
	    PanelBas.setLayout(new BorderLayout());

	    
	    //encadrement noir des panel
	    PanelDroit.setBorder(BorderFactory.createLineBorder(Color.black));
	    PanelGauche.setBorder(BorderFactory.createLineBorder(Color.black));
	    PanelBas.setBorder(BorderFactory.createLineBorder(Color.black));

	    JLabel Label1 = new JLabel();
	    Label1.setText("<html><u>Param�tres</u></html>");
	    PanelGauche.add(Label1, BorderLayout.NORTH);
	    Label1.setFont(police);
	    
	    JLabel Label2 = new JLabel();
	    Label2.setText("<html><u>Liste des ports</u></html>");
	    PanelDroit.add(Label2, BorderLayout.NORTH);
	    Label2.setFont(police);
	    
	    PanelGauche.add(ecran);
	    container.add(PanelGauche, BorderLayout.WEST);
	    container.add(PanelDroit, BorderLayout.EAST);
	    container.add(PanelBas);
		//On ajoute le conteneur
	 
		// Create a panel to hold all other components
		getContentPane().add( container );

		// Create some items to add to the list
		this.initComposant();
		String	listData2[] =
		{
			"Item 1",
			"Item 2",
			"Item 3",
			"Item 4"
		};
		
		Vector<String> liste =  new Vector<String>() ;
		liste.add("un") ;
		liste.add("deux") ;
		liste.add("trois") ;

		// Create a new listbox control
		listbox = new JList();
		listbox.setListData(JavaComm.listData);
		listbox.addListSelectionListener( this);
		listbox.setPreferredSize(new Dimension(150, 200));
		listbox.setBorder(BorderFactory.createLineBorder(Color.black));
		PanelDroit.add( listbox, BorderLayout.CENTER );
		
	    Valider.setPreferredSize(new Dimension(100, 40));
	    Valider.addActionListener(new ValiderListener());
	    PanelBas.add(Valider, BorderLayout.WEST);

	    
	    Rafraichir.setPreferredSize(new Dimension(100, 40));
	    Rafraichir.addActionListener(new RafraichirListener());
	    PanelBas.add(Rafraichir, BorderLayout.EAST);
			
	    this.setContentPane(container);
	    this.setVisible(true);
	  }
	  
	  public void valueChanged(ListSelectionEvent evt) { 
		  ecran.setText((String)listbox.getSelectedValue());
		 }
	      
	  private void initComposant(){
			
			//for (int i = 0; i < JavaComm.listData.length; i++) {
			//	System.out.println(JavaComm.listData[i]);
			//	}
	  }


	  //Listener utilis� pour les chiffres
	  //Permet de stocker les chiffres et de les afficher
	  class RafraichirListener implements ActionListener {
	    public void actionPerformed(ActionEvent e){
	      //On affiche le chiffre additionnel dans le label
	      String str = ((JButton)e.getSource()).getText();
	      ecran.setText(str);
	    }
	  }

	  //Listener affect� au bouton =
	  class ValiderListener implements ActionListener {
	    public void actionPerformed(ActionEvent arg0){

	    }
	  }
}
