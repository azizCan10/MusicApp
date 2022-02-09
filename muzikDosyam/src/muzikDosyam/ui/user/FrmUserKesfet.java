package muzikDosyam.ui.user;


import java.util.Random;

import java.util.Random;
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

public class FrmUserKesfet extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private JTable tblMusics;
	private JTextField txtMusic;
	int calmaListesiPopId;
	int calmaListesiJazzId;
	int calmaListesiKlasikId;
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
					FrmUserKesfet frame = new FrmUserKesfet();
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
	public FrmUserKesfet() {
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
				lblMusics.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmUserMusics().setVisible(true);
				dispose();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblMusics.setForeground(Color.decode("#696969"));
			}
		});
		lblMusics.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMusics.setBounds(768, 11, 121, 43);
		lblMusics.setForeground(Color.decode("#696969"));
		contentPane.add(lblMusics);
		
		JLabel lblKesfet = new JLabel("Ke\u015Ffet");
		lblKesfet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblKesfet);
			}
		});
		lblKesfet.setForeground(Color.WHITE);
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
		
		JLabel lblMusicId = new JLabel("M\u00FCzik:");
		lblMusicId.setForeground(Color.WHITE);
		lblMusicId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMusicId.setBounds(110, 782, 84, 60);
		contentPane.add(lblMusicId);
		
		txtMusic = new JTextField();
		txtMusic.setForeground(Color.WHITE);
		txtMusic.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMusic.setColumns(10);
		txtMusic.setBackground(Color.BLACK);
		txtMusic.setBounds(230, 782, 267, 60);
		contentPane.add(txtMusic);
		
		JButton btnDelete = new JButton("S\u0130L");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnDelete);
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDelete.setBorderPainted(false);
		btnDelete.setBackground(Color.DARK_GRAY);
		btnDelete.setBounds(857, 782, 267, 60);
		contentPane.add(btnDelete);
		
		JButton btnAdd = new JButton("EKLE");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnAdd);
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<CalmaListesi> calmaListesis = calmaListesiGetData.getData();
					for (CalmaListesi calmaListesi : calmaListesis) {
						if (calmaListesi.getUser_id() == FrmLogin.userId && calmaListesi.getName().equals("Pop")) {
							calmaListesiPopId = calmaListesi.getId();
						}
						if (calmaListesi.getUser_id() == FrmLogin.userId && calmaListesi.getName().equals("Jazz")) {
							calmaListesiJazzId = calmaListesi.getId();
						}
						if (calmaListesi.getUser_id() == FrmLogin.userId && calmaListesi.getName().equals("Klasik")) {
							calmaListesiKlasikId = calmaListesi.getId();
						}
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				try {
					ArrayList<MusicCalmaListesi> musicCalmaListesis = musicCalmaListesiGetData.getData();
					ArrayList<Music> musics = musicGetData.getData();
					connection = dbHelper.getConnection();
					String sql = "insert into music_calma_listesi (music_id, calma_listesi_id) values (?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, Integer.valueOf(txtMusic.getText()));
					if (musics.get(Integer.valueOf(txtMusic.getText())-1).getGenre().equals("Jazz")) {
						statement.setInt(2, calmaListesiJazzId);
					}
					if (musics.get(Integer.valueOf(txtMusic.getText())-1).getGenre().equals("Pop")) {
						statement.setInt(2, calmaListesiPopId);
					}
					if (musics.get(Integer.valueOf(txtMusic.getText())-1).getGenre().equals("Klasik")) {
						statement.setInt(2, calmaListesiKlasikId);
					}
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
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIconTextGap(0);
		lblBackground.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblBackground.setIcon(new ImageIcon(FrmUserMusics.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}
	
	public void populateTable() {
		Random random = new Random();
		int [] array= new int[20];
        int num, sameVal = 0;
        
        for (int i = 0; i < array.length; i++){
            sameVal = 0;
            num = random.nextInt(20);
            for (int j = 0; j < i; j++){
                if (num == array[j]){
                    sameVal--;
                }
            }
            if (sameVal <= -1){
               i--;
            }
            else{
                array[i] = num;
            }
        }
		model = (DefaultTableModel)tblMusics.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<Artist> artists = artistGetData.getData();
			ArrayList<Album> albums = albumGetData.getData();
			ArrayList<Music> musics = musicGetData.getData();
			for (int i = 0; i < 11; i++) {
				Object[] row = {musics.get(array[i]).getId(), musics.get(array[i]).getName(), artists.get(musics.get(array[i]).getArtist_id()-1).getName(), albums.get(musics.get(array[i]).getAlbum_id()-1).getName(), musics.get(array[i]).getDate(), musics.get(array[i]).getTime(), musics.get(array[i]).getDinlenme_sayisi(), musics.get(array[i]).getGenre()};
				model.addRow(row);
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
