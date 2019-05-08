package loadImage;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.net.MalformedURLException;
import java.net.URL;

public class cargarImagen {
	
	
	
	public BufferedImage cargar(URL path) {
		BufferedImage img = null;

		try 
		{
		    img = ImageIO.read(path); 
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		return img;
	}
	
	public Vector<BufferedImage> getBloques(BufferedImage img, int height, int width) {
		Vector<BufferedImage> bloques = new Vector<BufferedImage>();
		int i = 0; 
		int j = 0;
		
		while( i + height <= img.getHeight() ) {
			j = 0;
			
			while( j + width <= img.getWidth()) {
				
				BufferedImage bloque = img.getSubimage(j, i, width, height);
				bloques.add(bloque);
				j += 500;
			}
			
			i += 500;
		}
		
		return bloques;
	}
	
	public void crearImagen(BufferedImage img, String i) {
		File outputfile = new File("image"+i+".bmp");
		try {
			ImageIO.write(img, "bmp", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		cargarImagen c = new cargarImagen();
	
		
		URL url = null;
		try {
			System.out.println("downloading image....");
			url = new URL("https://raw.githubusercontent.com/ldini/TPE_Teoria-de-la-informacion/master/marsSurface.bmp");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage img = c.cargar(url);
		
		Vector<BufferedImage> bloques = c.getBloques(img, 500, 500);
		
		calculadorEntropia calculador = new calculadorEntropia();
		
		int i = 0;
		for(BufferedImage imagen: bloques) {
			System.out.println("Iteracion "+i);
			c.crearImagen(imagen,Integer.toString(i));
			System.out.println("CON MEMORIA = "+calculador.getEntropiaConMemoria(imagen));
			System.out.println("SIN MEMORIA = "+calculador.getEntropiaSinMemoria(imagen));
			i++;
		}
		
		BufferedImage mayor = calculador.getBloqueMaxEntropia(bloques);
		BufferedImage menor = calculador.getBloqueMinEntropia(bloques);
		
		System.out.print("bloque mayor " + calculador.getEntropiaConMemoria(mayor));
		System.out.print("bloque menor " + calculador.getEntropiaConMemoria(menor));
		
		


	}
}
