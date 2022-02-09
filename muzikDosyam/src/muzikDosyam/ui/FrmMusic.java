package muzikDosyam.ui;

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
import muzikDosyam.getData.MusicCalmaListesiGetData;
import muzikDosyam.getData.MusicGetData;
import muzikDosyam.ui.FrmKullaniciSecme;
import muzikDosyam.ui.FrmLogin;
import muzikDosyam.ui.user.FrmUserAlbums;
import muzikDosyam.ui.user.FrmUserArtists;
import muzikDosyam.ui.user.FrmUserCalmaListeleri;
import muzikDosyam.ui.user.FrmUserKesfet;
import muzikDosyam.ui.user.FrmUserKitaplik;
import muzikDosyam.ui.user.FrmUserMusics;

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

public class FrmMusic extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private ArtistGetData artistGetData;
	private AlbumGetData albumGetData;
	private MusicGetData musicGetData;
	private MusicCalmaListesiGetData musicCalmaListesiGetData;
	private JTable tblMusic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMusic frame = new FrmMusic();
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
	public FrmMusic() {
		artistGetData = new ArtistGetData();
		albumGetData = new AlbumGetData();
		musicGetData = new MusicGetData();
		musicCalmaListesiGetData = new MusicCalmaListesiGetData();
		
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
				new FrmUserKesfet().setVisible(true);
				dispose();
			}
		});
		lblYtMusicBackground.setIcon(new ImageIcon(FrmUserMusics.class.getResource("/images/YTMusicBackground.jpg")));
		lblYtMusicBackground.setBounds(0, 1, 108, 53);
		contentPane.add(lblYtMusicBackground);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setFocusable(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(110, 185, 1650, 699);
		contentPane.add(scrollPane);
		
		tblMusic = new JTable();
		tblMusic.setRowMargin(0);
		tblMusic.setIntercellSpacing(new Dimension(0, 0));
		tblMusic.setFocusable(false);
		tblMusic.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "M\u00FCzik"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblMusic.setShowVerticalLines(false);
		tblMusic.setBorder(null);
		tblMusic.setForeground(Color.WHITE);
		tblMusic.setBackground(Color.BLACK);
		
		tblMusic.setForeground(Color.WHITE);
		tblMusic.setBackground(Color.BLACK);
		tblMusic.getTableHeader().setOpaque(false);
		tblMusic.getTableHeader().setBackground(Color.BLACK);
		tblMusic.getTableHeader().setForeground(Color.WHITE);
		tblMusic.getTableHeader().setPreferredSize(new Dimension(0, 40));
		tblMusic.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		tblMusic.setRowHeight(40);
		
		scrollPane.setViewportView(tblMusic);
		
		if (FrmUserArtists.frmUserArtists) {
			populateMusicTableArtist();
			FrmUserArtists.frmUserArtists = false;
		}
		
		if (FrmUserAlbums.frmUserAlbum) {
			populateMusicTableAlbum();
			FrmUserAlbums.frmUserAlbum = false;
		}
		
		if (FrmUserCalmaListeleri.frmUserCalmaListeleri) {
			populateMusicTableCalmaListeleri();
			FrmUserCalmaListeleri.frmUserCalmaListeleri = false;
		}
		
		if (FrmUserKitaplik.frmUserKitaplikCalmaListesi) {
			populateMusicTableCalmaListeleri();
			FrmUserKitaplik.frmUserKitaplikCalmaListesi = false;
		}
		
		if (FrmUserKitaplik.frmUserKitaplikArtist) {
			populateMusicTableArtist();
			FrmUserKitaplik.frmUserKitaplikArtist = false;
		}
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIconTextGap(0);
		lblBackground.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblBackground.setIcon(new ImageIcon(FrmUserMusics.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}

	public void populateMusicTableArtist() {
		model = (DefaultTableModel)tblMusic.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<Artist> artists = artistGetData.getData();
			ArrayList<Music> musics = musicGetData.getData();
			for (Music music : musics) {
				if (music.getArtist_id() == FrmUserArtists.id) {
					Object[] row = {music.getId(), music.getName()};
					model.addRow(row);
				}
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void populateMusicTableAlbum() {
		model = (DefaultTableModel)tblMusic.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<Album> albums = albumGetData.getData();
			ArrayList<Music> musics = musicGetData.getData();
			for (Music music : musics) {
				if (music.getAlbum_id() == FrmUserAlbums.frmUserAlbumId) {
					Object[] row = {music.getId(), music.getName()};
					model.addRow(row);
				}
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void populateMusicTableCalmaListeleri() {
		model = (DefaultTableModel)tblMusic.getModel();
		model.setRowCount(0);
		
		try {
			ArrayList<MusicCalmaListesi> musicCalmaListesis = musicCalmaListesiGetData.getData();
			ArrayList<Music> musics = musicGetData.getData();
			for (MusicCalmaListesi musicCalmaListesi : musicCalmaListesis) {
				if (musicCalmaListesi.getCalma_listesi_id() == FrmUserCalmaListeleri.frmUserCalmaListeleriId) {
					Object[] row = {musics.get(musicCalmaListesi.getMusic_id()-1).getId(), musics.get(musicCalmaListesi.getMusic_id()-1).getName()};
					model.addRow(row);
				}
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
