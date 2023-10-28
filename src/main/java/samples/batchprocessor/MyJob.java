package samples.batchprocessor;

import hu.perit.spvitamin.core.batchprocessing.BatchJob;
import hu.perit.spvitamin.core.exception.ProcessingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class MyJob extends BatchJob
{
    private final Random random = new Random();
    private boolean success = false;

    @Override
    protected Boolean execute() throws Exception
    {
        log.debug("processing job");
        TimeUnit.MILLISECONDS.sleep(100);
        if (random.nextInt(100) < 10)
        {
            throw new ProcessingException("not found!");
        }
        this.success = true;
        return null;
    }


    @Override
    public boolean isFatalException(Throwable ex)
    {
        return !(ex instanceof ProcessingException);
    }
}
