package packages;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class FrmSort extends JFrame {

	private JPanel contentPane;
	public DefaultListModel<Rectangle> dlm = new DefaultListModel<Rectangle>();
	JList listRectangle = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmSort frame = new FrmSort();
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
	public FrmSort() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Sacic Jelena IT5/2018");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);

		JLabel lblSortRectangle = new JLabel("Sort rectangle");

		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnSort = new JButton("Sort");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rectangle[] rec = new Rectangle[dlm.size()];
				for (int i = 0; i < dlm.size(); i++){
					rec[i] = dlm.getElementAt(i);
				}

				dlm.removeAllElements();
				Arrays.sort(rec);
				for (int i = 0; i < rec.length; i++) {
					dlm.add(i, rec[i]);
				}
			}
		});

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgStack dlgStack = new DlgStack();
				dlgStack.setVisible(true);
				if (dlgStack.isOk) {
					Point upperLeftPoint = new Point(Integer.parseInt(dlgStack.getTxtX().getText().toString()), Integer.parseInt(dlgStack.getTxtY().getText().toString()));
					int width = Integer.parseInt(dlgStack.getTxtHeight().getText().toString());
					int height = Integer.parseInt(dlgStack.getTxtWidth().getText().toString());
					Rectangle r = new Rectangle(upperLeftPoint,width,height);
					dlm.add(0,r);
				}
			}
		});
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlCenter
				.createSequentialGroup()
				.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCenter.createSequentialGroup().addGap(172).addComponent(lblSortRectangle))
						.addGroup(gl_pnlCenter.createSequentialGroup().addGap(33).addComponent(scrollPane,
								GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(61, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_pnlCenter.createSequentialGroup().addContainerGap(100, Short.MAX_VALUE)
						.addComponent(btnAdd).addGap(31).addComponent(btnSort).addGap(149)));
		gl_pnlCenter.setVerticalGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlCenter
				.createSequentialGroup().addContainerGap().addComponent(lblSortRectangle).addGap(18)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE).addGap(27)
				.addGroup(
						gl_pnlCenter.createParallelGroup(Alignment.BASELINE).addComponent(btnSort).addComponent(btnAdd))
				.addContainerGap(35, Short.MAX_VALUE)));

		scrollPane.setViewportView(listRectangle);
		pnlCenter.setLayout(gl_pnlCenter);
		listRectangle.setModel(dlm);
	}
}
