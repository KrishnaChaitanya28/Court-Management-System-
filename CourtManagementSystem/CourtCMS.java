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

public class CourtCMS {

	//pincode					NOT NULL number
	//state_name						 varchar2(20)
	//city_name							 varchar2(20)
	
	private JPanel p1;
	private JFrame frame;

	private JMenuItem miSubmit7,miModify7,miDelete7,miView7;
	private JLabel lblpincode,lblstate_name,lblcity_name;
	private JTextField txtpincode,txtstate_name,txtcity_name;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public CourtCMS(JPanel p1,JFrame frame,JMenuItem miSubmit7,JMenuItem miModify7,JMenuItem miDelete7,JMenuItem miView7) {
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
		this.miSubmit7=miSubmit7;
		this.miDelete7=miDelete7;
		this.miModify7=miModify7;
		this.miView7=miView7;
		txtpincode=new JTextField(20);
		txtstate_name=new JTextField(20);
		txtcity_name=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblpincode=new JLabel("Pincode:");
		lblstate_name=new JLabel("State Name:");
		lblcity_name=new JLabel("City Name:");
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
	public void reg_court() {
		miSubmit7.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtpincode=new JTextField(20);
				txtstate_name=new JTextField(20);
				txtcity_name=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblpincode);
				p.add(txtpincode);
				p.add(lblstate_name);
				p.add(txtstate_name);
				p.add(lblcity_name);
				p.add(txtcity_name);
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
							String query= "INSERT INTO court VALUES(" + txtpincode.getText() + ", " + "'" + lblstate_name.getText() + "'," + "'" + txtcity_name.getText() + "'" + ")";
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
		
		miModify7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtpincode=new JTextField(20);
				txtstate_name=new JTextField(20);
				txtcity_name=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT pincode FROM court");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("pincode"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblpincode);
				p.add(txtpincode);
				p.add(lblstate_name);
				p.add(txtstate_name);
				p.add(lblcity_name);
				p.add(txtcity_name);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM court where PINCODE ="+idlist.getSelectedItem());
							rs.next();
							txtpincode.setText(rs.getString("PINCODE"));
							txtstate_name.setText(rs.getString("STATE_NAME"));
							txtcity_name.setText(rs.getString("CITY_NAME"));
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
							txtmsg.append("UPDATE court "
									+ "SET state_name='" + txtstate_name.getText() + "', "
									+ "city_name='" + txtcity_name.getText() + "'"
								     + " WHERE pincode = "
									+ idlist.getSelectedItem()+"\n");
							int i = statement.executeUpdate("UPDATE court "
							+ "SET state_name='" + txtstate_name.getText() + "', "
							+ "city_name='" + txtcity_name.getText() + "'"
							+ " WHERE pincode = "
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


		miDelete7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtpincode=new JTextField(20);
				txtstate_name=new JTextField(20);
				txtcity_name=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT pincode FROM court");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("pincode"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblpincode);
				p.add(txtpincode);
				p.add(lblstate_name);
				p.add(txtstate_name);
				p.add(lblcity_name);
				p.add(txtcity_name);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM court where pincode ="+idlist.getSelectedItem());
							rs.next();
							txtpincode.setText(rs.getString("PINCODE"));
							txtstate_name.setText(rs.getString("STATE_NAME"));
							txtcity_name.setText(rs.getString("CITY_NAME"));
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
							int i = statement.executeUpdate("delete from court where pincode=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtpincode.setText(null);
							txtstate_name.setText(null);
							txtcity_name.setText(null);
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
		
		miView7.addActionListener(new ActionListener() {
			
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
				model.addColumn("Pincode");
				model.addColumn("State  Name"); 
				model.addColumn("City Name"); 
				try {
					ResultSet rs=statement.executeQuery("select *from  court");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("pincode"),rs.getString("state_name"),rs.getString("city_name")});
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
