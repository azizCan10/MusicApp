package muzikDosyam.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import muzikDosyam.Utils;
import muzikDosyam.db.Album;
import muzikDosyam.db.DbHelper;
import muzikDosyam.db.User;
import muzikDosyam.getData.UserGetData;
import muzikDosyam.ui.user.FrmUserAlbums;
import muzikDosyam.ui.user.FrmUserKesfet;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtEMail;
	private JPasswordField txtPassword;
	public static String userName;
	public static int userId;
	private UserGetData userGetData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLogin frame = new FrmLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JLabel lblWrongInfo;

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
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
				new FrmKullaniciSecme().setVisible(true);
				dispose();
			}
		});
		lblYtMusicBackground.setIcon(new ImageIcon(FrmLogin.class.getResource("/images/YTMusicBackground.jpg")));
		lblYtMusicBackground.setBounds(0, 1, 108, 53);
		contentPane.add(lblYtMusicBackground);
		
		txtEMail = new JTextField();
		txtEMail.setForeground(Color.WHITE);
		txtEMail.setBackground(Color.BLACK);
		txtEMail.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtEMail.setBounds(904, 279, 264, 90);
		contentPane.add(txtEMail);
		txtEMail.setColumns(10);
		
		JButton btnLogin = new JButton("G\u0130R\u0130\u015E");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnLogin);
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				ResultSet resultSet;
				try {
					String sql = "select * from user where email = ? and password = ?";
					connection = dbHelper.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, txtEMail.getText());
					preparedStatement.setString(2, txtPassword.getText());
					resultSet = preparedStatement.executeQuery();
					
					int count = 0;
					
					while (resultSet.next()) {
						count++;
						userName = resultSet.getString("username");
						userId = resultSet.getInt("user_id");
					}
					
					if (count == 1) {
						new FrmUserKesfet().setVisible(true);
						dispose();
					}
					
					else {
						lblWrongInfo.setVisible(true);
					}
				}
				catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setBounds(761, 658, 278, 79);
		btnLogin.setBorderPainted(false);
		contentPane.add(btnLogin);
		
		JLabel lblEMail = new JLabel("E-Mail:");
		lblEMail.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblEMail.setForeground(Color.WHITE);
		lblEMail.setBounds(690, 277, 136, 91);
		contentPane.add(lblEMail);
		
		txtPassword = new JPasswordField();
		txtPassword.setForeground(Color.WHITE);
		txtPassword.setBackground(Color.BLACK);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 23));
		txtPassword.setBounds(904, 451, 264, 90);
		contentPane.add(txtPassword);
		
		JLabel lblPassword = new JLabel("\u015Eifre:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 27));
		lblPassword.setBounds(690, 451, 194, 91);
		contentPane.add(lblPassword);
		
		JLabel lblLoginText = new JLabel("Hesab\u0131n\u0131z yok mu?");
		lblLoginText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLoginText.setForeground(Color.LIGHT_GRAY);
		lblLoginText.setBounds(761, 773, 148, 71);
		contentPane.add(lblLoginText);
		
		JLabel lblLogin = new JLabel("KAYDOLUN");
		lblLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblLogin);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmRegister().setVisible(true);
				dispose();
			}
		});
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setBounds(919, 773, 116, 71);
		contentPane.add(lblLogin);
		
		lblWrongInfo = new JLabel("E-Mail veya \u015Fifre yanl\u0131\u015F. L\u00FCtfen tekrar deneyin.");
		lblWrongInfo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWrongInfo.setForeground(Color.RED);
		lblWrongInfo.setBounds(708, 189, 505, 53);
		lblWrongInfo.setVisible(false);
		contentPane.add(lblWrongInfo);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(FrmLogin.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}
}
