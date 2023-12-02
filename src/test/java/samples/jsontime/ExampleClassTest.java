package samples.jsontime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.perit.spvitamin.json.SpvitaminObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Slf4j
class ExampleClassTest
{
    @Test
    void testToJsonWithJavaTimeModul() throws JsonProcessingException
    {
        String json = getObjectMapperWithJavaTimeModul().writeValueAsString(getExampleClass());
        log.debug("JavaTimeModul serialization:      {}", json);
    }


    private static ExampleClass getExampleClass()
    {
        ExampleClass exampleClass = new ExampleClass();
        exampleClass.setLocalDateTime(LocalDateTime.now());
        exampleClass.setOffsetDateTime(OffsetDateTime.now());
        exampleClass.setLocalDate(LocalDate.now());
        exampleClass.setDate(new Date());
        return exampleClass;
    }


    @Test
    void testToJsonWithSpvitaminTimeModul() throws JsonProcessingException
    {
        String json = getObjectMapperWithSpvitaminTimeModul().writeValueAsString(getExampleClass());
        log.debug("SpvitaminTimeModul serialization: {}", json);
    }


    @Test
    void testDeserialization() throws JsonProcessingException
    {
        testDeserialization("2023-11-26 09:53");
        testDeserialization("2023-11-26 09:53:10.123");
        testDeserialization("2023-11-26 23:53:10.123-0200");
        testDeserialization("2023-11-26T09:53");
        testDeserialization("2023-11-26T09:53:10.123");
        testDeserialization("2023-11-26T09:53:10.123456");
        testDeserialization("2023-11-26T09:53:10.123456789");
        testDeserialization("2023-11-26T09:53:10.123+0200");
        testDeserialization("2023-11-26T09:53:10.123+0400");
        testDeserialization("2023-11-26T09:53:10.123Z");
    }


    private void testDeserialization(String dateString)
    {
        log.debug("================================================================================================================");
        for (ExampleClass.Field field : ExampleClass.Field.values())
        {
            testDeserializationWithJava(dateString, field);
            testDeserializationWithSpvitamin(dateString, field);
            log.debug("----------------------------------------------------------------------------------------------------------------");
        }
    }


    private void testDeserializationWithJava(String dateString, ExampleClass.Field field)
    {
        ObjectMapper mapper = getObjectMapperWithJavaTimeModul();
        readFromJson(Type.JAVA, mapper, field, dateString);
    }


    private void testDeserializationWithSpvitamin(String dateString, ExampleClass.Field field)
    {
        ObjectMapper mapper = getObjectMapperWithSpvitaminTimeModul();
        readFromJson(Type.SPVITAMIN, mapper, field, dateString);
    }


    private static ObjectMapper getObjectMapperWithJavaTimeModul()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


    private static ObjectMapper getObjectMapperWithSpvitaminTimeModul()
    {
        return SpvitaminObjectMapper.createMapper(SpvitaminObjectMapper.MapperType.JSON);
    }


    private enum Type
    {
        JAVA,
        SPVITAMIN
    }


    private void readFromJson(Type type, ObjectMapper mapper, ExampleClass.Field field, String timestampString)
    {
        try
        {
            ExampleClass exampleClass = mapper.readValue(String.format("{\"%s\":\"%s\"}", field.getFieldName(), timestampString), ExampleClass.class);
            if (type == Type.JAVA)
            {
                log.debug("JavaTimeModul deserialization:      {} to {} => {}", timestampString, field, exampleClass.printField(field));
            }
            else
            {
                log.debug("SpvitaminTimeModul deserialization: {} to {} => {}", timestampString, field, exampleClass.printField(field));
            }
        }
        catch (Exception e)
        {
            if (type == Type.JAVA)
            {
                log.debug("JavaTimeModul deserialization:      {} to {} => FAILED!", timestampString, field);
            }
            else
            {
                log.debug("SpvitaminTimeModul deserialization: {} to {} => FAILED!", timestampString, field);
            }
        }
    }
}