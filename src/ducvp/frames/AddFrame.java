package ducvp.frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.management.MalformedObjectNameException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ducvp.main.App;
import model.SongDTO;
import utils.JMXUtils;

public class AddFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtArtist;
	private JTextField txtDuration;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFrame frame = new AddFrame();
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
	public AddFrame() {
		setTitle("Add New Song");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 412, 280);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Song:");
		lblNewLabel.setBounds(30, 41, 58, 23);
		contentPane.add(lblNewLabel);

		JLabel lblArtist = new JLabel("Artist:");
		lblArtist.setBounds(30, 75, 58, 23);
		contentPane.add(lblArtist);

		JLabel lblNewLabel_1_1 = new JLabel("Duration:");
		lblNewLabel_1_1.setBounds(30, 109, 58, 23);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1 = new JLabel("ADD NEW SONG");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 347, 14);
		contentPane.add(lblNewLabel_1);

		txtName = new JTextField();
		txtName.setToolTipText("");
		txtName.setBounds(96, 42, 234, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtArtist = new JTextField();
		txtArtist.setColumns(10);
		txtArtist.setBounds(96, 76, 234, 20);
		contentPane.add(txtArtist);

		txtDuration = new JTextField();
		txtDuration.setColumns(10);
		txtDuration.setBounds(96, 110, 234, 20);
		contentPane.add(txtDuration);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JMXUtils utils = new JMXUtils();
				try {
					App app = new App(utils.getProxy());
					SongDTO dto = new SongDTO();
					dto.setName(txtName.getText());
					dto.setArtist(txtArtist.getText());
					dto.setDuration(Integer.parseInt(txtDuration.getText()));
					app.addSong(dto);
				} catch (MalformedObjectNameException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnAdd.setBounds(85, 163, 220, 23);
		contentPane.add(btnAdd);
	}
}
