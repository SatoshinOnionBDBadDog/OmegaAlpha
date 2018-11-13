package ch.supsi.omega.gui.common;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SPTParameterPanels extends JPanel
{
	private static final long	serialVersionUID	= -3491829766316508354L;

	private JLabel					l1						= new JLabel();
	private JLabel					l2						= new JLabel();
	private JLabel					l3						= new JLabel();
	private JLabel					l4						= new JLabel();
	private JLabel					l5						= new JLabel();

	private JTextField			f1						= new JTextField();
	private JTextField			f2						= new ParameterField();
	private JTextField			f3						= new ParameterField();

	
	public class ParameterField extends JTextField
	{
		private static final long	serialVersionUID	= 1904466405657666355L;

		public ParameterField()
		{
			setFont(new Font("Dialog", Font.PLAIN, 10));
			setHorizontalAlignment(JTextField.CENTER);
		}
	}
	
	public SPTParameterPanels(String label1, String label2, String label3, String label4, String info)
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));

		l1.setText(label1);
		l1.setPreferredSize(new Dimension(110, 25));
		add(l1);

		f1.setEnabled(false);
		f1.setHorizontalAlignment(JTextField.CENTER);
		f1.setPreferredSize(new Dimension(40, 25));
		add(f1);

		l2.setText(label2);
		l2.setPreferredSize(new Dimension(47, 25));
		add(l2);

		f2.setEnabled(false);
		f2.setHorizontalAlignment(JTextField.CENTER);
		f2.setPreferredSize(new java.awt.Dimension(56, 25));
		add(f2);

		l3.setText(label3);
		l3.setPreferredSize(new Dimension(20, 25));
		add(l3);

		l4.setText(label4);
		l4.setPreferredSize(new Dimension(96, 25));
		add(l4);

		f3.setEnabled(false);
		f3.setHorizontalAlignment(JTextField.CENTER);
		f3.setPreferredSize(new Dimension(56, 25));
		add(f3);
		
		l5.setText("]");
		l5.setPreferredSize(new Dimension(5, 25));
		add(l5);

		add(new InfoLabel(this, info));
	}
	
	public JTextField getF1()
	{
		return f1;
	}

	public JTextField getF2()
	{
		return f2;
	}

	public JTextField getF3()
	{
		return f3;
	}
}
