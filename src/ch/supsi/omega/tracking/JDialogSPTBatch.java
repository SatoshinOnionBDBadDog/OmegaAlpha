package ch.supsi.omega.tracking;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ch.supsi.omega.common.OmegaConstants;

public class JDialogSPTBatch extends JDialog {
	private static final long serialVersionUID = 2629439122389018289L;

	private boolean run = false;
	private int C = 0;
	private int Z = 0;

	public int getC() {
		return C;
	}

	public int getZ() {
		return Z;
	}

	public boolean isRun() {
		return run;
	}

	public JDialogSPTBatch() {
		initComponents();
	}

	private void initComponents() {
		jPanel3 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jTextFieldC = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		jTextFieldZ = new javax.swing.JTextField();
		jPanel4 = new javax.swing.JPanel();
		jLabel5 = new javax.swing.JLabel();
		jButtonRun = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(200, 100));
		setTitle(OmegaConstants.OMEGA_TITLE);
		getContentPane().setLayout(new java.awt.GridLayout(4, 1, 0, 5));

		jPanel3.setPreferredSize(new java.awt.Dimension(325, 25));
		jPanel3.setLayout(new java.awt.BorderLayout());

		jLabel3.setText(" Select the parameters for the SPT batch processing:");
		jLabel3.setPreferredSize(new java.awt.Dimension(200, 25));
		jPanel3.add(jLabel3, java.awt.BorderLayout.CENTER);
		jLabel3.getAccessibleContext().setAccessibleName("");

		getContentPane().add(jPanel3);

		jPanel1.setPreferredSize(new java.awt.Dimension(325, 25));
		jPanel1.setLayout(null);

		jLabel1.setText(" Image channel (C):");
		jLabel1.setPreferredSize(new java.awt.Dimension(130, 25));
		jPanel1.add(jLabel1);
		jLabel1.setBounds(0, 0, 130, 25);

		jTextFieldC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldC.setText("0");
		jTextFieldC.setPreferredSize(new java.awt.Dimension(50, 25));
		jPanel1.add(jTextFieldC);
		jTextFieldC.setBounds(130, 0, 50, 25);

		getContentPane().add(jPanel1);

		jPanel2.setPreferredSize(new java.awt.Dimension(325, 25));
		jPanel2.setLayout(null);

		jLabel2.setText(" Image plane (Z):");
		jLabel2.setPreferredSize(new java.awt.Dimension(130, 25));
		jPanel2.add(jLabel2);
		jLabel2.setBounds(0, 0, 130, 25);

		jTextFieldZ.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldZ.setText("0");
		jTextFieldZ.setPreferredSize(new java.awt.Dimension(50, 25));
		jPanel2.add(jTextFieldZ);
		jTextFieldZ.setBounds(130, 0, 50, 25);

		getContentPane().add(jPanel2);

		jPanel4.setPreferredSize(new java.awt.Dimension(325, 25));
		jPanel4.setLayout(new java.awt.GridLayout(1, 4, 10, 0));
		jPanel4.add(jLabel5);

		jButtonRun.setText("OK");
		jButtonRun.setFocusable(false);
		jButtonRun.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonRunActionPerformed(evt);
			}
		});
		jPanel4.add(jButtonRun);

		jButtonCancel.setText("Cancel");
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCancelActionPerformed(evt);
			}
		});

		jPanel4.add(jButtonCancel);
		jPanel4.add(jLabel4);

		getContentPane().add(jPanel4);

		pack();
	}

	private void jButtonCancelActionPerformed(ActionEvent evt) {
		run = false;
		this.dispose();
	}

	private void jButtonRunActionPerformed(ActionEvent evt) {
		String temp = "";
		try {
			temp = jTextFieldC.getText();
			C = Integer.valueOf(temp);
			temp = jTextFieldZ.getText();
			Z = Integer.valueOf(temp);
			run = true;
			this.dispose();
		} catch (Exception e) {
			JOptionPane
			        .showMessageDialog(null,
			                OmegaConstants.ERROR_C_Z_MUST_BE_NUMBERS,
			                OmegaConstants.OMEGA_TITLE,
			                JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private javax.swing.JButton jButtonRun;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JTextField jTextFieldC;
	private javax.swing.JTextField jTextFieldZ;
}
