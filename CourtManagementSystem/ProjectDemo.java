package CourtManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
//import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
//import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProjectDemo extends JFrame {

		private static final long serialVersionUID = 1L;
		private JMenu mnclient,mnselects,mnadvo,mnhas,mnbook,mntypes,mncourt,mnhastim,mnslot;
		private JMenuBar mnBar;
		private JMenuItem miSubmit1,miModify1,miDelete1,miView1;
		private JMenuItem miSubmit2,miModify2,miDelete2,miView2;
		private JMenuItem miSubmit3,miModify3,miDelete3,miView3;
		private JMenuItem miSubmit4,miModify4,miDelete4,miView4;
		private JMenuItem miSubmit5,miModify5,miDelete5,miView5;
		private JMenuItem miSubmit6,miModify6,miDelete6,miView6;
		private JMenuItem miSubmit7,miModify7,miDelete7,miView7;
		private JMenuItem miSubmit8,miModify8,miDelete8,miView8;
		private JMenuItem miSubmit9,miModify9,miDelete9,miView9;
		private JTextField txtField;
		
		static JPanel p1;
		int x=0,y=100;
		
		void initialize()
		{
			p1 = new JPanel();
			mnclient = new JMenu("Client");
			mnselects = new JMenu("Selects");
			mnadvo = new JMenu("Advocate");
			mnhas = new JMenu("Has");
			mnbook = new JMenu("Booking");
			mntypes = new JMenu("Types");
			mncourt = new JMenu("Court");
			mnhastim = new JMenu("Has_Timings");
			mnslot = new JMenu("Slot");
			mnBar = new JMenuBar();
			
			miSubmit1 = new JMenuItem("Submit");
			miModify1 = new JMenuItem("Modify");
			miDelete1 = new JMenuItem("Delete");
			miView1 = new JMenuItem("View");
			
			miSubmit2 = new JMenuItem("Submit");
			miModify2 = new JMenuItem("Modify");
			miDelete2 = new JMenuItem("Delete");
			miView2 = new JMenuItem("View");
			
			miSubmit3 = new JMenuItem("Submit");
			miModify3 = new JMenuItem("Modify");
			miDelete3 = new JMenuItem("Delete");
			miView3 = new JMenuItem("View");
			
			miSubmit4 = new JMenuItem("Submit");
			miModify4 = new JMenuItem("Modify");
			miDelete4 = new JMenuItem("Delete");
			miView4 = new JMenuItem("View");
			
			miSubmit5 = new JMenuItem("Submit");
			miModify5 = new JMenuItem("Modify");
			miDelete5 = new JMenuItem("Delete");
			miView5 = new JMenuItem("View");
			
			miSubmit6 = new JMenuItem("Submit");
			miModify6 = new JMenuItem("Modify");
			miDelete6 = new JMenuItem("Delete");
			miView6 = new JMenuItem("View");
			
			miSubmit7 = new JMenuItem("Submit");
			miModify7 = new JMenuItem("Modify");
			miDelete7 = new JMenuItem("Delete");
			miView7 = new JMenuItem("View");
			
			miSubmit8 = new JMenuItem("Submit");
			miModify8 = new JMenuItem("Modify");
			miDelete8 = new JMenuItem("Delete");
			miView8 = new JMenuItem("View");
			
			miSubmit9 = new JMenuItem("Submit");
			miModify9 = new JMenuItem("Modify");
			miDelete9 = new JMenuItem("Delete");
			miView9 = new JMenuItem("View");
			
			txtField = new JTextField("        Court Management System		");
			txtField.setFont(new Font("Serif",Font.PLAIN,42));
			txtField.setBackground(Color.cyan);
			txtField.setEditable(false);
		}
		
		void addComponentsToFrame()
		{
			mnclient.add(miSubmit1);
			mnclient.add(miModify1);
			mnclient.add(miDelete1);
			mnclient.add(miView1);
			
			mnselects.add(miSubmit2);
			mnselects.add(miModify2);
			mnselects.add(miDelete2);
			mnselects.add(miView2);
			
			mnadvo.add(miSubmit3);
			mnadvo.add(miModify3);
			mnadvo.add(miDelete3);
			mnadvo.add(miView3);
			
			mnhas.add(miSubmit4);
			mnhas.add(miModify4);
			mnhas.add(miDelete4);
			mnhas.add(miView4);
			
			mnbook.add(miSubmit5);
			mnbook.add(miModify5);
			mnbook.add(miDelete5);
			mnbook.add(miView5);
			
			mntypes.add(miSubmit6);
			mntypes.add(miModify6);
			mntypes.add(miDelete6);
			mntypes.add(miView6);
			
			mncourt.add(miSubmit7);
			mncourt.add(miModify7);
			mncourt.add(miDelete7);
			mncourt.add(miView7);
			
			mnhastim.add(miSubmit8);
			mnhastim.add(miModify8);
			mnhastim.add(miDelete8);
			mnhastim.add(miView8);
			
			mnslot.add(miSubmit9);
			mnslot.add(miModify9);
			mnslot.add(miDelete9);
			mnslot.add(miView9);
			
			mnBar.add(mnclient);
			mnBar.add(mnselects);
			mnBar.add(mnadvo);
			mnBar.add(mnhas);
			mnBar.add(mnbook);
			mnBar.add(mntypes);
			mnBar.add(mncourt);
			mnBar.add(mnhastim);
			mnBar.add(mnslot);
			setJMenuBar(mnBar);
			
			p1.setLayout(new BorderLayout());
			p1.add(txtField,BorderLayout.CENTER);
			this.setLayout(new BorderLayout());
			add(p1,BorderLayout.CENTER);
		}
		
		void register()
		{
			     ClientCMS c1 = new ClientCMS(p1,ProjectDemo.this,miSubmit1,miModify1,miDelete1,miView1);
			     c1.reg_client();
			     SelectsCMS c2 = new SelectsCMS(p1,ProjectDemo.this,miSubmit2,miModify2,miDelete2,miView2);
			     c2.reg_selects();
			     AdvocateCMS c3 = new AdvocateCMS(p1,ProjectDemo.this,miSubmit3,miModify3,miDelete3,miView3);
			     c3.reg_advocate();
			     HasCMS c4 = new HasCMS(p1,ProjectDemo.this,miSubmit4,miModify4,miDelete4,miView4);
			     c4.reg_has();
			     BookingCMS c5 = new BookingCMS(p1,ProjectDemo.this,miSubmit5,miModify5,miDelete5,miView5);
			     c5.reg_booking();
			     TypesCMS c6 = new TypesCMS(p1,ProjectDemo.this,miSubmit6,miModify6,miDelete6,miView6);
			     c6.reg_types();
			     CourtCMS c7 = new CourtCMS(p1,ProjectDemo.this,miSubmit7,miModify7,miDelete7,miView7);
			     c7.reg_court();
			     HasTimingCMS c8 = new HasTimingCMS(p1,ProjectDemo.this,miSubmit8,miModify8,miDelete8,miView8);
			     c8.reg_hastiming();
			     SlotCMS c9 = new SlotCMS(p1,ProjectDemo.this,miSubmit9,miModify9,miDelete9,miView9);
			     c9.reg_slot();   
			     
			     addWindowListener(new WindowAdapter(){
			    	 public void windowClosing(WindowEvent evt)
			    	 {
			    		 int a = JOptionPane.showConfirmDialog(ProjectDemo.this, "Are you sure?","This will close the UI",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			    		 if(a==0)
			    		 {
			    			 ProjectDemo.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    		 }
			    		 else if(a==2)
			    		 {
			    			 ProjectDemo.this.invalidate();
			    			 ProjectDemo.this.validate();
			    			 ProjectDemo.this.repaint();
			    		 }
			    	 }
			     });
		}
		
		public ProjectDemo()
		{
			initialize();
			addComponentsToFrame();
			register();
			pack();
			setTitle("Court Management System");
			setSize(600,500);
			setVisible(true);
		}
}