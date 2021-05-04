package ducvp.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.jms.JMSException;
import javax.management.MalformedObjectNameException;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ducvp.main.App;
import model.SongDTO;
import utils.JMXUtils;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	JMXUtils utils = new JMXUtils();
	App app = new App(utils.getProxy());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws MalformedObjectNameException
	 * @throws JMSException
	 * @throws NamingException 
	 */
	public MainFrame() throws MalformedObjectNameException, IOException, JMSException, NamingException {
		table = new JTable();
		updateTable();

		app.listenMessage(this);
		setTitle("MUSIC MANAGER");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 447, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(UIManager.getColor("Button.highlight"));
		contentPane.add(toolBar, BorderLayout.NORTH);

		// ADD BUTTON
		JButton btnAdd = new JButton();
		Image addIcon = getScaledImage(ImageIO.read(getClass().getClassLoader().getResource("resources/icons8-add-song-80.png")),
				30, 30);
		btnAdd.setIcon(new ImageIcon(addIcon));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFrame addFrame = new AddFrame();
				addFrame.setVisible(true);
			}
		});

		// DELETE BUTTON
		Image deleteIcon = getScaledImage(ImageIO.read(getClass().getClassLoader().getResource("resources/icons8-delete-80.png")),
				30, 30);
		JButton btnDelete = new JButton();
		btnDelete.setIcon(new ImageIcon(deleteIcon));
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
				app.deleteSong(id);
			}
		});

		btnAdd.setBackground(UIManager.getColor("Button.highlight"));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBackground(UIManager.getColor("Button.highlight"));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 25));
		toolBar.add(btnAdd);
		toolBar.add(btnDelete);
		JScrollPane jscrollPane = new JScrollPane(table);
		contentPane.add(jscrollPane, BorderLayout.CENTER);
	}

	public void updateTable() throws MalformedObjectNameException, IOException, JMSException {
		List<SongDTO> list = new ArrayList<SongDTO>();
		list = app.getSongs();
		table.setRowHeight(40);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Vector<String> headers = new Vector<String>();
		headers.add("ID");
		headers.add("Name");
		headers.add("Artist");
		headers.add("Duration");
		Vector<Vector<String>> rows = new Vector<Vector<String>>();
		for (int i = 0; i < list.size(); i++) {
			Vector<String> data = new Vector<String>();
			data.add(String.valueOf(list.get(i).getId()));
			data.add(String.valueOf(list.get(i).getName()));
			data.add(String.valueOf(list.get(i).getArtist()));
			data.add(String.valueOf(list.get(i).getDuration()));
			rows.add(data);
		}
		DefaultTableModel model = new DefaultTableModel(rows, headers) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1 || column == 2 || column == 3) {
					return true;
				} else
					return false;
			}

		};
		table.setModel(model);
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int id = Integer.valueOf((String) table.getValueAt(e.getFirstRow(), 0));
				String name = (String) table.getValueAt(e.getFirstRow(), 1);
				String artist = (String) table.getValueAt(e.getFirstRow(), 2);
				int duration = Integer.valueOf((String) table.getValueAt(e.getFirstRow(), 3));
				SongDTO dto = new SongDTO(id, name, artist, duration);
				try {
					app.updateSong(dto);
				} catch (MalformedObjectNameException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

}
