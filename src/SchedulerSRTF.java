import java.util.Queue;
import java.util.LinkedList;



public class SchedulerSRTF extends SchedulerBase implements Scheduler {
    final Platform platform;
    private Queue<Process> processQueue;

    public SchedulerSRTF(Platform platform) {
        this.platform = platform;
        this.processQueue = new LinkedList<>();
    }

    @Override
    public void notifyNewProcess(Process p) {
        processQueue.add(p);
    }

    @Override
    public Process update(Process cpu) {
        if (cpu != null) {

            if (cpu.isExecutionComplete()) {
                platform.log(String.format("Process %s burst complete", cpu.getName()));
                platform.log(String.format("Process %s execution complete", cpu.getName()));

                processQueue.remove(cpu);
                this.contextSwitches += 1;
                return getNextProcess(null); // Don't use cpu, since it's completed
            }

            if (cpu.isBurstComplete()) {
                platform.log(String.format("Process %s burst complete", cpu.getName()));
            }
        }

        // Getting the process with shortest remaining time
        Process nextProcess = getNextProcess(cpu);
        if (nextProcess != null) {
            return nextProcess;
        } else {
            return cpu; // Continue the same process
        }

    }


    private Process getNextProcess(Process currentProcess) {
        int shortestRemainingTime;
        // Getting shortestRemainingTime
        if (currentProcess != null) { // Use currentProcess' remainingTime
            int currentProcessRemainingTime = currentProcess.getTotalTime() - currentProcess.getElapsedTotal();
            shortestRemainingTime = currentProcessRemainingTime;
        } else { // Use MAX so all processes will be shorter than it
            shortestRemainingTime = Integer.MAX_VALUE;
        }

        // Getting process with the least remaining time
        Process shortestProcess = getShortestRemainingProcess(shortestRemainingTime, currentProcess);


        if (shortestProcess != null & shortestProcess != currentProcess) {
            if (currentProcess != null) {
                this.contextSwitches += 1;
                platform.log(String.format("Preemptively removed: %s", currentProcess.getName()));
            }

            this.contextSwitches += 1;
            logScheduledProcess(shortestProcess);
        }

        return shortestProcess;
    }


    private Process getShortestRemainingProcess(int shortestRemainingTime, Process currentProcess) {
        Process shortestProcess = currentProcess;

        for (Process p : processQueue) {
            if (p != currentProcess) {
                int remainingTime = p.getTotalTime() - p.getElapsedTotal();

                if (remainingTime < shortestRemainingTime) {
                    shortestRemainingTime = remainingTime;
                    shortestProcess = p;
                }
            }
        }
        return shortestProcess;
    }


    private void logScheduledProcess(Process p) {
        String log = String.format("Scheduled: %s", p.getName(), p.getElapsedTotal());
        platform.log(log);
    }




}
