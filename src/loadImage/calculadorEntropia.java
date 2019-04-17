package loadImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Set;

public class calculadorEntropia {
	
	public float getProbabilidad(BufferedImage imagen) {
		
		
		return 0;
	}
	
	public Hashtable<Integer, Integer> getOcurrencias( BufferedImage imagen) {
		Hashtable<Integer, Integer> salida = new Hashtable<Integer, Integer>();
		
		for(int i = 0; i < imagen.getWidth(); i++)
			for(int j = 0; j < imagen.getHeight(); j++) {
				int rgb = imagen.getRGB(i, j);
				Color color = new Color(rgb, true);
				int tonalidad = color.getRed();
				if( !salida.containsKey(tonalidad)) {
					salida.put(tonalidad, 1);
				}
				else {
					salida.put(tonalidad, salida.get(tonalidad) + 1);
				}
			}
		
		return salida;
	}
	
	public double getEntropiaSinMemoria(BufferedImage imagen) {
		Hashtable<Integer, Integer> ocurrencias = getOcurrencias(imagen);
		double entropia = 0;
		Set<Integer> claves = ocurrencias.keySet();
		
		for(Integer key: claves) {
			double cantidad = ocurrencias.get(key);
			double probabilidad = (cantidad) / (500 * 500);// ARREGLAR PARA QUE NO QUEDE HARDCODEADO
			double componenteEntropia = (probabilidad) * (Math.log10(probabilidad) / Math.log10(10));
			entropia += componenteEntropia * (-1);
		}		
		return entropia;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
