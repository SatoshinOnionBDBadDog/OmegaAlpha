package ch.supsi.omega.common;

import com.galliva.gallibrary.GConfigurationManager;

import ch.supsi.omega.gui.OMEGA;

public class JFrameEditMotions extends javax.swing.JFrame
{
	private static final long	serialVersionUID	= -8532339582776583337L;

	public JFrameEditMotions()
	{
		initComponents();
	}

	private void initComponents()
	{
		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jTextField4 = new javax.swing.JTextField();
		jLabel5 = new javax.swing.JLabel();
		jTextField5 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(220, 200));
		setResizable(false);
		getContentPane().setLayout(new java.awt.GridLayout(6, 2));

		jLabel1.setText("Motion type 1:");
		getContentPane().add(jLabel1);
		getContentPane().add(jTextField1);

		jLabel4.setText("Motion type 2:");
		getContentPane().add(jLabel4);
		getContentPane().add(jTextField2);

		jLabel2.setText("Motion type 3:");
		getContentPane().add(jLabel2);
		getContentPane().add(jTextField3);

		jLabel3.setText("Motion type 4:");
		getContentPane().add(jLabel3);
		getContentPane().add(jTextField4);

		jLabel5.setText("Motion type 5:");
		jLabel5.setToolTipText("");
		getContentPane().add(jLabel5);

		jTextField5.setEditable(false);
		getContentPane().add(jTextField5);

		jButton1.setText("Save");
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				saveMotions();
			}
		});
		getContentPane().add(jButton1);

		jButton2.setText("Cancel");
		jButton2.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				dispose();
			}
		});
		getContentPane().add(jButton2);

		pack();
	}

	private void saveMotions()
	{
		OMEGA.MOTIONTYPES[0] = jTextField1.getText();
		OMEGA.MOTIONTYPES[1] = jTextField2.getText();
		OMEGA.MOTIONTYPES[2] = jTextField3.getText();
		OMEGA.MOTIONTYPES[3] = jTextField4.getText();
		OMEGA.MOTIONTYPES[4] = jTextField5.getText();

		GConfigurationManager configurationManager = new GConfigurationManager();

		String motions = String.format("%s;%s;%s;%s;%s", OMEGA.MOTIONTYPES[0], OMEGA.MOTIONTYPES[1], OMEGA.MOTIONTYPES[2], OMEGA.MOTIONTYPES[3], OMEGA.MOTIONTYPES[4]);
		
		try
		{
			configurationManager.setIniFile(OmegaConstants.INIFILE);
			configurationManager.writeConfig("motions", motions);
		}
		catch(Exception e)
		{
			
		}

		dispose();
	}

	private javax.swing.JButton		jButton1;
	private javax.swing.JButton		jButton2;
	private javax.swing.JLabel			jLabel1;
	private javax.swing.JLabel			jLabel2;
	private javax.swing.JLabel			jLabel3;
	private javax.swing.JLabel			jLabel4;
	private javax.swing.JLabel			jLabel5;
	private javax.swing.JTextField	jTextField1;
	private javax.swing.JTextField	jTextField2;
	private javax.swing.JTextField	jTextField4;
	private javax.swing.JTextField	jTextField3;
	private javax.swing.JTextField	jTextField5;

	public javax.swing.JTextField getjTextField1()
	{
		return jTextField1;
	}

	public javax.swing.JTextField getjTextField2()
	{
		return jTextField2;
	}

	public javax.swing.JTextField getjTextField4()
	{
		return jTextField4;
	}

	public javax.swing.JTextField getjTextField3()
	{
		return jTextField3;
	}

	public javax.swing.JTextField getjTextField5()
	{
		return jTextField5;
	}
}
