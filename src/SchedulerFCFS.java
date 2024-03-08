import java.util.Queue;
import java.util.LinkedList;


// This one is complete (I think) EXCEPT all Schedulers have a number of context switches half of the example output


public class SchedulerFCFS extends SchedulerBase implements Scheduler {
    private final Platform platform;
    private Queue<Process> readyQueue;

    public SchedulerFCFS(Platform platform) {
        this.platform = platform;
        this.readyQueue = new LinkedList<>();
    }


    @Override
    public void notifyNewProcess(Process p) {
        readyQueue.add(p); // Add the new process to the ready queue
    }
    

    @Override
    public Process update(Process cpu) {
        if (cpu == null) {
            return getNextProcess(cpu); // Start the first process
        }

        if (cpu.isExecutionComplete()) {
            platform.log(String.format("Process %s burst complete", cpu.getName()));
            platform.log(String.format("Process %s execution complete", cpu.getName()));

            return getNextProcess(cpu);
        }

        if (cpu.isBurstComplete()) {
            platform.log(String.format("Process %s burst complete", cpu.getName()));
            
            readyQueue.add(cpu); // Process isn't complete, add to the end of the queue
            return getNextProcess(cpu);
        }

        return cpu; // Keep executing the current process
    }


    /*
     * Gets next process (if it exists)
     */
    private Process getNextProcess(Process p) {
        if (!readyQueue.isEmpty()) {
            Process nextProcess = readyQueue.poll(); // Get and remove the next process from the ready queue
            logScheduledProcess(nextProcess);
            this.contextSwitches += 1; // Increment contextSwitches
            return nextProcess;
        } else {
            return null; // No process to execute
        }
    }


    private void logScheduledProcess(Process p) {
        String log = String.format("Scheduled: %s", p.getName());
        platform.log(log);
    }
    
}
