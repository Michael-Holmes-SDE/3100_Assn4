import java.util.PriorityQueue;
import java.util.Comparator;


// This one is complete (I think)


public class SchedulerSJF extends SchedulerBase implements Scheduler {

    final Platform platform;
    PriorityQueue<Process> priorityQueue;


    public SchedulerSJF(Platform platform) {
        this.platform = platform;
        this.priorityQueue = new PriorityQueue<>(1, new CompareTimes()); // Have shortest jobs go first
    }


    @Override
    public void notifyNewProcess(Process p) {
        priorityQueue.add(p); // Add the new process to the ready queue
    }


    @Override // JUST FCFS, UPDATE
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





/*
 * Compares Process times to put the shortest job first
 */
class CompareTimes implements Comparator<Process> {
    
    public int compare(Process P1, Process P2) {
        if (P1.getTotalTime() <= P2.getTotalTime()) {
            return -1; // Choose P1
        }
        else {
            return 1; // Choose P2
        }       
    }
}
 