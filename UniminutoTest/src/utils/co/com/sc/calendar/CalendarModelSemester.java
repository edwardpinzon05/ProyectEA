package co.com.sc.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ApplicationScoped;

import org.jboss.seam.annotations.Name;
import org.richfaces.model.CalendarDataModelItem;

@Name("calendarModelSemester")
@ApplicationScoped
public class CalendarModelSemester extends CalendarModelBase
{
	private static final int OFFSET_ENABLED_DAY = 2;
	
	public CalendarDataModelItem[] getData(Date[] dateArray)
	{
		CalendarDataModelItem[] modelItems = new CalendarModelItem[dateArray.length];
		Calendar current = GregorianCalendar.getInstance();
		Calendar currentOffset = GregorianCalendar.getInstance();
		
		Calendar today = GregorianCalendar.getInstance();
        today.setTime(new Date());
        today.add(Calendar.DAY_OF_MONTH, 0);
		
        Calendar todayReference = GregorianCalendar.getInstance();
        todayReference.setTime(new Date());
        
		for (int i = 0; i < dateArray.length; i++)
		{
			current.setTime(dateArray[i]);
			currentOffset.setTime(dateArray[i]);
			
			CalendarModelItem modelItem = new CalendarModelItem();
			
			if (today.after(current))
			{
				if (!checkLastDayOfMonth(current) || (current.get(Calendar.MONTH) + 1 )%6 != 0)
				{
					modelItem.setEnabled(false);
					
					if (!checkWeekend(current))
					{	
						modelItem.setStyleClass(BUSY_DAY_CLASS);
					}
					else
					{
						modelItem.setStyleClass(WEEKEND_DAY_CLASS);
					}
				}
				else
				{
					currentOffset.add(Calendar.DAY_OF_MONTH, OFFSET_ENABLED_DAY);
					
					if (currentOffset.before(todayReference))
					{
						modelItem.setEnabled(true);
						modelItem.setStyleClass(ENABLED_DAY_CLASS);
					}
					else
					{
						modelItem.setEnabled(false);
						
						if (!checkWeekend(current))
						{	
							modelItem.setStyleClass(BUSY_DAY_CLASS);
						}
						else
						{
							modelItem.setStyleClass(WEEKEND_DAY_CLASS);
						}
					}
				}
			}
			else
			{
				modelItem.setEnabled(false);
				modelItem.setStyleClass(FUTURE_DAY_CLASS);
			}
			
			if (checkNow(current))
			{
				modelItem.setStyleClass(BUSYTODAY_DAY_CLASS);
			}
			
			modelItems[i] = modelItem;
		}

		return modelItems;
	}
}

