public class SchedulerFCFS extends SchedulerBase implements Scheduler {
    final Platform platform;
    //private int[] processes;  // For keeping track of all the processes in order (add to end) (make ArrayList?)

    public SchedulerFCFS(Platform platform) {
        this.platform = platform;
        //this.processes = processes;
    }


    /*
     * Returns the number of context switches that occur during the simulation.
     */
    @Override
    public int getNumberOfContextSwitches() {
        return this.getNumberOfContextSwitches();
    }


    /*
     * The simulator platform invokes this whenever a new process arrives and is available to be scheduled.
     */
    @Override
    public void notifyNewProcess(Process p) {
        this.contextSwitches += 1; // Increment contextSwitches
        String log = String.format("Scheduled: %s", p.getName());
        platform.log(log);
    }


    /*
     * Process update(Process cpu) : The simulator platform invokes this each time increment. The scheduler should update the scheduling when this method is called.
         * The cpu parameter is the currently running process on that cpu.  If no process is running on that cpu, cpu is null.
         * The return value is the process that should be (or continue to be) scheduled.
     */
    @Override
    public Process update(Process cpu) {
        if (cpu == null) { // Start the first process (including notifyNewProcess)
            // Do what?
            System.out.print("cpu is null"); //TEST
            return cpu; // How to get the first process?
        }
        if (cpu.isExecutionComplete()) { // Process has finished executing
            platform.log(String.format("Process %s burst complete", cpu.getName()));
            platform.log(String.format("Process %s execution complete", cpu.getName())); 
            
            // Load the next process
            // notifyNewProcess(nextProcess);
            // return nextProcess
        } 
        // 
        return cpu; // Keep executing the current process
    }


}
