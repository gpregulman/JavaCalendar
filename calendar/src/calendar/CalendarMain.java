package calendar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarMain {
	static JLabel monthLbl, yearLbl;
	static JButton prevBtn, nextBtn;
	static JTable calendarTbl;
	static JComboBox yearCmBx;
	static JFrame mainFrm;
	static Container pane;
	static DefaultTableModel calendarMTbl;
	static JScrollPane calendarScTbl;
	static JPanel calendarPnl;
	static int realDay, realMonth, realYear, currentMonth, currentYear;

	public static void main(String args[]){
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		mainFrm = new JFrame("Calendar App");
		monthLbl = new JLabel("January");
		yearLbl = new JLabel("Change year:");
		yearCmBx = new JComboBox();
		prevBtn = new JButton("<<");
		nextBtn = new JButton(">>");
		calendarMTbl = new DefaultTableModel();
		calendarTbl = new JTable(calendarMTbl);
		calendarScTbl = new JScrollPane(calendarTbl);
		calendarPnl = new JPanel(null);
		mainFrm.setSize(330, 375);
		pane = mainFrm.getContentPane();
		
	}
}
