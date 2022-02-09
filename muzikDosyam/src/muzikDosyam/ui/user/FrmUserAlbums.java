package muzikDosyam.ui.user;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import muzikDosyam.Utils;
import muzikDosyam.db.Album;
import muzikDosyam.db.Artist;
import muzikDosyam.db.CalmaListesi;
import muzikDosyam.db.DbHelper;
import muzikDosyam.getData.AlbumGetData;
import muzikDosyam.getData.ArtistGetData;
import muzikDosyam.getData.CalmaListesiGetData;
import muzikDosyam.ui.FrmLogin;
import muzikDosyam.ui.FrmMusic;

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

public class FrmUserAlbums extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private AlbumGetData albumGetData;
	private ArtistGetData artistGetData;
	public static int frmUserAlbumId;
	public static boolean frmUserAlbum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmUserAlbums frame = new FrmUserAlbums();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JTable tblAlbums;
	private JTextField txtAlbum;

	/**
	 * Create the frame.
	 */
	public FrmUserAlbums() {
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
				new FrmLogin().setVisible(true);
				dispose();
			}
		});
		lblYtMusicBackground.setIcon(new ImageIcon(FrmUserAlbums.class.getResource("/images/YTMusicBackground.jpg")));
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
				new FrmUserArtists().setVisible(true);
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
				new FrmUserMusics().setVisible(true);
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
				int row = tblAlbums.getSelectedRow();
				frmUserAlbumId = (int) (tblAlbums.getModel().getValueAt(row, 0));
				
				frmUserAlbum = true;
				
				new FrmMusic().setVisible(true);
				dispose();
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
		
		JLabel lblAlbumId = new JLabel("Alb\u00FCm:");
		lblAlbumId.setForeground(Color.WHITE);
		lblAlbumId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAlbumId.setBounds(110, 782, 108, 60);
		contentPane.add(lblAlbumId);
		
		txtAlbum = new JTextField();
		txtAlbum.setForeground(Color.WHITE);
		txtAlbum.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAlbum.setColumns(10);
		txtAlbum.setBackground(Color.BLACK);
		txtAlbum.setBounds(248, 782, 267, 60);
		contentPane.add(txtAlbum);
		
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
					ArrayList<Album> albums = albumGetData.getData();
					connection = dbHelper.getConnection();
					String sql = "insert into calma_listesi (calma_listesi_name, user_id) values (?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setString(1, albums.get(Integer.valueOf(txtAlbum.getText())-1).getName());
					statement.setInt(2, FrmLogin.userId);
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
					catch (SQLException e1) {
						System.out.println(e1);
					}
				}
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAdd.setBorderPainted(false);
		btnAdd.setBackground(Color.DARK_GRAY);
		btnAdd.setBounds(1493, 782, 267, 60);
		contentPane.add(btnAdd);
		
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
					ArrayList<Album> albums = albumGetData.getData();
					connection = dbHelper.getConnection();
					String sql = "delete from calma_listesi where calma_listesi_name = ? and user_id = ?";
					statement = connection.prepareStatement(sql);
					statement.setString(1, albums.get(Integer.valueOf(txtAlbum.getText())-1).getName());
					statement.setInt(2, FrmLogin.userId);
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
		btnDelete.setBounds(845, 782, 267, 60);
		contentPane.add(btnDelete);
		
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
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIconTextGap(0);
		lblBackground.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblBackground.setIcon(new ImageIcon(FrmUserAlbums.class.getResource("/images/background.jpg")));
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
