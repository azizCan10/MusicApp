package muzikDosyam.ui.user;

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
import muzikDosyam.db.CalmaListesi;
import muzikDosyam.db.DbHelper;
import muzikDosyam.db.Music;
import muzikDosyam.db.MusicCalmaListesi;
import muzikDosyam.db.User;
import muzikDosyam.db.UserArtist;
import muzikDosyam.getData.AlbumGetData;
import muzikDosyam.getData.ArtistGetData;
import muzikDosyam.getData.CalmaListesiGetData;
import muzikDosyam.getData.MusicCalmaListesiGetData;
import muzikDosyam.getData.MusicGetData;
import muzikDosyam.ui.FrmKullaniciSecme;
import muzikDosyam.ui.FrmLogin;

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

public class FrmUserMusics extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private ArtistGetData artistGetData;
	private AlbumGetData albumGetData;
	private MusicGetData musicGetData;
	private MusicCalmaListesiGetData musicCalmaListesiGetData;
	private CalmaListesiGetData calmaListesiGetData;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmUserMusics frame = new FrmUserMusics();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JTable tblMusics;
	private JTextField txtCalmaListesi;
	private JTextField txtMusic;

	/**
	 * Create the frame.
	 */
	public FrmUserMusics() {
		artistGetData = new ArtistGetData();
		albumGetData = new AlbumGetData();
		musicGetData = new MusicGetData();
		musicCalmaListesiGetData = new MusicCalmaListesiGetData();
		calmaListesiGetData = new CalmaListesiGetData();
		
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
				new FrmLogin().setVisible(true);
				dispose();
			}
		});
		lblYtMusicBackground.setIcon(new ImageIcon(FrmUserMusics.class.getResource("/images/YTMusicBackground.jpg")));
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
				new FrmUserAlbums().setVisible(true);
				dispose();
			}
		});
		lblAlbums.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAlbums.setForeground(Color.decode("#696969"));
		lblAlbums.setBounds(415, 10, 121, 43);
		contentPane.add(lblAlbums);
		
		JLabel lblCalmaListeleri = new JLabel("\u00C7alma Listeleri");
		lblCalmaListeleri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblCalmaListeleri);
				lblCalmaListeleri.setForeground(Color.decode("#ffffff"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCalmaListeleri.setForeground(Color.decode("#696969"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmUserCalmaListeleri().setVisible(true);
				dispose();
			}
		});
		lblCalmaListeleri.setForeground(SystemColor.controlDkShadow);
		lblCalmaListeleri.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCalmaListeleri.setBounds(926, 10, 166, 43);
		contentPane.add(lblCalmaListeleri);
		
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
				new FrmUserArtists().setVisible(true);
				dispose();
			}
		});
		lblArtists.setForeground(Color.decode("#696969"));
		lblArtists.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblArtists.setBounds(583, 10, 121, 43);
		contentPane.add(lblArtists);
		
		JLabel lblKitaplik = new JLabel("Kitapl\u0131k");
		lblKitaplik.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblKitaplik);
				lblKitaplik.setForeground(Color.decode("#ffffff"));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblKitaplik.setForeground(Color.decode("#696969"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmUserKitaplik().setVisible(true);
				dispose();
			}
		});
		lblKitaplik.setForeground(SystemColor.controlDkShadow);
		lblKitaplik.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblKitaplik.setBounds(1153, 10, 99, 43);
		contentPane.add(lblKitaplik);
		
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
		
		JLabel lblKesfet = new JLabel("Ke\u015Ffet");
		lblKesfet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmUserKesfet().setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblKesfet);
				lblKesfet.setForeground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblKesfet.setForeground(Color.decode("#696969"));
			}
		});
		lblKesfet.setForeground(Color.decode("#696969"));
		lblKesfet.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblKesfet.setBounds(264, 10, 121, 43);
		contentPane.add(lblKesfet);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setFocusable(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(110, 185, 1650, 450);
		contentPane.add(scrollPane);

		tblMusics = new JTable();
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
		
		JLabel lblCalmaListesiId = new JLabel("\u00C7alma Listesi:");
		lblCalmaListesiId.setForeground(Color.WHITE);
		lblCalmaListesiId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCalmaListesiId.setBounds(110, 782, 157, 60);
		contentPane.add(lblCalmaListesiId);
		
		txtCalmaListesi = new JTextField();
		txtCalmaListesi.setForeground(Color.WHITE);
		txtCalmaListesi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCalmaListesi.setColumns(10);
		txtCalmaListesi.setBackground(Color.BLACK);
		txtCalmaListesi.setBounds(277, 782, 267, 60);
		contentPane.add(txtCalmaListesi);
		
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
					String sql = "insert into music_calma_listesi (music_id, calma_listesi_id) values (?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, Integer.valueOf(txtMusic.getText()));
					statement.setInt(2, Integer.valueOf(txtCalmaListesi.getText()));
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
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBorderPainted(false);
		btnAdd.setBackground(Color.DARK_GRAY);
		btnAdd.setBounds(1493, 782, 267, 60);
		contentPane.add(btnAdd);
		
		JLabel lblMusicId = new JLabel("M\u00FCzik:");
		lblMusicId.setForeground(Color.WHITE);
		lblMusicId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMusicId.setBounds(625, 782, 84, 60);
		contentPane.add(lblMusicId);
		
		txtMusic = new JTextField();
		txtMusic.setForeground(Color.WHITE);
		txtMusic.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMusic.setColumns(10);
		txtMusic.setBackground(Color.BLACK);
		txtMusic.setBounds(719, 782, 267, 60);
		contentPane.add(txtMusic);
		
		JLabel lblWelcome = new JLabel("Ho\u015Fgeldiniz,");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setForeground(Color.LIGHT_GRAY);
		lblWelcome.setBounds(1640, 19, 75, 27);
		contentPane.add(lblWelcome);
		
		JLabel lblWelcomeName = new JLabel("");
		lblWelcomeName.setForeground(Color.WHITE);
		lblWelcomeName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWelcomeName.setBounds(1725, 19, 189, 27);
		lblWelcomeName.setText(FrmLogin.userName);
		contentPane.add(lblWelcomeName);
		
		JButton btnDelete = new JButton("S\u0130L");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				ResultSet resultSet;
				
				try {
					ArrayList<MusicCalmaListesi> musicCalmaListesis = musicCalmaListesiGetData.getData();
					connection = dbHelper.getConnection();
					String sql = "delete from music_calma_listesi where music_id = ? and calma_listesi_id = ?";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, Integer.valueOf(txtMusic.getText()));
					statement.setInt(2, Integer.valueOf(txtCalmaListesi.getText()));
					statement.executeUpdate();
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
		btnDelete.setBounds(1089, 782, 267, 60);
		contentPane.add(btnDelete);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIconTextGap(0);
		lblBackground.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblBackground.setIcon(new ImageIcon(FrmUserMusics.class.getResource("/images/background.jpg")));
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
