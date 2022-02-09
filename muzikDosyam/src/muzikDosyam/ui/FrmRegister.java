package muzikDosyam.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import muzikDosyam.Utils;
import muzikDosyam.db.DbHelper;
import muzikDosyam.db.User;
import muzikDosyam.getData.UserGetData;
import muzikDosyam.ui.user.FrmUserAlbums;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmRegister extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtEMail;
	private JPasswordField txtPassword;
	private JTextField txtPremium;
	private JTextField txtCountry;
	private int id;
	private UserGetData userGetData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmRegister frame = new FrmRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmRegister() {
		userGetData = new UserGetData();
		setTitle("M\u00FCzik Dosyam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-11, -1, 1941, 1049);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblYtMusicBackground = new JLabel("");
		lblYtMusicBackground.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblYtMusicBackground);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmLogin().setVisible(true);
				dispose();
			}
		});
		lblYtMusicBackground.setIcon(new ImageIcon(FrmLogin.class.getResource("/images/YTMusicBackground.jpg")));
		lblYtMusicBackground.setBounds(0, 1, 108, 53);
		contentPane.add(lblYtMusicBackground);
		
		JLabel lblUserName = new JLabel("Kullan\u0131c\u0131 Ad\u0131: ");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setBounds(334, 266, 206, 91);
		contentPane.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtUserName.setForeground(Color.WHITE);
		txtUserName.setBackground(Color.BLACK);
		txtUserName.setBounds(577, 266, 264, 90);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblEMail = new JLabel("E-Mail:");
		lblEMail.setForeground(Color.WHITE);
		lblEMail.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblEMail.setBounds(334, 434, 136, 91);
		contentPane.add(lblEMail);
		
		txtEMail = new JTextField();
		txtEMail.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtEMail.setForeground(Color.WHITE);
		txtEMail.setColumns(10);
		txtEMail.setBackground(Color.BLACK);
		txtEMail.setBounds(577, 434, 264, 90);
		contentPane.add(txtEMail);
		
		JLabel lblPassword = new JLabel("\u015Eifre:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblPassword.setBounds(334, 613, 194, 91);
		contentPane.add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setForeground(Color.WHITE);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtPassword.setBackground(Color.BLACK);
		txtPassword.setBounds(577, 615, 264, 90);
		contentPane.add(txtPassword);
		
		JLabel lblPremium = new JLabel("Premium:");
		lblPremium.setForeground(Color.WHITE);
		lblPremium.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblPremium.setBounds(1056, 267, 206, 91);
		contentPane.add(lblPremium);
		
		txtPremium = new JTextField();
		txtPremium.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtPremium.setForeground(Color.WHITE);
		txtPremium.setColumns(10);
		txtPremium.setBackground(Color.BLACK);
		txtPremium.setBounds(1272, 267, 264, 90);
		contentPane.add(txtPremium);
		
		JLabel lblCountry = new JLabel("\u00DClke:");
		lblCountry.setForeground(Color.WHITE);
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblCountry.setBounds(1056, 435, 206, 91);
		contentPane.add(lblCountry);
		
		txtCountry = new JTextField();
		txtCountry.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtCountry.setForeground(Color.WHITE);
		txtCountry.setColumns(10);
		txtCountry.setBackground(Color.BLACK);
		txtCountry.setBounds(1272, 435, 264, 90);
		contentPane.add(txtCountry);
		
		JButton btnRegister = new JButton("KAYDOL");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnRegister);
			}
		});
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				try {
					connection = dbHelper.getConnection();
					String sql = "insert into user (username, email, password, is_premium, country) values (?, ?, ?, ?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setString(1, txtUserName.getText());
					statement.setString(2, txtEMail.getText());
					statement.setString(3, txtPassword.getText());
					statement.setBoolean(4, Boolean.valueOf(txtPremium.getText()));
					statement.setString(5, txtCountry.getText());
					statement.executeUpdate();
					
					addCalmaListesi("Pop");
					addCalmaListesi("Jazz");
					addCalmaListesi("Klasik");
				} 
				
				catch (SQLException e2) {
					dbHelper.showErrorMessage(e2);
				}
				
				finally {
					try {
						statement.close();
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				new FrmLogin().setVisible(true);
				dispose();
			}
		});
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnRegister.setBorderPainted(false);
		btnRegister.setBackground(Color.DARK_GRAY);
		btnRegister.setBounds(1060, 614, 476, 91);
		contentPane.add(btnRegister);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(FrmLogin.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}
	
	
	public void addCalmaListesi(String tur) {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		PreparedStatement statement = null;
		try {
			ArrayList<User> users = userGetData.getData();
			for (User user : users) {
				id = user.getId();
			}
			connection = dbHelper.getConnection();
			String sql = "insert into calma_listesi (calma_listesi_name, user_id) values (?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, tur);
			statement.setInt(2, id);
			statement.executeUpdate();
		} 
		catch (SQLException e2) {
			dbHelper.showErrorMessage(e2);
		}
		finally {
			try {
				statement.close();
				connection.close();
			} 
			catch (SQLException e1) {}
		}
	}
}
