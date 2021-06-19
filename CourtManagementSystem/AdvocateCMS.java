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

public class AdvocateCMS {
	
	//advocate_id					NOT NULL	number
	//advocate_name								varchar2(20)
	//advocate_rating							varchar2(20)

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miSubmit3,miModify3,miDelete3,miView3;
	private JLabel lbladvocate_id,lbladvocate_name,lbladvocate_rating;
	private JTextField txtadvocate_id,txtadvocate_name,txtadvocate_rating;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public AdvocateCMS(JPanel p1,JFrame frame,JMenuItem miSubmit3,JMenuItem miModify3,JMenuItem miDelete3,JMenuItem miView3) {
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
		this.miSubmit3=miSubmit3;
		this.miDelete3=miDelete3;
		this.miModify3=miModify3;
		this.miView3=miView3;
		txtadvocate_id=new JTextField(20);
		txtadvocate_name=new JTextField(20);
		txtadvocate_rating=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lbladvocate_id=new JLabel("Advocate Id:");
		lbladvocate_name=new JLabel("Advocate Name:");
		lbladvocate_rating=new JLabel("Advocate Rating:");
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
	public void reg_advocate() {
		miSubmit3.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtadvocate_id=new JTextField(20);
				txtadvocate_name=new JTextField(20);
				txtadvocate_rating=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.add(lbladvocate_name);
				p.add(txtadvocate_name);
				p.add(lbladvocate_rating);
				p.add(txtadvocate_rating);
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
							String query= "INSERT INTO advocate VALUES(" + txtadvocate_id.getText() + ", " + "'" + txtadvocate_name.getText() + "'," + "'" + txtadvocate_rating.getText() + "'" + ")";
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
		
		miModify3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtadvocate_id=new JTextField(20);
				txtadvocate_name=new JTextField(20);
				txtadvocate_rating=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT advocate_id FROM advocate");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("advocate_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.add(lbladvocate_name);
				p.add(txtadvocate_name);
				p.add(lbladvocate_rating);
				p.add(txtadvocate_rating);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM advocate where ADVOCATE_ID ="+idlist.getSelectedItem());
							rs.next();
							txtadvocate_id.setText(rs.getString("ADVOCATE_ID"));
							txtadvocate_name.setText(rs.getString("ADVOCATE_NAME"));
							txtadvocate_rating.setText(rs.getString("ADVOCATE_RATING"));
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
							txtmsg.append("UPDATE advocate "
									+ "SET advocate_name='" + txtadvocate_name.getText() + "', "
									+ "advocate_rating='" + txtadvocate_rating.getText() + "'"
								     + " WHERE advocate_id = "
									+ idlist.getSelectedItem()+"\n");
							int i = statement.executeUpdate("UPDATE advocate "
							+ "SET advocate_name='" + txtadvocate_name.getText() + "', "
							+ "advocate_rating='" + txtadvocate_rating.getText() + "'"
							+ " WHERE advocate_id = "
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


		miDelete3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtadvocate_id=new JTextField(20);
				txtadvocate_name=new JTextField(20);
				txtadvocate_rating=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT advocate_id FROM advocate");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("advocate_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.add(lbladvocate_name);
				p.add(txtadvocate_name);
				p.add(lbladvocate_rating);
				p.add(txtadvocate_rating);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM advocate where ADVOCATE_ID ="+idlist.getSelectedItem());
							rs.next();
							txtadvocate_id.setText(rs.getString("ADVOCATE_ID"));
							txtadvocate_name.setText(rs.getString("ADVOCATE_NAME"));
							txtadvocate_rating.setText(rs.getString("ADVOCATE_RATING"));
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
							int i = statement.executeUpdate("delete from advocate where advocate_id=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtadvocate_id.setText(null);
							txtadvocate_name.setText(null);
							txtadvocate_rating.setText(null);
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
		
		miView3.addActionListener(new ActionListener() {
			
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
				model.addColumn("Advocate ID");
				model.addColumn("Advocate  Name"); 
				model.addColumn("Advocate Rating"); 
				try {
					ResultSet rs=statement.executeQuery("select *from  advocate");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("advocate_id"),rs.getString("advocate_name"),rs.getString("advocate_rating")});
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