import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GrafoMatriz {
    private int filas;
	private int columnas;
    private boolean bloqueado;
    private Nodo[][] matriz;
    private Random random;

    public GrafoMatriz(GrafoMatriz matriz){
        this.filas = matriz.getFilas();
        this.columnas = matriz.getColumnas();
        this.matriz = new Nodo[this.filas][this.columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.matriz[i][j] = new Nodo(matriz.getNodo(i, j).getNombre(),matriz.getNodo(i, j).getDistancia(),matriz.getNodo(i, j).getBloqueado(), (matriz.getNodo(i, j).getId()+1));
                this.matriz[i][j].setPosX(i);
                this.matriz[i][j].setPosY(j);
            }
        }
    }
    public GrafoMatriz(int filas,int columnas,int bloqueado) {
        random = new Random();
        int maxDistancia = 1000;
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new Nodo[this.filas][this.columnas];
       
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                matriz[i][j] = new Nodo( i+""+j,random.nextInt(maxDistancia+1));
                matriz[i][j].setPosX(i);
                matriz[i][j].setPosY(j);

               
            }
        }
        int numBloqueados =  (bloqueado * tamano()) /100;
        agregarBloqueos(numBloqueados);

    }
    public int getColumnas() {
        return this.columnas;
    }
    public int getFilas() {
        return this.filas;
    }
    public Nodo getNodo(int x,int y){
        return matriz[x][y];
    }
    public Nodo[][] getMatriz(){
        return matriz;
    }
    public int tamano() { // regresa el tamaño del tablero
		return (filas * columnas);
	}
    public void setEstado(Nodo nodo){
        this.matriz[nodo.getPosX()][nodo.getPosY()].setEstado(nodo.getEstado());
    }

    public void agregarBloqueos(int numBloqueos){
		if(numBloqueos == 0){
			return;
		}
		int filaRandom = random.nextInt(this.filas);
		int columnaRandom = random.nextInt(this.columnas);
		
		if(!matriz[filaRandom][columnaRandom].getBloqueado()){
			matriz[filaRandom][columnaRandom].setBloqueado(true);
			agregarBloqueos(numBloqueos - 1);
		}else{
			agregarBloqueos(numBloqueos);
		}
	}
    public void imprimirGrafo(){
        System.out.println(matriz.toString());
    }

    public String toString() { // regresa un string con toda la informacion del tablero
		
		
		String respuesta = "";
		respuesta += "\n";

		respuesta += "[ ]";
		for (int i = 0; i < filas; i++) {
			respuesta += " [" + i + "]";
		}
		respuesta += "\n";
		for (int i = 0; i < filas; i++) {
			respuesta += "[" + i + "] ";
			for (int j = 0; j < columnas; j++) {
				respuesta += matriz[i][j].toString();

			}
			respuesta += "\n";
		}

		return respuesta;
	}

    public String toString(boolean flag) { // regresa un string con toda la informacion del tablero
		
		
		String respuesta = "";
		respuesta += "\n";

		respuesta += "[ ]";
		for (int i = 0; i < filas; i++) {
			respuesta += " [" + i + "]";
		}
		respuesta += "\n";
		for (int i = 0; i < filas; i++) {
			respuesta += "[" + i + "] ";
			for (int j = 0; j < columnas; j++) {
				respuesta += matriz[i][j].toString(flag);

			}
			respuesta += "\n";
		}

		return respuesta;
	}
    public String toString(boolean flag, Stack<Nodo> optimalPath) {
        String respuesta = "";
        respuesta += "\n";

        respuesta += "[ ]";
        for (int i = 0; i < filas; i++) {
            respuesta += " [" + i + "]";
        }
        respuesta += "\n";
        for (int i = 0; i < filas; i++) {
            respuesta += "[" + i + "] ";
            for (int j = 0; j < columnas; j++) {
                Nodo current = matriz[i][j];
                if (current.getBloqueado()) {
                    respuesta += " ❌";
                } else {
                    if (flag && optimalPath.contains(current)) {
                        respuesta += " ✅"; // Highlight optimal path
                    } else {
                        switch (current.getEstado().toString()) {
                            case "NOVISITADO":
                                respuesta += " " + current.getPosX() + "" + current.getPosY();
                                break;
                            case "ABIERTO":
                                respuesta += " AB";
                                break;
                            case "CERRADO":
                                respuesta += " CE";
                                break;
                        }
                    }
                }
            }
            respuesta += "\n";
        }

        return respuesta;
    }

    public Nodo nodoInicial(){
        Nodo inicial = getNodo(0,0);
        inicial.setBloqueado(false);
        return inicial;
    }
    public Nodo nodoFinal(){
        Nodo finalNodo = getNodo(filas-1, columnas-1);
        finalNodo.setBloqueado(false);
        return finalNodo;
    }
    public Nodo nodoCentral(){
        Nodo central = getNodo(filas/2, columnas/2);
        central.setBloqueado(false);
        return central;
    }

   
    

   
}
