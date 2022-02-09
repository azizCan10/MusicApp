package muzikDosyam.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import muzikDosyam.Utils;
import muzikDosyam.ui.admin.FrmAdminAlbums;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmKullaniciSecme extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmKullaniciSecme frame = new FrmKullaniciSecme();
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
	public FrmKullaniciSecme() {
		setTitle("M\u00FCzik Dosyam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-11, -1, 1941, 1049);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdmin = new JButton("ADM\u0130N");
		btnAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnAdmin);
			}
		});
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmAdminAlbums().setVisible(true);
				dispose();
			}
		});
		btnAdmin.setForeground(Color.WHITE);
		btnAdmin.setBackground(Color.DARK_GRAY);
		btnAdmin.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnAdmin.setBounds(548, 426, 258, 147);
		btnAdmin.setBorderPainted(false);
		contentPane.add(btnAdmin);
		
		JButton btnUser = new JButton("KULLANICI");
		btnUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnUser);
			}
		});
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmLogin().setVisible(true);
				dispose();
			}
		});
		btnUser.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnUser.setForeground(Color.WHITE);
		btnUser.setBackground(Color.DARK_GRAY);
		btnUser.setBounds(1103, 426, 258, 147);
		btnUser.setBorderPainted(false);
		contentPane.add(btnUser);
		
		JButton btnExit = new JButton("\u00C7IKI\u015E");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnExit);
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.DARK_GRAY);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnExit.setBounds(887, 887, 181, 78);
		btnExit.setBorderPainted(false);
		contentPane.add(btnExit);
		
		JLabel lblYTMusicBackground = new JLabel("");
		lblYTMusicBackground.setIcon(new ImageIcon(FrmKullaniciSecme.class.getResource("/images/YTMusicBackground.jpg")));
		lblYTMusicBackground.setBounds(0, 1, 108, 53);
		contentPane.add(lblYTMusicBackground);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(FrmKullaniciSecme.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}
}
