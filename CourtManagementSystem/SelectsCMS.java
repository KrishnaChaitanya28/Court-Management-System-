package CourtManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SelectsCMS {

	// CLIENT_ID                                       NOT NULL NUMBER(5)
	// ADVOCATE_ID                                     NOT NULL NUMBER(5)
	
	private JPanel p1;
	private JFrame frame;
	String s1,s2;
	private JMenuItem miSubmit2,miModify2,miDelete2,miView2;
	private JLabel lblclient_id,lbladvocate_id;
	private JTextField txtclient_id,txtadvocate_id;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public SelectsCMS(JPanel p1,JFrame frame,JMenuItem miSubmit2,JMenuItem miModify2,JMenuItem miDelete2,JMenuItem miView2) {
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
		this.frame=frame;
		this.p1=p1;
		this.miSubmit2=miSubmit2;
		this.miDelete2=miDelete2;
		this.miModify2=miModify2;
		this.miView2=miView2;
		txtclient_id=new JTextField(20);
		txtadvocate_id=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblclient_id=new JLabel("Client Id:");
		lbladvocate_id=new JLabel("Advocate Id:");
	}
	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","lasya","lasya");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void reg_selects() {
		
		miSubmit2.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				 
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				
				JPanel p=new JPanel();
				txtclient_id=new JTextField(20);
				txtadvocate_id=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblclient_id);
				p.add(txtclient_id);
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				
				p.setLayout(new GridLayout(4,2));
				
				p1.add(p);
				p1.setBackground(Color.green);
				p1.add(btn);
				p1.add(txtmsg);//msg text area added to panel
				btn.setText("SUBMIT");
				p1.setLayout(new FlowLayout());
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				//register listener
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							Statement statement = connection.createStatement();
							String query= "INSERT INTO selects VALUES(" + txtclient_id.getText() + ","  + txtadvocate_id.getText()  +  ")";
							int i = statement.executeUpdate(query);
								txtmsg.append("\nInserted " + i + " rows successfully");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block;
							txtmsg.append(e1.getMessage());
						}  
					}
				});
				
			}
		});
		
		miModify2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtclient_id=new JTextField(20);
				txtadvocate_id=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT client_id,advocate_id FROM selects");
				  while (rs.next()) 
				  {
					  idlist.add("CLIENT_ID="+rs.getString("client_id")+"   ADVOCATE_ID="+rs.getString("advocate_ID"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblclient_id);
				p.add(txtclient_id);
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.setLayout(new GridLayout(4,2));
				p1.add(p);
				p1.setBackground(Color.yellow);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("MODIFY");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
			
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						StringTokenizer st =new StringTokenizer(idlist.getSelectedItem());
						s1=st.nextToken();
						s2=st.nextToken();
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM selects where "+s1+" and "+s2);
							rs.next();
							txtclient_id.setText(rs.getString("CLIENT_ID"));
							txtadvocate_id.setText(rs.getString("advocate_id"));
							
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("UPDATE selects "+ "SET advocate_id=" + txtadvocate_id.getText() + 
							  " WHERE "+s1+" and "+s2);
							txtmsg.append("\nUpdated " + i + " rows successfully");
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append("UPDATE selects "+ "SET advocate_id=" + txtadvocate_id.getText() +
									  " WHERE "+s1+" and "+s2+"\n"+insertException.getMessage());
						}
						
					}
				});
			}
		});


		miDelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtclient_id=new JTextField(20);
				txtadvocate_id=new JTextField(20);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT client_id,advocate_id FROM selects");
				  while (rs.next()) 
				  {
					  idlist.add("CLIENT_ID="+rs.getString("client_id")+"  ADVOCATE_ID="+rs.getString("advocate_ID"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblclient_id);
				p.add(txtclient_id);
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.setLayout(new GridLayout(4,2));
				p1.add(p);
				p1.setBackground(Color.red);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("DELETE");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
						StringTokenizer st =new StringTokenizer(idlist.getSelectedItem());
						s1=st.nextToken();
						s2=st.nextToken();
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM selects where "+s1+" and "+s2);
							rs.next();
							txtclient_id.setText(rs.getString("CLIENT_ID"));
							txtadvocate_id.setText(rs.getString("advocate_id"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("delete from selects where "+s1+" and "+s2);
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtclient_id.setText(null);
							txtadvocate_id.setText(null);
							idlist.removeAll();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});
		
		miView2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				p1.removeAll();
				frame.invalidate(); 
				frame.validate();
				frame.repaint(); 
				JTable  j;
				DefaultTableModel  model  =  new  DefaultTableModel();
				j  =  new  JTable(model);
				model.addColumn("Client ID");
				model.addColumn("Advocate ID");  
				try {
					ResultSet rs=statement.executeQuery("select *from  selects");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("client_id"),rs.getString("advocate_id")});
				}
				}
				catch(SQLException e1) { JOptionPane.showMessageDialog(frame,"Something Went Wrong");
				 
				}
				JScrollPane  sp  =  new  JScrollPane(j);
				 
				p1.add(sp,BorderLayout.NORTH); 
				p1.setBackground(Color.orange);
				frame.invalidate();
				frame.validate(); 
				frame.repaint();
				
			}
		});
	}

}
