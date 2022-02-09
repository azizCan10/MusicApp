package muzikDosyam.ui.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import muzikDosyam.Utils;
import muzikDosyam.db.Album;
import muzikDosyam.db.Artist;
import muzikDosyam.db.DbHelper;
import muzikDosyam.db.Music;
import muzikDosyam.getData.AlbumGetData;
import muzikDosyam.getData.ArtistGetData;
import muzikDosyam.getData.MusicGetData;
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
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmAdminMusics extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private ArtistGetData artistGetData;
	private AlbumGetData albumGetData;
	private MusicGetData musicGetData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmAdminMusics frame = new FrmAdminMusics();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JTable tblMusics;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtArtist;
	private JTextField txtAlbum;
	private JTextField txtDate;
	private JTextField txtTime;
	private JTextField txtDinlenmeSayisi;
	private JTextField txtGenre;
	
	/**
	 * Create the frame.
	 */
	public FrmAdminMusics() {
		artistGetData = new ArtistGetData();
		albumGetData = new AlbumGetData();
		musicGetData = new MusicGetData();
		
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
			}
		});
		lblMusics.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMusics.setBounds(768, 11, 121, 43);
		lblMusics.setForeground(Color.WHITE);
		contentPane.add(lblMusics);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setFocusable(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(110, 185, 1650, 450);
		contentPane.add(scrollPane);

		tblMusics = new JTable();
		tblMusics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				try {
					int row = tblMusics.getSelectedRow();
					String id = (tblMusics.getModel().getValueAt(row, 0).toString());
					
					connection = dbHelper.getConnection();
					String sql = "select * from music where music_id = " + id + "";
					statement = connection.prepareStatement(sql);
					resultSet = statement.executeQuery();
					
					while (resultSet.next()) {
						txtId.setText(String.valueOf(resultSet.getInt("music_id")));
						txtName.setText(resultSet.getString("music_name"));   
						txtDate.setText(resultSet.getString("date"));
						txtTime.setText(String.valueOf(resultSet.getDouble("time")));
						txtDinlenmeSayisi.setText(String.valueOf(resultSet.getInt("dinlenme_sayisi")));
						txtGenre.setText(resultSet.getString("genre"));
						txtArtist.setText(String.valueOf(resultSet.getInt("artist_id")));
						txtAlbum.setText(String.valueOf(resultSet.getInt("album_id")));
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		tblMusics.setRowMargin(0);
		tblMusics.setIntercellSpacing(new Dimension(0, 0));
		tblMusics.setFocusable(false);
		tblMusics.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "\u0130sim", "Sanat\u00E7\u0131", "Alb\u00FCm", "Tarih", "s\u00FCre", "Dinlenme Say\u0131s\u0131", "T\u00FCr"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, Double.class, Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblMusics.setShowVerticalLines(false);
		tblMusics.setBorder(null);
		tblMusics.setForeground(Color.WHITE);
		tblMusics.setBackground(Color.BLACK);
		
		tblMusics.setForeground(Color.WHITE);
		tblMusics.setBackground(Color.BLACK);
		tblMusics.getTableHeader().setOpaque(false);
		tblMusics.getTableHeader().setBackground(Color.BLACK);
		tblMusics.getTableHeader().setForeground(Color.WHITE);
		tblMusics.getTableHeader().setPreferredSize(new Dimension(0, 40));
		tblMusics.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		tblMusics.setRowHeight(40);
		UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
		
		scrollPane.setViewportView(tblMusics);
		
		populateTable();
		
		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblId.setBounds(110, 711, 52, 60);
		contentPane.add(lblId);
		
		txtId = new JTextField();
		txtId.setForeground(Color.WHITE);
		txtId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtId.setColumns(10);
		txtId.setBackground(Color.BLACK);
		txtId.setBounds(144, 711, 130, 60);
		contentPane.add(txtId);
		
		JLabel lblName = new JLabel("\u0130sim:");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName.setBounds(284, 711, 65, 60);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setForeground(Color.WHITE);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtName.setColumns(10);
		txtName.setBackground(Color.BLACK);
		txtName.setBounds(347, 711, 130, 60);
		contentPane.add(txtName);
		
		JLabel lblArtist = new JLabel("Sanat\u00E7\u0131:");
		lblArtist.setForeground(Color.WHITE);
		lblArtist.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblArtist.setBounds(487, 711, 87, 60);
		contentPane.add(lblArtist);
		
		txtArtist = new JTextField();
		txtArtist.setForeground(Color.WHITE);
		txtArtist.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtArtist.setColumns(10);
		txtArtist.setBackground(Color.BLACK);
		txtArtist.setBounds(574, 711, 130, 60);
		contentPane.add(txtArtist);
		
		JLabel lblAlbum = new JLabel("Alb\u00FCm:");
		lblAlbum.setForeground(Color.WHITE);
		lblAlbum.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAlbum.setBounds(714, 711, 75, 60);
		contentPane.add(lblAlbum);
		
		txtAlbum = new JTextField();
		txtAlbum.setForeground(Color.WHITE);
		txtAlbum.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAlbum.setColumns(10);
		txtAlbum.setBackground(Color.BLACK);
		txtAlbum.setBounds(788, 711, 130, 60);
		contentPane.add(txtAlbum);
		
		JLabel lblDate = new JLabel("Tarih:");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDate.setBounds(928, 711, 65, 60);
		contentPane.add(lblDate);
		
		txtDate = new JTextField();
		txtDate.setForeground(Color.WHITE);
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDate.setColumns(10);
		txtDate.setBackground(Color.BLACK);
		txtDate.setBounds(993, 711, 130, 60);
		contentPane.add(txtDate);
		
		JLabel lblTime = new JLabel("S\u00FCre:");
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTime.setBounds(1133, 711, 65, 60);
		contentPane.add(lblTime);
		
		txtTime = new JTextField();
		txtTime.setForeground(Color.WHITE);
		txtTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTime.setColumns(10);
		txtTime.setBackground(Color.BLACK);
		txtTime.setBounds(1197, 711, 130, 60);
		contentPane.add(txtTime);
		
		JLabel lblDinlenmeSayisi = new JLabel("Dinlenme:");
		lblDinlenmeSayisi.setForeground(Color.WHITE);
		lblDinlenmeSayisi.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDinlenmeSayisi.setBounds(1337, 711, 102, 60);
		contentPane.add(lblDinlenmeSayisi);
		
		txtDinlenmeSayisi = new JTextField();
		txtDinlenmeSayisi.setForeground(Color.WHITE);
		txtDinlenmeSayisi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDinlenmeSayisi.setColumns(10);
		txtDinlenmeSayisi.setBackground(Color.BLACK);
		txtDinlenmeSayisi.setBounds(1438, 711, 130, 60);
		contentPane.add(txtDinlenmeSayisi);
		
		JLabel lblGenre = new JLabel("T\u00FCr:");
		lblGenre.setForeground(Color.WHITE);
		lblGenre.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGenre.setBounds(1578, 711, 52, 60);
		contentPane.add(lblGenre);
		
		txtGenre = new JTextField();
		txtGenre.setForeground(Color.WHITE);
		txtGenre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtGenre.setColumns(10);
		txtGenre.setBackground(Color.BLACK);
		txtGenre.setBounds(1630, 711, 130, 60);
		contentPane.add(txtGenre);
		
		JButton btnAdd = new JButton("EKLE");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				try {
					connection = dbHelper.getConnection();
					String sql = "insert into music (music_name, artist_id, album_id, date, time, dinlenme_sayisi, genre) values (?, ?, ?, ?, ?, ?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setString(1, txtName.getText());
					statement.setInt(2, Integer.valueOf(txtArtist.getText()));
					statement.setInt(3, Integer.valueOf(txtAlbum.getText()));
					statement.setString(4, txtDate.getText());
					statement.setDouble(5, Double.valueOf(txtTime.getText()));
					statement.setInt(6, Integer.valueOf(txtDinlenmeSayisi.getText()));
					statement.setString(7, txtGenre.getText());
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
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnAdd);
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBorderPainted(false);
		btnAdd.setBackground(Color.DARK_GRAY);
		btnAdd.setBounds(144, 869, 222, 60);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("G\u00DCNCELLE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				
				try {
					connection = dbHelper.getConnection();
					String sql = "update music set music_name ='" + txtName.getText() + "', artist_id = " + Integer.valueOf(txtArtist.getText()) + ", album_id = " + Integer.valueOf(txtAlbum.getText()) + ", date = '" + txtDate.getText() + "', time = " + Double.valueOf(txtTime.getText()) + ", dinlenme_sayisi = " + Integer.valueOf(txtDinlenmeSayisi.getText()) + ",genre = '" + txtGenre.getText() + "' where music_id = ?";
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
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnUpdate);
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBackground(Color.DARK_GRAY);
		btnUpdate.setBounds(808, 869, 222, 60);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("S\u0130L");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				
				try {
					connection = dbHelper.getConnection();
					String sql = "delete from music where music_id = ?";
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
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnDelete);
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
		model = (DefaultTableModel)tblMusics.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<Artist> artists = artistGetData.getData();
			ArrayList<Album> albums = albumGetData.getData();
			ArrayList<Music> musics = musicGetData.getData();
			for (Music music : musics) {
				Object[] row = {music.getId(), music.getName(), artists.get(music.getArtist_id()-1).getName(), albums.get(music.getAlbum_id()-1).getName(), music.getDate(), music.getTime(), music.getDinlenme_sayisi(), music.getGenre()};
				model.addRow(row);
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
