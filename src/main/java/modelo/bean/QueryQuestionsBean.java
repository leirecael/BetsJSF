package modelo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class QueryQuestionsBean {
	
	private Date fecha;
	private Event evento;
	private BLFacade blfacade;
	private List<Event> eventos = new ArrayList<Event>();
	public Question pregunta;
	private List<Question> preguntas = new ArrayList<Question>();
	public String preg;
	public float min;

	
	
	public QueryQuestionsBean() {
		this.blfacade = new BLFacadeImplementation(new DataAccess());
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	

	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public List<Event> getEventos() {
		return eventos;
	}

	public void setEventos(List<Event> eventos) {
		this.eventos = eventos;
	}

	public Question getPregunta() {
		return pregunta;
	}

	public void setPregunta(Question pregunta) {
		this.pregunta = pregunta;
	}

	public List<Question> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Question> preguntas) {
		this.preguntas = preguntas;
	}

	public String getPreg() {
		return preg;
	}

	public void setPreg(String preg) {
		this.preg = preg;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public void onDateSelect(SelectEvent event) {
		
		this.fecha = (Date) event.getObject();
		setPreguntas(null);
		setEventos(getEvents(fecha));
		System.out.println(eventos);
		
		
	}
	
	public void onEventSelect(SelectEvent event) {
		
		this.evento = (Event) event.getObject();
		setPreguntas(getQuestions(evento));
		System.out.println(preguntas);
		
		
	}
	
	public List<Event> getEvents(Date date){
		return blfacade.getEvents(date);
	}
	
	public List<Question> getQuestions(Event event){
		return event.getQuestions();
	}
	
	public Question createQuestion(Event evento, String desc, float min) throws EventFinished, QuestionAlreadyExist {
		return blfacade.createQuestion(evento, desc, min);
	}
	
	public void crearPregunta(){
		try {
			if(evento!=null)
				pregunta = createQuestion(evento, preg, min);
			else
				System.out.println("ELIGE EVENTO");
		} catch (EventFinished e) {
			// TODO Auto-generated catch block
			System.out.println("EVENTO FINALIZADO");
		} catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			System.out.println("PREGUNTA YA EXISTE");
		}
		if(pregunta!=null) {
			setPreguntas(getQuestions(evento));
			System.out.println("PREGUNTA CREADA: "+ pregunta);
		}
		else
			System.out.println("PREGUNTA NO SE HA PODIDO CREAR");
	}

}
