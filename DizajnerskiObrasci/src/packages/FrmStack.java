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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmStack extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<Rectangle> dlm = new DefaultListModel<Rectangle>();
	JList listRectangle = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmStack frame = new FrmStack();
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
	public FrmStack() {
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

		JLabel lblAddDelete = new JLabel("Add or delete rectangle");

		JScrollPane scrollPane = new JScrollPane();

		JButton btnAddRectangle = new JButton("Add rectangle");
		btnAddRectangle.addActionListener(new ActionListener() {
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

		JButton btnDeleteRectangle = new JButton("Delete rectangle");
		btnDeleteRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listRectangle.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Not a single rectangle was selected from the list!");
				} else {
					DlgStack dlgStack = new DlgStack();
					String[] split = dlm.getElementAt(listRectangle.getSelectedIndex()).toString().split(" ");
					int index = listRectangle.getSelectedIndex();
					dlgStack.getTxtX().setText(split[5]);
					dlgStack.getTxtY().setText(split[7]);
					dlgStack.getTxtWidth().setText(split[12]);
					dlgStack.getTxtHeight().setText(split[16]);
					dlgStack.setVisible(true);
					if (dlgStack.isOk) {
						dlm.removeElementAt(index);
					}
				}

			}
		});
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCenter.createSequentialGroup().addGap(139).addComponent(lblAddDelete))
								.addGroup(gl_pnlCenter.createSequentialGroup().addGap(26).addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlCenter.createSequentialGroup().addGap(53).addComponent(btnAddRectangle)
										.addGap(63).addComponent(btnDeleteRectangle)))
						.addContainerGap(22, Short.MAX_VALUE)));
		gl_pnlCenter
				.setVerticalGroup(
						gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCenter.createSequentialGroup().addContainerGap()
										.addComponent(lblAddDelete).addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 111,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
										.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnAddRectangle).addComponent(btnDeleteRectangle))
										.addGap(31)));

		scrollPane.setViewportView(listRectangle);
		pnlCenter.setLayout(gl_pnlCenter);
		listRectangle.setModel(dlm);

	}
}
