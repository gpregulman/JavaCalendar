package calendar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarMain {
	static JLabel monthLbl, yearLbl;
	static JButton prevBtn, nextBtn;
	static JTable calendarTbl;
	static JComboBox<String> yearCmBx;
	static JFrame mainFrm;
	static Container pane;
	static DefaultTableModel calendarMTbl;
	static JScrollPane calendarScTbl;
	static JPanel calendarPnl;
	static int realDay, realMonth, realYear, currentMonth, currentYear;
	
	static class calendarTblRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
				boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if(column == 0 || column == 6){
				setBackground(new Color(255, 220, 220));
			}
			else{
				setBackground(new Color(255, 255, 255));
			}
			if(value != null){
				if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth &&
						currentYear == realYear){
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}
	
	public static void refreshCalendar(int month, int year){
		String[] months = {"January", "February", "March", "April", "May", "June", "July", 
				"August", "September", "October", "November", "December"};
		int nod, som;
		prevBtn.setEnabled(true);
		nextBtn.setEnabled(true);
		if(month == 0 && year <= realYear - 100){
			prevBtn.setEnabled(false);
		}
		if(month == 11 && year >= realYear + 100){
			nextBtn.setEnabled(false);
		}
		monthLbl.setText(months[month]);
		monthLbl.setBounds(160 - monthLbl.getPreferredSize().width / 2, 25, 180, 25);
		yearCmBx.setSelectedItem(String.valueOf(year));
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				calendarMTbl.setValueAt(null, i, j);
			}
		}
		
		for(int i = 1; i <= nod; i++){
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			calendarMTbl.setValueAt(i, row, column);
		}
		
		calendarTbl.setDefaultRenderer(calendarTbl.getColumnClass(0), new calendarTblRenderer());
	}
	
	static class prevBtn_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currentMonth == 0){
				currentMonth = 11;
				currentYear -= 1;
			}
			else{
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
		
	}
	
	static class nextBtn_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currentMonth == 11){
				currentMonth = 0;
				currentYear += 1;
			}
			else{
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	
	static class yearCmBx_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(yearCmBx.getSelectedItem() != null){
				String b = yearCmBx.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}

	public static void main(String args[]){
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		
		mainFrm = new JFrame("Calendar App");
		mainFrm.setSize(330, 375);
		pane = mainFrm.getContentPane();
		pane.setLayout(null);
		mainFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		monthLbl = new JLabel("January");
		yearLbl = new JLabel("Change year:");
		yearCmBx = new JComboBox();
		prevBtn = new JButton("<<");
		nextBtn = new JButton(">>");
		calendarMTbl = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex, int mColIndex){
				return false;
				}
			};
		calendarTbl = new JTable(calendarMTbl);
		calendarScTbl = new JScrollPane(calendarTbl);
		calendarPnl = new JPanel(null);
		
		calendarPnl.setBorder(BorderFactory.createTitledBorder("Calendar"));
		pane.add(calendarPnl);
		calendarPnl.add(monthLbl);
		calendarPnl.add(yearLbl);
		calendarPnl.add(yearCmBx);
		calendarPnl.add(prevBtn);
		calendarPnl.add(nextBtn);
		calendarPnl.add(calendarScTbl);
		calendarPnl.setBounds(0, 0, 325, 335);
		monthLbl.setBounds(160 - monthLbl.getPreferredSize().width / 2, 25, 100, 25);
		yearLbl.setBounds(10, 305, 80, 20);
		yearCmBx.setBounds(230, 305, 80, 20);
		prevBtn.setBounds(10, 25, 50, 25);
		nextBtn.setBounds(260, 25, 50, 25);
		calendarScTbl.setBounds(10, 50, 300, 250);
		
		GregorianCalendar cal = new GregorianCalendar();
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
		realMonth = cal.get(GregorianCalendar.MONTH);
		realYear = cal.get(GregorianCalendar.YEAR);
		currentMonth = realMonth;
		currentYear = realYear;
		
		for(int i = realYear - 100; i <= realYear + 100; i++){
			yearCmBx.addItem(String.valueOf(i));
		}
		String[] headers = {"Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};
		for(int i = 0; i < 7; i++){
			calendarMTbl.addColumn(headers[i]);
		}
		
		calendarTbl.getParent().setBackground(calendarTbl.getBackground());
		calendarTbl.getTableHeader().setResizingAllowed(false);
		calendarTbl.getTableHeader().setReorderingAllowed(false);
		calendarTbl.setColumnSelectionAllowed(true);
		calendarTbl.setRowSelectionAllowed(true);
		calendarTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		calendarTbl.setRowHeight(38);
		calendarMTbl.setColumnCount(7);
		calendarMTbl.setRowCount(6);
		
		prevBtn.addActionListener(new prevBtn_Action());
		nextBtn.addActionListener(new nextBtn_Action());
		yearCmBx.addActionListener(new yearCmBx_Action());
		
		refreshCalendar(realMonth, realYear);
		
		mainFrm.setResizable(false);
		mainFrm.setVisible(true);
	}
}
