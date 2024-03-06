package packages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Line;
import geometry.Point;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtStartPointX;
	private JTextField txtStartPointY;
	private JTextField txtEndPointX;
	private JTextField txtEndPointY;
	private JButton btnColor;
	public boolean isOk;
	private int startPointX;
	private int startPointY;
	private int endPointX;
	private int endPointY;
	private Line line;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setTitle("Line");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblStartPointX = new JLabel("Start point X:");
		
		JLabel lblStartPointY = new JLabel("Start point Y:");
		
		JLabel lblEndPointX = new JLabel("End point X:");
		
		JLabel lblEndPointY = new JLabel("End point Y:");
		
		txtStartPointX = new JTextField();
		txtStartPointX.setColumns(10);
		
		txtStartPointY = new JTextField();
		txtStartPointY.setColumns(10);
		
		txtEndPointX = new JTextField();
		txtEndPointX.setColumns(10);
		
		txtEndPointY = new JTextField();
		txtEndPointY.setColumns(10);
		
		JLabel lblColor = new JLabel("Color:");
		
		btnColor = new JButton("");
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Select color", btnColor.getBackground());
				btnColor.setBackground(color);
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(58)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblStartPointX)
							.addGap(18)
							.addComponent(txtStartPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStartPointY)
								.addComponent(lblEndPointX)
								.addComponent(lblEndPointY)
								.addComponent(lblColor))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(txtEndPointY)
									.addComponent(txtEndPointX)
									.addComponent(txtStartPointY)))))
					.addContainerGap(165, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtStartPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStartPointX))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblStartPointY)
						.addComponent(txtStartPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndPointX)
						.addComponent(txtEndPointX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndPointY)
						.addComponent(txtEndPointY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColor)
						.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
						if (txtStartPointX.getText().trim().isEmpty() || txtStartPointY.getText().trim().isEmpty()
								|| txtEndPointX.getText().trim().isEmpty() || txtEndPointY.getText().trim().isEmpty()) {
							isOk = false;
							setVisible(true);
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "All fields must be filled in!");
						} else {
							try {
								startPointX = Integer.parseInt(txtStartPointX.getText());
								startPointY = Integer.parseInt(txtStartPointY.getText());
								endPointX = Integer.parseInt(txtEndPointX.getText());
								endPointY = Integer.parseInt(txtEndPointY.getText());
								
								if (startPointX>0 && startPointY>0 && endPointX>0 && endPointY>0) {
									isOk = true;
									Point p1 = new Point(startPointX, startPointY);
									Point p2 = new Point(endPointX, endPointY);
									Color color = btnColor.getBackground();
									line = new Line(p1, p2, false, color);
									dispose();
								} else {
									JOptionPane.showMessageDialog(null, "X and Y coordinates must be positive values");
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

	public JTextField getTxtStartPointX() {
		return txtStartPointX;
	}

	public void setTxtStartPointX(JTextField txtStartPointX) {
		this.txtStartPointX = txtStartPointX;
	}

	public JTextField getTxtStartPointY() {
		return txtStartPointY;
	}

	public void setTxtStartPointY(JTextField txtStartPointY) {
		this.txtStartPointY = txtStartPointY;
	}

	public JTextField getTxtEndPointX() {
		return txtEndPointX;
	}

	public void setTxtEndPointX(JTextField txtEndPointX) {
		this.txtEndPointX = txtEndPointX;
	}

	public JTextField getTxtEndPointY() {
		return txtEndPointY;
	}

	public void setTxtEndPointY(JTextField txtEndPointY) {
		this.txtEndPointY = txtEndPointY;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

	public int getStartPointX() {
		return startPointX;
	}

	public void setStartPointX(int startPointX) {
		this.startPointX = startPointX;
	}

	public int getStartPointY() {
		return startPointY;
	}

	public void setStartPointY(int startPointY) {
		this.startPointY = startPointY;
	}

	public int getEndPointX() {
		return endPointX;
	}

	public void setEndPointX(int endPointX) {
		this.endPointX = endPointX;
	}

	public int getEndPointY() {
		return endPointY;
	}

	public void setEndPointY(int endPointY) {
		this.endPointY = endPointY;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
}
