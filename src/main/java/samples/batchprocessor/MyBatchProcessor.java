package samples.batchprocessor;

import hu.perit.spvitamin.core.batchprocessing.BatchProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyBatchProcessor extends BatchProcessor
{
    public MyBatchProcessor()
    {
        super(10);
    }

    public long processManyJobs()
    {
        List<MyJob> jobs = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
        {
            jobs.add(new MyJob());
        }

        try
        {
            super.process(jobs);
            return jobs.stream().filter(MyJob::isSuccess).count();
        }
        catch (ExecutionException e)
        {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
