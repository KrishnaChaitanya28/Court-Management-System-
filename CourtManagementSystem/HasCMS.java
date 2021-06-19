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

public class HasCMS {

	// ADVOCATE_ID                                          NOT NULL NUMBER(5)
	// ADVOCATE_TYPE_ID                                     NOT NULL NUMBER(5)
	
	private JPanel p1;
	private JFrame frame;
	String s1,s2;
	private JMenuItem miSubmit4,miModify4,miDelete4,miView4;
	private JLabel lbladvocate_id,lbladvocate_type_id;
	private JTextField txtadvocate_id,txtadvocate_type_id;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public HasCMS(JPanel p1,JFrame frame,JMenuItem miSubmit4,JMenuItem miModify4,JMenuItem miDelete4,JMenuItem miView4) {
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
		this.miSubmit4=miSubmit4;
		this.miDelete4=miDelete4;
		this.miModify4=miModify4;
		this.miView4=miView4;
		txtadvocate_id=new JTextField(20);
		txtadvocate_type_id=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lbladvocate_id=new JLabel("Advocate Id:");
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
	public void reg_has() {
		
		miSubmit4.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				 
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				
				JPanel p=new JPanel();
				txtadvocate_id=new JTextField(20);
				txtadvocate_type_id=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.add(lbladvocate_type_id);
				p.add(txtadvocate_type_id);
				
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
							String query= "INSERT INTO has VALUES(" + txtadvocate_id.getText() + ",'"  + txtadvocate_type_id.getText()  +  "')";
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
		
		miModify4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtadvocate_id=new JTextField(20);
				txtadvocate_type_id=new JTextField(20);
				
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT advocate_id,advocate_type_id FROM has");
				  while (rs.next()) 
				  {
					  idlist.add("ADVOCATE_ID="+rs.getString("advocate_id")+"   ADVOCATE_TYPE_ID='"+rs.getString("advocate_type_ID") + "'");
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.add(lbladvocate_type_id);
				p.add(txtadvocate_type_id);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM has where "+s1+" and "+s2);
							rs.next();
							txtadvocate_id.setText(rs.getString("ADVOCATE_ID"));
							txtadvocate_type_id.setText(rs.getString("advocate_type_id"));
							
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
							int i = statement.executeUpdate("UPDATE has "+ "SET advocate_type_id='" + txtadvocate_type_id.getText() + 
							  "' WHERE "+s1+" and "+s2);
							txtmsg.append("\nUpdated " + i + " rows successfully");
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append("UPDATE has "+ "SET advocate_type_id='" + txtadvocate_type_id.getText() +
									  "' WHERE "+s1+" and "+s2+"\n"+insertException.getMessage());
						}
						
					}
				});
			}
		});


		miDelete4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtadvocate_id=new JTextField(20);
				txtadvocate_type_id=new JTextField(20);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT advocate_id,advocate_type_id FROM has");
				  while (rs.next()) 
				  {
					  idlist.add("ADVOCATE_ID="+rs.getString("advocate_id")+"  ADVOCATE_TYPE_ID='"+rs.getString("advocate_type_ID") + "'");
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbladvocate_id);
				p.add(txtadvocate_id);
				p.add(lbladvocate_type_id);
				p.add(txtadvocate_type_id);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM has where "+s1+" and "+s2);
							rs.next();
							txtadvocate_id.setText(rs.getString("ADVOCATE_ID"));
							txtadvocate_type_id.setText(rs.getString("advocate_type_id"));
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
							int i = statement.executeUpdate("delete from has where "+s1+" and "+s2);
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtadvocate_id.setText(null);
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
		
		miView4.addActionListener(new ActionListener() {
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
				model.addColumn("Advocatet ID");
				model.addColumn("Advocate Type ID");  
				try {
					ResultSet rs=statement.executeQuery("select *from  has");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("advocate_id"),rs.getString("advocate_type_id")});
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