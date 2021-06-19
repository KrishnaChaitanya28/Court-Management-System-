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

public class SlotCMS {

	//slot_id					NOT NULL number
	//from_date							 varchar2(20)
	//tentative_years					 number
	//judge_name						 varchar2(20)
	
	private JPanel p1;
	private JFrame frame;

	private JMenuItem miSubmit9,miModify9,miDelete9,miView9;
	private JLabel lblslot_id,lblfrom_date,lbltentative_years,lbljudge_name;
	private JTextField txtslot_id,txtfrom_date,txttentative_years,txtjudge_name;
	private JButton btn;
	private JTextArea txtmsg;
	private Connection connection;
	private Statement statement;
	public SlotCMS(JPanel p1,JFrame frame,JMenuItem miSubmit9,JMenuItem miModify9,JMenuItem miDelete9,JMenuItem miView9) {
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
		this.miSubmit9=miSubmit9;
		this.miDelete9=miDelete9;
		this.miModify9=miModify9;
		this.miView9=miView9;
		txtslot_id=new JTextField(20);
		txtfrom_date=new JTextField(20);
		txttentative_years=new JTextField(20);
		txtjudge_name=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblslot_id=new JLabel("Slot Id:");
		lblfrom_date=new JLabel("From Date:");
		lbltentative_years=new JLabel("Tentative Years:");
		lbljudge_name=new JLabel("Judge Name:");
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
	public void reg_slot() {
		miSubmit9.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtslot_id=new JTextField(20);
				txtfrom_date=new JTextField(20);
				txttentative_years=new JTextField(20);
				txtjudge_name=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblslot_id);
				p.add(txtslot_id);
				p.add(lblfrom_date);
				p.add(txtfrom_date);
				p.add(lbltentative_years);
				p.add(txttentative_years);
				p.add(lbljudge_name);
				p.add(txtjudge_name);
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
							String query= "INSERT INTO slot VALUES('" + txtslot_id.getText() + "', " + "'" + txtfrom_date.getText() + "'," +  txttentative_years.getText() + ",'" + txtjudge_name.getText() + "'"+")";
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
		
		miModify9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtslot_id=new JTextField(20);
				txtfrom_date=new JTextField(20);
				txttentative_years=new JTextField(20);
				txtjudge_name=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT slot_id FROM slot");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("slot_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblslot_id);
				p.add(txtslot_id);
				p.add(lblfrom_date);
				p.add(txtfrom_date);
				p.add(lbltentative_years);
				p.add(txttentative_years);
				p.add(lbljudge_name);
				p.add(txtjudge_name);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM slot where slot_id ='"+idlist.getSelectedItem() + "'");
							rs.next();
							txtslot_id.setText(rs.getString("SLOT_ID"));
							txtfrom_date.setText(rs.getString("FROM_DATE"));
							txttentative_years.setText(rs.getString("TENTATIVE_YEARS"));
							txtjudge_name.setText(rs.getString("JUDGE_NAME"));
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
							txtmsg.append("UPDATE slot "
									+ "SET from_date='" + txtfrom_date.getText() + "', "
									+ "tentative_years=" + txttentative_years.getText() + ", "
									+ "judge_name ='"+ txtjudge_name.getText() + "' " + " WHERE slot_id = '"
									+ idlist.getSelectedItem()+"' \n");
							int i = statement.executeUpdate("UPDATE slot "
							+ "SET from_date='" + txtfrom_date.getText() + "', "
							+ "tentative_years=" + txttentative_years.getText() + ", "
							+ "judge_name ='"+ txtjudge_name.getText() + "'" + " WHERE slot_id = '"
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


		miDelete9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtslot_id=new JTextField(20);
				txtfrom_date=new JTextField(20);
				txttentative_years=new JTextField(20);
				txtjudge_name=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT slot_id FROM slot");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("slot_id"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblslot_id);
				p.add(txtslot_id);
				p.add(lblfrom_date);
				p.add(txtfrom_date);
				p.add(lbltentative_years);
				p.add(txttentative_years);
				p.add(lbljudge_name);
				p.add(txtjudge_name);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM slot where slot_id ='"+idlist.getSelectedItem() + "'");
							rs.next();
							txtslot_id.setText(rs.getString("SLOT_ID"));
							txtfrom_date.setText(rs.getString("FROM_DATE"));
							txttentative_years.setText(rs.getString("TENTATIVE_YEARS"));
							txtjudge_name.setText(rs.getString("JUDGE_NAME"));
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
							int i = statement.executeUpdate("delete from slot where slot_id=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							txtslot_id.setText(null);
							txtfrom_date.setText(null);
							txttentative_years.setText(null);
							txtjudge_name.setText(null);
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
		
		miView9.addActionListener(new ActionListener() {
			
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
				model.addColumn("Slot ID");
				model.addColumn("From Date"); 
				model.addColumn("Tentative Years"); 
				model.addColumn("Judge Name");
				try {
					ResultSet rs=statement.executeQuery("select *from  slot");

				while(rs.next()) {
				model.addRow(new  Object[]{rs.getString("slot_id"),rs.getString("from_date"),rs.getString("tentative_years"),rs.getString("judge_name")});
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
