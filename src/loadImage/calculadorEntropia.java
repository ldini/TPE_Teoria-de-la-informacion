package loadImage;

import java.util.Vector;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Set;

public class calculadorEntropia {
	
	private Vector<Double> PX = new Vector<Double>();
	private BufferedImage imagen;
	
	public float getProbabilidad(BufferedImage imagen) {
		return 0;
	}
	
	
	public int getIndexOfTonalidad(Vector<Tonalidad> vector, int tonalidad) {
		for(int i = 0; i < vector.size(); i++) {
			if(vector.elementAt(i).getTonalidadGris() == tonalidad)
				return i;
		}
		return -1;
	}
	
	public Vector<Tonalidad> getOcurrencias( BufferedImage imagen) {
		Vector<Tonalidad> salida = new Vector<Tonalidad>();
		int indiceVector = 0;
		for(int i = 0; i < imagen.getWidth(); i++)
			for(int j = 0; j < imagen.getHeight(); j++) {
				int rgb = imagen.getRGB(i, j);
				Color color = new Color(rgb, true);
				int tonalidad = color.getRed();
				int indiceTonalidad = getIndexOfTonalidad(salida,tonalidad);
				if( indiceTonalidad != -1) {
					salida.elementAt(indiceTonalidad).add1();
				}
				else {
					Tonalidad ton = new Tonalidad(tonalidad, 1);
					salida.add(indiceVector, ton);
					indiceVector++;
				}
			}
		return salida;
	}
	
	
	  public double[][] getMatrizdepasaje(BufferedImage imagen) { 
	  Vector<Tonalidad> tonalidades = getOcurrencias(imagen);
	  
	  double matPasaje[][]; 
	  int cantSimbolos = tonalidades.size(); 
	  matPasaje = new double[cantSimbolos][cantSimbolos];
	  int pixelActual, pixelSiguiente = 0;
	  
	  for( int i = 0; i < imagen.getHeight(); i++) 
		  for( int j = 0; j < imagen.getWidth() - 1; j++) {
			  int rgb = imagen.getRGB(j, i); 
			  Color color = new Color(rgb, true); pixelActual = color.getRed();
			  rgb = imagen.getRGB(j + 1, i);
			  color = new Color(rgb, true); 
			  pixelSiguiente =color.getRed();
			  int columna = getIndexOfTonalidad(tonalidades,pixelActual);
			  int fila = getIndexOfTonalidad(tonalidades,pixelSiguiente); 
			  matPasaje[fila][columna]+=1;
		  	} 
	  for(int i=0; i < tonalidades.size();i++){
		  for(int j=0; j < tonalidades.size(); j++) {
			  matPasaje[i][j] = (double) (matPasaje[i][j] / (tonalidades.elementAt(j).getOcurrencias())); 
		  }
	  } 
	  return matPasaje; 
	  }
	 


	private void Cargar_PX(BufferedImage imagen){
		  Vector<Tonalidad> tonalidades = getOcurrencias(imagen);		  
			double entropia = 0;
			for(Tonalidad T: tonalidades) {
				double cantidad = T.getOcurrencias();
				double probabilidad = (cantidad) / (500 * 500);
				PX.add(probabilidad);
				double componenteEntropia = (probabilidad) * (Math.log10(probabilidad) / Math.log10(10));
				entropia += componenteEntropia * (-1);
	  }
			
	  }
	
	 
	
	  public double getEntropiaConMemoria(BufferedImage imagen) {
		  Cargar_PX(imagen);
		  double[][] a = getMatrizdepasaje(imagen);
		
		double entropy = 0;

		for (int columna = 0; columna < a.length; columna++) {
			double entropy_part = 0;
			for (int fila=0; fila < a.length; fila++) {
				if (a[fila][columna]>0)
					entropy_part+=(a[fila][columna]*(Math.log(a[fila][columna])/Math.log(2)));
			}
			entropy_part = entropy_part*PX.elementAt(columna).doubleValue();
			entropy = entropy + entropy_part;
		}
		return (entropy/2)*(-1);
	  }
	
	  
	  
	  public double getEntropiaSinMemoria(BufferedImage imagen) {
		Cargar_PX(imagen);
		double entropia = 0;
		for(int i=0;i<PX.size();i++) {
			double componenteEntropia = PX.elementAt(i) * (Math.log10(PX.elementAt(i)) / Math.log10(10));
			entropia += componenteEntropia * (-1);
		}
		return entropia;
	  }

		public static void main(String[] args) {
			// TODO Auto-generated method stub
	
		}
		
		
		public BufferedImage getBloqueMinEntropia(Vector<BufferedImage> v) {
			BufferedImage bloqueSalida = null;
			double entropiaMin = 0;
			for(BufferedImage bloque: v) {
				double entropiaActual = this.getEntropiaConMemoria(bloque);
				if(entropiaActual < entropiaMin) {
					bloqueSalida = bloque;
				}
			}
			return bloqueSalida;
		}		
		
		public BufferedImage getBloqueMaxEntropia(Vector<BufferedImage> v) {
			BufferedImage bloqueSalida = null;
			double entropiaMax = 0;
			for(BufferedImage bloque: v) {
				double entropiaActual = this.getEntropiaConMemoria(bloque);
				if(entropiaActual > entropiaMax) {
					bloqueSalida = bloque;
				}
			}
			return bloqueSalida;
		}

}

