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

public class ClientCMS {
	
	//client_id					NOT NULL number
	//client_name						 varchar2(20)
	//client_pass						 varchar2(20)

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miSubmit1,miModify1,miDelete1,miView1;
	private JLabel lblclient_id,lblclient_name,lblclient_pass;
	private JTextField txtclient_id,txtclient_name,txtclient_pass;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public ClientCMS(JPanel p1,JFrame frame,JMenuItem miSubmit1,JMenuItem miModify1,JMenuItem miDelete1,JMenuItem miView1) {
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
		this.miSubmit1=miSubmit1;
		this.miDelete1=miDelete1;
		this.miModify1=miModify1;
		this.miView1=miView1;
		txtclient_id=new JTextField(20);
		txtclient_name=new JTextField(20);
		txtclient_pass=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblclient_id=new JLabel("Client Id:");
		lblclient_name=new JLabel("Client Name:");
		lblclient_pass=new JLabel("Client Password:");
		//queryHandler();
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
	public void reg_client() {
		miSubmit1.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtclient_id=new JTextField(20);
				txtclient_name=new JTextField(20);
				txtclient_pass=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblclient_id);
				p.add(txtclient_id);
				p.add(lblclient_name);
				p.add(txtclient_name);
				p.add(lblclient_pass);
				p.add(txtclient_pass);
				p.setLayout(new GridLayout(5,2));
				
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
							String query= "INSERT INTO client VALUES(" + txtclient_id.getText() + ", " + "'" + txtclient_name.getText() + "'," + "'" + txtclient_pass.getText() + "'" + ")";
							int i = statement.executeUpdate(query);
								txtmsg.append("\nInserted " + i + " rows successfully");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block

							txtmsg.append(e1.getMessage());
						}  
					}
				});
				
			}
		});
		
		miModify1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtclient_id=new JTextField(20);
				txtclient_name=new JTextField(20);
				txtclient_pass=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT client_id FROM client");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("client_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblclient_id);
				p.add(txtclient_id);
				p.add(lblclient_name);
				p.add(txtclient_name);
				p.add(lblclient_pass);
				p.add(txtclient_pass);
				p.setLayout(new GridLayout(5,2));
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
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM client where CLIENT_ID ="+idlist.getSelectedItem());
							rs.next();
							txtclient_id.setText(rs.getString("CLIENT_ID"));
							txtclient_name.setText(rs.getString("CLIENT_NAME"));
							txtclient_pass.setText(rs.getString("CLIENT_PASS"));
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
							txtmsg.append("UPDATE client "
									+ "SET client_name='" + txtclient_name.getText() + "', "
									+ "client_pass='" + txtclient_pass.getText() + "'"
								     + " WHERE client_id = "
									+ idlist.getSelectedItem()+"\n");
							int i = statement.executeUpdate("UPDATE client "
							+ "SET client_name='" + txtclient_name.getText() + "', "
							+ "client_pass='" + txtclient_pass.getText() + "'"
							+ " WHERE client_id = "
							+ idlist.getSelectedItem());
							System.out.println("successful");
							
							txtmsg.append("\nUpdated " + i + " rows successfully");
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});


		miDelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtclient_id=new JTextField(20);
				txtclient_name=new JTextField(20);
				txtclient_pass=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT client_id FROM client");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("client_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblclient_id);
				p.add(txtclient_id);
				p.add(lblclient_name);
				p.add(txtclient_name);
				p.add(lblclient_pass);
				p.add(txtclient_pass);
				p.setLayout(new GridLayout(5,2));
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
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM client where CLIENT_ID ="+idlist.getSelectedItem());
							rs.next();
							txtclient_id.setText(rs.getString("CLIENT_ID"));
							txtclient_name.setText(rs.getString("CLIENT_NAME"));
							txtclient_pass.setText(rs.getString("CLIENT_PASS"));
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
							int i = statement.executeUpdate("delete from client where client_id=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtclient_id.setText(null);
							txtclient_name.setText(null);
							txtclient_pass.setText(null);
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
		
		miView1.addActionListener(new ActionListener() {
			
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
				model.addColumn("Client  Name"); 
				model.addColumn("Client Pass"); 
				try {
					ResultSet rs=statement.executeQuery("select *from  client");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("client_id"),rs.getString("client_name"),rs.getString("client_pass")});
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
    