package packages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import adapter.HexagonAdapter;
import geometry.Point;

public class DlgHexagon extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCenterPointX;
	private JTextField txtCenterPointY;
	private JTextField txtRadius;
	private JButton btnInnerColor;
	private JButton btnOuterColor;
	public boolean isOk;
	private int centerPointX;
	private int centerPointY;
	private int radius;
	private HexagonAdapter hexagonAdapt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgHexagon dialog = new DlgHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgHexagon() {
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setTitle("Hexagon");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.EAST);
		JLabel lblCenterPoint = new JLabel("Center point X:");
		JLabel lblCenterPoint_1 = new JLabel("Center point Y:");
		JLabel lblRadius = new JLabel("Radius:");
		JLabel lblInnerColor = new JLabel("Inner color:");
		JLabel lblOuterColor = new JLabel("Outer color:");
		txtCenterPointX = new JTextField();
		txtCenterPointX.setColumns(10);
		txtCenterPointY = new JTextField();
		txtCenterPointY.setColumns(10);
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		btnInnerColor = new JButton("");
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Select color", btnInnerColor.getBackground());
				btnInnerColor.setBackground(color);
			}
		});
		btnOuterColor = new JButton("");
		btnOuterColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Select color", btnOuterColor.getBackground());
				btnOuterColor.setBackground(color);
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(51)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblOuterColor)
							.addGap(55)
							.addComponent(btnOuterColor, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblInnerColor)
							.addGap(57)
							.addComponent(btnInnerColor, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCenterPoint)
								.addComponent(lblCenterPoint_1)
								.addComponent(lblRadius))
							.addGap(37)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtRadius)
								.addComponent(txtCenterPointY)
								.addComponent(txtCenterPointX))))
					.addGap(113))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterPoint)
						.addComponent(txtCenterPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenterPoint_1)
						.addComponent(txtCenterPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(lblInnerColor))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(lblOuterColor))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOuterColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtCenterPointX.getText().trim().isEmpty() || txtCenterPointY.getText().trim().isEmpty()
								|| txtRadius.getText().trim().isEmpty()) {
							isOk = false;
							setVisible(true);
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "All fields must be filled in!");
						} else {
							try {
								centerPointX = Integer.parseInt(txtCenterPointX.getText());
								centerPointY = Integer.parseInt(txtCenterPointY.getText());
								radius = Integer.parseInt(txtRadius.getText());
								
								if (centerPointX>0 && centerPointY>0 && radius>0) {
									isOk = true;
									Point p = new Point(centerPointX, centerPointY);
									Color color = btnOuterColor.getBackground();
									Color innerColor = btnInnerColor.getBackground();
									hexagonAdapt = new HexagonAdapter(centerPointX, centerPointY, radius, false, color, innerColor);
									dispose();
								} else {
									JOptionPane.showMessageDialog(null, "X, Y coordinates and radius must be positive values");
								}
							} catch (NumberFormatException nfe) {
								JOptionPane.showMessageDialog(null, "All values in the fields must be numbers!");
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getTxtCenterPointX() {
		return txtCenterPointX;
	}

	public void setTxtCenterPointX(JTextField txtCenterPointX) {
		this.txtCenterPointX = txtCenterPointX;
	}

	public JTextField getTxtCenterPointY() {
		return txtCenterPointY;
	}

	public void setTxtCenterPointY(JTextField txtCenterPointY) {
		this.txtCenterPointY = txtCenterPointY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}

	public void setBtnOuterColor(JButton btnOuterColor) {
		this.btnOuterColor = btnOuterColor;
	}

	public int getCenterPointX() {
		return centerPointX;
	}

	public void setCenterPointX(int centerPointX) {
		this.centerPointX = centerPointX;
	}

	public int getCenterPointY() {
		return centerPointY;
	}

	public void setCenterPointY(int centerPointY) {
		this.centerPointY = centerPointY;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public HexagonAdapter getHexagonAdapt() {
		return hexagonAdapt;
	}

	public void setHexagonAdapt(HexagonAdapter hexagonAdapt) {
		this.hexagonAdapt = hexagonAdapt;
	}
	
}
