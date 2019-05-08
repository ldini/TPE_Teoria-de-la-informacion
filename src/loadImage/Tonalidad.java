package loadImage;

public class Tonalidad {
	int tonalidadGris;
	int ocurrencias;
	
	public Tonalidad(int tonalidad, int ocurrencias) {
		this.tonalidadGris = tonalidad;
		this.ocurrencias = ocurrencias;
	}
	
	public int getOcurrencias() {
		return ocurrencias;
	}
	
	public int getTonalidadGris() {
		return tonalidadGris;
	}

	public void add1() {
		this.ocurrencias++;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
