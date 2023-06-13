package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calendars implements Serializable{
	private List<Calendar>Calendars = new ArrayList<Calendar>();

		// コンストラクタの追加
		public Calendars(Calendar Calendar) {
			this.Calendars.add(Calendar);
		}

		// get, setメソッドの追加
		public List<Calendar> getCalendars() {
			return Calendars;
		}

		public void setCalendars(List<Calendar> calendar) {
			Calendars = calendar;
		}
}
