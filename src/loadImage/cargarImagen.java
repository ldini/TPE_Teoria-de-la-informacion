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
	
	public Vector<BufferedImage> getBloques(BufferedImage img) {
		Vector<BufferedImage> bloques = new Vector<BufferedImage>();
		for(int i = 0; i+500 < img.getHeight(); i += 500 ) {
			for(int j = 0; j+500 < img.getWidth(); j += 500) {
				BufferedImage bloque = img.getSubimage(i, j, 500, 500);
				bloques.add(bloque);
			}
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
			url = new URL("https://raw.githubusercontent.com/ldini/TPE_Teoria-de-la-informacion/master/bin/loadImage/marsSurface.bmp");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage img = c.cargar(url);
		
		Vector<BufferedImage> bloques = c.getBloques(img);
		
		int i = 0;
		for(BufferedImage imagen: bloques) {
			System.out.println("Iteracion "+i);
			c.crearImagen(imagen,Integer.toString(i));
			i++;
		}

	}
}
