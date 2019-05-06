package logic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.SwingConstants;
import java.awt.Color;

public class PrintTicket {

	private JFrame frame;
	private String name="",route="",date="",time="",seat="";

	public JPanel panel=new JPanel();
	

	public PrintTicket( String name, String route, String date, String time,String seat) {
		super();
		this.name = name;
		this.route = route;
		this.date = date;
		this.time = time;
		this.seat=seat;
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*PrintTicket window = new PrintTicket();
					window.frame.setVisible(true);*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100,710, 915);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 695, 900);
		frame.getContentPane().add(panel);
		
		
		JLabel passengerName = new JLabel("  "+name);
		passengerName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passengerName.setBounds(132, 92, 226, 30);
		panel.add(passengerName);
		
		JLabel passengerDate = new JLabel("  "+date);
		passengerDate.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passengerDate.setBounds(132, 159, 226, 30);
		panel.add(passengerDate);
		
		JLabel passengerTime = new JLabel("  "+time);
		passengerTime.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passengerTime.setBounds(132, 227, 226, 30);
		panel.add(passengerTime);
		
		JLabel passengerRoute = new JLabel("  "+route);
		passengerRoute.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passengerRoute.setBounds(132, 295, 226, 30);
		panel.add(passengerRoute);
		
		JLabel companyName = new JLabel("  "+name);
		companyName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		companyName.setBounds(132, 555, 226, 30);
		panel.add(companyName);
		
		JLabel companyDate = new JLabel("  "+date);
		companyDate.setFont(new Font("Tahoma", Font.PLAIN, 25));
		companyDate.setBounds(132, 623, 226, 30);
		panel.add(companyDate);
		
		JLabel companyTime = new JLabel("  "+time);
		companyTime.setFont(new Font("Tahoma", Font.PLAIN, 25));
		companyTime.setBounds(132, 691, 226, 30);
		panel.add(companyTime);
		
		JLabel companyRoute = new JLabel("  "+route);
		companyRoute.setFont(new Font("Tahoma", Font.PLAIN, 25));
		companyRoute.setBounds(132, 759, 226, 30);
		panel.add(companyRoute);
		
		JLabel passengerSeat = new JLabel(""+seat);
		passengerSeat.setForeground(Color.BLUE);
		passengerSeat.setHorizontalAlignment(SwingConstants.CENTER);
		passengerSeat.setFont(new Font("Times New Roman", Font.BOLD, 99));
		passengerSeat.setBounds(444, 184, 177, 111);
		panel.add(passengerSeat);
		
		JLabel companySeat = new JLabel(""+seat);
		companySeat.setBounds(444, 649, 177, 111);
		companySeat.setForeground(Color.BLUE);
		companySeat.setHorizontalAlignment(SwingConstants.CENTER);
		companySeat.setFont(new Font("Times New Roman", Font.BOLD, 99));
		panel.add(companySeat);
		
		JLabel label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setIcon(new ImageIcon(PrintTicket.class.getResource("/images/ticketPrint.jpg")));
		label.setBounds(0, 0, 695, 900);
		panel.add(label);

		
	}

	// print job
	public boolean printWork(JPanel panel) {
		boolean hasPrinted = false;
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setJobName("Ticket of "+name);
		printerJob.setPrintable(new Printable() {

			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex > 0) {
					return Printable.NO_SUCH_PAGE;
				}
				Paper paper = pageFormat.getPaper();
				//paper.setSize(8.5 * 72, 11 * 72);
				Graphics2D graphics2d = (Graphics2D) graphics;
				//pageFormat.setOrientation(PageFormat.LANDSCAPE);
				graphics2d.translate(pageFormat.getImageableX() , pageFormat.getImageableY());
				graphics2d.scale(.9, .9);
				panel.paint(graphics2d);
				panel.repaint();
				return Printable.PAGE_EXISTS;
			}
		});
		boolean returningResult = printerJob.printDialog();
		if (returningResult) {
			try {
				printerJob.print();
				hasPrinted = true;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Warning", "Print Error: " + e.getMessage(), 0);
				hasPrinted = false;
			}
		}
		return hasPrinted;

	}
}
