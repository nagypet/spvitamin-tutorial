package samples.batchprocessor;

import hu.perit.spvitamin.core.took.Took;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MyBatchProcessorTest
{
    @Test
    void test()
    {
        MyBatchProcessor myBatchProcessor = new MyBatchProcessor();
        try (Took took = new Took())
        {
            long processed = myBatchProcessor.processManyJobs();
            log.debug("{} jobs succeeded", processed);
        }
    }
}