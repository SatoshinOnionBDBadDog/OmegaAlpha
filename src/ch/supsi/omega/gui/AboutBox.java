package ch.supsi.omega.gui;

import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import ch.supsi.omega.common.OmegaConstants;

public class AboutBox extends JDialog
{
	private static final long	serialVersionUID	= 7769472786298064518L;

	public AboutBox(java.awt.Frame parent)
	{
		super(parent);
		initComponents();
	}

	private void initComponents()
	{
		closeButton = new javax.swing.JButton();
		javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
		javax.swing.JLabel versionLabel = new javax.swing.JLabel();
		javax.swing.JLabel appVersionLabel = new javax.swing.JLabel();
		javax.swing.JLabel vendorLabel = new javax.swing.JLabel();
		javax.swing.JLabel appVendorLabel = new javax.swing.JLabel();
		javax.swing.JLabel homepageLabel = new javax.swing.JLabel();
		javax.swing.JLabel appHomepageLabel = new javax.swing.JLabel();
		javax.swing.JLabel appDescLabel = new javax.swing.JLabel();
		javax.swing.JLabel imageLabel = new javax.swing.JLabel();
		
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("About: " + OmegaConstants.OMEGA_TITLE);
		setModal(true);
		setResizable(false);
		getContentPane().setBackground(new Color(49, 51, 153));

		// title
		appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize() + 4));
		appTitleLabel.setForeground(Color.WHITE);
		appTitleLabel.setText(String.format("%s %s", OmegaConstants.OMEGA_TITLE, OmegaConstants.OMEGA_BUILD));
		
		// description
		appDescLabel.setFont(appTitleLabel.getFont().deriveFont(appDescLabel.getFont().getStyle() | java.awt.Font.PLAIN, appDescLabel.getFont().getSize() - 1));
		appDescLabel.setText(OmegaConstants.OMEGA_DESCRIPTION);
		appDescLabel.setForeground(Color.WHITE);
				
		// version
		versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
		versionLabel.setText("");
		versionLabel.setForeground(Color.WHITE);
		
		//appVersionLabel.setText(OmegaConstants.OMEGA_BUILD);
		//appVersionLabel.setForeground(Color.WHITE);
		
		// vendor
		vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | java.awt.Font.BOLD));
		vendorLabel.setText("<html>"+
				"Created by the OMEGA team: Caterina Strambio De Castillia, Jasmine<br>" +
				"Clark, Andrea Danani, Vanni Galli, Raffaello Giulietti, Loris Grossi, Eric<br>" +
				"Hunter, Tiziano Leidi, Jeremy Luban, Massimo Maiolo, Lara Pereira,<br>" +
				"Alex Rigano and Mario Valle.<br><br>Copyright \u00a9 2011 OMEGA team</html>");
		vendorLabel.setForeground(Color.WHITE);
		
		//appVendorLabel.setText(OmegaConstants.OMEGA_AUTHOR);
		//appVendorLabel.setForeground(Color.WHITE);

		// homepage
		homepageLabel.setFont(appTitleLabel.getFont().deriveFont(appDescLabel.getFont().getStyle() | java.awt.Font.PLAIN, appDescLabel.getFont().getSize() - 1));
		homepageLabel.setForeground(Color.WHITE);
		homepageLabel.setText("<html>OMEGA is licensed and distributed under the LGPL License version 3.<br><br><br>" +
				"The OMEGA team gratefully acknowledges Kaloyan Enimanev, Bernd Rinn,<br>" +
				"Peter Kunszt, Ela Hunt and the entire openBIS development team for sharing<br>" +
				"their expertise and providing invaluable help for integrating this software with<br>" +
				"the openBIS platform.<br><br>" +
				"For more information about openBIS refer to: Kozak, K; Bauch, A; Pylak, T;<br>" + 
				"Rinn, B (2010). Towards a comprehensive open source platform for<br>"+
				"management and analysis of High Content Screening data. European<br>" +
				"Pharmaceutical Review, 4:34-38.<br><br>" +
				"This project was funded in part by Systemsx.ch as part of the Systemsx.ch<br>" +
				"Biology IT (SyBIT) project, by the European Commission 7th Framework<br>" +
				"Programme for Scientific Research (Project number: HEALTH-2007-2.3.2, GA<br>" +
				"number: HEALTH-F3-2008-201,032 to C. S.-D.-C.) and by the Information<br>" +
				"Systems and Networking Institute (ISIN) of the Department of Innovative<br>" +
				"Technologies (DTI) of the University of Applied Science of Southern<br>" +
				"Switzerland (SUPSI).</html>");
	
		
		//appHomepageLabel.setText(OmegaConstants.OMEGA_WEBSITE);
		//appHomepageLabel.setForeground(Color.WHITE);
		
		// image
		imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ch/supsi/omega/gui/resources/OMEGA_logo_blue.jpg")));
		
		// close button
		closeButton.setText("Close");
		closeButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				dispose();
			}
		});
		
		getRootPane().setDefaultButton(closeButton);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(imageLabel)
						.addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												layout.createSequentialGroup()
														.addGroup(
																layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(versionLabel)
																		.addComponent(vendorLabel).addComponent(homepageLabel))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(
																layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(appVersionLabel)
																		.addComponent(appVendorLabel).addComponent(appHomepageLabel)))
										.addComponent(appTitleLabel, javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(appDescLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 266,
												Short.MAX_VALUE).addComponent(closeButton)).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(appTitleLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(appDescLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(versionLabel)
												.addComponent(appVersionLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(vendorLabel)
												.addComponent(appVendorLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(homepageLabel)
												.addComponent(appHomepageLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE).addComponent(closeButton)
								.addContainerGap()));
		pack();
	}

	private javax.swing.JButton	closeButton;
}
