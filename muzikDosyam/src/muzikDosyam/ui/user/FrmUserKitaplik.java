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
import muzikDosyam.db.User;
import muzikDosyam.db.UserArtist;
import muzikDosyam.getData.AlbumGetData;
import muzikDosyam.getData.ArtistGetData;
import muzikDosyam.getData.CalmaListesiGetData;
import muzikDosyam.getData.MusicGetData;
import muzikDosyam.getData.UserArtistGetData;
import muzikDosyam.getData.UserGetData;
import muzikDosyam.ui.FrmKullaniciSecme;
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
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmUserKitaplik extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private CalmaListesiGetData calmaListesiGetData;
	private UserArtistGetData userArtistGetData;
	private ArtistGetData artistGetData;
	private JTable tblCalmaListeleri;
	private JTable tblArtist;
	public static JTextField txtCalmaListesi;
	public static boolean frmUserKitaplikCalmaListesi;
	public static boolean frmUserKitaplikArtist;
	public static int frmUserKitaplikId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmUserKitaplik frame = new FrmUserKitaplik();
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
	public FrmUserKitaplik() {
		calmaListesiGetData = new CalmaListesiGetData();
		userArtistGetData = new UserArtistGetData();
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
		
		JLabel lblKitaplik = new JLabel("Kitapl\u0131k");
		lblKitaplik.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblKitaplik);
			}
		});
		lblKitaplik.setForeground(Color.WHITE);
		lblKitaplik.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblKitaplik.setBounds(1153, 10, 99, 43);
		contentPane.add(lblKitaplik);
		
		JLabel lblCalmaListeleri = new JLabel("\u00C7alma Listeleri");
		lblCalmaListeleri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(lblCalmaListeleri);
				lblCalmaListeleri.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				new FrmUserCalmaListeleri().setVisible(true);
				dispose();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCalmaListeleri.setForeground(Color.decode("#696969"));
			}
		});
		lblCalmaListeleri.setForeground(Color.decode("#696969"));
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
		
		JScrollPane scrollPaneCalmaListesi = new JScrollPane();
		scrollPaneCalmaListesi.setBackground(Color.BLACK);
		scrollPaneCalmaListesi.setOpaque(false);
		scrollPaneCalmaListesi.setFocusable(false);
		scrollPaneCalmaListesi.setBorder(null);
		scrollPaneCalmaListesi.setBounds(110, 100, 1650, 400);
		contentPane.add(scrollPaneCalmaListesi);
		
		tblCalmaListeleri = new JTable();
		tblCalmaListeleri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblCalmaListeleri.getSelectedRow();
				frmUserKitaplikId = (int) (tblCalmaListeleri.getModel().getValueAt(row, 0));
				frmUserKitaplikCalmaListesi = true;
				
				new FrmMusic().setVisible(true);
				dispose();
			}
		});
		tblCalmaListeleri.setShowVerticalLines(false);
		tblCalmaListeleri.setRowMargin(0);
		tblCalmaListeleri.setRowHeight(40);
		tblCalmaListeleri.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "\u00C7alma Listesi"
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
		
		tblCalmaListeleri.setShowVerticalLines(false);
		tblCalmaListeleri.setBorder(null);
		tblCalmaListeleri.setForeground(Color.WHITE);
		tblCalmaListeleri.setBackground(Color.BLACK);
		
		tblCalmaListeleri.setForeground(Color.WHITE);
		tblCalmaListeleri.setBackground(Color.BLACK);
		tblCalmaListeleri.getTableHeader().setOpaque(false);
		tblCalmaListeleri.getTableHeader().setBackground(Color.BLACK);
		tblCalmaListeleri.getTableHeader().setForeground(Color.WHITE);
		tblCalmaListeleri.getTableHeader().setPreferredSize(new Dimension(0, 40));
		tblCalmaListeleri.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		tblCalmaListeleri.setRowHeight(40);
		UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
		
		scrollPaneCalmaListesi.setViewportView(tblCalmaListeleri);
		
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
		
		JScrollPane scrollPaneArtist = new JScrollPane();
		scrollPaneArtist.setBounds(110, 548, 1650, 400);
		contentPane.add(scrollPaneArtist);
		
		tblArtist = new JTable();
		tblArtist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblArtist.getSelectedRow();
				frmUserKitaplikId = (int) (tblArtist.getModel().getValueAt(row, 0));
				
				frmUserKitaplikArtist = true;
				
				new FrmMusic().setVisible(true);
				dispose();
			}
		});
		tblArtist.setShowVerticalLines(false);
		tblArtist.setRowMargin(0);
		tblArtist.setRowHeight(40);
		tblArtist.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Sanat\u00E7\u0131"
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
		tblArtist.setShowVerticalLines(false);
		tblArtist.setBorder(null);
		tblArtist.setForeground(Color.WHITE);
		tblArtist.setBackground(Color.BLACK);
		
		tblArtist.setForeground(Color.WHITE);
		tblArtist.setBackground(Color.BLACK);
		tblArtist.getTableHeader().setOpaque(false);
		tblArtist.getTableHeader().setBackground(Color.BLACK);
		tblArtist.getTableHeader().setForeground(Color.WHITE);
		tblArtist.getTableHeader().setPreferredSize(new Dimension(0, 40));
		tblArtist.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		tblArtist.setRowHeight(40);
		
		scrollPaneArtist.setViewportView(tblArtist);
		
		populateTable2();
		
		txtCalmaListesi = new JTextField();
		txtCalmaListesi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCalmaListesi.setForeground(Color.WHITE);
		txtCalmaListesi.setBackground(Color.BLACK);
		txtCalmaListesi.setBounds(1770, 153, 144, 61);
		contentPane.add(txtCalmaListesi);
		txtCalmaListesi.setColumns(10);
		
		JButton btnAdd = new JButton("EKLE");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Utils.setHandCursor(btnAdd);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				try {
					ArrayList<CalmaListesi> calmaListesis = calmaListesiGetData.getData();
					connection = dbHelper.getConnection();
					String sql = "insert into calma_listesi (calma_listesi_name, user_id) values (?, ?)";
					statement = connection.prepareStatement(sql);
					statement.setString(1, txtCalmaListesi.getText());
					statement.setInt(2, FrmLogin.userId);
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
					catch (SQLException e1) {
						System.out.println(e1);
					}
				}
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.DARK_GRAY);
		btnAdd.setBounds(1770, 285, 144, 61);
		btnAdd.setBorderPainted(false);
		contentPane.add(btnAdd);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIconTextGap(0);
		lblBackground.setBackground(UIManager.getColor("Button.disabledShadow"));
		lblBackground.setIcon(new ImageIcon(FrmUserMusics.class.getResource("/images/background.jpg")));
		lblBackground.setBounds(0, 0, 1924, 1010);
		contentPane.add(lblBackground);
	}

	public void populateTable() {
		model = (DefaultTableModel)tblCalmaListeleri.getModel();
		model.setRowCount(0);
		
		try {  
			ArrayList<CalmaListesi> calmaListesis = calmaListesiGetData.getData2();
			for (CalmaListesi calmaListesi: calmaListesis) {
					Object[] row = {calmaListesi.getId(), calmaListesi.getName()};
					model.addRow(row);
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void populateTable2() {
		model = (DefaultTableModel)tblArtist.getModel();
		model.setRowCount(0);
		
		try {  
			ArrayList<UserArtist> userArtists = userArtistGetData.getData();
			ArrayList<Artist> artists = artistGetData.getData();
			for (UserArtist userArtist: userArtists) {
					Object[] row = {artists.get(userArtist.getArtist_id()-1).getId(), artists.get(userArtist.getArtist_id()-1).getName()};
					model.addRow(row);
			} 
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
