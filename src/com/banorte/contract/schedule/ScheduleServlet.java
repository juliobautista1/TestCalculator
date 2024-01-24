package com.banorte.contract.schedule;

import com.banorte.contract.layout.LayoutOperations;
import com.banorte.contract.util.Formatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author MRIOS
 */
public class ScheduleServlet extends HttpServlet {

	@Override
	public void init() {
		Runnable task1 = new Runnable() {

			public void run() {
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					System.out.println("Ejecutando Layouts..." + (new java.sql.Timestamp(System.currentTimeMillis())));
					try {
						LayoutOperations layOut = LayoutOperations.getInstance();
						layOut.setProducto(null);
						layOut.startSend(false, true, true);
						layOut.startRecipe(false,true);

					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de Layouts..." + (new java.sql.Timestamp(System.currentTimeMillis())));
				}
			}
		};

		Runnable task2 = new Runnable() {

			public void run() {
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					System.out.println("Ejecutando Layouts de Instalacion..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
					try {
						LayoutOperations layOut = LayoutOperations
								.getInstance();
						layOut.startRecipeInstall(false);

					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(
								Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de Layouts de Instalacion..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
				}
			}
		};

		Runnable task3 = new Runnable() {

			public void run() {
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					System.out.println("Ejecutando Layout de OIP..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
					try {
						LayoutOperations layOut = LayoutOperations
								.getInstance();
						layOut.startSendOIP(false);
					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(
								Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de Layout de OIP..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
				}
			}
		};

		//Tarea para actualizar el catálogo de ejecutivos
		Runnable task4 = new Runnable() {

			public void run() {
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					System.out.println("Ejecutando Actualización del Catalogo de Ejecutivos..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
					try {
						
						LayoutOperations layOut = LayoutOperations.getInstance();
						
						//Actualizar catalogo de ejecutivos
						layOut.startRecipeExecutives();
						layOut.startRecipeExecutiveBranch();

					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(
								Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de la Actualización del Catalogo de Ejecutivos..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
				}
			}
		};
		
		//Tarea para obtener los mantenimientos de Operaciones y altas locales y así enviarlos a Tralix y Directa
		Runnable task5 = new Runnable() {

			public void run() {
					
				System.out.println("Inicio obtencion Layout de Mantenimientos..."
								+ (new java.sql.Timestamp(System
										.currentTimeMillis())));
				try {

					LayoutOperations layout = LayoutOperations.getInstance();

					// Procesar las altas y mantenimientos del día
					Calendar proccesDate = new Formatter().formatDate(Calendar.getInstance().getTime());
					layout.startMaintanceProcess(proccesDate.getTime());

					// Enviar a directa y a tralix la informacion de las altas y
					// mantenimientos
					layout.startMaintanceSend();

				} catch (Exception ex) {
					Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("ERROR:" + ex);
				}

				System.out.println("Fin obtencion Layout de Mantenimientos..."
						+ (new java.sql.Timestamp(System.currentTimeMillis())));
				
			}
		};		
		
		//Tarea para leer las respuestas de Directa
		Runnable task6 = new Runnable() {

			public void run() {
				Calendar startOperationDate = Calendar.getInstance();
					System.out.println("Ejecutando Lectura de Layouts Directa..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
					try {
						
						LayoutOperations layOut = LayoutOperations.getInstance();
						
						Calendar endOperationDate =  Calendar.getInstance();
						
						endOperationDate.add(Calendar.DAY_OF_MONTH, 1);
						
						//Obtener los resultados de Directa
						layOut.startRecipeDirecta(new Formatter().formatDate(startOperationDate.getTime()).getTime(),new Formatter().formatDate(endOperationDate.getTime()).getTime());

					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(
								Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de Lectura de Layouts Directa..."
							+ (new java.sql.Timestamp(System
									.currentTimeMillis())));
				}
		};
		
		// Ejecutar lecutra de respuestas
		Runnable task7 = new Runnable() {

			public void run() {
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					System.out.println("Ejecutando Lectura de respuestas..." + (new java.sql.Timestamp(System.currentTimeMillis())));
					try {
						LayoutOperations layOut = LayoutOperations.getInstance();
						layOut.startRecipe(false,true);

					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de Lectura..." + (new java.sql.Timestamp(System.currentTimeMillis())));
				}
			}
		};
		//Crear Reportes Altas TPV y Nómina
		Runnable task8= new Runnable() {
			@Override
			public void run() {
				Calendar calendar=Calendar.getInstance();
				int day=calendar.get(Calendar.DAY_OF_WEEK);
				if(Calendar.SATURDAY!=day&&Calendar.SUNDAY!=day){
					try{
						LayoutOperations layout=LayoutOperations.getInstance();
						System.out.println("Ejecutando creación de Reporte Altas TPV... "+(new java.sql.Timestamp(System.currentTimeMillis())));
						layout.createTpvPayrollDTOReport();
					}catch(Exception e){
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, e);
						System.out.println("ERROR: " + e);
					}
					System.out.println("Fin de Reportes TPV y Nomina. "+(new java.sql.Timestamp(System.currentTimeMillis())));
				}
			}
		};
		Runnable task9 = new Runnable() {
			public void run() {
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					System.out.println("Ejecutando Lectura de cuentas PyME Mujer..." + (new java.sql.Timestamp(System.currentTimeMillis())));
					try {
						LayoutOperations layOut = LayoutOperations.getInstance();
						layOut.startLecturaCuentasMujerPyME(false,true);
					} catch (Exception ex) {
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
						System.out.println("ERROR:" + ex);
					}
					System.out.println("Fin de Lectura de cuentas PyME Mujer..." + (new java.sql.Timestamp(System.currentTimeMillis())));
				}
			}
		};
		//Crear Reportes Diarios Adquirente
		Runnable task10= new Runnable() {
			@Override
			public void run() {
				Calendar calendar=Calendar.getInstance();
				//int day=calendar.get(Calendar.DAY_OF_WEEK);
				//if(Calendar.SATURDAY!=day&&Calendar.SUNDAY!=day){
					try{
						LayoutOperations layout=LayoutOperations.getInstance();
						System.out.println("Ejecutando creación de Reporte Diario Adquirente... "+(new java.sql.Timestamp(System.currentTimeMillis())));
						layout.createAdquirerStatusDTOReport(calendar,calendar,true);
					}catch(Exception e){
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, e);
						System.out.println("ERROR: " + e);
					}
					System.out.println("Fin de creación de Reportes Diario Adquirente. "+(new java.sql.Timestamp(System.currentTimeMillis())));

			}
		};
		
		//Cambio de horario
		Runnable task11= new Runnable() {
			@Override
			public void run() {
				//El cambio de hora en México se realiza de forma oficial dos veces al año:
				//	• El primer domingo de abril.
				//	• El último domingo de octubre.
				Calendar calendar=Calendar.getInstance();
				System.out.println("Validando si se ejecuta cambio de horario en tareas programadas... "+(new java.sql.Timestamp(System.currentTimeMillis())));
				
				if(calendar.get(Calendar.MONTH)== Calendar.APRIL && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_MONTH) <=7 ) {
					try{
						System.out.println("Ejecutando cambio de horario de verano en las tareas programadas... "+(new java.sql.Timestamp(System.currentTimeMillis())));
						reProgramarAll();		
					}catch(Exception e){
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, e);
						System.out.println("ERROR: " + e);
					}
				}else{
					System.out.println("VERANO");
					System.out.println("Mes: "+calendar.get(Calendar.MONTH) +" es "+ Calendar.APRIL +" = " + (calendar.get(Calendar.MONTH)== Calendar.APRIL));
					System.out.println("Dia: "+calendar.get(Calendar.DAY_OF_WEEK) + " es "+ Calendar.SUNDAY +" = " +(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY));
					System.out.println("Dia semana: "+calendar.get(Calendar.DAY_OF_MONTH) +" <=7  = " +(calendar.get(Calendar.DAY_OF_MONTH) <=7 ));					
				}
				if(calendar.get(Calendar.MONTH)== Calendar.OCTOBER && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_MONTH) > (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-7)) {
					try{
						System.out.println("Ejecutando cambio de horario de invierno en las tareas programadas... "+(new java.sql.Timestamp(System.currentTimeMillis())));
						reProgramarAll();						
					}catch(Exception e){
						Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, e);
						System.out.println("ERROR: " + e);
					}
				}else{
					System.out.println("INVIERNO");
					System.out.println("Mes: "+calendar.get(Calendar.MONTH) +" es "+ Calendar.OCTOBER +" = " + (calendar.get(Calendar.MONTH)== Calendar.OCTOBER));
					System.out.println("Dia: "+calendar.get(Calendar.DAY_OF_WEEK) + " es "+ Calendar.SUNDAY +" = " +(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY));
					System.out.println("Dia semana: "+calendar.get(Calendar.DAY_OF_MONTH) +" > "+(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-7)+" = " +(calendar.get(Calendar.DAY_OF_MONTH) > (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-7)));									
				}
				
				System.out.println("Fin de tarea de cambio de Horario. "+(new java.sql.Timestamp(System.currentTimeMillis())));

			}
		};
		
		ScheduleExecutorManager.scheduleByHour("ProcesoLayout 9AM", task1, 9, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLayout 11AM", task1, 11, 00);
		
		ScheduleExecutorManager.scheduleByHour("ProcesoLayout 14PM", task1, 14, 00);
	
		ScheduleExecutorManager.scheduleByHour("ProcesoLayout 18PM", task1, 18, 00);
		
		ScheduleExecutorManager.scheduleByHour("ProcesoLectura 10AM", task7, 10, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLectura 12PM", task7, 12, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLectura 13PM", task7, 13, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLectura 15PM", task7, 15, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLectura 16PM", task7, 16, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLectura 17PM", task7, 17, 00);
		
		ScheduleExecutorManager.scheduleByHour("ProcesoLecturaCuentasMujerPyME 4AM", task9, 4, 00);
		ScheduleExecutorManager.scheduleByHour("ReportesDiariosAdquirente 5AM", task10, 5, 00);
		//ScheduleExecutorManager.scheduleByHour("CambioHorario 3AM", task11, 3, 30);
		ScheduleExecutorManager.scheduleByHour("CambioHorario 3AM", task11, 7, 40);
		
		
		//FIXME gina - PRUEBA LOCAL
	/*	ScheduleExecutorManager.scheduleByHour("ProcesoLayout", task1, 10, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLayoutInstalaciones", task2, 10, 10);
		ScheduleExecutorManager.scheduleByHour("ReportesAltasTPVyNomina3", task8, 10, 30);*/
		// fin solo local
		
		//Para Reportes Altas TPV y Nomina
		ScheduleExecutorManager.scheduleByHour("ReportesAltasTPVyNomina", task8, 8, 00);
		
		//prueba cambio horario dava tener en cuenta el servidor va una hora adelante Nomina
		//ScheduleExecutorManager.scheduleByHour("ReportesAltasTPVyNomina", task8, 18, 30);
		
		
		ScheduleExecutorManager.scheduleByHour("ProcesoLayoutInstalaciones 9PM", task2, 21, 00);
		ScheduleExecutorManager.scheduleByHour("ProcesoLayoutOIP 6AM", task3, 06, 00);

		// Programacion para la Actualización del Catalogo de Ejecutivos
		ScheduleExecutorManager.scheduleByHour("ProcesoActualizacionEjecutivos 11PM", task4, 23, 00);

		// Programacion para envio outbound
//		ScheduleExecutorManager.scheduleByHour("ProcesoLayoutEnvioDirectaTralix 6AM", task5, 06, 00); // se apagan para evitar envíos

		// Programacion para lectura de Directa
//		ScheduleExecutorManager.scheduleByHour("ProcesoLayoutLeerDirecta 10PM", task6, 22, 00); // se apagan para evitar envíos


	}

	@Override
	public void destroy() {
		super.destroy();
		ScheduleExecutorManager.shutdown();
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String execIdParam = request.getParameter("execId");
		String reProgramarIdParam = request.getParameter("reProgramarId");
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ScheduleServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet ScheduleServlet at "
					+ request.getContextPath() + "</h1>");
			out.println("<table border=1>");
			out.println("<tr>");
			out.println("<td>Actual Time:</td>");
			out.println("<td>"
					+ new java.sql.Timestamp(System.currentTimeMillis())
					+ "</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<br/>");

			if (execIdParam != null) {
				if (exec(execIdParam)) {
					response.sendRedirect("/" + request.getContextPath() + "/"
							+ getServletName());
				}
			}
			
			if (reProgramarIdParam != null) {
				if (reProgramar(reProgramarIdParam)) {
					response.sendRedirect("/" + request.getContextPath() + "/"
							+ getServletName());
				}
			}

			Collection<ScheduleTask> list = ScheduleExecutorManager
					.getScheduleTask();
			out.println("<table border=1>");
			out.println("<tr>");
			out.println("<td>Task</td>");
			out.println("<td>Last Exec</td>");
			out.println("<td>Next Exec</td>");
			out.println("<td>Status</td>");
			out.println("<td>Exec Now</td>");
			out.println("<td>Re-schedule</td>");
			out.println("</tr>");
			for (ScheduleTask task : list) {
				out.println("<tr>");
				out.println("<td>");
				out.println(task.getTaskName());
				out.println("</td>");
				out.println("<td>");
				out.println(task.getLastExec() > 0 ? (new java.sql.Timestamp(
						task.getLastExec())).toString() : "Unknown");
				out.println("</td>");
				out.println("<td>");
				out.println(task.getNextExec() > 0 ? (new java.sql.Timestamp(
						task.getNextExec())).toString() : "Unknown");
				out.println("</td>");
				out.println("<td>");
				out
						.println(task.isRunning() ? "<font color=33AA33>Running</font>"
								: "<font color=3333AA>Scheduled</font>");
				out.println("</td>");
				out.println("<td>");
				out.println("<a href='./ScheduleServlet?execId="
						+ task.getTaskName() + "'>Ejecutar</a>");
				out.println("</td>");
				out.println("<td>");
				out.println("<a href='./ScheduleServlet?reProgramarId="
						+ task.getTaskName() + "'>ReProgramar</a>");
				out.println("</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	private boolean exec(String taskId) {
		Collection<ScheduleTask> list = ScheduleExecutorManager
				.getScheduleTask();
		for (ScheduleTask task : list) {
			if (task.getTaskName().equals(taskId)) {
				ScheduleExecutorManager.execute(task);
				return true;
			}
		}
		return false;
	}
	
	private boolean reProgramar(String taskId) {
		Collection<ScheduleTask> list = ScheduleExecutorManager.getScheduleTask();
		for (ScheduleTask task : list) {
			if (task.getTaskName().equals(taskId)) {
				System.out.println("Reschedule de tarea... "+task.getTaskName());
				if(task.getTaskName().equals("ProcesoLayout 9AM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLayout 9AM", task1, 9, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 9, 00);
				}else if(task.getTaskName().equals("ProcesoLayout 11AM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLayout 11AM", task1, 11, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 11, 00);
				}else if(task.getTaskName().equals("ProcesoLayout 14PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLayout 14PM", task1, 14, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 14, 00);
				}else if(task.getTaskName().equals("ProcesoLayout 18PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLayout 18PM", task1, 18, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 18, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 10AM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLectura 10AM", task7, 10, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 10, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 12PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLectura 12PM", task7, 12, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 12, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 13PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLectura 13PM", task7, 13, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 13, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 15PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLectura 15PM", task7, 15, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 15, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 16PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLectura 16PM", task7, 16, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 16, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 17PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLectura 17PM", task7, 17, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 17, 00);
				}else if(task.getTaskName().equals("ProcesoLecturaCuentasMujerPyME 4AM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLecturaCuentasMujerPyME 4AM", task9, 4, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 4, 00);
				}else if(task.getTaskName().equals("ReportesDiariosAdquirente 5AM")) {
					//ScheduleExecutorManager.scheduleByHour("ReportesDiariosAdquirente 5AM", task10, 5, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 5, 00);
				}else if(task.getTaskName().equals("CambioHorario 3AM")) {
					//ScheduleExecutorManager.scheduleByHour("CambioHorario 3AM", task11, 3, 30);
					//ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 3, 30);
					reProgramarAll();
				}else if(task.getTaskName().equals("ReportesAltasTPVyNomina")) {
					//ScheduleExecutorManager.scheduleByHour("ReportesAltasTPVyNomina", task8, 8, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 8, 00);
				}else if(task.getTaskName().equals("ProcesoLayoutInstalaciones 9PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLayoutInstalaciones 9PM", task2, 21, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 21, 00);
				}else if(task.getTaskName().equals("ProcesoLayoutOIP 6AM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoLayoutOIP 6AM", task3, 06, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 06, 00);
				}else if(task.getTaskName().equals("ProcesoActualizacionEjecutivos 11PM")) {
					//ScheduleExecutorManager.scheduleByHour("ProcesoActualizacionEjecutivos 11PM", task4, 23, 00);
					ScheduleExecutorManager.executeReProgramacion(taskId, task, task.getScheduledFuture(), 23, 00);
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean reProgramarAll() {
		Collection<ScheduleTask> list = ScheduleExecutorManager.getScheduleTask();
		for (ScheduleTask task : list) {
			//if (task.getTaskName().equals(taskId)) {
				System.out.println("Reschedule de TODAS las tarea... "+task.getTaskName());
				if(task.getTaskName().equals("ProcesoLayout 9AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 9, 00);
				}else if(task.getTaskName().equals("ProcesoLayout 11AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 11, 00);
				}else if(task.getTaskName().equals("ProcesoLayout 14PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 14, 00);
				}else if(task.getTaskName().equals("ProcesoLayout 18PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 18, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 10AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 10, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 12PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 12, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 13PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 13, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 15PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 15, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 16PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 16, 00);
				}else if(task.getTaskName().equals("ProcesoLectura 17PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 17, 00);
				}else if(task.getTaskName().equals("ProcesoLecturaCuentasMujerPyME 4AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 4, 00);
				}else if(task.getTaskName().equals("ReportesDiariosAdquirente 5AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 5, 00);
				}else if(task.getTaskName().equals("CambioHorario 3AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 3, 30);
				}else if(task.getTaskName().equals("ReportesAltasTPVyNomina")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 8, 00);
				}else if(task.getTaskName().equals("ProcesoLayoutInstalaciones 9PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 21, 00);
				}else if(task.getTaskName().equals("ProcesoLayoutOIP 6AM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 06, 00);
				}else if(task.getTaskName().equals("ProcesoActualizacionEjecutivos 11PM")) {
					ScheduleExecutorManager.executeReProgramacion(task.getTaskName(), task, task.getScheduledFuture(), 23, 00);
				}
				//return true;
			//}
		}
		//return false;
		return true;
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "ScheduleServlet";
	}// </editor-fold>
}
