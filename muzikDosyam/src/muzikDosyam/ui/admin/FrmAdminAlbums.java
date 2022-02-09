package muzikDosyam.ui.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import muzikDosyam.Utils;
import muzikDosyam.db.Album;
import muzikDosyam.db.Artist;
import muzikDosyam.db.DbHelper;
import muzikDosyam.getData.AlbumGetData;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmAdminAlbums extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private AlbumGetData albumGetData;
	private ArtistGetData artistGetData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmAdminAlbums frame = new FrmAdminAlbums();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JTable tblAlbums;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtArtist;
	private JTextField txtDate;
	private JTextField txtGenre;

	/**
	 * Create the frame.
	 */
	public FrmAdminAlbums() {
		albumGetData = new AlbumGetData();
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
			}
		});
		lblAlbums.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAlbums.setForeground(Color.WHITE);
		lblAlbums.setBounds(415, 10, 121, 43);
		contentPane.add(lblAlbums);
		
		JLabel lblArtists = new JLabel("Sanat\u00E7\u0131lar");
		lblArtists.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblArtists);
				lblArtists.setForeground(Color.decode("#ffffff"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblArtists.setForeground(Color.decode("#696969"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmAdminArtists().setVisible(true);
				dispose();
			}
		});
		lblArtists.setForeground(Color.decode("#696969"));
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
		
		tblAlbums = new JTable();
		tblAlbums.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				try {
					int row = tblAlbums.getSelectedRow();
					String id = (tblAlbums.getModel().getValueAt(row, 0).toString());
					
					connection = dbHelper.getConnection();
					String sql = "select * from album where album_id = " + id + "";
					statement = connection.prepareStatement(sql);
					resultSet = statement.executeQuery();
					
					while (resultSet.next()) {
						txtId.setText(String.valueOf(resultSet.getInt("album_id")));
						txtName.setText(resultSet.getString("album_name"));   
						txtArtist.setText(String.valueOf(resultSet.getInt("artist_id")));
						txtDate.setText(resultSet.getString("date"));
						txtGenre.setText(resultSet.getString("genre"));
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		tblAlbums.setShowGrid(false);
		tblAlbums.setShowVerticalLines(false);
		tblAlbums.setShowHorizontalLines(true);
		tblAlbums.setBorder(null);
		tblAlbums.setForeground(Color.WHITE);
		tblAlbums.setBackground(Color.BLACK);
		tblAlbums.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Alb\u00FCm", "Sanat\u00E7\u0131", "Tarih", "T\u00FCr"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		tblAlbums.setForeground(Color.WHITE);
		tblAlbums.setBackground(Color.BLACK);
		tblAlbums.getTableHeader().setOpaque(false);
		tblAlbums.getTableHeader().setBackground(Color.BLACK);
		tblAlbums.getTableHeader().setForeground(Color.WHITE);
		tblAlbums.getTableHeader().setPreferredSize(new Dimension(0, 40));
		tblAlbums.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		tblAlbums.setRowHeight(40);
		UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
		
		scrollPane.setViewportView(tblAlbums);
		populateTable();
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblId.setForeground(Color.WHITE);
		lblId.setBounds(110, 711, 52, 60);
		contentPane.add(lblId);
		
		txtId = new JTextField();
		txtId.setForeground(Color.WHITE);
		txtId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtId.setColumns(10);
		txtId.setBackground(Color.BLACK);
		txtId.setBounds(172, 711, 210, 60);
		contentPane.add(txtId);
		
		JLabel lblName = new JLabel("\u0130sim:");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName.setBounds(425, 711, 65, 60);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setForeground(Color.WHITE);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtName.setColumns(10);
		txtName.setBackground(Color.BLACK);
		txtName.setBounds(500, 711, 210, 60);
		contentPane.add(txtName);
		
		JLabel lblArtist = new JLabel("Sanat\u00E7\u0131:");
		lblArtist.setForeground(Color.WHITE);
		lblArtist.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblArtist.setBounds(768, 711, 91, 60);
		contentPane.add(lblArtist);
		
		txtArtist = new JTextField();
		txtArtist.setForeground(Color.WHITE);
		txtArtist.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtArtist.setColumns(10);
		txtArtist.setBackground(Color.BLACK);
		txtArtist.setBounds(869, 711, 210, 60);
		contentPane.add(txtArtist);
		
		JLabel lblDate = new JLabel("Tarih:");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDate.setBounds(1130, 711, 72, 60);
		contentPane.add(lblDate);
		
		txtDate = new JTextField();
		txtDate.setForeground(Color.WHITE);
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDate.setColumns(10);
		txtDate.setBackground(Color.BLACK);
		txtDate.setBounds(1212, 711, 210, 60);
		contentPane.add(txtDate);
		
		JLabel lblGenre = new JLabel("T\u00FCr:");
		lblGenre.setForeground(Color.WHITE);
		lblGenre.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGenre.setBounds(1481, 711, 52, 60);
		contentPane.add(lblGenre);
		
		txtGenre = new JTextField();
		txtGenre.setForeground(Color.WHITE);
		txtGenre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtGenre.setColumns(10);
		txtGenre.setBackground(Color.BLACK);
		txtGenre.setBounds(1550, 711, 210, 60);
		contentPane.add(txtGenre);
		
		JButton btnAdd = new JButton("EKLE");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnAdd);
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				try {
					connection = dbHelper.getConnection();
					String sql = "insert into album (album_name, artist_id, date, genre) values (?, ?, ?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setString(1, txtName.getText());
					statement.setInt(2, Integer.valueOf(txtArtist.getText()));
					statement.setString(3, txtDate.getText());
					statement.setString(4, txtGenre.getText());
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
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBorderPainted(false);
		btnAdd.setBackground(Color.DARK_GRAY);
		btnAdd.setBounds(172, 869, 222, 60);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("G\u00DCNCELLE");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnUpdate);
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				
				try {
					connection = dbHelper.getConnection();
					String sql = "update album set album_name ='" + txtName.getText() + "', artist_id = " + Integer.valueOf(txtArtist.getText()) + ", date = '" + txtDate.getText() + "', genre = '" + txtGenre.getText() + "' where album_id = ?";
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
					String sql = "delete from album where album_id = ?";
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
		model = (DefaultTableModel)tblAlbums.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<Album> albums = albumGetData.getData();
			ArrayList<Artist> artists = artistGetData.getData();
			for (Album album : albums) {
				Object[] row = {album.getId(), album.getName(), artists.get(album.getArtist_id()-1).getName(), album.getDate(), album.getGenre()};
				model.addRow(row);
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
