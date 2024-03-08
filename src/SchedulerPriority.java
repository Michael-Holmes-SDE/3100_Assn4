import java.util.PriorityQueue;
import java.util.Comparator;



public class SchedulerPriority extends SchedulerBase implements Scheduler {
    final Platform platform;
    private PriorityQueue<Process> priorityQueue;

    public SchedulerPriority(Platform platform) {
        this.platform = platform;
        this.priorityQueue = new PriorityQueue<>(1, new ComparePriority()); // Have highest priority jobs go first
    }


    @Override
    public void notifyNewProcess(Process p) {
        priorityQueue.add(p); // Add the new process to the priority queue
    }


    @Override
    public Process update(Process cpu) {
        if (cpu == null) {
            return getNextProcess(cpu); // Start the first process
        }

        if (cpu.isExecutionComplete()) {
            platform.log(String.format("Process %s burst complete", cpu.getName()));
            platform.log(String.format("Process %s execution complete", cpu.getName()));

            this.contextSwitches += 1;
            return getNextProcess(cpu);
        }

        if (cpu.isBurstComplete()) {
            platform.log(String.format("Process %s burst complete", cpu.getName()));
            
            this.contextSwitches += 1;
            priorityQueue.add(cpu); // Process isn't complete, add back to the priority queue
            return getNextProcess(cpu);
        }

        return cpu; // Keep executing the current process
    }


    /*
     * Gets next process (if it exists)
     */
    private Process getNextProcess(Process p) {
        if (!priorityQueue.isEmpty()) {
            Process nextProcess = priorityQueue.poll(); // Get and remove the next process from the priority queue
            logScheduledProcess(nextProcess);
            this.contextSwitches += 1;
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



/*
 * Compares Priorities to put the Processes in order of Priority
 */
class ComparePriority implements Comparator<Process> {
    
    public int compare(Process P1, Process P2) {
        if (P1.getPriority() <= P2.getPriority()) { // Lower priority number means higher priority (i.e. 1 is higher priority than 5)
            return -1; // Choose P1
        }
        else {
            return 1; // Choose P2
        }       
    }
}
