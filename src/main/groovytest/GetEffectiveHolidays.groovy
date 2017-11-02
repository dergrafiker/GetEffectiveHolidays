package groovytest

import de.jollyday.Holiday
import de.jollyday.HolidayCalendar
import de.jollyday.HolidayManager
import de.jollyday.ManagerParameters

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoField

@Grapes([
        @Grab(group = 'de.jollyday', module = 'jollyday', version = '0.5.2')
])
class GetEffectiveHolidays {

    public static void main(String[] args) {
        def manager = HolidayManager.getInstance(ManagerParameters.create(HolidayCalendar.GERMANY))
        def workDays = { Holiday holiday ->
            def dayOfWeek = DayOfWeek.of(holiday.getDate().get(ChronoField.DAY_OF_WEEK))
            (DayOfWeek.MONDAY.ordinal()..DayOfWeek.FRIDAY.ordinal()).contains(dayOfWeek.ordinal())
        }

        (2012..LocalDate.now().year + 1).each { it ->
            manager.getHolidays(it, 'nw').findAll(workDays).asList().sort { it.date }.each { Holiday holiday ->
                println(String.format("%s\t%s", holiday.date, holiday.getDescription(Locale.GERMAN as Locale)));
            }
            println ""
        }
    }
}
