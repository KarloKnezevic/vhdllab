/**
 * 
 */
package hr.fer.zemris.vhdllab.applets.schema.drawings;

import hr.fer.zemris.vhdllab.applets.schema.SchemaColorProvider;
import hr.fer.zemris.vhdllab.applets.schema.components.AbstractSchemaComponent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Crtaca ploha komponenti sa svom pameti (reagira na misa, akcije, itd..)
 * @author Tommy
 *
 */
public class SchemaDrawingCanvas extends JComponent {

	private static final long serialVersionUID = 168392906688186429L;
	
	private Dimension dimension = null;
	private SchemaDrawingGrid grid = null;
	private ArrayList<SchemaDrawingComponentEnvelope> components = null;	
	private SchemaColorProvider colors = null;	
	private BufferedImage canvas = null;
	private SchemaDrawingCanvasListeners listeners = null;
	public Point mousePosition;
	
	public SchemaDrawingCanvas(SchemaColorProvider colors) {
		components=new ArrayList<SchemaDrawingComponentEnvelope>();
		this.colors=colors;		
		initGUI();
		initListeners();
	}
	
	public SchemaDrawingCanvas(SchemaColorProvider colors, Dimension dimension){
		 this.dimension=dimension;
		 initGUI();
		 initListeners();
	}
	
	public SchemaDrawingAdapter getAdapter(){
		return grid.getAdapter();
	}
			

	/**
	 * Inicijalizacija GUIa
	 */
	private void initGUI() {
		if(dimension==null){
			dimension=new Dimension(1000,500);//ovo je samo privremeno...
		}
		
		this.canvas=new BufferedImage(dimension.width,dimension.height,BufferedImage.TYPE_3BYTE_BGR);
		this.grid=new SchemaDrawingGrid(colors,canvas);
	}
	
	/**
	 * Inicijalizacija listenera za ovaj razred
	 */
	private void initListeners() {
		listeners=new SchemaDrawingCanvasListeners(this);
	}
	
	
	/**
	 * Dodavanje komponente na crtacu povrsinu...
	 * @param component Komponenta koja se dodaje.
	 */	
	public void addComponent(AbstractSchemaComponent component, Point position){
		SchemaDrawingComponentEnvelope envelope=new SchemaDrawingComponentEnvelope(component,position);
		
		components.add(envelope);
		this.repaint();
	}
	
	public ArrayList<SchemaDrawingComponentEnvelope> getComponentList(){
		return this.components;
	}

	/**
	 * Metoda za iscrtavanje plohe
	 */
	protected void paintComponent(Graphics gr){
		if(canvas==null){
			try {
				throw new Exception("Za sada jedan veliki problem - konstruktor ne radi kako spada");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		//transformiraj BufferedImage u nekaj korisno
		Graphics2D graph=(Graphics2D) canvas.getGraphics();
		
		//ocisti crtacu plohu
		graph.setColor(colors.GRID_BG);
		graph.fillRect(0,0,canvas.getWidth(),canvas.getHeight());		
		
		
		//graph.setColor(new Color(15,200,200));
		//graph.drawString(listeners.getX()+", "+listeners.getY(),listeners.getX(),listeners.getY());
		
		// ovo sam ja dodo, ti prouci i reci kaj mislis. Btw, ovo:
		// grid.getAdapter().drawLine(0, 0, 100, 100);
		// bi ovdje bar trebalo raditi, al ne radi.
		// Ono sto je zanimljivo - grid.getAdapter().drawLine(0, 0, 150, 100); radi savrseno.
		// 
		// Uglavnom, ti ovo promijeni kako mislis da je potrebno.
		SchemaDrawingAdapter adapter = grid.getAdapter();
		
		grid.ShowCursorPoint(mousePosition);
		
		for (SchemaDrawingComponentEnvelope envelope : components) {
			Point p = envelope.getPosition();
			adapter.setStartingCoordinates(p.x, p.y);
			envelope.getComponent().draw(adapter);
		}
		
		//iscrtaj BufferedImage
		gr.drawImage(canvas,0,0,canvas.getWidth(),canvas.getHeight(),null);
		
	}
	
		
}
