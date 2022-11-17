package dfaadsfaefea;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class Java_CRUD {

	private JFrame frame;
	private JTextField txtbid;
	private JTextField txtedition;
	private JTextField txtbname;
	private JTextField txtprice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Java_CRUD window = new Java_CRUD();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Java_CRUD() {
		initialize();

		// Step2: Add below line
		Connect();
		// Step8: Add below line
		table_load();
	}

	// Step1.. Establish connection

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;

	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "65x9rh9imnak");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	// Step6: Insert the below
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			// Step7: Replace table with scrollPane and download rs2xml.jar and undo
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 906, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnINSERT = new JButton("INSERT");
		btnINSERT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Step3: Add function of INSERT

				String bname, edition, price;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();

///Step5; insert the completed code here

				try {
					pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?);");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					table_load();

					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}

				catch (SQLException e1) {

					e1.printStackTrace();
				}

				/// Step5 end

			}
		});
		btnINSERT.setBounds(323, 72, 91, 21);
		frame.getContentPane().add(btnINSERT);

		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price, bid;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid = txtbid.getText();

				try {
					pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Update!!!!!");
					table_load();

					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}

				catch (SQLException e1) {

					e1.printStackTrace();

				}
			}
		});

		btnUpdate.setBounds(323, 103, 91, 21);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bid;
				bid = txtbid.getText();

				try {
					pst = con.prepareStatement("delete from book where id =?");

					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
					table_load();

					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}

				catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		btnDelete.setBounds(323, 134, 91, 21);
		frame.getContentPane().add(btnDelete);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);

			}
		});
		btnExit.setBounds(323, 165, 91, 21);
		frame.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();

			}
		});
		btnClear.setBounds(323, 197, 91, 21);
		frame.getContentPane().add(btnClear);

		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {

					String id = txtbid.getText();

					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {

						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);

						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);

					} else {
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");

					}

				}

				catch (SQLException ex) {

				}
			}
		});

		txtbid.setFont(new Font("MS UI Gothic", Font.PLAIN, 28));
		txtbid.setHorizontalAlignment(SwingConstants.LEFT);
		txtbid.setBounds(21, 312, 363, 143);
		frame.getContentPane().add(txtbid);
		txtbid.setColumns(10);

		/// 三個のマド
		txtedition = new JTextField();
		txtedition.setBounds(131, 41, 182, 61);
		frame.getContentPane().add(txtedition);
		txtedition.setColumns(10);

		txtbname = new JTextField();
		txtbname.setColumns(10);
		txtbname.setBounds(131, 123, 182, 61);
		frame.getContentPane().add(txtbname);

		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(131, 207, 182, 61);
		frame.getContentPane().add(txtprice);
		/// 三個のマドおわり

		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		lblEdition.setBounds(21, 55, 100, 27);
		frame.getContentPane().add(lblEdition);

		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		lblBookTitle.setBounds(21, 147, 100, 27);
		frame.getContentPane().add(lblBookTitle);

		JLabel lblPrice = new JLabel("$$$");
		lblPrice.setFont(new Font("MS UI Gothic", Font.PLAIN, 22));
		lblPrice.setBounds(21, 231, 100, 27);
		frame.getContentPane().add(lblPrice);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(511, 72, 363, 312);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Bookstore");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 45));
		lblNewLabel.setBounds(487, 0, 289, 52);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Retrieve the info by book ID");
		lblNewLabel_1.setFont(new Font("MS UI Gothic", Font.PLAIN, 31));
		lblNewLabel_1.setBounds(21, 278, 417, 26);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
