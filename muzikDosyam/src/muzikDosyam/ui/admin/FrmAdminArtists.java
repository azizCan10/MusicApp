package muzikDosyam.ui.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import muzikDosyam.Utils;
import muzikDosyam.db.Artist;
import muzikDosyam.db.DbHelper;
import muzikDosyam.getData.ArtistGetData;
import muzikDosyam.ui.FrmKullaniciSecme;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmAdminArtists extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private ArtistGetData artistGetData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmAdminArtists frame = new FrmAdminArtists();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JTable tblArtists;
	private JTextField txtName;
	private JTextField txtCountry;
	private JTextField txtId;
	
	/**
	 * Create the frame.
	 */
	public FrmAdminArtists() {
		artistGetData = new ArtistGetData();
		
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
		lblYtMusicBackground.setIcon(new ImageIcon(FrmAdminAlbums.class.getResource("/images/YTMusicBackground.jpg")));
		lblYtMusicBackground.setBounds(0, 1, 108, 53);
		contentPane.add(lblYtMusicBackground);
		
		JLabel lblAlbums = new JLabel("Alb\u00FCmler");
		lblAlbums.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblAlbums);
				lblAlbums.setForeground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAlbums.setForeground(Color.decode("#696969"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmAdminAlbums().setVisible(true);
				dispose();
			}
		});
		lblAlbums.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAlbums.setForeground(Color.decode("#696969"));
		lblAlbums.setBounds(415, 10, 121, 43);
		contentPane.add(lblAlbums);
		
		JLabel lblArtists = new JLabel("Sanat\u00E7\u0131lar");
		lblArtists.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblArtists);
			}
		});
		lblArtists.setForeground(Color.WHITE);
		lblArtists.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblArtists.setBounds(583, 10, 121, 43);
		contentPane.add(lblArtists);
		
		JLabel lblMusics = new JLabel("M\u00FCzikler");
		lblMusics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblMusics);
				lblMusics.setForeground(Color.decode("#ffffff"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblMusics.setForeground(Color.decode("#696969"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmAdminMusics().setVisible(true);
				dispose();
			}
		});
		lblMusics.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMusics.setBounds(768, 11, 121, 43);
		lblMusics.setForeground(Color.decode("#696969"));
		contentPane.add(lblMusics);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setFocusable(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(110, 185, 1650, 450);
		contentPane.add(scrollPane);
		
		tblArtists = new JTable();
		tblArtists.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				try {
					int row = tblArtists.getSelectedRow();
					String id = (tblArtists.getModel().getValueAt(row, 0).toString());
					
					connection = dbHelper.getConnection();
					String sql = "select * from artist where artist_id = " + id + "";
					statement = connection.prepareStatement(sql);
					resultSet = statement.executeQuery();
					
					while (resultSet.next()) {
						txtId.setText(String.valueOf(resultSet.getInt("artist_id")));
						txtName.setText(resultSet.getString("artist_name"));   
						txtCountry.setText(resultSet.getString("artist_country"));
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		tblArtists.setShowVerticalLines(false);
		tblArtists.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "\u0130sim", "\u00DClke"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblArtists.setForeground(Color.WHITE);
		tblArtists.setBackground(Color.BLACK);
		
		tblArtists.setForeground(Color.WHITE);
		tblArtists.setBackground(Color.BLACK);
		tblArtists.getTableHeader().setOpaque(false);
		tblArtists.getTableHeader().setBackground(Color.BLACK);
		tblArtists.getTableHeader().setForeground(Color.WHITE);
		tblArtists.getTableHeader().setPreferredSize(new Dimension(0, 40));
		tblArtists.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		tblArtists.setRowHeight(40);
		UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
		
		scrollPane.setViewportView(tblArtists);
		
		populateTable();
		
		JLabel lblName = new JLabel("\u0130sim:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(716, 711, 70, 60);
		contentPane.add(lblName);
		
		JLabel lblCountry = new JLabel("\u00DClke:");
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCountry.setForeground(Color.WHITE);
		lblCountry.setBounds(1424, 711, 70, 60);
		contentPane.add(lblCountry);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtName.setForeground(Color.WHITE);
		txtName.setBackground(Color.BLACK);
		txtName.setBounds(785, 711, 267, 60);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtCountry = new JTextField();
		txtCountry.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCountry.setForeground(Color.WHITE);
		txtCountry.setBackground(Color.BLACK);
		txtCountry.setBounds(1493, 711, 267, 60);
		contentPane.add(txtCountry);
		txtCountry.setColumns(10);
		
		JButton btnAdd = new JButton("EKLE");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				try {
					connection = dbHelper.getConnection();
					String sql = "insert into artist (artist_name, artist_country) values (?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setString(1, txtName.getText());
					statement.setString(2, txtCountry.getText());
					statement.executeUpdate();
					populateTable();
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
		});
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnAdd);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.DARK_GRAY);
		btnAdd.setBounds(166, 869, 222, 60);
		btnAdd.setBorderPainted(false);
		contentPane.add(btnAdd);
		
		txtId = new JTextField();
		txtId.setForeground(Color.WHITE);
		txtId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtId.setColumns(10);
		txtId.setBackground(Color.BLACK);
		txtId.setBounds(166, 711, 267, 60);
		contentPane.add(txtId);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblId.setBounds(110, 711, 52, 60);
		contentPane.add(lblId);
		
		JButton btnUpdate = new JButton("G\u00DCNCELLE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				
				try {
					connection = dbHelper.getConnection();
					String sql = "update artist set artist_name ='" + txtName.getText() + "', artist_country = '" + txtCountry.getText() + "' where artist_id = ?";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, Integer.valueOf(txtId.getText()));
					statement.executeUpdate();
					populateTable();
				} 
				
				catch (SQLException e4) {
					dbHelper.showErrorMessage(e4);
				}
				
				finally {
					try {
						statement.close();
						connection.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnUpdate);		}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBackground(Color.DARK_GRAY);
		btnUpdate.setBounds(808, 869, 222, 60);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("S\u0130L");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnDelete);
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				
				try {
					connection = dbHelper.getConnection();
					String sql = "delete from artist where artist_id = ?";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, Integer.valueOf(txtId.getText()));
					statement.executeUpdate();
					populateTable();
				} 
				
				catch (SQLException e3) {
					dbHelper.showErrorMessage(e3);
				}
				
				finally {
					try {
						statement.close();
						connection.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDelete.setBorderPainted(false);
		btnDelete.setBackground(Color.DARK_GRAY);
		btnDelete.setBounds(1538, 869, 222, 60);
		contentPane.add(btnDelete);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIconTextGap(0);
		lblBackground.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblBackground.setIcon(new ImageIcon(FrmAdminAlbums.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}
	
	public void populateTable() {
		model = (DefaultTableModel)tblArtists.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<Artist> artists = artistGetData.getData();
			for (Artist artist : artists) {
				Object[] row = {artist.getId(), artist.getName(), artist.getArtist_country()};
				model.addRow(row);
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
