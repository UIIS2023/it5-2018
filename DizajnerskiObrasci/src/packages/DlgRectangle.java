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

import geometry.Point;
import geometry.Rectangle;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUpperLeftPointX;
	private JTextField txtUpperLeftPointY;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JButton btnInnerColor;
	private JButton btnOuterColor;
	public boolean isOk;
	private int upperLeftPointX;
	private int upperLeftPointY;
	private int width;
	private int height;
	private Rectangle rect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setTitle("Rectangle");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.EAST);
		JLabel lblUpperLeftPoint = new JLabel("Upper left point X:");
		JLabel lblUpperLeftPoint_1 = new JLabel("Upper left point Y:");
		JLabel lblWidth = new JLabel("Width:");
		JLabel lblHeight = new JLabel("Height:");
		JLabel lblInnerColor = new JLabel("Inner color:");
		JLabel lblOuterColor = new JLabel("Outer color:");
		txtUpperLeftPointX = new JTextField();
		txtUpperLeftPointX.setColumns(10);
		txtUpperLeftPointY = new JTextField();
		txtUpperLeftPointY.setColumns(10);
		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		txtHeight = new JTextField();
		txtHeight.setColumns(10);
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
						.addComponent(lblUpperLeftPoint)
						.addComponent(lblUpperLeftPoint_1)
						.addComponent(lblWidth)
						.addComponent(lblHeight)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblInnerColor)
							.addComponent(lblOuterColor)))
					.addGap(37)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOuterColor, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
						.addComponent(btnInnerColor, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(txtWidth)
							.addComponent(txtUpperLeftPointY)
							.addComponent(txtUpperLeftPointX)
							.addComponent(txtHeight)))
					.addGap(113))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUpperLeftPoint)
						.addComponent(txtUpperLeftPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUpperLeftPoint_1)
						.addComponent(txtUpperLeftPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWidth)
						.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHeight)
						.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblInnerColor)
						.addComponent(btnInnerColor, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblOuterColor)
						.addComponent(btnOuterColor, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
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
						if (txtUpperLeftPointX.getText().trim().isEmpty() || txtUpperLeftPointY.getText().trim().isEmpty()
								|| txtWidth.getText().trim().isEmpty() || txtHeight.getText().trim().isEmpty()) {
							isOk = false;
							setVisible(true);
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "All fields must be filled in!");
						} else {
							try {
								upperLeftPointX = Integer.parseInt(txtUpperLeftPointX.getText());
								upperLeftPointY = Integer.parseInt(txtUpperLeftPointY.getText());
								width = Integer.parseInt(txtWidth.getText());
								height = Integer.parseInt(txtHeight.getText());
								
								if (upperLeftPointX>0 && upperLeftPointY>0 && width>0 && height>0) {
									isOk = true;
									Point p = new Point(upperLeftPointX, upperLeftPointY);
									Color color = btnOuterColor.getBackground();
									Color innerColor = btnInnerColor.getBackground();
									rect = new Rectangle(p, height, width, false, color, innerColor);
									dispose();
								} else {
									JOptionPane.showMessageDialog(null, "X, Y coordinates, width and height must be positive values");
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

	public JTextField getTxtUpperLeftPointX() {
		return txtUpperLeftPointX;
	}

	public void setTxtUpperLeftPointX(JTextField txtUpperLeftPointX) {
		this.txtUpperLeftPointX = txtUpperLeftPointX;
	}

	public JTextField getTxtUpperLeftPointY() {
		return txtUpperLeftPointY;
	}

	public void setTxtUpperLeftPointY(JTextField txtUpperLeftPointY) {
		this.txtUpperLeftPointY = txtUpperLeftPointY;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
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

	public int getUpperLeftPointX() {
		return upperLeftPointX;
	}

	public void setUpperLeftPointX(int upperLeftPointX) {
		this.upperLeftPointX = upperLeftPointX;
	}

	public int getUpperLeftPointY() {
		return upperLeftPointY;
	}

	public void setUpperLeftPointY(int upperLeftPointY) {
		this.upperLeftPointY = upperLeftPointY;
	}

	public int getWidthR() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeightR() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
}
