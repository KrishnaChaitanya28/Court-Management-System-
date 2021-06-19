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

public class TypesCMS {

	//advocate_type							 varchar2(20)
	//experience							 number
	//advocate_type_id				NOT NULL varchar2(20)
	
	private JPanel p1;
	private JFrame frame;

	private JMenuItem miSubmit6,miModify6,miDelete6,miView6;
	private JLabel lbladvocate_type,lblexperience,lbladvocate_type_id;
	private JTextField txtadvocate_type,txtexperience,txtadvocate_type_id;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public TypesCMS(JPanel p1,JFrame frame,JMenuItem miSubmit6,JMenuItem miModify6,JMenuItem miDelete6,JMenuItem miView6) {
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
		this.miSubmit6=miSubmit6;
		this.miDelete6=miDelete6;
		this.miModify6=miModify6;
		this.miView6=miView6;
		txtadvocate_type=new JTextField(20);
		txtexperience=new JTextField(20);
		txtadvocate_type_id=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lbladvocate_type=new JLabel("Advocate Type:");
		lblexperience=new JLabel("Experience:");
		lbladvocate_type_id=new JLabel("Advocate Type Id:");
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
	public void reg_types() {
		miSubmit6.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtadvocate_type=new JTextField(20);
				txtexperience=new JTextField(20);
				txtadvocate_type_id=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lbladvocate_type);
				p.add(txtadvocate_type);
				p.add(lblexperience);
				p.add(txtexperience);
				p.add(lbladvocate_type_id);
				p.add(txtadvocate_type_id);
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
							String query= "INSERT INTO types VALUES("+"'" + txtadvocate_type.getText() + "', " +  txtexperience.getText() + ",'" +   txtadvocate_type_id.getText() +"'" + ")";
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
		
		miModify6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtadvocate_type=new JTextField(20);
				txtexperience=new JTextField(20);
				txtadvocate_type_id=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT advocate_type_id FROM types");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("advocate_type_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbladvocate_type);
				p.add(txtadvocate_type);
				p.add(lblexperience);
				p.add(txtexperience);
				p.add(lbladvocate_type_id);
				p.add(txtadvocate_type_id);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM types where advocate_type_id ='"+idlist.getSelectedItem() + "'");
							rs.next();
							txtadvocate_type.setText(rs.getString("ADVOCATE_TYPE"));
							txtexperience.setText(rs.getString("EXPERIENCE"));
							txtadvocate_type_id.setText(rs.getString("ADVOCATE_TYPE_ID"));
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
							txtmsg.append("UPDATE types "
									+ "SET advocate_type='" + txtadvocate_type.getText() + "', "
									+ "experience=" + txtexperience.getText() 
								     + " WHERE advocate_type_id = '"
									+ idlist.getSelectedItem()+"' \n");
							int i = statement.executeUpdate("UPDATE types "
							+ "SET advocate_type='" + txtadvocate_type.getText() + "', "
							+ "experience=" + txtexperience.getText() 
							+ " WHERE advocate_type_id = '"
							+ idlist.getSelectedItem() + "'");
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


		miDelete6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtadvocate_type=new JTextField(20);
				txtexperience=new JTextField(20);
				txtadvocate_type_id=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT advocate_type_id FROM types");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("advocate_type_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbladvocate_type);
				p.add(txtadvocate_type);
				p.add(lblexperience);
				p.add(txtexperience);
				p.add(lbladvocate_type_id);
				p.add(txtadvocate_type_id);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM types where ADVOCATE_TYPE_ID ='"+idlist.getSelectedItem() + "'");
							rs.next();
							txtadvocate_type.setText(rs.getString("ADVOCATE_TYPE"));
							txtexperience.setText(rs.getString("EXPERIENCE"));
							txtadvocate_type_id.setText(rs.getString("ADVOCATE_TYPE_ID"));
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
							int i = statement.executeUpdate("delete from types where advocate_type_id='" + idlist.getSelectedItem() + "'");
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtadvocate_type.setText(null);
							txtexperience.setText(null);
							txtadvocate_type_id.setText(null);
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
		
		miView6.addActionListener(new ActionListener() {
			
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
				model.addColumn("Advocate Type");
				model.addColumn("Experience"); 
				model.addColumn("Advocate Type ID"); 
				try {
					ResultSet rs=statement.executeQuery("select *from  types");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("advocate_type"),rs.getString("experience"),rs.getString("advocate_type_id")});
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
