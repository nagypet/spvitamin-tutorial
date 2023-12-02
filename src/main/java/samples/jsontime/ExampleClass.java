package samples.jsontime;

import hu.perit.spvitamin.json.time.Constants;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.StringJoiner;

@Setter
@Getter
public class ExampleClass
{
    @Getter
    public enum Field
    {
        LOCAL_DATE_TIME("localDateTime"),
        OFFSET_DATE_TIME("offsetDateTime"),
        LOCAL_DATE("localDate"),
        DATE("date"),
        ZONED_DATE_TIME("zonedDateTime"),
        INSTANT("instant");

        private final String fieldName;


        Field(String fieldName)
        {
            this.fieldName = fieldName;
        }
    }

    private LocalDateTime localDateTime;
    private OffsetDateTime offsetDateTime;
    private LocalDate localDate;
    private Date date;
    private ZonedDateTime zonedDateTime;
    private Instant instant;


    @Override
    public String toString()
    {
        return new StringJoiner(", ", "[", "]")
            .add("localDateTime=" + localDateTime)
            .add("offsetDateTime=" + offsetDateTime)
            .add("localDate=" + localDate)
            .add("date=" + formatDate(date))
            .toString();
    }


    private static String formatDate(Date date)
    {
        if (date == null)
        {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_JACKSON_ZONEDTIMESTAMPFORMAT);
        return simpleDateFormat.format(date);
    }


    public String printField(Field field)
    {
        return switch (field)
        {
            case LOCAL_DATE_TIME -> localDateTime.toString();
            case OFFSET_DATE_TIME -> offsetDateTime.toString();
            case LOCAL_DATE -> localDate.toString();
            case DATE -> formatDate(date);
            case ZONED_DATE_TIME -> zonedDateTime.toString();
            case INSTANT -> instant.toString();
        };
    }
}
