import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
public class gui extends JFrame
{
	private JLabel l1,id,vow,con,course,caps,pun,l2,l3,l4;
	private JTextField in,gvow,gcon,gpun,dob,crse;
	private JButton cmd,sd;
	private JComboBox cb;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement ps;
	public gui()
	{
		try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Sdbms?useSSL=false","root","root"); 
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }

		this.setSize(500,700);
		l1=new JLabel("Student DBMS");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("Serif", Font.ROMAN_BASELINE, 30));
		l1.setBounds(25, 10, 450, 50);
		in = new JTextField();
		in.setBounds(175, 225, 150, 20);
		this.add(in);
		id = new JLabel("Enter Student ID");
		id.setBounds(125, 190, 250, 25);
		id.setHorizontalAlignment(SwingConstants.CENTER);
		gvow = new JTextField();
		gvow.setBounds(250, 300, 200, 20);
		this.add(gvow);
		l2=new JLabel();
		l2.setBounds(175, 260, 250, 25);
		l2.setForeground(Color.red);
		in.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				KeyPressed(ke,l2);
			}
		});
		cmd=new JButton("Add");
		cmd.setBounds(350, 550, 100, 25);
		cmd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				cmdActionPerformed();
			}
		});
		sd=new JButton("Show Details");
		sd.setBounds(300, 550, 150, 25);
		sd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				sdActionPerformed();
			}
		});
		sd.setVisible(false);
		String st[]= {"Check","Add","Update","Delete"};
		cb=new JComboBox(st);
		cb.setBounds(200, 125, 100, 20);
		cb.setSelectedIndex(1);
		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e)
			{
				cbItemStateChanged();
			}
		});
		gcon = new JTextField();
		gcon.setBounds(250, 350, 200, 20);
		this.add(gcon);
		l3=new JLabel();
		l3.setBounds(250, 420, 200, 20);
		l3.setForeground(Color.red);
		this.add(l3);
		gpun = new JTextField();
		gpun.setBounds(250, 400, 200, 20);
		gpun.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				KeyPressed(ke,l3);
			}
		});
		this.add(gpun);
		dob = new JTextField();
		dob.setBounds(250, 450, 200, 20);
		this.add(dob);
		crse = new JTextField();
		crse.setBounds(250, 500, 200, 20);
		this.add(crse);
		vow = new JLabel("First Name");
		con = new JLabel("Last Name");
		pun = new JLabel("Grade");
		caps = new JLabel("DOB");
		course = new JLabel("Course");
		vow.setBounds(75, 300, 200, 20);
		vow.setHorizontalAlignment(SwingConstants.LEFT);
		course.setBounds(75, 500, 200, 20);
		course.setHorizontalAlignment(SwingConstants.LEFT);
		con.setBounds(75, 350, 200, 20);
		con.setHorizontalAlignment(SwingConstants.LEFT);
		pun.setBounds(75, 400, 200, 20);
		pun.setHorizontalAlignment(SwingConstants.LEFT);
		caps.setBounds(75, 450, 200, 20);
		caps.setHorizontalAlignment(SwingConstants.LEFT);
		l4=new JLabel("");
		l4.setBounds(220, 600, 200, 20);
		l4.setForeground(Color.red);
		this.add(l1);
		this.add(l2);
		this.add(l4);
		this.add(vow);
		this.add(con);
		this.add(pun);
		this.add(caps);
		this.add(course);
		this.add(id);
		this.add(cb);
		this.add(cmd);
		this.add(sd);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e) {
				l2.setText("");
				l3.setText("");
				in.setEditable(true);
				if(cb.getSelectedIndex()==1 || cb.getSelectedIndex()==2)
				{
					gpun.setEditable(true);
				}
			}
			public void mouseDragged(MouseEvent e)
			{}
		});
		this.setLayout(null);
		this.setVisible(true);
	}

	private void cbItemStateChanged()
	{
		String s=cb.getSelectedItem().toString();
		int m=cb.getSelectedIndex();
		cmd.setText(s);
		cmd.setVisible(true);
		in.setText("");
		gvow.setText("");
		gcon.setText("");
		gpun.setText("");
		dob.setText("");
		crse.setText("");
		l4.setText("");
		if(m==0 || m==3)
		{
			gvow.setEditable(false);
			gcon.setEditable(false);
			gpun.setEditable(false);
			dob.setEditable(false);
			crse.setEditable(false);
			if(m==3)
			{
			sd.setVisible(true);
			cmd.setVisible(false);
			}
		}
		else
		{
			gvow.setEditable(true);
			gcon.setEditable(true);
			gpun.setEditable(true);
			dob.setEditable(true);
			crse.setEditable(true);
		}

	}
	private void cmdActionPerformed()
	{
		try
		{
		l4.setText("");
		if(!in.getText().equals(""))
		{
		int idEntered =Integer.parseInt(in.getText());
		if(cb.getSelectedIndex()==1)
		{
			rs=stmt.executeQuery("SELECT * FROM info WHERE ID = "+idEntered);
			if(!rs.next())
			{
			ps=conn.prepareStatement("INSERT INTO info (ID,fname,lname,grade,dob,course) VALUES (?,?,?,?,?,?)");
			ps.setInt(1, idEntered);
			ps.setString(2,gvow.getText());
			ps.setString(3, gcon.getText());
			ps.setInt(4, Integer.parseInt(gpun.getText()));
			ps.setDate(5, Date.valueOf(dob.getText()));
			ps.setString(6, crse.getText());
			ps.executeUpdate();
			}
			else
			{
				l4.setText("* Entery Already Exists!!!!");
			}
		}
		else
		{
			rs=stmt.executeQuery("SELECT * FROM info WHERE ID = "+idEntered);
			if(rs.next())
			{
				if (cb.getSelectedIndex()==0)
				{
					gvow.setText(rs.getString(2));
					gcon.setText(rs.getString(3));
					gpun.setText(rs.getString(4));
					dob.setText(rs.getDate(5).toString());
					crse.setText(rs.getString(6));
				}
				else if(cb.getSelectedIndex()==3)
				{
					stmt.executeUpdate("DELETE FROM info WHERE ID = "+idEntered);
				}
				else if(cb.getSelectedIndex()==2)
				{
					String A="";
					int i=0;
					if(!gvow.getText().equals(""))
					{
						A=A+"fname= \""+gvow.getText()+"\", ";
						i++;
						gvow.setText((""));
					}
					if(!gcon.getText().equals(""))
					{
						A=A+"lname= \""+gcon.getText()+"\", ";
						i++;
						gcon.setText((""));
					}
					if(!gpun.getText().equals(""))
					{
						A=A+"grade= "+gpun.getText()+",";
						i++;
						gpun.setText((""));
					}
					if(!dob.getText().equals(""))
					{
						A=A+"dob= \""+dob.getText()+"\",";
						i++;
						dob.setText("");
					}
					if(!crse.getText().equals(""))
					{
						A=A+"course= \"" +crse.getText()+"\",";
						i++;
						crse.setText((""));
					}
					if(i>0)
					{
					A=A.substring(0,A.length()-1);
					stmt.executeUpdate("UPDATE info SET "+A+" WHERE ID= "+idEntered);
					}
					else
						l4.setText("* No Field Updated!!!");
				}
			}
			else
			{
				l4.setText("* Entery doesn't exists!!!!!");
			}
		}
		}
		else
		{
			l4.setText(("* No ID entered!!!"));
		}
		if(cb.getSelectedIndex()!=0)
		{
		in.setText("");
		gvow.setText("");
		gcon.setText("");
		gpun.setText("");
		dob.setText("");
		crse.setText("");
		}
	}
		catch (Exception e) {
            System.out.println(e);
	}
}
private void sdActionPerformed()
{
	try{
		rs=stmt.executeQuery("SELECT * FROM info WHERE ID = "+Integer.parseInt(in.getText()));
			if(rs.next())
			{
					gvow.setText(rs.getString(2));
					gcon.setText(rs.getString(3));
					gpun.setText(rs.getString(4));
					dob.setText(rs.getDate(5).toString());
					crse.setText(rs.getString(6));
			}
			sd.setVisible(false);
			cmd.setVisible(true);
	}
	catch (Exception e) {
		System.out.println(e);
}
}
	private void KeyPressed(KeyEvent ke,JLabel l) {
		JTextField j=(JTextField)ke.getSource();
		
            if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')||ke.getKeyCode() == KeyEvent.VK_BACK_SPACE||ke.getKeyCode() == KeyEvent.VK_DELETE) {
               j.setEditable(true);
               l.setText("");
            } else {
               j.setEditable(false);
			   l.setText("* Enter only numeric digits(0-9)");
            }
	}

	public static void main(String[] args) {
		gui g=new gui();
	}
}