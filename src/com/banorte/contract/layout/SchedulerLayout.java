/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.layout;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 *
 * @author cgomez
 */
public class SchedulerLayout implements Job {
    public void execute(JobExecutionContext context)
            throws JobExecutionException {          
        LayoutOperations layOut = LayoutOperations.getInstance();
        layOut.startSend(false, true,true);
        layOut.startRecipe(false,true);
    }
}
